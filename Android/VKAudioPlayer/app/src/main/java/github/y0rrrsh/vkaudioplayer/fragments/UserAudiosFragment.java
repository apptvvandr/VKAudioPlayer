package github.y0rrrsh.vkaudioplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.adapters.UserAudiosAdapter;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.Audio;
import github.y0rrrsh.vkaudioplayer.network.service.VkApi.VkArrayCallback;

/**
 * @author Artur Yorsh
 */
@FragmentWithArgs
public class UserAudiosFragment extends VkTabFragment<UserAudiosAdapter> {

    @Arg(required = false)
    String userId;

    @Override
    protected UserAudiosAdapter onCreateItemAdapter() {
        return new UserAudiosAdapter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        api.getAudios(userId, new VkArrayCallback<Audio>() {
            @Override
            public void onResponse(List<Audio> response) {
                adapter.setItems(response);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}