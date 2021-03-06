package uk.co.lewiscook.ambientnoises;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static uk.co.lewiscook.ambientnoises.App.CHANNEL_1_ID;

public class AudioActivity extends AppCompatActivity {

    public static AudioManager mAudioManager;

    private AudioManager audioManager = null;

    public static NotificationManagerCompat notificationManager;

    //Static MediaPlayer so all classes can access it
    public static PerfectLoopMediaPlayer mMediaPlayer;

    public static CountDownTimer countDown;

    int AudioResID;

    TextView tView;
    public static TextView countdownnView;
    public static TextView countdownnView2;
    public static TextView countdowntView;
    public static TextView countdownHMView;

    //Release the MediaPlayer and abandon AudioFocus
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {

            mMediaPlayer.release();

            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mAudioFocusChange);
        }
    }

    //Code for the notification and method is called once MediaPlayer is started by button push
    public void sendNotification() {

        Intent OpenApp = new Intent(this, MainActivity.class);
        PendingIntent open = PendingIntent.getActivity(this, 0, OpenApp, 0);

        Intent PlayMusic = new Intent(this, PlayReceiver.class);
        PendingIntent play = PendingIntent.getBroadcast(this, 0, PlayMusic, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent PauseMusic = new Intent(this, PauseReceiver.class);
        PendingIntent pause = PendingIntent.getBroadcast(this, 0, PauseMusic, PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap thumbNail = BitmapFactory.decodeResource(getResources(), R.drawable.ambient_noise_ic);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//                .addAction(R.drawable.ic_play, "Play", play)
                .addAction(R.drawable.ic_stop, "Pause", pause)
                .setContentIntent(open)
                .setLargeIcon(thumbNail)
                .setSmallIcon(R.drawable.ic_music)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(1, notification);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initControls();

        countdowntView = findViewById(R.id.countdown_TextView);
        countdownnView = findViewById(R.id.countdown_NumView);
        countdownnView2 = findViewById(R.id.countdown_NumView2);
        countdownHMView = findViewById(R.id.countdownHM_TextView);

        notificationManager = NotificationManagerCompat.from(this);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //change the background with intent from main activity
        ImageView backGround = findViewById(R.id.starWars);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int ImageResID = bundle.getInt(MainActivity.IMAGE_BUNDLE_KEY);
            backGround.setImageResource(ImageResID);
            AudioResID = bundle.getInt(MainActivity.AUDIO_BUNDLE_KEY);
        }

        //Button that displays the dialog to set countdown timer
        ImageView popupButton = findViewById(R.id.pop_up);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AudioActivity.this);
                LayoutInflater inflater = AudioActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.popup_window, null);
                dialogBuilder.setView(dialogView);

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                tView = dialogView.findViewById(R.id.textField);
                final NumberPicker hourNumPicker = dialogView.findViewById(R.id.hour_Numb_Picker);
                final NumberPicker minNumPicker = dialogView.findViewById(R.id.min_Numb_Picker);

                hourNumPicker.setMinValue(0);
                hourNumPicker.setMaxValue(9);
                hourNumPicker.setWrapSelectorWheel(true);

                minNumPicker.setMinValue(0);
                minNumPicker.setMaxValue(60);
                minNumPicker.setWrapSelectorWheel(true);


                //Button that displays the dialog to set countdown timer
                ImageView start = dialogView.findViewById(R.id.start_Button_Popup);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        countdowntView.setVisibility(View.VISIBLE);
                        countdownHMView.setVisibility(View.VISIBLE);

                        final int hours = hourNumPicker.getValue();
                        final int minutes = minNumPicker.getValue();
                        int hoursAndMinutes = hours + minutes;

                        if (hoursAndMinutes == 0){
                            textViewVisibilityInVis();
                            tView.setVisibility(View.VISIBLE);
                            tView.setText(R.string.set_timer_value);
                            return;
                        }

                        cancelCountdownTimer();
                        startMediaPlayerInDialog();

                        long timerLengthInHours = Long.parseLong(String.valueOf(hours));
                        timerLengthInHours = timerLengthInHours * 60 * 60 * 1000;

                        long timerLengthInMinutes = Long.parseLong(String.valueOf(minutes));
                        timerLengthInMinutes = timerLengthInMinutes * 60 * 1000;

                        long allTime = timerLengthInHours + timerLengthInMinutes;

                        countDown = new CountDownTimer(allTime, 1000) {

                            public void onTick(long millisUntilFinished) {
                                showRemainingTime(millisUntilFinished);
                            }

                            public void onFinish() {
                                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                                    tView.setText(R.string.timer_finished);
                                    mMediaPlayer.stop();
                                    releaseMediaPlayer();
                                    textViewVisibilityInVis();
                                    notificationManager.cancelAll();
                                }
                            }
                        }.start();
                    }
                });

                ImageView stop = dialogView.findViewById(R.id.stop_Button_Popup);
                stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stopMediaPlayer();
                        tView.setVisibility(View.INVISIBLE);
                        textViewVisibilityInVis();
                    }
                });
            }
        });

        //Button that plays audio, contains method call for notification
        ImageView playButton = findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelCountdownTimer();
                startMediaPlayer();
            }
        });

        //Button that stops audio, contains method call for releasing MediaPlayer and also cancelling the notification
        ImageView pauseButton = findViewById(R.id.pause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMediaPlayer();
            }
        });
    }

    //Setting what happens at different times when audio focus changes
    AudioManager.OnAudioFocusChangeListener mAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }

    };

    //For changing the seek bar to change volume
    private void initControls() {
        try {
            SeekBar volumeSeekbar = findViewById(R.id.seekBarVolume);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelCountdownTimer() {
        if (countDown != null) {
            countDown.cancel();
        }
    }

    @Override
    public void finish() {
        super.finish();
        stopMediaPlayer();
    }

    public void textViewVisibilityInVis() {
        countdownnView.setVisibility(View.INVISIBLE);
        countdownnView2.setVisibility(View.INVISIBLE);
        countdowntView.setVisibility(View.INVISIBLE);
        countdownHMView.setVisibility(View.INVISIBLE);
    }

    public void stopMediaPlayer() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            releaseMediaPlayer();
            cancelCountdownTimer();
            textViewVisibilityInVis();
            notificationManager.cancelAll();
        }
    }

    public void startMediaPlayer() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            releaseMediaPlayer();
            textViewVisibilityInVis();
            mMediaPlayer = PerfectLoopMediaPlayer.create(AudioActivity.this, AudioResID);
            mMediaPlayer.start();
            sendNotification();
        } else {
            releaseMediaPlayer();
            int result = mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                textViewVisibilityInVis();
                mMediaPlayer = PerfectLoopMediaPlayer.create(AudioActivity.this, AudioResID);
                mMediaPlayer.start();
                sendNotification();
            }
        }
    }

    public void startMediaPlayerInDialog() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            releaseMediaPlayer();
            mMediaPlayer = PerfectLoopMediaPlayer.create(AudioActivity.this, AudioResID);
            mMediaPlayer.start();
            sendNotification();
        } else {
            releaseMediaPlayer();
            int result = mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMediaPlayer = PerfectLoopMediaPlayer.create(AudioActivity.this, AudioResID);
                mMediaPlayer.start();
                sendNotification();
            }
        }
    }

    //Show the remaining time
    public void showRemainingTime(long millisUntilFinished) {
        long hoursRemaining = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
        long totalMinutesRemaining = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
        long totalSecondsRemaining = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
        long minutesRemaining = totalMinutesRemaining % 60;
        long secondsRemaining = totalSecondsRemaining % 60;

        String outputString = String.format(Locale.getDefault(),getString(R.string.remaining_time) + "\nH: %d M: %d S: %d", hoursRemaining, minutesRemaining, secondsRemaining);
        String simpleTimeOutput = String.format(Locale.getDefault(), "%d:%d", hoursRemaining, minutesRemaining);
        String simpleTimeOutputSeconds = String.format(Locale.getDefault(), ":%d", secondsRemaining);


        countdownnView.setText(simpleTimeOutput);
        countdownnView2.setText(simpleTimeOutputSeconds);
        tView.setText(outputString);
        tView.setVisibility(View.VISIBLE);
        countdownnView.setVisibility(View.VISIBLE);
        countdownnView2.setVisibility(View.VISIBLE);
    }

}