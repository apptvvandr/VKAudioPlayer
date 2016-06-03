package github.y0rrrsh.vkaudioplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import github.y0rrrsh.vkaudioplayer.network.asynctask.CallbackTask;
import github.y0rrrsh.vkaudioplayer.network.asynctask.RetrieveAudioCoverTask;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi.VkCallback;

import static java.util.Collections.shuffle;

public class AudioPlayerActivity extends PlaybackActivity implements PlaybackActionHandler {

    public static final String ARG_START_POSITION = "playlist_start_position";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.control_player) PlaybackControlView playbackControlView;
    @BindView(R.id.text_player_artist) TextView textArtist;
    @BindView(R.id.text_player_name) TextView textName;
    @BindView(R.id.btn_playlist_edit) ImageButton btnPlaylistEdit;
    @BindView(R.id.image_player_cover) ImageView imageCover;

    private Bitmap defaultCover;
    private List<AudioModel> playlist;

    private VKAPService api = VKApi.getApiService();
//    private int managedAudioId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_audio_player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        playbackControlView.setActionHandler(this);
        defaultCover = BitmapFactory.decodeResource(getResources(), R.drawable.bg_cover_default);

        int shuffleTint = VKAPPreferences.isShuffleEnabled(this) ? R.color.colorAccent : android.R.color.black;
        int repeatTint = VKAPPreferences.isRepeatEnabled(this) ? R.color.colorAccent : android.R.color.black;
        playbackControlView.setShuffleButtonTintColor(shuffleTint);
        playbackControlView.setRepeatButtonTintColor(repeatTint);
    }

    @Override
    protected void onStart() {
        super.onStart();

        playlist = new ArrayList<>((List<AudioModel>) player.getPlaylist());

        Intent starter = getIntent();
        if (starter.hasExtra(ARG_START_POSITION)) {
            int startPosition = starter.getIntExtra(ARG_START_POSITION, 0);

            AudioPlayerItem currentItem = player.getCurrentItem();
            if (currentItem == null || !currentItem.equals(playlist.get(startPosition))) {
                player.play(startPosition);
            }
            if (VKAPPreferences.isShuffleEnabled(this)) {
                shuffle(player.getPlaylist());
            }

            starter.removeExtra(ARG_START_POSITION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int playButtonRes = player.isPlaying() ? R.drawable.ic_pause_black_24dp : R.drawable.ic_play_arrow_black_24dp;
        playbackControlView.setPlayButtonIcon(playButtonRes);
        playbackControlView.setSeekCurrentProgress(player.getProgress());
        playbackControlView.setSeekMaxProgress(player.getItemDuration());

//        setEditPlaylistIcon(VKAPUtils.canBeAddedOrRestored(this, (AudioModel) player.getCurrentItem()));
        setTrackInfo((AudioModel) player.getCurrentItem());
    }

    @OnClick(R.id.btn_playlist_edit)
    protected void onPlaylistEditClicked() {
//        AudioModel currentItem = (AudioModel) player.getCurrentItem();
//        if (!VKAPUtils.canBeAddedOrRestored(this, currentItem)) {
//            if (VKAPUtils.canBeRestored(this, currentItem)) {
//                restoreAudioRequest(currentItem);
//                return;
//            }
//            addAudioRequest(currentItem);
//        } else {
//            removeAudioRequest(currentItem);
//        }
    }

    private void addAudioRequest(final AudioModel currentAudio) {
        api.addAudio(currentAudio.getId(), currentAudio.getOwnerId(), new VkCallback<Integer>() {
            @Override
            public void onResponse(Integer response) {
                String audioInfo = String.format("%s - %s", currentAudio.getArtist(), currentAudio.getName());
                Toast.makeText(AudioPlayerActivity.this, audioInfo + " was added to your page", Toast.LENGTH_SHORT).show();
                btnPlaylistEdit.setImageResource(R.drawable.ic_playlist_minus_black_24dp);

//                VKAPPreferences.setManagedAudioId(AudioPlayerActivity.this, response);
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

//    private void removeAudioRequest(final AudioModel currentAudio) {
//        api.removeAudio(managedAudioId, VKApi.USER_ID, new VkCallback<Integer>() {
//            @Override
//            public void onResponse(Integer response) {
//                String audioInfo = String.format("%s - %s", currentAudio.getArtist(), currentAudio.getName());
//                Toast.makeText(AudioPlayerActivity.this, audioInfo + " was removed from your page", Toast.LENGTH_SHORT).show();
//                btnPlaylistEdit.setImageResource(R.drawable.ic_playlist_plus_black_24dp);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//            }
//        });
//    }

//    private void restoreAudioRequest(final AudioModel currentAudio) {
//        api.restoreAudio(managedAudioId, VKApi.USER_ID, new VkCallback<AudioDTO>() {
//            @Override
//            public void onResponse(AudioDTO response) {
//                String audioInfo = String.format("%s - %s", currentAudio.getArtist(), currentAudio.getName());
//                Toast.makeText(AudioPlayerActivity.this, audioInfo + " was added to your page", Toast.LENGTH_SHORT).show();
//                btnPlaylistEdit.setImageResource(R.drawable.ic_playlist_minus_black_24dp);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//            }
//        });
//    }

    @Override
    public void onPreviousClicked() {
        player.playPrevious();
        imageCover.setImageBitmap(defaultCover);
        setTrackInfo((AudioModel) player.getCurrentItem());
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
        imageCover.setImageBitmap(defaultCover);
        setTrackInfo((AudioModel) player.getCurrentItem());
    }

    @Override
    public void onShuffleClicked() {
        boolean shuffleEnabled = VKAPPreferences.isShuffleEnabled(this);
        VKAPPreferences.setShuffleEnabled(this, !shuffleEnabled);
        if (!shuffleEnabled) {
            shuffle(player.getPlaylist());
            playbackControlView.setShuffleButtonTintColor(R.color.colorAccent);
        } else {
            player.setPlaylist(playlist);
            playbackControlView.setShuffleButtonTintColor(android.R.color.black);
        }
    }

    @Override
    public void onRepeatClicked() {
        boolean repeatEnabled = VKAPPreferences.isRepeatEnabled(this);
        VKAPPreferences.setRepeatEnabled(this, !repeatEnabled);

        if (!repeatEnabled) {
            playbackControlView.setRepeatButtonTintColor(R.color.colorAccent);
        } else {
            playbackControlView.setRepeatButtonTintColor(android.R.color.black);
        }
    }

    @Override
    public void onSeekDragged(int currentValue) {
        player.seekTo(currentValue);
    }

    @Override
    protected void onStartPlaying(AudioPlayerItem currentItem) {
        AudioModel audio = (AudioModel) currentItem;

        playbackControlView.setPlayButtonIcon(R.drawable.ic_pause_black_24dp);
        playbackControlView.setSeekMaxProgress(player.getItemDuration());
//        setEditPlaylistIcon(VKAPUtils.canBeAddedOrRestored(this, audio));
        setTrackInfo(audio);

        RetrieveAudioCoverTask.retrieve(currentItem.getUrl(), new CallbackTask.Callback<Bitmap>() {
            @Override
            public void onResult(Bitmap result) {
                imageCover.setImageBitmap(result);
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    @Override
    protected void onPausePlaying() {
        playbackControlView.setPlayButtonIcon(R.drawable.ic_play_arrow_black_24dp);
    }

    @Override
    protected void onProgressUpdated(int progress) {
        playbackControlView.setSeekCurrentProgress(progress);
    }

    @Override
    protected void onBufferUpdated(float percent) {
        int bufferProgress = (int) (percent * player.getItemDuration());
        playbackControlView.setSeekSecondaryProgress(bufferProgress);
    }

    private void setEditPlaylistIcon(boolean canBeAddedOrRestored) {
        int editButtonRes = canBeAddedOrRestored ? R.drawable.ic_playlist_plus_black_24dp : R.drawable.ic_playlist_minus_black_24dp;
        btnPlaylistEdit.setImageResource(editButtonRes);
    }

    private void setTrackInfo(AudioModel currentItem) {
        textArtist.setText(currentItem.getArtist());
        textName.setText(currentItem.getName());
    }

    public static void start(Context context, List<AudioModel> playlist, int startPosition) {
        player.setPlaylist(playlist);
        Intent starter = new Intent(context, AudioPlayerActivity.class)
                .putExtra(ARG_START_POSITION, startPosition);
        context.startActivity(starter);
    }
}
