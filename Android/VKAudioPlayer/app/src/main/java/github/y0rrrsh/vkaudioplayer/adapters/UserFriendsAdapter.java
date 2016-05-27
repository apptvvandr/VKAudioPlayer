package github.y0rrrsh.vkaudioplayer.adapters;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.ListAudioActivity;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemHolder;
import github.y0rrrsh.vkaudioplayer.models.Friend;

/**
 * @author Artur Yorsh
 */
public class UserFriendsAdapter extends VkItemAdapter<Friend, UserFriendsAdapter.FriendHolder> {

    @Override
    protected int getItemViewResId() {
        return R.layout.item_friend;
    }

    @Override
    protected FriendHolder onCreateViewHolder(View itemView, int viewType) {
        return new FriendHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(FriendHolder holder, Friend item, int position) {
        Picasso.with(holder.itemView.getContext()).load(item.getPhoto200()).into(holder.imageAvatar);
        holder.textName.setText(String.format("%s %s", item.getFirstName(), item.getLastName()));

        holder.itemView.setOnClickListener(v -> {
                    Activity activity = (Activity) holder.itemView.getContext();
                    ActivityOptionsCompat options = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                                holder.imageAvatar, holder.imageAvatar.getTransitionName());
                    }
                    ListAudioActivity.start(activity, item.getId(), item.getFirstName(), item.getPhoto200(), options);
                }
        );
    }

    static class FriendHolder extends VkItemHolder {

        @BindView(R.id.image_friend_avatar) RoundedImageView imageAvatar;
        @BindView(R.id.text_friend_name) TextView textName;

        public FriendHolder(View itemView) {
            super(itemView);
        }
    }
}
