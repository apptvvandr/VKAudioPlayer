package github.y0rrrsh.vkaudioplayer.network.service;

import android.support.annotation.Nullable;

import github.y0rrrsh.vkapi.VKApi.VKArrayCallback;
import github.y0rrrsh.vkapi.VKApi.VKCallback;
import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;
import github.y0rrrsh.vkaudioplayer.models.dto.UserDTO;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkError;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkSimpleArrayResponse;
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
    public void getAudios(String userId, VKArrayCallback<AudioDTO> callback) {
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
    public void getGroups(VKArrayCallback<GroupDTO> callback) {
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
    public void getFriends(VKArrayCallback<FriendDTO> callback) {
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
    public void restoreAudio(Integer id, Integer ownerId, VKCallback<AudioDTO> callback) {
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

    @Override
    public void getUserInfo(Integer id, VKCallback<UserDTO> callback) {
        service.getUserInfo(id, "photo_big").enqueue(new Callback<VkSimpleArrayResponse<UserDTO>>() {
            @Override
            public void onResponse(Call<VkSimpleArrayResponse<UserDTO>> call, Response<VkSimpleArrayResponse<UserDTO>> response) {
                VkError error = response.body().getError();
                if (error != null) {
                    onFailure(call, error);
                    return;
                }
                callback.onResponse(response.body().getItems().get(0));
            }

            @Override
            public void onFailure(Call<VkSimpleArrayResponse<UserDTO>> call, Throwable t) {
                t.printStackTrace();
                callback.onError(t);
            }
        });
    }

    private static final class VKAPServiceInstanceHolder {
        private static VKAPService INSTANCE = new VKAPServiceImpl();
    }
}
