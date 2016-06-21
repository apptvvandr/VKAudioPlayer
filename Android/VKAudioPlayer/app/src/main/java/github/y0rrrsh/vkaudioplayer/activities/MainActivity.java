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
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkapi.VKApi.VKCallback;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.BaseActivity;
import github.y0rrrsh.vkaudioplayer.adapters.VkTabAdapter;
import github.y0rrrsh.vkaudioplayer.models.dto.UserDTO;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPServiceImpl;
import jp.wasabeef.picasso.transformations.BlurTransformation;

import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_APP_ID;
import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_APP_SCOPE;

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
                    VKApi.logout(this);
                    VKApi.login(this, VKAP_APP_ID, VKAP_APP_SCOPE);
                    break;
            }
            drawerLayout.closeDrawers();
            return false;
        });

        viewPager.setAdapter(new VkTabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //noinspection ConstantConditions
        VKAPServiceImpl.getInstance().getUserInfo(VKApi.USER_ID, new VKCallback<UserDTO>() {
            @Override
            public void onResponse(UserDTO user) {
                int blurRadius = MainActivity.this.getResources().getInteger(R.integer.blur_radius);
                Transformation blur = new BlurTransformation(MainActivity.this, blurRadius);
                RequestCreator picassoRequest = Picasso.with(MainActivity.this)
                        .load(user.getPhotoBig())
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .transform(blur);

                View headerView = navigationView.getHeaderView(0);
                ImageView imageAvatar = (ImageView) headerView.findViewById(R.id.drawer_image_user_avatar);
                TextView textUserName = (TextView) headerView.findViewById(R.id.drawer_text_user_name);

                picassoRequest.into(imageAvatar);
                picassoRequest.into(imageBackground);

                textUserName.setText(user.getFirstName() + " " + user.getLastName());
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

    public static void start(Activity activity) {
        Intent starter = new Intent(activity, MainActivity.class);
        activity.startActivity(starter);
        activity.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "To use this app you should be logged in!", Toast.LENGTH_SHORT).show();
            VKApi.login(this, VKAP_APP_ID, VKAP_APP_SCOPE);
            return;
        }
        MainActivity.start(this);
    }
}