package uk.co.lewiscook.ambientnoises;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PauseReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (MainActivity.mMediaPlayer != null && MainActivity.mMediaPlayer.isPlaying()) {
            MainActivity.mMediaPlayer.pause();
        }
    }
}