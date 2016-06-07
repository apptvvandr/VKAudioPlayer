package github.y0rrrsh.vkaudioplayer.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import github.y0rrrsh.vkapi.VKApi.VKArrayCallback;
import github.y0rrrsh.vkaudioplayer.activities.ListAudioActivity;
import github.y0rrrsh.vkaudioplayer.adapters.UserFriendsAdapter;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;

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
        api.getFriends(new VKArrayCallback<FriendDTO>() {
            @Override
            public void onResponse(List<FriendDTO> response) {
                progressBar.setVisibility(View.GONE);
                adapter.setItems(response);
            }

            @Override
            public void onError(Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        adapter.setItemClickListener((item, itemPosition, viewHolder) -> {
            ActivityOptionsCompat options = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        viewHolder.imageAvatar, viewHolder.imageAvatar.getTransitionName());
            }
            ListAudioActivity.start(getActivity(), item.getId(), item.getFirstName(), item.getPhoto200(), options);
        });

        return contentView;
    }

    @Override
    protected void onEmpty() {
        emptyView.setMessage("Seems, there are no friends in your list\nor something went wrong.");
        emptyView.show();
    }
}
