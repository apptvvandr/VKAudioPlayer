package github.y0rrrsh.vkaudioplayer;

import android.os.Parcelable;

/**
 * @author Artur Yorsh. 30.05.16.
 */
public interface AudioPlayerItem extends Parcelable {
    String getArtist();

    String getName();

    String getUrl();

    long getDuration();
}
