package github.y0rrrsh.vkaudioplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artur Yorsh
 */
public class AudioPlayer {

    public static final String ACTION_START = "github.y0rrrsh.vkaudioplayer.START";
    public static final String ACTION_PAUSE = "github.y0rrrsh.vkaudioplayer.PAUSE";
    public static final String ACTION_COMPLETE = "github.y0rrrsh.vkaudioplayer.COMPLETE";

    private static AudioPlayer instance;
    private Context context;

    private int currentItemPosition;
    private AudioPlayerItem currentItem;
    private List<? extends AudioPlayerItem> playlist = new ArrayList<>();
    private MediaPlayer player;

    private AudioPlayer(Context context) {
        this.context = context;
        player = new MediaPlayer();

        player.setOnPreparedListener(mp -> {
            broadcast(ACTION_START);
            player.start();
        });
        player.setOnCompletionListener(mp -> {
            if (player.getCurrentPosition() == 0)
                return; // FIXME: 01.06.16 because called sometimes before onPrepared
            broadcast(ACTION_COMPLETE);
        });
        player.setOnBufferingUpdateListener((mp, percent) -> {
            // TODO: 5/31/2016 cache control
        });
        player.setOnSeekCompleteListener(mp -> play());

        instance = this;
    }

    public static AudioPlayer getInstance(Context context) {
        return instance != null ? instance : new AudioPlayer(context);
    }

    //Stream control

    public void play() {
        if (currentItem == null) {
            play(0);
            return;
        }
        broadcast(ACTION_START);
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

    private void setCurrentItem(AudioPlayerItem item) {
        try {
            player.reset();
            player.setDataSource(item.getUrl());
            player.prepareAsync();
            currentItem = item;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        player.pause();
        broadcast(ACTION_PAUSE);
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void seekTo(int seconds) {
        player.pause();
        player.seekTo(seconds * 1000);
    }

    public int getProgress() {
        return player.getCurrentPosition() / 1000;
    }

    public int getItemDuration() {
        return player.getDuration() / 1000;
    }

    private void broadcast(String action) {
        context.sendBroadcast(new Intent(action));
    }

    public List<? extends AudioPlayerItem> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<? extends AudioPlayerItem> playlist) {
        this.playlist = new ArrayList<>(playlist);
    }

    public AudioPlayerItem getCurrentItem() {
        return currentItem;
    }

    public int getCurrentItemPosition() {
        return currentItemPosition;
    }

    public interface AudioPlayerItem {
        String getUrl();

        long getDuration();
    }
}
