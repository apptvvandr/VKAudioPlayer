package github.y0rrrsh.vkaudioplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.BaseActivity;
import github.y0rrrsh.vkaudioplayer.adapters.VkTabAdapter;

/**
 * @author Artur Yorsh
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager.setAdapter(new VkTabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    public static void start(Activity activity) {
        Intent starter = new Intent(activity, MainActivity.class);
        activity.startActivity(starter);
        activity.finish();
    }
}
