package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkaudioplayer.models.Audio;
import github.y0rrrsh.vkaudioplayer.models.Group;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Artur Yorsh
 */
interface VKAPRetrofitService {

    @GET("audio.get")
    Call<VkResponse<VkArrayResponse<Audio>>> getAudios(@Query("owner_id") String userId);

    @GET("groups.get")
    Call<VkResponse<VkArrayResponse<Group>>> getGroups(@Query("extended") boolean extended);
}
