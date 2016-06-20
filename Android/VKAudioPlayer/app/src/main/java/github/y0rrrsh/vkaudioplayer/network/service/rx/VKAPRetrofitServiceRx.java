package github.y0rrrsh.vkaudioplayer.network.service.rx;

import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.UserDTO;
import github.y0rrrsh.vkaudioplayer.network.response.VkArray;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public interface VKAPRetrofitServiceRx {

    @GET("audio.get")
    Observable<VkResponse<VkArray<AudioDTO>>> getAudios(@Query("owner_id") Integer ownerId);

    @GET("groups.get")
    Observable<VkResponse<VkArray<GroupDTO>>> getGroups(@Query("extended") boolean extended, @Query("fields") String fields);

    @GET("friends.get")
    Observable<VkResponse<VkArray<FriendDTO>>> getFriends(@Query("order") String order, @Query("fields") String fields);

    @GET("users.get")
    Observable<VkArrayResponse<UserDTO>> getUserInfo(@Query("user_ids") Integer id, @Query("fields") String fields);

    @GET("audio.add")
    Observable<VkResponse<Integer>> addAudio(@Query("audio_id") Integer id, @Query("owner_id") Integer ownerId);

    @GET("audio.delete")
    Observable<VkResponse<Integer>> removeAudio(@Query("audio_id") Integer id, @Query("owner_id") Integer ownerId);

    @GET("audio.restore")
    Observable<VkResponse<AudioDTO>> restoreAudio(@Query("audio_id") Integer id, @Query("owner_id") Integer ownerId);
}
