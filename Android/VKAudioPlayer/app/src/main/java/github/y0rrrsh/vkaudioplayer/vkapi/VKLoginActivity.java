package github.y0rrrsh.vkaudioplayer.vkapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VKLoginActivity extends AppCompatActivity {

    static final int REQUEST_CODE = 8724;

    private static String EXTRA_APP_ID = "vk_app_id";
    private static String EXTRA_APP_SCOPE = "vk_app_scope";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);

        Intent starter = getIntent();
        String appId = starter.getStringExtra(EXTRA_APP_ID);
        String appScope = starter.getStringExtra(EXTRA_APP_SCOPE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("access_token=")) {
                    String tokenSubstring = url.split("#")[1];
                    String[] accessParams = tokenSubstring.split("&");

                    String token = accessParams[0].split("=")[1];
                    String expiresIn = accessParams[1].split("=")[1];
                    String userId = accessParams[2].split("=")[1];

                    VKApi.init(VKLoginActivity.this, token, expiresIn, userId);

                    setResult(RESULT_OK);
                    finish();
                    return true;
                }
                if (url.contains("error")) {
                    setResult(RESULT_CANCELED);
                    finish();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        String authUrl = String.format("http://oauth.vk.com/authorize"
                + "?client_id=%s"
                + "&scope=%s"
                + "&display=touch"
                + "&v=" + "5.52"
                + "&response_type=token", appId, appScope);
        webView.loadUrl(authUrl);
    }

    static void startForResult(Activity activity, @NonNull String appId, String appScope) {
        Intent intent = new Intent(activity, VKLoginActivity.class)
                .putExtra(EXTRA_APP_ID, appId)
                .putExtra(EXTRA_APP_SCOPE, appScope);
        activity.startActivityForResult(intent, VKLoginActivity.REQUEST_CODE);
    }
}