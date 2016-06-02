package github.y0rrrsh.vkaudioplayer.network.asynctask;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * @author Artur Yorsh. 02.06.16.
 */
public abstract class CallbackTask<T> extends AsyncTask<Void, Void, T> {

    private Callback<T> callback;

    public CallbackTask(@NonNull Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    protected T doInBackground(Void... params) {
        return onDoInBackground();
    }

    @Override
    protected void onPostExecute(T result) {
        callback.onResult(result);
    }

    protected abstract T onDoInBackground();

    public interface Callback<T> {
        void onResult(T result);
    }
}
