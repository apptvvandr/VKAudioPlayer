package github.y0rrrsh.vkaudioplayer.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import github.y0rrrsh.vkaudioplayer.vkapi.VkItem;

/**
 * @author Artur Yorsh
 */
public class Friend implements VkItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("photo_200")
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

    public String getPhoto200() {
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
