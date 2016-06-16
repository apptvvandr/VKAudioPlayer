package github.y0rrrsh.vkaudioplayer.network.service.rx;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.y0rrrsh.vkaudioplayer.VKAPApplication;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.network.Callback;
import github.y0rrrsh.vkaudioplayer.network.response.VkArrayResponse;
import github.y0rrrsh.vkaudioplayer.network.response.VkResponse;
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
import static rx.Observable.just;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public class VKAPServiceRxImpl implements VKAPServiceRx {

    private static final String TAG = "VKAPServiceRxImpl";

    private VKAPRetrofitServiceRx service;

    private VKAPServiceRxImpl() {
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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(VKAPRetrofitServiceRx.class);
    }

    public static VKAPServiceRx getInstance() {
        return VKAPServiceRxInstanceHolder.INSTANCE;
    }

    @Override
    public void getAudios(Integer userId, Callback<List<AudioModel>> callback) {
        getAudioDtos(userId).compose(applyMap())
                .subscribe(callback::onResult, callback::onError);
    }

    @Override
    public void getNewAudios(Integer userId, Callback<List<AudioModel>> callback) {
        getAudioDtos(userId).compose(applyFilter()).compose(applyMap())
                .subscribe(callback::onResult, callback::onError);
    }

    @Override
    public void getNewAudios(int[] ids, Callback<Map<VkItem, List<AudioModel>>> callback) {
        List<Observable<VkResponse<VkArrayResponse<AudioDTO>>>> observables = new ArrayList<>();
        for (Integer id : ids) observables.add(service.getAudios(id));

        Map<VkItem, List<AudioModel>> newAudiosMap = new HashMap<>();
        //noinspection unchecked
        Observable<List<AudioDTO>> combinedDtos = Observable.combineLatest(observables, Arrays::asList)
                .compose(applySchedulers())
                .flatMap(Observable::from)
                .map(object -> ((VkResponse<VkArrayResponse<AudioDTO>>) object))
                .filter(response -> response.getError() == null)
                .flatMap(response -> just(response.getResponse().getItems()));

        combinedDtos.compose(applyFilter()).compose(applyMap())
                .forEach(audioModels -> {
                    Integer ownerId = audioModels.get(0).getOwnerId();
                    VkItem owner = VkItemDB.getInstance().get(ownerId);
                    newAudiosMap.put(owner, audioModels);

                    callback.onResult(newAudiosMap);
                }, callback::onError);
    }

    protected Observable<List<AudioDTO>> getAudioDtos(Integer userId) {
        return service.getAudios(userId)
                .compose(applySchedulers())
                .filter(response -> response.getError() == null)
                .flatMap(response -> just(response.getResponse().getItems()));
    }

    @NonNull
    protected Observable.Transformer<List<AudioDTO>, List<AudioModel>> applyMap() {
        return observable -> observable
                .flatMap(Observable::from)
                .map(dto -> new AudioModel(dto.getId(), dto.getOwnerId(), dto.getUrl(),
                        dto.getDuration(), dto.getArtist(), dto.getTitle(), dto.getDate()))
                .toList();
    }

    @NonNull
    private Observable.Transformer<List<AudioDTO>, List<AudioDTO>> applyFilter() {
        return observable -> observable.flatMap(Observable::from)
                .filter(dto -> dto.getDate() > VKAPPreferences.getLastLoginMillis(VKAPApplication.getContext()) / 1000)
                .toList();
    }

    @NonNull
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return source -> source.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static final class VKAPServiceRxInstanceHolder {
        private static final VKAPServiceRx INSTANCE = new VKAPServiceRxImpl();
    }
}
