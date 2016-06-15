package github.y0rrrsh.vkaudioplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.PlaybackActivity;
import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragment;
import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragment.PlaylistReadyListener;
import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragmentBuilder;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;

import static android.support.design.widget.FloatingActionButton.OnVisibilityChangedListener;

public class ListAudioActivity extends PlaybackActivity implements PlaylistReadyListener {

    public static final String ARG_OWNER_AVATAR = "owner_avatar";
    public static String ARG_OWNER_ID = "owner_id";
    public static String ARG_OWNER_NAME = "owner_name";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab_play) FloatingActionButton fabPlay;
    @BindView(R.id.image_owner_avatar) ImageView imageOwnerAvatar;

    private int ownerId;
    private List<AudioModel> playlist;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_audios;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ownerId = getIntent().getIntExtra(ARG_OWNER_ID, 0);
        String ownerName = getIntent().getStringExtra(ARG_OWNER_NAME);
        String ownerAvatar = getIntent().getStringExtra(ARG_OWNER_AVATAR);

        toolbar.setTitle(ownerName);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        Picasso.with(this).load(ownerAvatar).into(imageOwnerAvatar);

        UserAudiosFragment userAudiosFragment = new UserAudiosFragmentBuilder(ownerId).build();
        userAudiosFragment.setPlaylistReadyListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, userAudiosFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isNowPlayingPlaylist = player.isPlaying() && ((AudioModel) player.getCurrentItem()).getOwnerId() == ownerId;
        int playButtonRes = isNowPlayingPlaylist ? R.drawable.ic_pause : R.drawable.ic_play;
        fabPlay.setImageResource(playButtonRes);
    }

    @OnClick(R.id.fab_play)
    protected void onFabClicked() {
        if (player.isPlaying()) {
            if (ownerId == ((AudioModel) player.getCurrentItem()).getOwnerId()) {
                player.pause();
            } else {
                player.setPlaylist(playlist);
                player.play(0);
            }
        } else if (player.getCurrentItem() != null) {
            player.play();
        } else {
            player.setPlaylist(playlist);
            player.play(0);
        }
    }

    @Override
    protected void onPlayerItemSelected(AudioModel currentItem, int position) {
        // TODO: 07.06.16 present loading
    }

    @Override
    protected void onStartPlaying(AudioModel currentItem) {
        fabPlay.setImageResource(R.drawable.ic_pause);
    }

    @Override
    protected void onPausePlaying() {
        fabPlay.setImageResource(R.drawable.ic_play);
    }

    @Override
    public void onPlaylistReady(List<AudioModel> playlist) {
        this.playlist = playlist;
        if (playlist != null && !playlist.isEmpty()) {
            fabPlay.show();
        } else {
            fabPlay.hide();
        }
    }

    @Override
    public void onBackPressed() {
        fabPlay.hide(new OnVisibilityChangedListener() {
            @Override
            public void onHidden(FloatingActionButton fab) {
                supportFinishAfterTransition();
            }
        });
    }

    public static void start(Activity activity, int ownerId, String ownerName, String avatarUrl,
                             @Nullable ActivityOptionsCompat options) {
        Intent starter = new Intent(activity, ListAudioActivity.class)
                .putExtra(ARG_OWNER_ID, ownerId)
                .putExtra(ARG_OWNER_NAME, ownerName)
                .putExtra(ARG_OWNER_AVATAR, avatarUrl);
        Bundle transitionOptions = options == null ? null : options.toBundle();
        ActivityCompat.startActivity(activity, starter, transitionOptions);
    }
}
