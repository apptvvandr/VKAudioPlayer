package github.y0rrrsh.vkaudioplayer.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import github.y0rrrsh.vkapi.VKItem;

/**
 * @author Artur Yorsh
 */
public class GroupDTO implements VKItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("is_closed")
    @Expose
    private Integer isClosed;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("photo_max_orig")
    @Expose
    private String photoMaxOrig;

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public Integer getIsClosed() {
        return isClosed;
    }

    public String getType() {
        return type;
    }

    public String getPhotoMaxOrig() {
        return photoMaxOrig;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
