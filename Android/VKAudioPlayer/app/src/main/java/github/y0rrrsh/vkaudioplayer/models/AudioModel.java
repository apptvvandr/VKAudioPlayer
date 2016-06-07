package github.y0rrrsh.vkaudioplayer.models;

import github.y0rrrsh.streamplayer.StreamPlayer.StreamItem;

/**
 * @author Artur Yorsh. 30.05.16.
 */
public class AudioModel implements StreamItem {

    private Integer id;
    private Integer ownerId;

    private String url;
    private int duration;
    private String artist;
    private String name;

    public AudioModel(Integer id, Integer ownerId, String url, int duration, String artist, String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.url = url;
        this.duration = duration;
        this.artist = artist;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AudioModel that = (AudioModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }
}
