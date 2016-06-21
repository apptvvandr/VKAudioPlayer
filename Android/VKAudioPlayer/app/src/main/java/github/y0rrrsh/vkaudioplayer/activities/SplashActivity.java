package github.y0rrrsh.vkaudioplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import github.y0rrrsh.vkapi.VKApi;

import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_APP_ID;
import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_APP_SCOPE;

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
            VKApi.login(this, VKAP_APP_ID, VKAP_APP_SCOPE);
        }, 1500);
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
