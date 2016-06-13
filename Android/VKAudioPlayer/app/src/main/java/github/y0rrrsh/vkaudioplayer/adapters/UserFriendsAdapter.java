package github.y0rrrsh.vkaudioplayer.adapters;

import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerHolder;
import github.y0rrrsh.vkaudioplayer.models.FriendModel;
import github.y0rrrsh.vkaudioplayer.models.dto.FriendDTO;

/**
 * @author Artur Yorsh
 */
public class UserFriendsAdapter extends BaseRecyclerAdapter<FriendModel, UserFriendsAdapter.FriendHolder> {

    @Override
    protected int getItemViewResId(int viewType) {
        return R.layout.item_friend;
    }

    @Override
    protected FriendHolder onCreateViewHolder(View itemView, int viewType) {
        return new FriendHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(FriendHolder holder, FriendModel item, int position) {
        Picasso.with(holder.itemView.getContext()).load(item.getAvatarUrl())
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_default)
                .into(holder.imageAvatar);
        holder.textName.setText(String.format("%s %s", item.getFirstName(), item.getLastName()));
    }

    public static class FriendHolder extends BaseRecyclerHolder {

        @BindView(R.id.image_friend_avatar) public RoundedImageView imageAvatar;
        @BindView(R.id.text_friend_name) public TextView textName;

        public FriendHolder(View itemView) {
            super(itemView);
        }
    }
}
