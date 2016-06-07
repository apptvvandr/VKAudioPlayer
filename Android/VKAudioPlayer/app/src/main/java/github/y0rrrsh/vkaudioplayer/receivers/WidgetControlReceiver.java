package github.y0rrrsh.vkaudioplayer.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import github.y0rrrsh.streamplayer.StreamPlayer;

/**
 * @author Artur Yorsh. 01.06.16.
 */
public class WidgetControlReceiver extends BroadcastReceiver {

    public static final String ACTION_PLAY = "github.y0rrrsh.vkaudioplayer.widget.PLAY";
    public static final String ACTION_NEXT = "github.y0rrrsh.vkaudioplayer.widget.NEXT";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        StreamPlayer player = StreamPlayer.getInstance(context);

        if (ACTION_PLAY.equals(action)) {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.play();
            }
            return;
        }
        if (ACTION_NEXT.equals(action)) {
            player.playNext();
        }
    }
}
