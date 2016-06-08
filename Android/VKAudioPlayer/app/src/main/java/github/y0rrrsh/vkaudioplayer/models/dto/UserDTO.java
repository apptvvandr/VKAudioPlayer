package github.y0rrrsh.vkaudioplayer.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import github.y0rrrsh.vkapi.VKItem;

/**
 * @author Artur Yorsh. 08.06.16.
 */
public class UserDTO implements VKItem {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("photo_big")
    @Expose
    public String photoBig;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhotoBig() {
        return photoBig;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
