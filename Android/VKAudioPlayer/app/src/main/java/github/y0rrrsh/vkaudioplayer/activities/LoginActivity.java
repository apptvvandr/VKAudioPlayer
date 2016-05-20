package github.y0rrrsh.vkaudioplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.common.BaseActivity;
import github.y0rrrsh.vkaudioplayer.models.AccessToken;

public class LoginActivity extends BaseActivity {

    public static final String URL_AUTH = "http://oauth.vk.com/authorize"
            + "?client_id=" + "5424227"
            + "&scope=" + "audio"
            + "&display=touch"
            + "&v=" + "5.12"
            + "&response_type=token";

    @BindView(R.id.web_view) WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("access_token=")) {
                    String tokenSubstring = url.split("#")[1];
                    String[] accessParams = tokenSubstring.split("&");

                    String token = accessParams[0].split("=")[1];
                    String expiresIn = accessParams[1].split("=")[1];
                    String userId = accessParams[2].split("=")[1];

                    AccessToken.init(token, expiresIn, userId);
                    MainActivity.start(LoginActivity.this);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.loadUrl(URL_AUTH);
    }

    public static void start(Activity activity) {
        Intent starter = new Intent(activity, LoginActivity.class);
        activity.startActivity(starter);
        activity.finish();
    }
}