package github.y0rrrsh.vkaudioplayer.vkapi;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.models.VkItem;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPRetrofitService;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPServiceImpl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Artur Yorsh
 */
public class VKApi {

    private static VKApi instance;

    private String userId;
    private String accessToken;
    private String tokenExpiresIn;

    //TODO: Implement DI for api service service
    private VKAPService service;

    private VKApi(String accessToken, String tokenExpiresIn, String userId) {
        this.accessToken = accessToken;
        this.tokenExpiresIn = tokenExpiresIn;
        this.userId = userId;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        Interceptor requestInterceptor = chain -> {
            Request original = chain.request();

            HttpUrl url = original.url().newBuilder()
                    .addQueryParameter("access_token", accessToken)
                    .addQueryParameter("v", "5.52")
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

        service = new VKAPServiceImpl(retrofit.create(VKAPRetrofitService.class));
    }

    public static void init(@NonNull Context context) {
        if (instance != null) {
            return;
        }
        String[] tokenValues = VKPreferences.getTokenValues(context);
        if (tokenValues[0] == null) {
            return;
        }
        instance = new VKApi(tokenValues[0], tokenValues[1], tokenValues[2]);
    }

    static void init(Context context, @NonNull String token, String expiresIn, String userId) {
        VKPreferences.saveTokenValues(context, token, expiresIn, userId);
        instance = new VKApi(token, expiresIn, userId);
    }

    public static void login(@NonNull Activity activity, @NonNull String appId, @NonNull String appScope) {
        VKLoginActivity.startForResult(activity, appId, appScope);
    }

    public static boolean isInitialized() {
        return instance != null;
    }

    @Nullable
    public static VKAPService getApiService() {
        return instance == null ? null : instance.service;
    }

    public interface VkCallback<T extends VkItem> {
        void onResponse(T response);

        void onError(Throwable t);
    }

    public interface VkArrayCallback<T extends VkItem> {
        void onResponse(List<T> response);

        void onError(Throwable t);
    }
}