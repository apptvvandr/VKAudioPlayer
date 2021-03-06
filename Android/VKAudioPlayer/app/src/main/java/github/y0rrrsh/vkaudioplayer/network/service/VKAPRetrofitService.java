package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.UserDTO;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkSimpleArrayResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Artur Yorsh
 */
public interface VKAPRetrofitService {

    @GET("audio.get")
    Call<VkResponse<VkArrayResponse<AudioDTO>>> getAudios(@Query("owner_id") Integer userId);

    @GET("groups.get")
    Call<VkResponse<VkArrayResponse<GroupDTO>>> getGroups(@Query("extended") boolean extended);

    @GET("friends.get")
    Call<VkResponse<VkArrayResponse<FriendDTO>>> getFriends(@Query("fields") String fields);

    @GET("audio.add")
    Call<VkResponse<Integer>> addAudio(@Query("audio_id") Integer id, @Query("owner_id") Integer ownerId);

    @GET("audio.delete")
    Call<VkResponse<Integer>> removeAudio(@Query("audio_id") Integer id, @Query("owner_id") Integer ownerId);

    @GET("audio.restore")
    Call<VkResponse<AudioDTO>> restoreAudio(@Query("audio_id") Integer id, @Query("owner_id") Integer ownerId);

    @GET("users.get")
    Call<VkSimpleArrayResponse<UserDTO>> getUserInfo(@Query("user_ids") Integer id, @Query("fields") String fields);
}
