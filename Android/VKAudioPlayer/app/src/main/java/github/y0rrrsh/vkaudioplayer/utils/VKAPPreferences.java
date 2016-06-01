package github.y0rrrsh.vkaudioplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Artur Yorsh. 01.06.16.
 */
public class VKAPPreferences {

    private static final String PREFS_NAME_VKAP = "prefs_vkap";

    private static final String KEY_REPEAT_ENABLED = "repeat_enabled";
    private static final String KEY_SHUFFLE_ENABLED = "shuffle_enabled";

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

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME_VKAP, Context.MODE_PRIVATE);
    }
}
