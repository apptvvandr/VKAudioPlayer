package github.y0rrrsh.vkaudioplayer.fragments;

import android.support.annotation.NonNull;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.adapters.UserAudiosAdapter;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.Audio;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.network.service.VkApi;

/**
 * @author Artur Yorsh
 */
@FragmentWithArgs
public class UserAudiosFragment extends VkTabFragment<UserAudiosAdapter> {

    @Arg(required = false)
    String userId;
    @Arg(required = false)
    String ownerName;

    @Override
    protected UserAudiosAdapter onCreateItemAdapter() {
        return new UserAudiosAdapter();
    }

    @Override
    protected void onDataRequest(@NonNull VKAPService api) {
        api.getAudios(userId, new VkApi.VkArrayCallback<Audio>() {
            @Override
            public void onResponse(List<Audio> response) {
                adapter.setItems(response);
            }

            @Override
            public void onError(Throwable t) {
                progressBar.setVisibility(View.GONE);
                onEmpty();
            }
        });
    }

    @Override
    protected void onEmpty() {
        String owner = ownerName == null ? "your" : ownerName + "'s";
        String message = String.format("Seems, %s playlist is empty,\nor something went wrong.", owner);
        emptyView.setMessage(message);
        emptyView.show();
    }
}