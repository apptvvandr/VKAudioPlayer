package github.y0rrrsh.vkaudioplayer.database.vkitem;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import github.y0rrrsh.vkaudioplayer.models.FriendModel;
import github.y0rrrsh.vkaudioplayer.models.GroupModel;
import github.y0rrrsh.vkaudioplayer.models.UserModel;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Artur Yorsh. 09.06.16.
 *         <p>
 *         Database to store all user groups and friends
 */
public class VkItemDB {

    private static VkItemDB instance;

    private static final String BASE_NAME = "vkap_vk_items_";

    private Realm realm;

    private VkItemDB(Context context, int userId) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context)
                .name(BASE_NAME + userId)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
    }

    public static void init(Context context, int userId) {
        instance = new VkItemDB(context, userId);
    }

    public static VkItemDB getInstance() {
        return instance;
    }

    public <T extends VkItem> void put(T object) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(object));
    }

    public <T extends VkItem> void update(DataType type, List<T> newData) {
        realm.executeTransaction(realm -> {
            realm.delete(type.dataClass);
            realm.copyToRealmOrUpdate(newData);
        });
    }

    public VkItem get(DataType type, int id) {
        return realm.where(type.dataClass).equalTo("id", id).findFirst();
    }

    @SuppressWarnings("unchecked")
    public <T extends VkItem> List<T> getAll(DataType type) {
        Class<? extends VkItem> dataClass = type.dataClass;
        return (List<T>) realm.where(dataClass).findAll();
    }

    @SuppressWarnings("unchecked")
    public <T extends VkItem> List<T> getAll() {
        List<T> objects = new ArrayList<>();

        objects.addAll(getAll(DataType.USER));
        objects.addAll(getAll(DataType.FRIENDS));
        objects.addAll(getAll(DataType.GROUPS));

        return objects;
    }

    public boolean contains(DataType type, int id) {
        return !realm.where(type.dataClass).equalTo("id", id).findAll().isEmpty();
    }

    public boolean contains(int id) {
        return contains(DataType.USER, id) || contains(DataType.GROUPS, id) || contains(DataType.FRIENDS, id);
    }

    public void setSyncForAllEnabled(boolean enabled) {
        List<VkItem> all = getAll();
        realm.executeTransaction(realm -> {
            for (VkItem item : all) {
                item.setSyncEnabled(enabled);
            }
        });
    }

    public enum DataType {
        USER(UserModel.class), FRIENDS(FriendModel.class), GROUPS(GroupModel.class);

        private Class<? extends VkItem> dataClass;

        DataType(Class<? extends VkItem> dataClass) {
            this.dataClass = dataClass;
        }

        public String getName() {
            return this.dataClass.getSimpleName();
        }

        public static DataType forName(String name) {
            if (UserModel.class.getSimpleName().equals(name)) return DataType.USER;
            if (FriendModel.class.getSimpleName().equals(name)) return DataType.FRIENDS;
            if (GroupModel.class.getSimpleName().equals(name)) return DataType.GROUPS;
            throw new IllegalArgumentException("Incorrect class name: " + name);
        }
    }
}
