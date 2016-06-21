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

import github.y0rrrsh.vkapi.VKApi.VKArrayCallback;
import github.y0rrrsh.vkaudioplayer.activities.AudioPlayerActivity;
import github.y0rrrsh.vkaudioplayer.adapters.UserAudiosAdapter;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.models.dto.AudioDTO;
import github.y0rrrsh.vkaudioplayer.models.mapper.ResponseAudioMapper;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.utils.VKAPUtils;

/**
 * @author Artur Yorsh
 */
@FragmentWithArgs
public class UserAudiosFragment extends VkTabFragment<UserAudiosAdapter> {

    @Arg(required = false)
    String userId;

    @Arg(required = false)
    String ownerName;

    private PlaylistReadyListener playlistReadyListener;

    @Override
    protected UserAudiosAdapter onCreateItemAdapter() {
        return new UserAudiosAdapter();
    }

    @NonNull
    @Override
    protected String getDataTag() {
        return "audio";
    }

    @Override
    protected void onDataRequest(@NonNull VKAPService api) {
        api.getAudios(userId, new VKArrayCallback<AudioDTO>() {
            @Override
            public void onResponse(List<AudioDTO> response) {
                List<AudioModel> playlist = new ResponseAudioMapper().map(response);
                adapter.setItems(playlist);
                if (playlistReadyListener != null) {
                    playlistReadyListener.onPlaylistReady(playlist);
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

        adapter.setItemClickListener((item, itemPosition, viewHolder) -> {
            List<AudioModel> playlist = adapter.getItems();
            AudioPlayerActivity.start(getActivity(), playlist, itemPosition);
        });

        return contentView;
    }

    @Override
    protected boolean canPerformDataRequest() {
        boolean isEmpty = super.canPerformDataRequest();
        return dataTag.startsWith("main_") ? isEmpty || VKAPUtils.lastRequestIsOlder(getActivity(), dataTag, 0.25) : isEmpty;
    }

    @Override
    protected void onEmpty() {
        String owner = ownerName == null ? "your" : ownerName + "'s";
        String message = String.format("Seems, %s playlist is empty,\nor something went wrong.", owner);
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