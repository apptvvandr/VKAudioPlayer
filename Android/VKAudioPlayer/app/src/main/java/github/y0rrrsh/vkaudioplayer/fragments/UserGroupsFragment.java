package github.y0rrrsh.vkaudioplayer.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import github.y0rrrsh.vkapi.VKApi.VKArrayCallback;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.ListAudioActivity;
import github.y0rrrsh.vkaudioplayer.adapters.UserGroupsAdapter;
import github.y0rrrsh.vkaudioplayer.database.syncitem.SyncItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.GroupModel;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.utils.SimpleAlertDialog;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;

import static github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB.DataType.GROUPS;

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

    @NonNull
    @Override
    protected String getDataTag() {
        return "groups";
    }

    @Override
    protected void onDataRequest(@NonNull VKAPService api) {
        api.getGroups(new VKArrayCallback<GroupModel>() {
            @Override
            public void onResponse(List<GroupModel> response) {
                adapter.setItems(response);
                VkItemDB.getInstance().update(GROUPS, response);
                SyncItemDB.getInstance().update();
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
            ListAudioActivity.start(getActivity(), -item.getId(), item.getName(), item.getAvatarUrl(), options);
        });

        return contentView;
    }

    @Override
    public void onDataSizeChanged(int size) {
        super.onDataSizeChanged(size);

        FragmentActivity activity = getActivity();
        if (!VKAPPreferences.isAskedSync(activity, dataTag)) {
            SimpleAlertDialog dialog = new SimpleAlertDialog(activity,
                    R.string.dialog_sync_title,
                    R.string.dialog_sync_message_groups,
                    R.string.yes,
                    () -> {
                        VkItemDialogFragment dialog1 = VkItemDialogFragment.newInstance(GROUPS);
                        dialog1.show(activity.getSupportFragmentManager(), null);
                        VKAPPreferences.setAutoSyncEnabled(activity, GROUPS, true);
                    },
                    R.string.no, null);
            dialog.setCancelable(false);
            dialog.show();
            VKAPPreferences.setAskedSync(activity, dataTag, true);
        }
    }

    @Override
    protected void onEmpty() {
        emptyView.setMessage(R.string.empty_message_groups);
        emptyView.show();
    }
}
