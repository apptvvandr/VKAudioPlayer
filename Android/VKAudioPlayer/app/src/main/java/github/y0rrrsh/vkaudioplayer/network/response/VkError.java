package github.y0rrrsh.vkaudioplayer.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Yorsh
 */
public class VkError extends Throwable {

    @SerializedName("error_code")
    @Expose
    public Integer errorCode;

    @SerializedName("error_msg")
    @Expose
    public String errorMsg;

    @Override
    public String getMessage() {
        return String.format("%d: %s", errorCode, errorMsg);
    }
}
