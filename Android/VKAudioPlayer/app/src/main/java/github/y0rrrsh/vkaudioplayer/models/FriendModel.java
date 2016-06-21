package github.y0rrrsh.vkaudioplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

import github.y0rrrsh.vkaudioplayer.database.syncdb.Synchronized;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public class FriendModel extends RealmObject implements Synchronized {

    @PrimaryKey
    private int id;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private boolean isSyncEnabled;

    public FriendModel() {
    }

    public FriendModel(int id, String firstName, String lastName, String photo200) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarUrl = photo200;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public static final Parcelable.Creator<FriendModel> CREATOR = new Parcelable.Creator<FriendModel>() {
        public FriendModel createFromParcel(Parcel in) {
            return new FriendModel(in);
        }

        public FriendModel[] newArray(int size) {
            return new FriendModel[size];
        }
    };

    private FriendModel(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.avatarUrl = in.readString();
        this.isSyncEnabled = in.readInt() == 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(avatarUrl);
        dest.writeInt(isSyncEnabled ? 0 : 1);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
