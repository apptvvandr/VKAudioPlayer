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
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("screen_name")
    @Expose
    public String screenName;
    @SerializedName("is_closed")
    @Expose
    public Integer isClosed;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("photo_50")
    @Expose
    public String photo50;
    @SerializedName("photo_100")
    @Expose
    public String photo100;
    @SerializedName("photo_200")
    @Expose
    public String photo200;

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

    public String getPhoto50() {
        return photo50;
    }

    public String getPhoto100() {
        return photo100;
    }

    public String getPhoto200() {
        return photo200;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
