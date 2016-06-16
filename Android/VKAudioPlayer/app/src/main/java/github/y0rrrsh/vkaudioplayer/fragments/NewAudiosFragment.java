package github.y0rrrsh.vkaudioplayer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.NewMusicAdapter;
import github.y0rrrsh.vkaudioplayer.database.syncitem.SyncItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.network.Callback;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPServiceImpl;
import github.y0rrrsh.vkaudioplayer.network.service.rx.VKAPServiceRxImpl;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public class NewAudiosFragment extends VkTabFragment<NewMusicAdapter> {

    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefresh;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_audios;
    }

    @NonNull
    @Override
    protected String getDataTag() {
        return "new_audio";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        //noinspection ConstantConditions
        swipeRefresh.setOnRefreshListener(() -> onDataRequest(VKAPServiceImpl.getInstance()));

        return contentView;
    }

    @Override
    protected NewMusicAdapter onCreateItemAdapter() {
        return new NewMusicAdapter();
    }

    @Override
    protected void onDataRequest(@NonNull VKAPService api) {
        int[] syncItemIds = SyncItemDB.getInstance().getAllIds();
        VKAPServiceRxImpl.getInstance().getNewAudios(syncItemIds, new Callback<Map<VkItem, List<AudioModel>>>() {
            @Override
            public void onResult(Map<VkItem, List<AudioModel>> result) {
                Log.d("api", "onResult: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("api", "onError: ", e);
            }
        });
    }

    @Override
    public void onDataSizeChanged(int size) {
        super.onDataSizeChanged(size);
        swipeRefresh.setRefreshing(false);
    }
}
