package github.y0rrrsh.vkaudioplayer.network.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * @author Artur Yorsh. 02.06.16.
 */
public class RetrieveAudioCoverTask extends CallbackTask<Bitmap> {


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
        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
        String source = url.replaceFirst("https", "http");
        retriever.setDataSource(source);
        byte[] picture = retriever.getEmbeddedPicture();
        if (picture != null) {
            retriever.release();
            return BitmapFactory.decodeByteArray(picture, 0, picture.length);
        }
        return null;
    }

    @Override
    protected boolean isWrongResult(Bitmap result) {
        return result == null;
    }
}