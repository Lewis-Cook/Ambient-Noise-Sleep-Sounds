package uk.co.lewiscook.ambientnoises;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static uk.co.lewiscook.ambientnoises.AudioActivity.notificationManager;

public class PauseReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (AudioActivity.mMediaPlayer != null && AudioActivity.mMediaPlayer.isPlaying()) {
            AudioActivity.mMediaPlayer.pause();
            notificationManager.cancelAll();
        }
    }
}