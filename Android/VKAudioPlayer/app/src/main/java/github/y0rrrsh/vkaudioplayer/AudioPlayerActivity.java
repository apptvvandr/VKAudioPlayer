package github.y0rrrsh.vkaudioplayer;

import android.content.Context;
import android.content.Intent;
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
import github.y0rrrsh.vkaudioplayer.activities.common.BaseActivity;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.views.PlaybackControlView;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi;

public class AudioPlayerActivity extends BaseActivity implements PlaybackControlView.ActionHandler {

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
    private AudioModel currentAudio;
    private int currentItemPosition;

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
        currentItemPosition = starter.getIntExtra(ARG_START_POSITION, 0);

        currentAudio = playlist.get(currentItemPosition);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        playbackControlView.setActionHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        textArtist.setText(currentAudio.getArtist());
        textName.setText(currentAudio.getName());
    }

    @OnClick(R.id.btn_player_add)
    protected void onAddClicked() {
        api.addAudio(currentAudio.getId(), currentAudio.getOwnerId(), new VKApi.VkCallback<Integer>() {
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
        api.removeAudio(currentAudio.getId(), currentAudio.getOwnerId(), new VKApi.VkCallback<Integer>() {
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
        // TODO: 30.05.16: onPreviousClicked
        Toast.makeText(this, "onPreviousClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlayClicked() {
        // TODO: 30.05.16: onPlayClicked
        Toast.makeText(this, "onPlayClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextClicked() {
        // TODO: 30.05.16: onNextClicked
        Toast.makeText(this, "onNextClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShuffleClicked() {
        // TODO: 30.05.16: onShuffleClicked
        Toast.makeText(this, "onShuffleClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRepeatClicked() {
        // TODO: 30.05.16: onRepeatClicked
        Toast.makeText(this, "onRepeatClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSeekDragged(int currentValue) {
        // TODO: 30.05.16: onSeekDragged
        Toast.makeText(this, "onSeekDragged", Toast.LENGTH_SHORT).show();
    }

    public static void start(Context context, List<AudioModel> playlist, int startPosition) {
        Intent starter = new Intent(context, AudioPlayerActivity.class)
                .putParcelableArrayListExtra(ARG_PLAYLIST, (ArrayList<? extends Parcelable>) playlist)
                .putExtra(ARG_START_POSITION, startPosition);
        context.startActivity(starter);
    }
}
