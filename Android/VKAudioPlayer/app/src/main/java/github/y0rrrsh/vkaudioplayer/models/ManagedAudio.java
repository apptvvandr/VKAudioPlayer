package github.y0rrrsh.vkaudioplayer.models;

/**
 * @author Artur Yorsh. 07.06.16.
 *
 * Support model for handling removed, added or restored audios from current playlist
 */
public class ManagedAudio {

    boolean wasRemoved;
    int id;

    public ManagedAudio(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean wasRemoved() {
        return wasRemoved;
    }

    public void setWasRemoved(boolean wasRemoved) {
        this.wasRemoved = wasRemoved;
    }
}
