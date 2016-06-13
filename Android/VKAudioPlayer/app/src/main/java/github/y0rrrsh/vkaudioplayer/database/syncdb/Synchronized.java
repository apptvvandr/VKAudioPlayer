package github.y0rrrsh.vkaudioplayer.database.syncdb;

import android.os.Parcelable;

import io.realm.RealmModel;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public interface Synchronized extends RealmModel, Parcelable {

    int getId();

    String getName();

    String getAvatarUrl();

    boolean isSyncEnabled();

    void setSyncEnabled(boolean enabled);
}
