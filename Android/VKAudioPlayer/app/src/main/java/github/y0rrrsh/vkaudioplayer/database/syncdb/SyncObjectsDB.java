package github.y0rrrsh.vkaudioplayer.database.syncdb;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.VKAPApplication;
import github.y0rrrsh.vkaudioplayer.models.FriendModel;
import github.y0rrrsh.vkaudioplayer.models.GroupModel;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public class SyncObjectsDB {

    public static final String BASE_NAME = "sync_list_";

    private SyncObjectsDB(Context context) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context)
                .name(BASE_NAME + VKApi.USER_ID)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static SyncObjectsDB getInstance() {
        return SyncObjectsDBInstanceHolder.INSTANCE;
    }

    public <T extends Synchronized> void updateData(DataType type, List<T> newData) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            realm.delete(type.dataClass);
            realm.copyToRealmOrUpdate(newData);
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends Synchronized> List<T> getAll(DataType type) {
        Class<? extends Synchronized> dataClass = type.dataClass;
        return (List<T>) Realm.getDefaultInstance().where(dataClass).findAll();
    }

    @SuppressWarnings("unchecked")
    public <T extends Synchronized> List<T> getAll() {
        List<T> objects = new ArrayList<>();

        objects.addAll(getAll(DataType.FRIENDS));
        objects.addAll(getAll(DataType.GROUPS));

        return objects;
    }

    public void setSyncForObjectEnabled(Synchronized object, boolean enabled) {
        Realm.getDefaultInstance().executeTransaction(realm ->
                object.setSyncEnabled(enabled));
    }

    public void setSyncForAllEnabled(boolean enabled) {
        List<Synchronized> all = getAll();
        Realm.getDefaultInstance().executeTransaction(realm -> {
            for (Synchronized item : all) {
                item.setSyncEnabled(enabled);
            }
        });
    }

    public enum DataType {
        FRIENDS(FriendModel.class), GROUPS(GroupModel.class);

        private Class<? extends Synchronized> dataClass;

        DataType(Class<? extends Synchronized> dataClass) {
            this.dataClass = dataClass;
        }

        public String getName() {
            return this.dataClass.getSimpleName();
        }

        public static DataType forName(String name) {
            if (FriendModel.class.getSimpleName().equals(name)) return DataType.FRIENDS;
            if (GroupModel.class.getSimpleName().equals(name)) return DataType.GROUPS;
            throw new IllegalArgumentException("Incorrect class name");
        }
    }

    private static final class SyncObjectsDBInstanceHolder {
        private static final SyncObjectsDB INSTANCE = new SyncObjectsDB(VKAPApplication.getContext());
    }
}
