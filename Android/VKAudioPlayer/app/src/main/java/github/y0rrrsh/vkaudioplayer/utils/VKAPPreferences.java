package github.y0rrrsh.vkaudioplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * @author Artur Yorsh. 01.06.16.
 */
public class VKAPPreferences {

    private static final String PREFS_NAME_VKAP = "prefs_vkap";

    private static final String KEY_REPEAT_ENABLED = "repeat_enabled";
    private static final String KEY_SHUFFLE_ENABLED = "shuffle_enabled";
    private static final String KEY_LAST_UPDATE = "last_update_";

    public static boolean isRepeatEnabled(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_REPEAT_ENABLED, false);
    }

    public static void setRepeatEnabled(Context context, boolean enabled) {
        getEditor(context).putBoolean(KEY_REPEAT_ENABLED, enabled).apply();
    }

    public static boolean isShuffleEnabled(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_SHUFFLE_ENABLED, false);
    }

    public static void setShuffleEnabled(Context context, boolean enabled) {
        getEditor(context).putBoolean(KEY_SHUFFLE_ENABLED, enabled).apply();
    }

    public static long getLastDataUpdate(Context context, String dataTag) {
        return getSharedPreferences(context).getLong(KEY_LAST_UPDATE + dataTag, 0);
    }

    public static void setLastUpdate(Context context, long time, @NonNull String dataTag) {
        getEditor(context).putLong(KEY_LAST_UPDATE + dataTag, time).apply();
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME_VKAP, Context.MODE_PRIVATE);
    }
}
