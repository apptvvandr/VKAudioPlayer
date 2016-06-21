package github.y0rrrsh.vkaudioplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

import github.y0rrrsh.vkaudioplayer.database.syncdb.Synchronized;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public class GroupModel extends RealmObject implements Synchronized {

    @PrimaryKey
    private int id;
    private String name;
    private String avatarUrl;
    private boolean isSyncEnabled;

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
        return isSyncEnabled;
    }

    @Override
    public void setSyncEnabled(boolean enabled) {
        this.isSyncEnabled = enabled;
    }

    public static final Parcelable.Creator<GroupModel> CREATOR = new Parcelable.Creator<GroupModel>() {
        public GroupModel createFromParcel(Parcel in) {
            return new GroupModel(in);
        }

        public GroupModel[] newArray(int size) {
            return new GroupModel[size];
        }
    };

    private GroupModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.avatarUrl = in.readString();
        this.isSyncEnabled = in.readInt() == 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(avatarUrl);
        dest.writeInt(isSyncEnabled ? 0 : 1);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
