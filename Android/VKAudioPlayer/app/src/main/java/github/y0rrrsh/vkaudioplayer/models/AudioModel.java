package github.y0rrrsh.vkaudioplayer.models;

import android.os.Parcel;

import github.y0rrrsh.vkaudioplayer.AudioPlayerItem;

/**
 * @author Artur Yorsh. 30.05.16.
 */
public class AudioModel implements AudioPlayerItem {

    private long id;
    private long ownerId;

    private String url;
    private long duration;
    private String artist;
    private String name;

    public AudioModel(long id, long ownerId, String url, long duration, String artist, String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.url = url;
        this.duration = duration;
        this.artist = artist;
        this.name = name;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    protected AudioModel(Parcel in) {
        url = in.readString();
        duration = in.readLong();
        artist = in.readString();
        name = in.readString();
    }

    public static final Creator<AudioModel> CREATOR = new Creator<AudioModel>() {
        @Override
        public AudioModel createFromParcel(Parcel in) {
            return new AudioModel(in);
        }

        @Override
        public AudioModel[] newArray(int size) {
            return new AudioModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(ownerId);
        dest.writeString(url);
        dest.writeLong(duration);
        dest.writeString(artist);
        dest.writeString(name);
    }
}
