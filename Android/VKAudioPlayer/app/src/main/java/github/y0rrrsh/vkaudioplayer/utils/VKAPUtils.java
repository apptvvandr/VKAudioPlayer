package github.y0rrrsh.vkaudioplayer.utils;

import android.app.Activity;
import android.content.Context;

import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.activities.LoginActivity;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;

import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_APP_ID;
import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_APP_SCOPE;

/**
 * @author Artur Yorsh. 07.06.16.
 */
public class VKAPUtils {

    public static String formatProgress(int progress) {
        int minutes = progress / 60;
        int seconds = progress % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public static boolean lastRequestIsOlder(Context context, String dataTag, double minutes) {
        long lastDataUpdate = VKAPPreferences.getLastDataUpdate(context, dataTag);
        long now = System.currentTimeMillis();

        return now - lastDataUpdate > minutes * 60 * 1000;
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
