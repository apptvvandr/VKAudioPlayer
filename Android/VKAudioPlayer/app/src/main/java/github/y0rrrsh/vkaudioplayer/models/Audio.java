package github.y0rrrsh.vkaudioplayer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Yorsh
 */
public class Audio implements VkItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("genre_id")
    @Expose
    private Integer genreId;
    @SerializedName("lyrics_id")
    @Expose
    private Integer lyricsId;

    public Integer getId() {
        return id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public Integer getLyricsId() {
        return lyricsId;
    }

}
