package github.y0rrrsh.vkaudioplayer.adapters;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.activities.ListAudioActivity;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemHolder;
import github.y0rrrsh.vkaudioplayer.models.Group;

/**
 * @author Artur Yorsh
 */
public class UserGroupsAdapter extends VkItemAdapter<Group, UserGroupsAdapter.GroupHolder> {

    @Override
    protected int getItemViewResId() {
        return R.layout.item_group;
    }

    @Override
    protected GroupHolder onCreateViewHolder(View itemView, int viewType) {
        return new GroupHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(GroupHolder holder, Group item, int position) {
        Picasso.with(holder.itemView.getContext()).load(item.getPhoto200()).into(holder.imageAvatar);
        holder.textTitle.setText(item.getName());

        holder.itemView.setOnClickListener(v -> {
                    Activity activity = (Activity) holder.itemView.getContext();
                    ActivityOptionsCompat options = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                                holder.imageAvatar, holder.imageAvatar.getTransitionName());
                    }
                    ListAudioActivity.start(activity, -item.getId(), item.getName(), item.getPhoto200(), options);
                }

        );
    }

    static class GroupHolder extends VkItemHolder {

        @BindView(R.id.text_group_title) TextView textTitle;
        @BindView(R.id.image_group_avatar) ImageView imageAvatar;

        public GroupHolder(View itemView) {
            super(itemView);
        }
    }
}
