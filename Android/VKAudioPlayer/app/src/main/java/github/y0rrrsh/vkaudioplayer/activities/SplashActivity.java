package github.y0rrrsh.vkaudioplayer.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.utils.VKAPUtils;

/**
 * @author Artur Yorsh
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            if (VKApi.isInitialized()) {
                MainActivity.start(this);
                return;
            }
            VKAPUtils.login(this);
        }, 1500);
    }
}
