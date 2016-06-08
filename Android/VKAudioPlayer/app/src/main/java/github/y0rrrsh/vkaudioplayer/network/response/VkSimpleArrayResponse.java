package github.y0rrrsh.vkaudioplayer.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artur Yorsh. 08.06.16.
 */
public class VkSimpleArrayResponse<T> {

    @SerializedName("response")
    @Expose
    private List<T> response = new ArrayList<>();

    @SerializedName("error")
    @Expose
    private VkError error;

    public List<T> getItems() {
        return response;
    }

    public VkError getError() {
        return error;
    }
}
