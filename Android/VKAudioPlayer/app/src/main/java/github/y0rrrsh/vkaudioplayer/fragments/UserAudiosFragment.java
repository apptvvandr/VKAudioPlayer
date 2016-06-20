package github.y0rrrsh.vkaudioplayer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import java.util.List;

import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.activities.AudioPlayerActivity;
import github.y0rrrsh.vkaudioplayer.adapters.UserAudiosAdapter;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.network.Callback;
import github.y0rrrsh.vkaudioplayer.network.service.rx.VKAPServiceRx;
import github.y0rrrsh.vkaudioplayer.utils.VKAPUtils;

import static github.y0rrrsh.vkaudioplayer.utils.VKAPUtils.REQUEST_DEYAY_USER_AUDIOS;

/**
 * @author Artur Yorsh
 */
@FragmentWithArgs
public class UserAudiosFragment extends VkTabFragment<UserAudiosAdapter> {

    @Arg
    int ownerId;
    private VkItem owner;

    private PlaylistReadyListener playlistReadyListener;

    @Override
    protected UserAudiosAdapter onCreateItemAdapter() {
        owner = VkItemDB.getInstance().get(ownerId);
        return new UserAudiosAdapter(owner);
    }

    @NonNull
    @Override
    protected String getDataTag() {
        return "audio";
    }

    @Override
    protected void onDataRequest(@NonNull VKAPServiceRx api) {
        api.getAudios(ownerId, new Callback<List<AudioModel>>() {
            @Override
            public void onResult(List<AudioModel> response) {
                adapter.setItems(response);
                if (playlistReadyListener != null) {
                    playlistReadyListener.onPlaylistReady(response);
                }
            }

            @Override
            public void onError(Throwable t) {
                progressBar.setVisibility(View.GONE);
                onDataSizeChanged(0);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        adapter.addItemClickListener((item, itemPosition, viewHolder) -> {
            List<AudioModel> playlist = adapter.getItems();
            AudioPlayerActivity.start(getActivity(), playlist, itemPosition);
        });

        return contentView;
    }

    @Override
    protected boolean canPerformDataRequest() {
        boolean isEmpty = super.canPerformDataRequest();
        boolean isUserAudios = dataTag.startsWith("main_");
        return isUserAudios ? isEmpty || VKAPUtils.lastRequestIsOlder(getActivity(), dataTag, REQUEST_DEYAY_USER_AUDIOS) : isEmpty;
    }

    @Override
    public void onDataSizeChanged(int size) {
        super.onDataSizeChanged(size);
        if (owner.isSyncEnabled()) {
            // TODO: 6/19/2016:
            long lastSyncSeconds = System.currentTimeMillis() / 1000 - 60;
            owner.setSyncSeconds(lastSyncSeconds);
        }
    }

    @Override
    protected void onEmpty() {
        String ownerName = ownerId == VKApi.USER_ID ? "your" : owner.getName() + "'s";
        String message = String.format("Seems, %s playlist is empty,\nor something went wrong.", ownerName);
        emptyView.setMessage(message);
        emptyView.show();
    }

    public void setPlaylistReadyListener(PlaylistReadyListener listener) {
        this.playlistReadyListener = listener;
    }

    public interface PlaylistReadyListener {
        void onPlaylistReady(List<AudioModel> playlist);
    }
}