package github.y0rrrsh.vkaudioplayer.database.syncitem;

import android.content.Context;

import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * @author Artur Yorsh. 14.06.16.
 */
public class SyncItemDB {

    private static SyncItemDB instance;
    private static final String BASE_NAME = "vkap_sync_list_";

    private Realm realm;

    private SyncItemDB(Context context, int userId) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context)
                .name(BASE_NAME + userId)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
    }

    public static void init(Context context, int userId) {
        instance = new SyncItemDB(context, userId);
    }

    public static SyncItemDB getInstance() {
        return instance;
    }

    public boolean isSyncEnabledForId(int id) {
        return realm.where(SyncItem.class).equalTo("id", id).findFirst() != null;
    }

    public void setSyncEnabledForId(int id, boolean enabled) {
        if (enabled) {
            SyncItem item = new SyncItem(id);
            realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
        } else {
            realm.executeTransaction(realm1 -> realm1.where(SyncItem.class).equalTo("id", id).findAll().deleteAllFromRealm());
        }
    }

    public long getSyncMillisForId(int id) {
        SyncItem syncItem = realm.where(SyncItem.class).equalTo("id", id).findFirst();
        return syncItem == null ? 0 : syncItem.getLastSyncMillis();
    }

    public void setSyncMillisForId(int id, long millis) {
        SyncItem item = realm.where(SyncItem.class).equalTo("id", id).findFirst();
        if (item != null) realm.executeTransaction(realm1 -> item.setLastSyncMillis(millis));
    }

    /**
     * Remove all items from this db,
     * that are not contained in user's vk items(groups, or friends could be deleted from website)
     */
    public void update() {
        RealmResults<SyncItem> syncItems = realm.where(SyncItem.class).findAll();
        for (SyncItem syncItem : syncItems) {
            if (!VkItemDB.getInstance().contains(syncItem.getId())) {
                setSyncEnabledForId(syncItem.getId(), false);
            }
        }
    }
}
