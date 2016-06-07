package github.y0rrrsh.streamplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artur Yorsh
 */
public class StreamPlayer {

    private static StreamPlayer instance;
    private Context context;

    private int currentItemPosition;
    private StreamItem currentItem;
    private List<? extends StreamItem> playlist = new ArrayList<>();
    private MediaPlayer player = new MediaPlayer();

    private StreamPlayer(final Context context) {
        this.context = context;

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                context.sendBroadcast(new Intent(BasePlayerReceiver.ACTION_START));
                player.start();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (player.getCurrentPosition() == 0)
                    return; // FIXME: 01.06.16 because called sometimes before onPrepared
                context.sendBroadcast(new Intent(BasePlayerReceiver.ACTION_COMPLETE));
            }
        });
        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Intent intent = new Intent(BasePlayerReceiver.ACTION_BUFFER_UPDATE);
                intent.putExtra(BasePlayerReceiver.EXTRA_BUFFER_PERCENT, percent);
                context.sendBroadcast(intent);
            }
        });
        player.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                StreamPlayer.this.play();
            }
        });

        instance = this;
    }

    public static StreamPlayer getInstance(Context context) {
        return instance != null ? instance : new StreamPlayer(context);
    }

    //Stream control

    private void setCurrentItem(StreamItem item) {
        try {
            player.reset();
            player.setDataSource(item.getUrl());
            player.prepareAsync();
            currentItem = item;
            context.sendBroadcast(new Intent(BasePlayerReceiver.ACTION_SELECT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (currentItem == null) {
            play(0);
            return;
        }
        context.sendBroadcast(new Intent(BasePlayerReceiver.ACTION_START));
        player.start();
    }

    public void play(int playlistPosition) {
        if (playlist.isEmpty()) return;

        currentItemPosition = playlistPosition;
        setCurrentItem(playlist.get(currentItemPosition));
    }

    public void playPrevious() {
        int position = currentItemPosition == 0 ? playlist.size() - 1 : currentItemPosition - 1;
        play(position);
    }

    public void playNext() {
        int position = currentItemPosition == playlist.size() - 1 ? 0 : currentItemPosition + 1;
        play(position);
    }

    public void pause() {
        player.pause();
        context.sendBroadcast(new Intent(BasePlayerReceiver.ACTION_PAUSE));
    }

    public void seekTo(int seconds) {
        player.pause();
        player.seekTo(seconds * 1000);
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public int getProgress() {
        return player.getCurrentPosition() / 1000;
    }

    public int getItemDuration() {
        return player.getDuration() / 1000;
    }

    public List<? extends StreamItem> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<? extends StreamItem> playlist) {
        this.playlist = new ArrayList<>(playlist);
    }

    public StreamItem getCurrentItem() {
        return currentItem;
    }

    public int getCurrentItemPosition() {
        return currentItemPosition;
    }


    public interface StreamItem {
        String getUrl();

        int getDuration();
    }
}
