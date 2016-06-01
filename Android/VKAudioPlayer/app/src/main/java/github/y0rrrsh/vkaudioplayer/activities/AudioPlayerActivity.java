package github.y0rrrsh.vkaudioplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import github.y0rrrsh.playbackcontrolview.PlaybackControlView;
import github.y0rrrsh.playbackcontrolview.PlaybackControlView.PlaybackActionHandler;
import github.y0rrrsh.vkaudioplayer.AudioPlayer.AudioPlayerItem;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.PlaybackActivity;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi.VkCallback;

import static java.util.Collections.shuffle;

public class AudioPlayerActivity extends PlaybackActivity implements PlaybackActionHandler {

    private static final String ARG_PLAYLIST = "audio_playlist";
    private static final String ARG_START_POSITION = "playlist_start_position";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.control_player) PlaybackControlView playbackControlView;
    @BindView(R.id.text_player_artist) TextView textArtist;
    @BindView(R.id.text_player_name) TextView textName;
    @BindView(R.id.btn_player_add) ImageButton btnAdd;
    @BindView(R.id.btn_player_remove) ImageButton btnRemove;
    @BindView(R.id.image_player_cover) ImageView imageCover;

    private List<AudioModel> playlist;

    private VKAPService api = VKApi.getApiService();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_audio_player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent starter = getIntent();
        playlist = starter.getParcelableArrayListExtra(ARG_PLAYLIST);
        int startPosition = starter.getIntExtra(ARG_START_POSITION, 0);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        playbackControlView.setActionHandler(this);

        ArrayList<AudioModel> playerList = new ArrayList<>(playlist);
        player.setPlaylist(playerList);
        player.play(startPosition);

        if (VKAPPreferences.isShuffleEnabled(this)) {
            shuffle(player.getPlaylist());
            playbackControlView.btnShuffle.setColorFilter(getResources().getColor(R.color.colorAccent));
        }
        if (VKAPPreferences.isRepeatEnabled(this)) {
            playbackControlView.btnRepeat.setColorFilter(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioPlayerItem currentItem = player.getCurrentItem();
        setTrackInfo(currentItem);
    }

    @OnClick(R.id.btn_player_add)
    protected void onAddClicked() {
        AudioModel currentAudio = (AudioModel) player.getCurrentItem();
        api.addAudio(currentAudio.getId(), currentAudio.getOwnerId(), new VkCallback<Integer>() {
            @Override
            public void onResponse(Integer response) {
                String audioInfo = String.format("%s - %s", currentAudio.getArtist(), currentAudio.getName());
                Toast.makeText(AudioPlayerActivity.this, audioInfo + " was added to your page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

    @OnClick(R.id.btn_player_remove)
    protected void onRemoveClicked() {
        AudioModel currentAudio = (AudioModel) player.getCurrentItem();
        api.removeAudio(currentAudio.getId(), currentAudio.getOwnerId(), new VkCallback<Integer>() {
            @Override
            public void onResponse(Integer response) {
                String audioInfo = String.format("%s - %s", currentAudio.getArtist(), currentAudio.getName());
                Toast.makeText(AudioPlayerActivity.this, audioInfo + " was removed from your page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

    @Override
    public void onPreviousClicked() {
        player.playPrevious();
        setTrackInfo(player.getCurrentItem());
    }

    @Override
    public void onPlayClicked() {
        if (player.isPlaying()) {
            player.pause();
        } else {
            player.play();
        }
    }

    @Override
    public void onNextClicked() {
        player.playNext();
        setTrackInfo(player.getCurrentItem());
    }

    @Override
    public void onShuffleClicked() {
        boolean shuffleEnabled = VKAPPreferences.isShuffleEnabled(this);
        VKAPPreferences.setShuffleEnabled(this, !shuffleEnabled);
        if (!shuffleEnabled) {
            shuffle(player.getPlaylist());
            // TODO: 01.06.16 replace in playback view selector
            playbackControlView.btnShuffle.setColorFilter(getResources().getColor(R.color.colorAccent));
        } else {
            player.setPlaylist(new ArrayList<>(playlist));
            playbackControlView.btnShuffle.clearColorFilter();
        }
    }

    @Override
    public void onRepeatClicked() {
        boolean repeatEnabled = VKAPPreferences.isRepeatEnabled(this);
        VKAPPreferences.setRepeatEnabled(this, !repeatEnabled);

        // TODO: 01.06.16 replace in playback view selector
        if (!repeatEnabled) {
            playbackControlView.btnRepeat.setColorFilter(getResources().getColor(R.color.colorAccent));
        } else {
            playbackControlView.btnRepeat.clearColorFilter();
        }
    }

    @Override
    public void onSeekDragged(int currentValue) {
        player.seekTo(currentValue);
    }

    @Override
    protected void onStartPlaying(AudioPlayerItem currentItem) {
        setTrackInfo(currentItem);
        Drawable pauseImage = getResources().getDrawable(R.drawable.ic_pause_black_24dp);
        playbackControlView.btnPlay.setImageDrawable(pauseImage);
        playbackControlView.seekProgress.setMax((int) currentItem.getDuration());
    }

    @Override
    protected void onPausePlaying() {
        Drawable playImage = getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp);
        playbackControlView.btnPlay.setImageDrawable(playImage);
    }

    @Override
    protected void onProgressUpdated(int progress) {
        playbackControlView.seekProgress.setProgress(progress);
    }

    private void setTrackInfo(AudioPlayerItem currentItem) {
        textArtist.setText(currentItem.getArtist());
        textName.setText(currentItem.getName());
    }

    public static void start(Context context, List<AudioModel> playlist, int startPosition) {
        Intent starter = new Intent(context, AudioPlayerActivity.class)
                .putParcelableArrayListExtra(ARG_PLAYLIST, (ArrayList<? extends Parcelable>) playlist)
                .putExtra(ARG_START_POSITION, startPosition);
        context.startActivity(starter);
    }
}
