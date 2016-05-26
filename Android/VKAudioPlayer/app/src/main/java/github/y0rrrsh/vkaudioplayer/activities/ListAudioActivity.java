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

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.BaseActivity;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemAdapter.ItemObserver;
import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragment;
import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragmentBuilder;

public class ListAudioActivity extends BaseActivity implements ItemObserver {

    public static String ARG_OWNER_ID = "owner_id";
    public static String ARG_OWNER_NAME = "owner_name";
    public static final String ARG_OWNER_AVATAR = "owner_avatar";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab_play) FloatingActionButton fabPlay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_audios;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int ownerId = getIntent().getIntExtra(ARG_OWNER_ID, 0);
        String ownerName = getIntent().getStringExtra(ARG_OWNER_NAME);
        String ownerAvatar = getIntent().getStringExtra(ARG_OWNER_AVATAR);

        toolbar.setTitle(ownerName);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ImageView imageView = (ImageView) findViewById(R.id.image_owner_avatar);
        Picasso.with(this).load(ownerAvatar).into(imageView);

        UserAudiosFragment userAudiosFragment = new UserAudiosFragmentBuilder()
                .userId(String.valueOf(ownerId))
                .ownerName(ownerName)
                .build();
        userAudiosFragment.setItemObserver(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, userAudiosFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        fabPlay.hide(new FloatingActionButton.OnVisibilityChangedListener() {
            @Override
            public void onHidden(FloatingActionButton fab) {
                supportFinishAfterTransition();
            }
        });
    }

    @Override
    public void onDataSizeChanged(int size) {
        if (size == 0) {
            fabPlay.hide();
        } else {
            fabPlay.show();
        }
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
