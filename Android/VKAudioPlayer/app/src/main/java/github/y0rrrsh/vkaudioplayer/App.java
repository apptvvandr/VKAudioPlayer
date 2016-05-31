package github.y0rrrsh.vkaudioplayer;

import android.app.Application;
import android.content.Context;

import github.y0rrrsh.vkaudioplayer.vkapi.VKApi;

/**
 * @author Artur Yorsh
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        VKApi.init(this);
    }

    public static Context getContext() {
        return context;
    }
}
