package github.y0rrrsh.streamplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Artur Yorsh. 02.06.16.
 */
public abstract class BasePlayerReceiver extends BroadcastReceiver {

    public static final String ACTION_SELECT = "github.y0rrrsh.vkaudioplayer.SELECT";
    public static final String ACTION_START = "github.y0rrrsh.vkaudioplayer.START";
    public static final String ACTION_PAUSE = "github.y0rrrsh.vkaudioplayer.PAUSE";
    public static final String ACTION_COMPLETE = "github.y0rrrsh.vkaudioplayer.COMPLETE";
    public static final String ACTION_BUFFER_UPDATE = "github.y0rrrsh.vkaudioplayer.BUFFER";

    public static final String EXTRA_BUFFER_PERCENT = "player_buffer_percent";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (ACTION_SELECT.equals(action)) {
            onPlayerItemSelected(context);
            return;
        }
        if (ACTION_START.equals(action)) {
            onStartPlaying(context);
            return;
        }
        if (ACTION_PAUSE.equals(action)) {
            onPausePlaying(context);
            return;
        }
        if (ACTION_COMPLETE.equals(action)) {
            onCompletePlaying(context);
            return;
        }
        if (ACTION_BUFFER_UPDATE.equals(action)) {
            int percent = intent.getIntExtra(EXTRA_BUFFER_PERCENT, 0);
            onBufferUpdated(context, percent / 100.0f);
        }
    }

    protected abstract void onPlayerItemSelected(Context context);

    protected abstract void onStartPlaying(Context context);

    protected abstract void onPausePlaying(Context context);

    protected abstract void onCompletePlaying(Context context);

    protected abstract void onBufferUpdated(Context context, float percent);
}
