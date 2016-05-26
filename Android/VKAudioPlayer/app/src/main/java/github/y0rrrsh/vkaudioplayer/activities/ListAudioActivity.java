package github.y0rrrsh.vkaudioplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.BaseActivity;
import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragment;
import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragmentBuilder;

public class ListAudioActivity extends BaseActivity {

    private static String ARG_OWNER_ID = "owner_id";
    private static String ARG_OWNER_NAME = "owner_name";

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_audios;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int ownerId = getIntent().getIntExtra(ARG_OWNER_ID, 0);
        String  ownerName = getIntent().getStringExtra(ARG_OWNER_NAME);
        toolbar.setTitle(String.format("%s's audios", ownerName));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        UserAudiosFragment userAudiosFragment = new UserAudiosFragmentBuilder()
                .userId(String.valueOf(ownerId))
                .ownerName(ownerName)
                .build();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, userAudiosFragment)
                .commit();
    }

    public static void start(Context context, int ownerId, String ownerName) {
        Intent starter = new Intent(context, ListAudioActivity.class);
        starter.putExtra(ARG_OWNER_ID, ownerId);
        starter.putExtra(ARG_OWNER_NAME, ownerName);
        context.startActivity(starter);
    }
}
