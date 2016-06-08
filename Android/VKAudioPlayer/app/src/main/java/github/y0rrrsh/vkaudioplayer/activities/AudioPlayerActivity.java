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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import github.y0rrrsh.playbackcontrolview.PlaybackControlView;
import github.y0rrrsh.playbackcontrolview.PlaybackControlView.PlaybackActionHandler;
import github.y0rrrsh.streamplayer.StreamPlayer.StreamItem;
import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkapi.VKApi.VKCallback;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.PlaybackActivity;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.models.ManagedAudio;
import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.network.asynctask.CallbackTask;
import github.y0rrrsh.vkaudioplayer.network.asynctask.RetrieveAudioCoverTask;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPServiceImpl;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;
import github.y0rrrsh.vkaudioplayer.utils.VKAPUtils;

import static java.util.Collections.shuffle;

public class AudioPlayerActivity extends PlaybackActivity implements PlaybackActionHandler {

    public static final String ARG_START_POSITION = "playlist_start_position";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.control_player) PlaybackControlView playbackControlView;
    @BindView(R.id.text_player_artist) TextView textArtist;
    @BindView(R.id.text_player_name) TextView textName;
    @BindView(R.id.btn_playlist_edit) ImageButton btnPlaylistEdit;
    @BindView(R.id.image_player_cover) ImageView imageCover;
    @BindView(R.id.text_time_current) TextView textTimeCurrent;
    @BindView(R.id.text_time_full) TextView textTimeFull;

    private Bitmap defaultCover;
    private List<AudioModel> playlist;

    private VKAPService api = VKAPServiceImpl.getInstance();

    private Map<Integer, ManagedAudio> managedAudios = new HashMap<>();

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

        int shuffleTint = VKAPPreferences.isShuffleEnabled(this) ? R.color.colorPrimaryDark : android.R.color.black;
        int repeatTint = VKAPPreferences.isRepeatEnabled(this) ? R.color.colorPrimaryDark : android.R.color.black;
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

            StreamItem currentItem = player.getCurrentItem();
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

        int playIcon = player.isPlaying() ? R.drawable.ic_pause : R.drawable.ic_play;
        playbackControlView.setPlayButtonIcon(playIcon);
        playbackControlView.setSeekCurrentProgress(player.getProgress());
        playbackControlView.setSeekMaxProgress(player.getItemDuration());

        AudioModel currentItem = (AudioModel) player.getCurrentItem();
        int currentItemPosition = player.getCurrentItemPosition();
        onPlayerItemSelected(currentItem, currentItemPosition);
        updateEditPlaylistIcon(currentItem, currentItemPosition);
    }

    @OnClick(R.id.btn_playlist_edit)
    protected void onPlaylistEditClicked() {
        AudioModel currentItem = (AudioModel) player.getCurrentItem();
        ManagedAudio managedAudio = managedAudios.get(player.getCurrentItemPosition());

        if (currentItem.getOwnerId() != VKApi.USER_ID && managedAudio == null) {
            addAudioRequest(currentItem); //not owned by default
            return;
        }
        if (currentItem.getOwnerId() == VKApi.USER_ID && managedAudio == null) {
            removeAudioRequest(currentItem, currentItem.getId()); //owned by default
            return;
        }
        if (!managedAudio.wasRemoved()) {
            removeAudioRequest(currentItem, managedAudio.getId()); //was owned by adding
            return;
        }
        if (managedAudio.wasRemoved()) {
            restoreAudioRequest(currentItem, managedAudio.getId()); //was owned some-how and then removed
        }
    }

    private void addAudioRequest(final AudioModel currentAudio) {
        api.addAudio(currentAudio.getId(), currentAudio.getOwnerId(), new VKCallback<Integer>() {
            @Override
            public void onResponse(Integer newAudioId) {
                String audioInfo = String.format("%s - %s", currentAudio.getArtist(), currentAudio.getName());
                Toast.makeText(AudioPlayerActivity.this, audioInfo + " was added to your page", Toast.LENGTH_LONG).show();

                managedAudios.put(player.getCurrentItemPosition(), new ManagedAudio(newAudioId));
                btnPlaylistEdit.setImageResource(R.drawable.ic_playlist_minus);
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

    private void removeAudioRequest(final AudioModel currentAudio, Integer audioId) {
        api.removeAudio(audioId, VKApi.USER_ID, new VKCallback<Integer>() {
            @Override
            public void onResponse(Integer response) {
                String audioInfo = String.format("%s - %s", currentAudio.getArtist(), currentAudio.getName());
                Toast.makeText(AudioPlayerActivity.this, audioInfo + " was removed from your page", Toast.LENGTH_LONG).show();

                ManagedAudio managedAudio = managedAudios.get(player.getCurrentItemPosition());
                if (managedAudio == null) {
                    managedAudio = new ManagedAudio(currentAudio.getId());
                    if (currentAudio.getOwnerId() == VKApi.USER_ID) {
                        managedAudio.setWasRemoved(true);
                    }
                } else {
                    managedAudio.setWasRemoved(true);
                }
                managedAudios.put(player.getCurrentItemPosition(), managedAudio);
                btnPlaylistEdit.setImageResource(R.drawable.ic_playlist_plus);
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

    private void restoreAudioRequest(final AudioModel currentAudio, Integer audioId) {
        api.restoreAudio(audioId, VKApi.USER_ID, new VKCallback<AudioDTO>() {
            @Override
            public void onResponse(AudioDTO restoredAudio) {
                String audioInfo = String.format("%s - %s", currentAudio.getArtist(), currentAudio.getName());
                Toast.makeText(AudioPlayerActivity.this, audioInfo + " was added to your page", Toast.LENGTH_LONG).show();

                managedAudios.put(player.getCurrentItemPosition(), new ManagedAudio(restoredAudio.getId()));
                btnPlaylistEdit.setImageResource(R.drawable.ic_playlist_minus);
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

    @Override
    public void onPreviousClicked() {
        player.playPrevious();
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
    }

    @Override
    public void onShuffleClicked() {
        boolean shuffleEnabled = VKAPPreferences.isShuffleEnabled(this);
        VKAPPreferences.setShuffleEnabled(this, !shuffleEnabled);
        if (!shuffleEnabled) {
            shuffle(player.getPlaylist());
        } else {
            player.setPlaylist(playlist);
        }
        int shuffleTint = !shuffleEnabled ? R.color.colorPrimaryDark : android.R.color.black;
        playbackControlView.setShuffleButtonTintColor(shuffleTint);
    }

    @Override
    public void onRepeatClicked() {
        boolean repeatEnabled = VKAPPreferences.isRepeatEnabled(this);
        VKAPPreferences.setRepeatEnabled(this, !repeatEnabled);

        int repeatTint = !repeatEnabled ? R.color.colorPrimaryDark : android.R.color.black;
        playbackControlView.setRepeatButtonTintColor(repeatTint);
    }

    @Override
    public void onSeekDragged(int currentValue) {
        player.seekTo(currentValue);
    }

    @Override
    protected void onPlayerItemSelected(AudioModel currentItem, int position) {
        imageCover.setImageBitmap(defaultCover);
        textArtist.setText(currentItem.getArtist());
        textName.setText(currentItem.getName());
        textTimeFull.setText(VKAPUtils.formatProgress(currentItem.getDuration()));
        updateEditPlaylistIcon(currentItem, position);
    }

    @Override
    protected void onStartPlaying(AudioModel currentItem) {
        playbackControlView.setPlayButtonIcon(R.drawable.ic_pause);
        playbackControlView.setSeekMaxProgress(player.getItemDuration());

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
        playbackControlView.setPlayButtonIcon(R.drawable.ic_play);
    }

    @Override
    protected void onProgressChanged(int progress) {
        playbackControlView.setSeekCurrentProgress(progress);
        textTimeCurrent.setText(VKAPUtils.formatProgress(progress));
    }

    @Override
    protected void onBufferUpdated(float percent) {
        int bufferProgress = (int) (percent * player.getItemDuration());
        playbackControlView.setSeekSecondaryProgress(bufferProgress);
    }

    private void updateEditPlaylistIcon(AudioModel currentItem, int playlistPosition) {
        ManagedAudio managedAudio = managedAudios.get(playlistPosition);
        boolean isOwner = currentItem.getOwnerId() != VKApi.USER_ID && managedAudio == null;
        boolean canBeRestored = managedAudio != null && managedAudio.wasRemoved();

        int editIcon = isOwner || canBeRestored ? R.drawable.ic_playlist_plus : R.drawable.ic_playlist_minus;
        btnPlaylistEdit.setImageResource(editIcon);
    }

    public static void start(Context context, List<AudioModel> playlist, int startPosition) {
        player.setPlaylist(playlist);
        Intent starter = new Intent(context, AudioPlayerActivity.class)
                .putExtra(ARG_START_POSITION, startPosition);
        context.startActivity(starter);
    }
}
