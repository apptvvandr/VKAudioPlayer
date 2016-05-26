package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkaudioplayer.models.Audio;
import github.y0rrrsh.vkaudioplayer.models.Friend;
import github.y0rrrsh.vkaudioplayer.models.Group;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
import github.y0rrrsh.vkaudioplayer.network.service.VkApi.VkArrayCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Artur Yorsh
 */
class VKAPServiceImpl implements VKAPService {

    private static final String TAG = "VKAPServiceImpl";

    private VKAPRetrofitService service;

    VKAPServiceImpl(VKAPRetrofitService service) {
        this.service = service;
    }

    @Override
    public void getAudios(String userId, VkArrayCallback<Audio> callback) {
        service.getAudios(userId).enqueue(new Callback<VkResponse<VkArrayResponse<Audio>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArrayResponse<Audio>>> call, Response<VkResponse<VkArrayResponse<Audio>>> response) {
                VkArrayResponse<Audio> itemsResponse = response.body().getResponse();
                if (itemsResponse == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                callback.onResponse(itemsResponse.getItems());
            }

            @Override
            public void onFailure(Call<VkResponse<VkArrayResponse<Audio>>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    @Override
    public void getGroups(VkArrayCallback<Group> callback) {
        service.getGroups(true).enqueue(new Callback<VkResponse<VkArrayResponse<Group>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArrayResponse<Group>>> call, Response<VkResponse<VkArrayResponse<Group>>> response) {
                VkArrayResponse<Group> itemsResponse = response.body().getResponse();
                if (itemsResponse == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                callback.onResponse(itemsResponse.getItems());
            }

            @Override
            public void onFailure(Call<VkResponse<VkArrayResponse<Group>>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    @Override
    public void getFriends(VkArrayCallback<Friend> callback) {
        service.getFriends("name, photo_100").enqueue(new Callback<VkResponse<VkArrayResponse<Friend>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArrayResponse<Friend>>> call, Response<VkResponse<VkArrayResponse<Friend>>> response) {
                VkArrayResponse<Friend> itemsResponse = response.body().getResponse();
                if (itemsResponse == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                callback.onResponse(itemsResponse.getItems());
            }

            @Override
            public void onFailure(Call<VkResponse<VkArrayResponse<Friend>>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }
}
