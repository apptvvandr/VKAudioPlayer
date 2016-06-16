package github.y0rrrsh.vkaudioplayer.network.asynctask;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import github.y0rrrsh.vkaudioplayer.network.Callback;

/**
 * @author Artur Yorsh. 02.06.16.
 */
public abstract class CallbackTask<T> extends AsyncTask<Void, Void, T> {

    private Callback<T> callback;

    private Exception exception;

    public CallbackTask(@NonNull Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    protected T doInBackground(Void... params) {
        try {
            T result = onDoInBackground();
            if (isWrongResult(result)) {
                this.exception = new Exception("Wrong result");
                return null;
            }
            return result;
        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(T result) {
        if (exception != null) {
            callback.onError(exception);
            return;
        }
        callback.onResult(result);
    }

    protected abstract T onDoInBackground() throws Exception;

    protected boolean isWrongResult(T result) {
        return false;
    }
}
