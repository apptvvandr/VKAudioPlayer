package github.y0rrrsh.vkaudioplayer.network.service.rx;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.y0rrrsh.vkaudioplayer.VKAPApplication;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.network.Callback;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static github.y0rrrsh.vkapi.VKApi.ACCESS_TOKEN;
import static github.y0rrrsh.vkaudioplayer.BuildConfig.VKAP_API_VERSION;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public class VKAPServiceRxImpl implements VKAPServiceRx {

    private static final String TAG = "VKAPServiceRxImpl";

    private VKAPRetrofitServiceRx service;

    private VKAPServiceRxImpl() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(VKAPRetrofitServiceRx.class);
    }

    public static VKAPServiceRx getInstance() {
        return VKAPServiceRxInstanceHolder.INSTANCE;
    }

    @Override
    public void getAudios(Integer ownerId, Callback<List<AudioModel>> callback) {
        getAudiosObservable(ownerId)
                .subscribe(callback::onResult, callback::onError);
    }

    @Override
    public void getNewAudios(Integer ownerId, Callback<List<AudioModel>> callback) {
        getAudiosObservable(ownerId).compose(applyFilter())
                .subscribe(callback::onResult, callback::onError);
    }

    @Override
    public void getNewAudios(int[] ids, Callback<Map<VkItem, List<AudioModel>>> callback) {
        List<Observable<List<AudioModel>>> obs = new ArrayList<>();
        for (int id : ids) obs.add(getAudiosObservable(id).compose(applyFilter()));

        Observable.combineLatest(obs, audiosList -> {
            Map<VkItem, List<AudioModel>> map = new HashMap<>();

            for (Object arg : audiosList) {
                List<AudioModel> audios = (List<AudioModel>) arg;
                if(audios.isEmpty()) continue;

                Integer ownerId = audios.get(0).getOwnerId();
                VkItem owner = VkItemDB.getInstance().get(ownerId);

                map.put(owner, audios);
            }
            return map;
        }).subscribe(callback::onResult, callback::onError);
    }

    private Observable<List<AudioModel>> getAudiosObservable(Integer ownerId) {
        return service.getAudios(ownerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(response -> response.getError() == null)
                .map(response -> response.getResponse().getItems())
                .flatMap(Observable::from)
                .map(dto -> new AudioModel(dto.getId(), dto.getOwnerId(), dto.getUrl(),
                        dto.getDuration(), dto.getArtist(), dto.getTitle(), dto.getDate()))
                .toList();
    }

    @NonNull
    private Observable.Transformer<List<AudioModel>, List<AudioModel>> applyFilter() {
        return observable -> observable.flatMap(Observable::from)
                .filter(audio -> audio.getDate() > VKAPPreferences.getLastLoginMillis(VKAPApplication.getContext()) / 1000)
                .toList();
    }

    public static final class VKAPServiceRxInstanceHolder {
        private static final VKAPServiceRx INSTANCE = new VKAPServiceRxImpl();
    }
}
