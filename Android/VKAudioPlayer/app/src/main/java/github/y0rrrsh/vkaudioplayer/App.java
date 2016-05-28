package github.y0rrrsh.vkaudioplayer;

import android.app.Application;

import github.y0rrrsh.vkaudioplayer.vkapi.VKApi;

/**
 * @author Artur Yorsh
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKApi.init(this);
    }
}
