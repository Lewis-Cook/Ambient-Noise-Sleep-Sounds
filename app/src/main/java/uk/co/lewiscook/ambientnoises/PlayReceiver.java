package uk.co.lewiscook.ambientnoises;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PlayReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Boolean isPaused = !MainActivity.mMediaPlayer.isPlaying();

        if (isPaused) {
            MainActivity.mMediaPlayer.start();
        }
    }
}