package uk.co.lewiscook.ambientnoises;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import static uk.co.lewiscook.ambientnoises.AudioActivity.countDown;
import static uk.co.lewiscook.ambientnoises.AudioActivity.countdownHMView;
import static uk.co.lewiscook.ambientnoises.AudioActivity.countdownnView;
import static uk.co.lewiscook.ambientnoises.AudioActivity.countdownnView2;
import static uk.co.lewiscook.ambientnoises.AudioActivity.countdowntView;
import static uk.co.lewiscook.ambientnoises.AudioActivity.notificationManager;

public class PauseReceiver extends BroadcastReceiver {

    public void textViewVisibilityInVis() {
        countdownnView.setVisibility(View.INVISIBLE);
        countdownnView2.setVisibility(View.INVISIBLE);
        countdowntView.setVisibility(View.INVISIBLE);
        countdownHMView.setVisibility(View.INVISIBLE);
    }

    public void cancelCountdownTimer() {
        if (countDown != null) {
            countDown.cancel();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (AudioActivity.mMediaPlayer != null && AudioActivity.mMediaPlayer.isPlaying()) {
            AudioActivity.mMediaPlayer.pause();
            cancelCountdownTimer();
            textViewVisibilityInVis();
            notificationManager.cancelAll();
        }
    }
}