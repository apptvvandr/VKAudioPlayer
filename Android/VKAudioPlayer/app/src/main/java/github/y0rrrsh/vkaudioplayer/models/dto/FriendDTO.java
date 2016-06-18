package github.y0rrrsh.vkaudioplayer.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import github.y0rrrsh.vkapi.VKItem;

/**
 * @author Artur Yorsh
 */
public class FriendDTO implements VKItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("photo_max")
    @Expose
    private String photo200;
    @SerializedName("online")
    @Expose
    private Integer online;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhotoMax() {
        return photo200;
    }

    public Integer getOnline() {
        return online;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
