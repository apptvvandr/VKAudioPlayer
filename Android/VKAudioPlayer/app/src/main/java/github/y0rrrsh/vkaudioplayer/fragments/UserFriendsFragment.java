package github.y0rrrsh.vkaudioplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.adapters.UserFriendsAdapter;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.Friend;
import github.y0rrrsh.vkaudioplayer.network.service.VkApi;

/**
 * @author Artur Yorsh
 */
public class UserFriendsFragment extends VkTabFragment<UserFriendsAdapter> {

    @Override
    protected UserFriendsAdapter onCreateItemAdapter() {
        return new UserFriendsAdapter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        api.getFriends(new VkApi.VkArrayCallback<Friend>() {
            @Override
            public void onResponse(List<Friend> response) {
                adapter.setItems(response);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}
