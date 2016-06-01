package github.y0rrrsh.vkaudioplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Random;

import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;

/**
 * @author Artur Yorsh
 */
public class PlaybackReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (AudioPlayer.ACTION_COMPLETE.equals(action)) {
            AudioPlayer player = AudioPlayer.getInstance(context);
            if (VKAPPreferences.isRepeatEnabled(context)) {
                player.play();
                return;
            }
            player.playNext();
        }
    }
}
