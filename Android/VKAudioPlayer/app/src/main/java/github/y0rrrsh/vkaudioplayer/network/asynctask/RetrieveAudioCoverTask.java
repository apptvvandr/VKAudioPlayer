package github.y0rrrsh.vkaudioplayer.network.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * @author Artur Yorsh. 02.06.16.
 */
public class RetrieveAudioCoverTask extends CallbackTask<Bitmap> {

    private FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
    private String url;

    public static void retrieve(String url, @NonNull Callback<Bitmap> callback) {
        new RetrieveAudioCoverTask(url, callback).execute();
    }

    private RetrieveAudioCoverTask(String url, @NonNull Callback<Bitmap> callback) {
        super(callback);
        this.url = url;
    }

    @Override
    protected Bitmap onDoInBackground() {
        retriever.setDataSource(url.replaceFirst("https", "http"));
        byte[] picture = retriever.getEmbeddedPicture();
        if (picture != null) {
            return BitmapFactory.decodeByteArray(picture, 0, picture.length);
        }
        return null;
    }
}