package github.y0rrrsh.vkaudioplayer.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artur Yorsh
 *
 * Should be the type class of {@link VkResponse}
 *         <p>
 *         json response schema:
 *         response: {
 *         count: 120,
 *         items: [{
 *         id: 456239261,
 *         owner_id: 124259152,
 *         artist: 'Magna',
 *         title: 'Divide (Miami Edit)',
 *         duration: 256,
 *         date: 1466378101,
 *         url: 'https://cs9-7v4.v...p-Ry9AxBjW71xddV1rM',
 *         genre_id: 1001
 *         }, {
 *         id: 456239259,
 *         owner_id: 124259152,
 *         artist: 'aUtOdiDakT, Kroyclub, Trumpdisco & YOL',
 *         title: 'Gangsta',
 *         duration: 231,
 *         date: 1466339485,
 *         url: 'https://psv4.vk.m...iTKxUdPhk8jl4zH6f2k'
 *         }]
 *         }
 */
public class VkArray<T> {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("items")
    @Expose
    private List<T> items = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public List<T> getItems() {
        return items;
    }
}