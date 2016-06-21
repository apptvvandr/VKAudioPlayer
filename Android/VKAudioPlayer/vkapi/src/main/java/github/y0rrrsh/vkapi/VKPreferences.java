package github.y0rrrsh.vkapi;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Artur Yorsh
 */
class VKPreferences {

    private static final String PREFS_NAME_VK = "preferences_vk";
    private static final String KEY_VK_TOKEN = "vk_token";
    private static final String KEY_VK_TOKEN_EXPIRE = "vk_token_expire";
    private static final String KEY_VK_USER_ID = "vk_user_id";

    static String[] getTokenValues(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);

        return new String[]{
                preferences.getString(KEY_VK_TOKEN, null),
                preferences.getString(KEY_VK_TOKEN_EXPIRE, null),
                preferences.getString(KEY_VK_USER_ID, null)};
    }

    static void saveTokenValues(Context context, String token, String expireIn, String userId) {
        getEditor(context)
                .putString(KEY_VK_TOKEN, token)
                .putString(KEY_VK_TOKEN_EXPIRE, expireIn)
                .putString(KEY_VK_USER_ID, userId)
                .apply();
    }

    static void clearValues(Context context) {
        getEditor(context).clear().apply();
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME_VK, Context.MODE_PRIVATE);
    }
}
