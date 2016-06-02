package github.y0rrrsh.vkaudioplayer.receivers.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import github.y0rrrsh.vkaudioplayer.AudioPlayer;

/**
 * @author Artur Yorsh. 02.06.16.
 */
public abstract class AudioPlayerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(AudioPlayer.ACTION_START)) {
            onPlayerStart(context);
            return;
        }
        if (action.equals(AudioPlayer.ACTION_PAUSE)) {
            onPlayerPause(context);
            return;
        }
        if (action.equals(AudioPlayer.ACTION_COMPLETE)) {
            onPlayerComplete(context);
            return;
        }
        if (AudioPlayer.ACTION_BUFFER_UPDATE.equals(action)) {
            int percent = intent.getIntExtra(AudioPlayer.EXTRA_BUFFER_PERCENT, 0);
            onPlayerBufferUpdate(context, percent / 100.0f);
        }
    }

    protected abstract void onPlayerStart(Context context);

    protected abstract void onPlayerPause(Context context);

    protected abstract void onPlayerComplete(Context context);

    protected abstract void onPlayerBufferUpdate(Context context, float percent);
}
