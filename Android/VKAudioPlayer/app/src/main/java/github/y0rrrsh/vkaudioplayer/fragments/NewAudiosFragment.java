package github.y0rrrsh.vkaudioplayer.fragments;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import github.y0rrrsh.vkaudioplayer.activities.AudioPlayerActivity;
import github.y0rrrsh.vkaudioplayer.adapters.NewMusicAdapter;
import github.y0rrrsh.vkaudioplayer.database.syncitem.SyncItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.models.NewMusicAdapterItem;
import github.y0rrrsh.vkaudioplayer.network.Callback;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.network.service.rx.VKAPServiceRxImpl;
import github.y0rrrsh.vkaudioplayer.utils.VKAPUtils;

import static github.y0rrrsh.vkaudioplayer.adapters.NewMusicAdapter.ITEM_TYPE_AUDIO;
import static github.y0rrrsh.vkaudioplayer.utils.VKAPUtils.REQUEST_DELAY_NEW_AUDIOS;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public class NewAudiosFragment extends VkTabFragment<NewMusicAdapter> {

    @NonNull
    @Override
    protected String getDataTag() {
        return "new_audio";
    }

    @Override
    protected NewMusicAdapter onCreateItemAdapter() {
        NewMusicAdapter adapter = new NewMusicAdapter();
        adapter.addItemClickListener(ITEM_TYPE_AUDIO, (item, itemPosition, viewHolder)
                -> AudioPlayerActivity.start(getActivity(), Collections.singletonList(((AudioModel) item)), 0));
        return adapter;
    }

    @Override
    protected void onDataRequest(@NonNull VKAPService api) {
        int[] syncItemIds = SyncItemDB.getInstance().getAllIds();
        VKAPServiceRxImpl.getInstance().getNewAudios(syncItemIds, new Callback<Map<VkItem, List<AudioModel>>>() {
            @Override
            public void onResult(Map<VkItem, List<AudioModel>> result) {
                List<NewMusicAdapterItem> items = new ArrayList<>();
                for (Map.Entry<VkItem, List<AudioModel>> entry : result.entrySet()) {
                    items.add(entry.getKey());
                    items.addAll(entry.getValue());
                }
                adapter.setItems(items);
            }

            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
                onDataSizeChanged(0);
            }
        });
    }

    @Override
    protected boolean canPerformDataRequest() {
        boolean isEmpty = super.canPerformDataRequest();
        return isEmpty || VKAPUtils.lastRequestIsOlder(getActivity(), dataTag, REQUEST_DELAY_NEW_AUDIOS);
    }
}
