package github.y0rrrsh.vkaudioplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.BaseActivity;
import github.y0rrrsh.vkaudioplayer.adapters.VkTabAdapter;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;
import github.y0rrrsh.vkaudioplayer.utils.VKAPUtils;
import jp.wasabeef.picasso.transformations.BlurTransformation;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;
import static github.y0rrrsh.vkapi.VKApi.USER_ID;
import static github.y0rrrsh.vkaudioplayer.adapters.VkTabAdapter.POSITION_USER_AUDIO;
import static github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB.DataType.USER;

/**
 * @author Artur Yorsh
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.image_tabs_bg) ImageView imageBackground;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.desc_drawer_open, R.string.desc_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_nav_downloads:
                    // TODO: 09.06.16 present downloads
                    break;
                case R.id.menu_nav_settings:
                    SettingsActivity.start(this);
                    break;
                case R.id.menu_nav_about:
                    // TODO: 09.06.16 present app info
                    break;
                case R.id.menu_nav_logout:
                    VKAPUtils.logout(this);
                    VKAPUtils.login(this);
                    break;
            }
            drawerLayout.closeDrawers();
            return false;
        });

        viewPager.setAdapter(new VkTabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(POSITION_USER_AUDIO);

        //noinspection ConstantConditions
        VkItem userModel = VkItemDB.getInstance().get(USER, USER_ID);
        int blurRadius = MainActivity.this.getResources().getInteger(R.integer.blur_radius);
        Transformation blur = new BlurTransformation(this, blurRadius);
        RequestCreator picassoRequest = Picasso.with(this)
                .load(userModel.getAvatarUrl())
                .memoryPolicy(NO_CACHE, NO_STORE)
                .transform(blur);

        View headerView = navigationView.getHeaderView(0);
        ImageView imageAvatar = (ImageView) headerView.findViewById(R.id.drawer_image_user_avatar);
        TextView textUserName = (TextView) headerView.findViewById(R.id.drawer_text_user_name);

        picassoRequest.into(imageAvatar);
        picassoRequest.into(imageBackground);
        textUserName.setText(userModel.getName());
    }

    public static void start(Activity activity) {
        Intent starter = new Intent(activity, MainActivity.class);
        activity.startActivity(starter);
        activity.finish();
    }
}