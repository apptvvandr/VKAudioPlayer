package github.y0rrrsh.vkaudioplayer.network.service.rx;

import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public interface VKAPRetrofitServiceRx {

    @GET("audio.get")
    Observable<VkResponse<VkArrayResponse<AudioDTO>>> getAudios(@Query("owner_id") Integer ownerId);
}
