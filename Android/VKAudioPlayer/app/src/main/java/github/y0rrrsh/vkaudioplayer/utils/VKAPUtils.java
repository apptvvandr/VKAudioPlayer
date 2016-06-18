package github.y0rrrsh.vkaudioplayer.utils;

import android.app.Activity;
import android.content.Context;

import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.activities.LoginActivity;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;

import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_APP_ID;
import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_APP_SCOPE;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Artur Yorsh. 07.06.16.
 */
public class VKAPUtils {

    public static final int REQUEST_DELAY_NEW_AUDIOS = 60;
    public static final int REQUEST_DEYAY_USER_AUDIOS = 15;
    public static final int REQUEST_DELAY_USER_GROUPS = 5 * 60;
    public static final int REQUEST_DELAY_USER_FRIENDS = 5 * 60;

    public static String formatProgress(int progress) {
        int minutes = progress / 60;
        int seconds = progress % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public static boolean lastRequestIsOlder(Context context, String dataTag, int seconds) {
        long lastDataUpdate = VKAPPreferences.getLastDataUpdate(context, dataTag);
        return System.currentTimeMillis() - lastDataUpdate > MILLISECONDS.convert(seconds, SECONDS);
    }

    public static void resetSettings(Context context) {
        VKAPPreferences.clear(context);
        VkItemDB.getInstance().setSyncForAllEnabled(false);
    }

    public static void login(Activity activity) {
        LoginActivity.start(activity, VKAP_APP_ID, VKAP_APP_SCOPE);
    }

    public static void logout(Activity activity) {
        VKApi.logout(activity);
    }
}
