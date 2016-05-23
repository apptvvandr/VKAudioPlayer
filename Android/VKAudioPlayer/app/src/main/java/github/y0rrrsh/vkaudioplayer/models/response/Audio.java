package github.y0rrrsh.vkaudioplayer.models.response;

/**
 * @author Artur Yorsh
 */
public class Audio extends VkItem {

    private String title;
    private String artist;
    private int duration;

    public Audio(long id, String artist, String title, int duration) {
        super(id);
        this.artist = artist;
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }
}
