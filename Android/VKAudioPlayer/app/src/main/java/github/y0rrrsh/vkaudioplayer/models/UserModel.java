package github.y0rrrsh.vkaudioplayer.models;

import github.y0rrrsh.vkaudioplayer.adapters.NewMusicAdapter;
import github.y0rrrsh.vkaudioplayer.database.syncitem.SyncItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Artur Yorsh. 14.06.16.
 */
public class UserModel extends RealmObject implements VkItem {

    @PrimaryKey
    private int id;
    private String firstName;
    private String lastName;
    private String photoBig;

    public UserModel() {
    }

    public UserModel(int id, String firstName, String lastName, String photoMax) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoBig = photoMax;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getAvatarUrl() {
        return photoBig;
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

    @Override
    public int getItemType() {
        return NewMusicAdapter.ITEM_TYPE_HEADER;
    }
}
