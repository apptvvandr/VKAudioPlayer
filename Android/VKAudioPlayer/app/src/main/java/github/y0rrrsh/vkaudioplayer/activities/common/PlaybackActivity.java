package github.y0rrrsh.vkaudioplayer.activities.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import github.y0rrrsh.vkaudioplayer.App;
import github.y0rrrsh.vkaudioplayer.AudioPlayer;
import github.y0rrrsh.vkaudioplayer.AudioPlayerItem;

/**
 * @author Artur Yorsh
 */
public abstract class PlaybackActivity extends BaseActivity {

    protected AudioPlayer player = AudioPlayer.getInstance(App.getContext());
    private PlaybackReceiver playbackReceiver = new PlaybackReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int progress = player.getProgress();
                onProgressUpdated(progress);
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(playbackReceiver, playbackReceiver.filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(playbackReceiver);
    }

    protected void onProgressUpdated(int progress){
    }

    protected void onStartPlaying(AudioPlayerItem currentItem) {
    }

    protected void onStopPlaying(int stopSeconds) {
    }

    private class PlaybackReceiver extends BroadcastReceiver {

        public IntentFilter filter = new IntentFilter();

        public PlaybackReceiver() {
            filter.addAction(AudioPlayer.ACTION_PLAY);
            filter.addAction(AudioPlayer.ACTION_STOP);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (AudioPlayer.ACTION_PLAY.equals(action)) {
                AudioPlayerItem currentItem = player.getCurrentItem();
                onStartPlaying(currentItem);
                return;
            }
            if (AudioPlayer.ACTION_STOP.equals(action)) {
                onStopPlaying(player.getProgress());
            }
        }

    }
}
