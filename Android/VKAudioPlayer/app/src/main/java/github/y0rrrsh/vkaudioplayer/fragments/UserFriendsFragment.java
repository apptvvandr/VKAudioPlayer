package github.y0rrrsh.vkaudioplayer.fragments;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.adapters.UserFriendsAdapter;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.Friend;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.network.service.VkApi.VkArrayCallback;

/**
 * @author Artur Yorsh
 */
public class UserFriendsFragment extends VkTabFragment<UserFriendsAdapter> {

    @Override
    protected UserFriendsAdapter onCreateItemAdapter() {
        return new UserFriendsAdapter();
    }

    @Override
    protected void onDataRequest(@NonNull VKAPService api) {
        api.getFriends(new VkArrayCallback<Friend>() {
            @Override
            public void onResponse(List<Friend> response) {
                progressBar.setVisibility(View.GONE);
                adapter.setItems(response);
            }

            @Override
            public void onError(Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onEmpty() {
        emptyView.setMessage("Seems, there are no friends in your list\nor something went wrong.");
        emptyView.show();
    }
}
