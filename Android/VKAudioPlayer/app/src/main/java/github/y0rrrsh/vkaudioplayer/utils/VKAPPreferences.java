package github.y0rrrsh.vkaudioplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB.DataType;

import static github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB.DataType.FRIENDS;
import static github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB.DataType.GROUPS;

/**
 * @author Artur Yorsh. 01.06.16.
 */
public class VKAPPreferences {

    private static final String PREFS_NAME_VKAP = "prefs_vkap_";

    private static final String KEY_REPEAT_ENABLED = "repeat_enabled";
    private static final String KEY_SHUFFLE_ENABLED = "shuffle_enabled";
    private static final String KEY_LAST_UPDATE = "last_update_";
    public static final String KEY_AUTO_SYNC_ENABLED = "auto_sync_enabled_";
    public static final String KEY_ASKED_SYNC = "asked_sync_";
    private static final String KEY_FIRST_USE_MILLIS = "first_use_millis";

    //player
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

    //data update
    public static long getLastDataUpdate(Context context, String dataTag) {
        return getSharedPreferences(context).getLong(KEY_LAST_UPDATE + dataTag, 0);
    }

    public static void setLastUpdate(Context context, long time, @NonNull String dataTag) {
        getEditor(context).putLong(KEY_LAST_UPDATE + dataTag, time).apply();
    }

    //auto synchronization
    public static boolean isAskedSync(Context context, String dataTag) {
        return getSharedPreferences(context).getBoolean(KEY_ASKED_SYNC + dataTag, false);
    }

    public static void setAskedSync(Context context, String dataTag, boolean askedSync) {
        getEditor(context).putBoolean(KEY_ASKED_SYNC + dataTag, askedSync).apply();
    }

    public static boolean isAutoSyncEnabled(Context context, @NonNull DataType type) {
        return getSharedPreferences(context).getBoolean(KEY_AUTO_SYNC_ENABLED + type.getName(), false);
    }

    public static boolean isAutoSyncEnabled(Context context) {
        return isAutoSyncEnabled(context, FRIENDS) || isAutoSyncEnabled(context, GROUPS);
    }

    public static void setAutoSyncEnabled(Context context, @NonNull DataType type, boolean enabled) {
        getEditor(context).putBoolean(KEY_AUTO_SYNC_ENABLED + type.getName(), enabled).apply();
    }

    public static void setAutoSyncEnabled(Context context, boolean enabled) {
        setAutoSyncEnabled(context, FRIENDS, enabled);
        setAutoSyncEnabled(context, GROUPS, enabled);
    }

    public static long getLastLoginMillis(Context context) {
        return getSharedPreferences(context).getLong(KEY_FIRST_USE_MILLIS, 0);
    }

    public static void setLastLoginMillis(Context context, long millis) {
        getEditor(context).putLong(KEY_FIRST_USE_MILLIS, millis).apply();
    }

    public static void clear(Context context) {
        getEditor(context).clear().apply();
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME_VKAP + VKApi.USER_ID, Context.MODE_PRIVATE);
    }
}
