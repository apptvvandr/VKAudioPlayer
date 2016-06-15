package github.y0rrrsh.vkaudioplayer.database.vkitem;

import io.realm.RealmModel;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public interface VkItem extends RealmModel {

    int getId();

    String getName();

    String getAvatarUrl();

    boolean isSyncEnabled();

    void setSyncEnabled(boolean enabled);

    long getSyncSeconds();

    void setSyncSeconds(long seconds);
}
