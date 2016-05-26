package github.y0rrrsh.vkaudioplayer.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.adapters.UserGroupsAdapter;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.Group;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.network.service.VkApi.VkArrayCallback;

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
        api.getGroups(new VkArrayCallback<Group>() {
            @Override
            public void onResponse(List<Group> response) {
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
        emptyView.setMessage("Seems, you are not a member of any group,\nor something went wrong.");
        emptyView.show();
    }
}
