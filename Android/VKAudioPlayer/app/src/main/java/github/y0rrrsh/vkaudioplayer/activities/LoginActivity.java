package github.y0rrrsh.vkaudioplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkapi.VKLoginActivity;
import github.y0rrrsh.vkaudioplayer.VKAPApplication;
import github.y0rrrsh.vkaudioplayer.database.syncitem.SyncItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;
import github.y0rrrsh.vkaudioplayer.models.UserModel;
import github.y0rrrsh.vkaudioplayer.network.Callback;
import github.y0rrrsh.vkaudioplayer.network.service.rx.VKAPServiceRxImpl;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;

/**
 * @author Artur Yorsh. 14.06.16.
 */
public class LoginActivity extends VKLoginActivity {

    @Override
    protected void onLoginResult() {
        VkItemDB.init(VKAPApplication.getContext(), VKApi.USER_ID);
        SyncItemDB.init(VKAPApplication.getContext(), VKApi.USER_ID);
        VKAPPreferences.setLastLoginMillis(this, System.currentTimeMillis());

        //noinspection ConstantConditions
        VKAPServiceRxImpl.getInstance().getUserInfo(VKApi.USER_ID, new Callback<UserModel>() {
            @Override
            public void onResult(UserModel response) {
                response.setSyncEnabled(true);
                VkItemDB.getInstance().put(response);

                MainActivity.start(LoginActivity.this);
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

    @Override
    protected void onLoginError() {
        Toast.makeText(this, "To use this app you should be logged in!", Toast.LENGTH_SHORT).show();
        webView.reload();
    }

    public static void start(Activity activity, String appId, String appScope) {
        Intent intent = new Intent(activity, LoginActivity.class)
                .putExtra(EXTRA_APP_ID, appId)
                .putExtra(EXTRA_APP_SCOPE, appScope);
        activity.startActivity(intent);
        activity.finish();
    }
}
