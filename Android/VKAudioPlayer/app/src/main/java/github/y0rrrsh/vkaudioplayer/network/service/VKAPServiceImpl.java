package github.y0rrrsh.vkaudioplayer.network.service;

import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;
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
    public void getGroups(VkArrayCallback<GroupDTO> callback) {
        service.getGroups(true).enqueue(new Callback<VkResponse<VkArrayResponse<GroupDTO>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArrayResponse<GroupDTO>>> call, Response<VkResponse<VkArrayResponse<GroupDTO>>> response) {
                VkArrayResponse<GroupDTO> itemsResponse = response.body().getResponse();
                if (itemsResponse == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                callback.onResponse(itemsResponse.getItems());
            }

            @Override
            public void onFailure(Call<VkResponse<VkArrayResponse<GroupDTO>>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    @Override
    public void getFriends(VkArrayCallback<FriendDTO> callback) {
        service.getFriends("name, photo_200").enqueue(new Callback<VkResponse<VkArrayResponse<FriendDTO>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArrayResponse<FriendDTO>>> call, Response<VkResponse<VkArrayResponse<FriendDTO>>> response) {
                VkArrayResponse<FriendDTO> itemsResponse = response.body().getResponse();
                if (itemsResponse == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                callback.onResponse(itemsResponse.getItems());
            }

            @Override
            public void onFailure(Call<VkResponse<VkArrayResponse<FriendDTO>>> call, Throwable t) {
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
                if (responseId == null) {
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
                if (responseId == null) {
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
    public void restoreAudio(Integer id, Integer ownerId, VkCallback<AudioDTO> callback) {
        service.restoreAudio(id, ownerId).enqueue(new Callback<VkResponse<AudioDTO>>() {
            @Override
            public void onResponse(Call<VkResponse<AudioDTO>> call, Response<VkResponse<AudioDTO>> response) {
                AudioDTO responseAudio = response.body().getResponse();
                if (responseAudio == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                callback.onResponse(responseAudio);
            }

            @Override
            public void onFailure(Call<VkResponse<AudioDTO>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }
}
