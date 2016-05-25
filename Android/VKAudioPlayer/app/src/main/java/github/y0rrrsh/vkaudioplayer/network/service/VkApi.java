package github.y0rrrsh.vkaudioplayer.network.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.models.VkItem;
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
public class VkApi {

    private static VkApi instance;

    private String userId;
    private String accessToken;
    private String tokenExpiresIn;

    private VKAPService service;

    private VkApi(String accessToken, String tokenExpiresIn, String userId) {
        this.accessToken = accessToken;
        this.tokenExpiresIn = tokenExpiresIn;
        this.userId = userId;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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

    public static void init(@NonNull String urlWithToken) {
        String tokenSubstring = urlWithToken.split("#")[1];
        String[] accessParams = tokenSubstring.split("&");

        String token = accessParams[0].split("=")[1];
        String expiresIn = accessParams[1].split("=")[1];
        String userId = accessParams[2].split("=")[1];

        instance = new VkApi(token, expiresIn, userId);
    }

    @Nullable
    public static VKAPService getServiceInstance() {
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