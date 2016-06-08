package github.y0rrrsh.vkaudioplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.BaseActivity;
import github.y0rrrsh.vkaudioplayer.adapters.VkTabAdapter;
import github.y0rrrsh.vkaudioplayer.models.dto.UserDTO;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPServiceImpl;
import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * @author Artur Yorsh
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.image_tabs_bg) ImageView imageBackground;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager.setAdapter(new VkTabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        //noinspection ConstantConditions
        VKAPServiceImpl.getInstance().getUserInfo(VKApi.USER_ID, new VKApi.VKCallback<UserDTO>() {
            @Override
            public void onResponse(UserDTO user) {
                int blurRadius = MainActivity.this.getResources().getInteger(R.integer.blur_radius);
                Transformation blur = new BlurTransformation(MainActivity.this, blurRadius);
                Picasso.with(MainActivity.this)
                        .load(user.getPhotoBig())
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .transform(blur)
                        .into(imageBackground);
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "onError: ");
            }
        });
    }

    public static void start(Activity activity) {
        Intent starter = new Intent(activity, MainActivity.class);
        activity.startActivity(starter);
        activity.finish();
    }
}
