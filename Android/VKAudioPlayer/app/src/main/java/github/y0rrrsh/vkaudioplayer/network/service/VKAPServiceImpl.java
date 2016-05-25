package github.y0rrrsh.vkaudioplayer.network.service;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.models.Audio;
import github.y0rrrsh.vkaudioplayer.network.service.VkApi.VkArrayCallback;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Artur Yorsh
 */
class VKAPServiceImpl implements VKAPService {

    private VKAPRetrofitService service;

    VKAPServiceImpl(VKAPRetrofitService service) {
        this.service = service;
    }

    @Override
    public void getAudios(String userId, VkArrayCallback<Audio> callback) {
        service.getAudios(userId).enqueue(new Callback<VkResponse<VkArrayResponse<Audio>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArrayResponse<Audio>>> call, Response<VkResponse<VkArrayResponse<Audio>>> response) {
                List<Audio> items = response.body().getResponse().getItems();
                callback.onResponse(items);
            }

            @Override
            public void onFailure(Call<VkResponse<VkArrayResponse<Audio>>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }
}
