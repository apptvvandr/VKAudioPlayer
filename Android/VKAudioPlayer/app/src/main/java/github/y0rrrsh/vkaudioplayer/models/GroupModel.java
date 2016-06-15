package github.y0rrrsh.vkaudioplayer.models;

import github.y0rrrsh.vkaudioplayer.database.syncitem.SyncItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public class GroupModel extends RealmObject implements VkItem {

    @PrimaryKey
    private int id;
    private String name;
    private String avatarUrl;

    public GroupModel() {
    }

    public GroupModel(int id, String name, String photo200) {
        this.id = id;
        this.name = name;
        this.avatarUrl = photo200;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public boolean isSyncEnabled() {
        return SyncItemDB.getInstance().isSyncEnabledForId(id);
    }

    @Override
    public void setSyncEnabled(boolean enabled) {
        SyncItemDB.getInstance().setSyncEnabledForId(id, enabled);
    }

    @Override
    public long getSyncSeconds() {
        return SyncItemDB.getInstance().getSyncMillisForId(id);
    }

    @Override
    public void setSyncSeconds(long seconds) {
        SyncItemDB.getInstance().setSyncMillisForId(id, seconds);
    }
}
