package github.y0rrrsh.vkaudioplayer.database.syncitem;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Artur Yorsh. 14.06.16.
 */
public class SyncItem extends RealmObject {

    @PrimaryKey int id;
    long lastSyncMillis;

    public SyncItem() {
    }

    SyncItem(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }

    long getLastSyncMillis() {
        return lastSyncMillis;
    }

    void setLastSyncMillis(long lastSyncMillis) {
        this.lastSyncMillis = lastSyncMillis;
    }
}
