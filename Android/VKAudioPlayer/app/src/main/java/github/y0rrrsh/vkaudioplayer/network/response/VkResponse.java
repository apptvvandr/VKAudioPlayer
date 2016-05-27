package github.y0rrrsh.vkaudioplayer.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Yorsh
 */
public class VkResponse<T> {

    @SerializedName("response")
    @Expose
    private T response;

    @SerializedName("error")
    @Expose
    private VkError error;

    public T getResponse() {
        return response;
    }

    public VkError getError() {
        return error;
    }
}