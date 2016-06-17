package github.y0rrrsh.vkaudioplayer.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import github.y0rrrsh.vkapi.VKApi.VKArrayCallback;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.ListAudioActivity;
import github.y0rrrsh.vkaudioplayer.adapters.UserFriendsAdapter;
import github.y0rrrsh.vkaudioplayer.database.syncitem.SyncItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.FriendModel;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.utils.SimpleAlertDialog;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;

import static github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB.DataType.FRIENDS;

/**
 * @author Artur Yorsh
 */
public class UserFriendsFragment extends VkTabFragment<UserFriendsAdapter> {

    @Override
    protected UserFriendsAdapter onCreateItemAdapter() {
        return new UserFriendsAdapter();
    }

    @NonNull
    @Override
    protected String getDataTag() {
        return "friends";
    }

    @Override
    protected void onDataRequest(@NonNull VKAPService api) {
        api.getFriends(new VKArrayCallback<FriendModel>() {
            @Override
            public void onResponse(List<FriendModel> response) {
                adapter.setItems(response);
                VkItemDB.getInstance().update(FRIENDS, response);
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

        adapter.addItemClickListener((item, itemPosition, viewHolder) -> {
            ActivityOptionsCompat options = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        viewHolder.imageAvatar, viewHolder.imageAvatar.getTransitionName());
            }
            ListAudioActivity.start(getActivity(), item.getId(), item.getFirstName(), item.getAvatarUrl(), options);
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
                    R.string.dialog_sync_message_friends,
                    R.string.yes,
                    () -> {
                        VkItemDialogFragment dialog1 = VkItemDialogFragment.newInstance(FRIENDS);
                        dialog1.show(activity.getSupportFragmentManager(), null);
                        VKAPPreferences.setAutoSyncEnabled(activity, FRIENDS, true);
                    },
                    R.string.no, null);
            dialog.setCancelable(false);
            dialog.show();
            VKAPPreferences.setAskedSync(activity, dataTag, true);
        }
    }

    @Override
    protected void onEmpty() {
        emptyView.setMessage(R.string.empty_message_friends);
        emptyView.show();
    }
}
