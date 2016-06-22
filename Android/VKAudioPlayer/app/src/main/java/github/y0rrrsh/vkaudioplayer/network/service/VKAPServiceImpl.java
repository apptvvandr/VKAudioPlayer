package github.y0rrrsh.vkaudioplayer.network.service;

import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;

import github.y0rrrsh.vkapi.VKApi.VKArrayCallback;
import github.y0rrrsh.vkapi.VKApi.VKCallback;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.models.FriendModel;
import github.y0rrrsh.vkaudioplayer.models.GroupModel;
import github.y0rrrsh.vkaudioplayer.models.UserModel;
import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.UserDTO;
import github.y0rrrsh.vkaudioplayer.models.mapper.ResponseAudioMapper;
import github.y0rrrsh.vkaudioplayer.models.mapper.ResponseFriendsMapper;
import github.y0rrrsh.vkaudioplayer.models.mapper.ResponseGroupsMapper;
import github.y0rrrsh.vkaudioplayer.models.mapper.common.ResponseUserMapper;
import github.y0rrrsh.vkaudioplayer.network.response.VkArray;
import github.y0rrrsh.vkaudioplayer.network.response.VkError;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static github.y0rrrsh.vkapi.VKApi.ACCESS_TOKEN;
import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_API_VERSION;

/**
 * @author Artur Yorsh
 */
public class VKAPServiceImpl implements VKAPService {

    private VKAPRetrofitService service;

    private VKAPServiceImpl() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor requestInterceptor = chain -> {
            Request original = chain.request();

            HttpUrl url = original.url().newBuilder()
                    .addQueryParameter("access_token", ACCESS_TOKEN)
                    .addQueryParameter("v", VKAP_API_VERSION)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            return chain.proceed(requestBuilder.build());
        };

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(VKAPRetrofitService.class);
    }

    @Nullable
    public static VKAPService getInstance() {
        return ACCESS_TOKEN == null ? null : VKAPServiceInstanceHolder.INSTANCE;
    }

    @Override
    public void getAudios(Integer userId, VKArrayCallback<AudioModel> callback) {
        service.getAudios(userId).enqueue(new Callback<VkResponse<VkArray<AudioDTO>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArray<AudioDTO>>> call, Response<VkResponse<VkArray<AudioDTO>>> response) {
                VkArray<AudioDTO> itemsResponse = response.body().getResponse();
                if (itemsResponse == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                List<AudioModel> audioModels = new ResponseAudioMapper().map(itemsResponse.getItems());
                callback.onResponse(audioModels);
            }

            @Override
            public void onFailure(Call<VkResponse<VkArray<AudioDTO>>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    @Override
    public void getGroups(VKArrayCallback<GroupModel> callback) {
        service.getGroups(true, "photo_max_orig").enqueue(new Callback<VkResponse<VkArray<GroupDTO>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArray<GroupDTO>>> call, Response<VkResponse<VkArray<GroupDTO>>> response) {
                VkArray<GroupDTO> itemsResponse = response.body().getResponse();
                if (itemsResponse == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                List<GroupModel> groupModels = new ResponseGroupsMapper().map(itemsResponse.getItems());
                callback.onResponse(groupModels);
            }

            @Override
            public void onFailure(Call<VkResponse<VkArray<GroupDTO>>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    @Override
    public void getFriends(VKArrayCallback<FriendModel> callback) {
        service.getFriends("hints", "name, photo_max_orig").enqueue(new Callback<VkResponse<VkArray<FriendDTO>>>() {
            @Override
            public void onResponse(Call<VkResponse<VkArray<FriendDTO>>> call, Response<VkResponse<VkArray<FriendDTO>>> response) {
                VkArray<FriendDTO> itemsResponse = response.body().getResponse();
                if (itemsResponse == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                List<FriendModel> friendModels = new ResponseFriendsMapper().map(itemsResponse.getItems());
                callback.onResponse(friendModels);
            }

            @Override
            public void onFailure(Call<VkResponse<VkArray<FriendDTO>>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    @Override
    public void addAudio(Integer id, Integer ownerId, VKCallback<Integer> callback) {
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
    public void removeAudio(Integer id, Integer ownerId, VKCallback<Integer> callback) {
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
    public void restoreAudio(Integer id, Integer ownerId, VKCallback<AudioModel> callback) {
        service.restoreAudio(id, ownerId).enqueue(new Callback<VkResponse<AudioDTO>>() {
            @Override
            public void onResponse(Call<VkResponse<AudioDTO>> call, Response<VkResponse<AudioDTO>> response) {
                AudioDTO responseAudio = response.body().getResponse();
                if (responseAudio == null) {
                    onFailure(call, response.body().getError());
                    return;
                }
                List<AudioModel> audioModels = new ResponseAudioMapper().map(Collections.singletonList(responseAudio));
                callback.onResponse(audioModels.get(0));
            }

            @Override
            public void onFailure(Call<VkResponse<AudioDTO>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    @Override
    public void getUserInfo(Integer id, VKCallback<UserModel> callback) {
        service.getUserInfo(id, "photo_max_orig").enqueue(new Callback<VkArrayResponse<UserDTO>>() {
            @Override
            public void onResponse(Call<VkArrayResponse<UserDTO>> call, Response<VkArrayResponse<UserDTO>> response) {
                VkError error = response.body().getError();
                if (error != null) {
                    onFailure(call, error);
                    return;
                }
                UserModel userModel = new ResponseUserMapper().map(response.body().getResponse().get(0));
                callback.onResponse(userModel);
            }

            @Override
            public void onFailure(Call<VkArrayResponse<UserDTO>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    private static final class VKAPServiceInstanceHolder {
        private static VKAPService INSTANCE = new VKAPServiceImpl();
    }
}
