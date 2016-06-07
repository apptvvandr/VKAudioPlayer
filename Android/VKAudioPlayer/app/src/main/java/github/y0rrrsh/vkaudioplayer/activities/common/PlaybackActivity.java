package github.y0rrrsh.vkaudioplayer.activities.common;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import github.y0rrrsh.streamplayer.BasePlayerReceiver;
import github.y0rrrsh.streamplayer.StreamPlayer;
import github.y0rrrsh.streamplayer.StreamPlayer.StreamItem;
import github.y0rrrsh.vkaudioplayer.VKAPApplication;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;

/**
 * @author Artur Yorsh
 */
public abstract class PlaybackActivity extends BaseActivity {

    protected static StreamPlayer player = StreamPlayer.getInstance(VKAPApplication.getContext());
    private PlaybackReceiver playbackReceiver = new PlaybackReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int progress = player.getProgress();
                onProgressChanged(progress);
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

    protected void onProgressChanged(int progress) {
    }

    protected void onPlayerItemSelected(AudioModel currentItem, int position) {
    }

    protected void onStartPlaying(AudioModel currentItem) {
    }

    protected void onPausePlaying() {
    }

    protected void onCompletePlaying() {
    }

    protected void onBufferUpdated(float percent) {
    }


    private class PlaybackReceiver extends BasePlayerReceiver {

        public IntentFilter filter = new IntentFilter();

        public PlaybackReceiver() {
            filter.addAction(BasePlayerReceiver.ACTION_SELECT);
            filter.addAction(BasePlayerReceiver.ACTION_START);
            filter.addAction(BasePlayerReceiver.ACTION_PAUSE);
            filter.addAction(BasePlayerReceiver.ACTION_COMPLETE);
            filter.addAction(BasePlayerReceiver.ACTION_BUFFER_UPDATE);
        }

        @Override
        protected void onPlayerItemSelected(Context context) {
            PlaybackActivity.this.onPlayerItemSelected((AudioModel) player.getCurrentItem(), player.getCurrentItemPosition());
        }

        @Override
        protected void onStartPlaying(Context context) {
            StreamItem currentItem = player.getCurrentItem();
            PlaybackActivity.this.onStartPlaying((AudioModel) currentItem);
        }

        @Override
        protected void onPausePlaying(Context context) {
            PlaybackActivity.this.onPausePlaying();
        }

        @Override
        protected void onCompletePlaying(Context context) {
            PlaybackActivity.this.onCompletePlaying();
        }

        @Override
        protected void onBufferUpdated(Context context, float percent) {
            PlaybackActivity.this.onBufferUpdated(percent);
        }
    }
}
