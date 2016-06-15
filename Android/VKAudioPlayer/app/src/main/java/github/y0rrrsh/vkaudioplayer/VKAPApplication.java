package github.y0rrrsh.vkaudioplayer;

import android.app.Application;
import android.content.Context;

import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.database.syncitem.SyncItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;

/**
 * @author Artur Yorsh
 */
public class VKAPApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        VKApi.init(this);
        VkItemDB.init(this, VKApi.USER_ID);
        SyncItemDB.init(this, VKApi.USER_ID);
    }

    public static Context getContext() {
        return context;
    }
}
