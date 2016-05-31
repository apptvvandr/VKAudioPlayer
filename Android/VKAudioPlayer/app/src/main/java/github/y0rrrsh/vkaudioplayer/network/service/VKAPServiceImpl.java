package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.Friend;
import github.y0rrrsh.vkaudioplayer.models.dto.Group;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi.VkArrayCallback;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi.VkCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Artur Yorsh
 */
public class VKAPServiceImpl implements VKAPService {

    private static final String TAG = "VKAPServiceImpl";

    private VKAPRetrofitService service;

    public VKAPServiceImpl(VKAPRetrofitService service) {
        this.service = service;
    }

    @Override
    public void getAudios(String userId, VkArrayCallback<AudioDTO> callback) {
        service.getAudios(userId).enqueue(new Callback<VkResponse<VkArrayResponse<AudioDTO>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArrayResponse<AudioDTO>>> call, Response<VkResponse<VkArrayResponse<AudioDTO>>> response) {
                VkArrayResponse<AudioDTO> itemsResponse = response.body().getResponse();
                if (itemsResponse == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                callback.onResponse(itemsResponse.getItems());
            }

            @Override
            public void onFailure(Call<VkResponse<VkArrayResponse<AudioDTO>>> call, Throwable t) {
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
        service.getFriends("name, photo_200").enqueue(new Callback<VkResponse<VkArrayResponse<Friend>>>() {
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

    @Override
    public void addAudio(Integer id, Integer ownerId, VkCallback<Integer> callback) {
        service.addAudio(id, ownerId).enqueue(new Callback<VkResponse<Integer>>() {
            @Override
            public void onResponse(Call<VkResponse<Integer>> call, Response<VkResponse<Integer>> response) {
                Integer responseId = response.body().getResponse();
                if (responseId == null){
                    onFailure(call, response.body().getError());
                    return;
                }
                callback.onResponse(responseId);
            }

            @Override
            public void onFailure(Call<VkResponse<Integer>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    @Override
    public void removeAudio(Integer id, Integer ownerId, VkCallback<Integer> callback) {
        service.removeAudio(id, ownerId).enqueue(new Callback<VkResponse<Integer>>() {
            @Override
            public void onResponse(Call<VkResponse<Integer>> call, Response<VkResponse<Integer>> response) {
                Integer responseId = response.body().getResponse();
                if (responseId == null){
                    onFailure(call, response.body().getError());
                    return;
                }
                callback.onResponse(responseId);
            }

            @Override
            public void onFailure(Call<VkResponse<Integer>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }
}
