package github.y0rrrsh.vkaudioplayer.network;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public interface Callback<T> {
    void onResult(T result);

    void onError(Throwable e);
}
