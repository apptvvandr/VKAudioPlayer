package github.y0rrrsh.vkaudioplayer.activities.common;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import github.y0rrrsh.vkaudioplayer.App;
import github.y0rrrsh.vkaudioplayer.AudioPlayer;
import github.y0rrrsh.vkaudioplayer.AudioPlayer.AudioPlayerItem;
import github.y0rrrsh.vkaudioplayer.receivers.common.AudioPlayerReceiver;

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

    protected void onProgressUpdated(int progress) {
    }

    protected void onStartPlaying(AudioPlayerItem currentItem) {
    }

    protected void onPausePlaying() {
    }

    protected void onCompletePlaying() {
    }

    private class PlaybackReceiver extends AudioPlayerReceiver {

        public IntentFilter filter = new IntentFilter();

        public PlaybackReceiver() {
            filter.addAction(AudioPlayer.ACTION_START);
            filter.addAction(AudioPlayer.ACTION_PAUSE);
            filter.addAction(AudioPlayer.ACTION_COMPLETE);
        }

        @Override
        protected void onPlayerStart(Context context) {
            AudioPlayerItem currentItem = player.getCurrentItem();
            onStartPlaying(currentItem);
        }

        @Override
        protected void onPlayerPause(Context context) {
            onPausePlaying();
        }

        @Override
        protected void onPlayerComplete(Context context) {
            onCompletePlaying();
        }
    }
}
