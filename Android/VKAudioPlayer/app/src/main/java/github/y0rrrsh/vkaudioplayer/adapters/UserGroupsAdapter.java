package github.y0rrrsh.vkaudioplayer.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerHolder;
import github.y0rrrsh.vkaudioplayer.models.GroupModel;
import github.y0rrrsh.vkaudioplayer.models.dto.GroupDTO;

/**
 * @author Artur Yorsh
 */
public class UserGroupsAdapter extends BaseRecyclerAdapter<GroupModel, UserGroupsAdapter.GroupHolder> {

    @Override
    protected int getItemViewResId(int viewType) {
        return R.layout.item_group;
    }

    @Override
    protected GroupHolder onCreateViewHolder(View itemView, int viewType) {
        return new GroupHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(GroupHolder holder, GroupModel item, int position) {
        Picasso.with(holder.itemView.getContext()).load(item.getAvatarUrl())
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_default)
                .into(holder.imageAvatar);
        holder.textTitle.setText(item.getName());
    }

    public static class GroupHolder extends BaseRecyclerHolder {

        @BindView(R.id.text_group_title) public TextView textTitle;
        @BindView(R.id.image_group_avatar) public ImageView imageAvatar;

        public GroupHolder(View itemView) {
            super(itemView);
        }
    }
}
