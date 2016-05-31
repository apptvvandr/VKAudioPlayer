package github.y0rrrsh.vkaudioplayer.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.activities.ListAudioActivity;
import github.y0rrrsh.vkaudioplayer.adapters.UserGroupsAdapter;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.vkapi.VKApi.VkArrayCallback;

/**
 * @author Artur Yorsh
 */
public class UserGroupsFragment extends VkTabFragment<UserGroupsAdapter> {

    @Override
    protected UserGroupsAdapter onCreateItemAdapter() {
        return new UserGroupsAdapter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new GridLayoutManager(context, 2);
    }

    @Override
    protected void onDataRequest(@NonNull VKAPService api) {
        api.getGroups(new VkArrayCallback<GroupDTO>() {
            @Override
            public void onResponse(List<GroupDTO> response) {
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
            ListAudioActivity.start(getActivity(), -item.getId(), item.getName(), item.getPhoto200(), options);
        });

        return contentView;
    }

    @Override
    protected void onEmpty() {
        emptyView.setMessage("Seems, you are not a member of any group,\nor something went wrong.");
        emptyView.show();
    }
}
