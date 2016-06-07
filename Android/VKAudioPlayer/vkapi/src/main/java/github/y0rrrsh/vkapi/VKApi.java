package github.y0rrrsh.vkapi;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author Artur Yorsh
 */
public class VKApi {

    private static VKApi instance;

    public static int USER_ID;
    public static String ACCESS_TOKEN;

    private VKApi(String accessToken, String userId) {
        ACCESS_TOKEN = accessToken;
        USER_ID = Integer.valueOf(userId);
    }

    public static void init(@NonNull Context context) {
        if (instance != null) {
            return;
        }
        String[] tokenValues = VKPreferences.getTokenValues(context);
        if (tokenValues[0] == null) {
            return;
        }
        instance = new VKApi(tokenValues[0], tokenValues[2]);
    }

    static void init(Context context, @NonNull String token, String expiresIn, String userId) {
        VKPreferences.saveTokenValues(context, token, expiresIn, userId);
        instance = new VKApi(token, userId);
    }

    public static void login(@NonNull Activity activity, @NonNull String appId, @NonNull String appScope) {
        VKLoginActivity.startForResult(activity, appId, appScope);
    }

    public static boolean isInitialized() {
        return instance != null;
    }

    public interface VKCallback<T> {
        void onResponse(T response);

        void onError(Throwable t);
    }

    public interface VKArrayCallback<T extends VKItem> {
        void onResponse(List<T> response);

        void onError(Throwable t);
    }
}