package github.y0rrrsh.vkaudioplayer.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import github.y0rrrsh.vkapi.VKApi;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerHolder;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public class VkItemAdapter<T extends VkItem> extends BaseRecyclerAdapter<T, VkItemAdapter.SyncObjectHolder> {

    public static final int TYPE_CURRENT_USER = 0;
    public static final int TYPE_ITEM_REGULAR = 1;

    @Override
    protected int getItemViewType(T item, int position) {
        return item.getId() == VKApi.USER_ID ? TYPE_CURRENT_USER : TYPE_ITEM_REGULAR;
    }

    @Override
    protected int getItemViewResId(int viewType) {
        return R.layout.list_item_sync_object;
    }

    @Override
    protected SyncObjectHolder onCreateViewHolder(View itemView, int viewType) {
        return new SyncObjectHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(SyncObjectHolder holder, T item, int position) {
        Picasso.with(holder.itemView.getContext()).load(item.getAvatarUrl())
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_default)
                .into(holder.imageAvatar);


        holder.textName.setText(item.getName());
        holder.checkSyncEnabled.setChecked(item.isSyncEnabled());
        holder.checkSyncEnabled.setOnClickListener(v ->
                itemClickListener.onItemClicked(item, position, holder));

        boolean isCurrentUser = holder.getItemViewType() == TYPE_CURRENT_USER;
        holder.itemView.setEnabled(!isCurrentUser);
        holder.checkSyncEnabled.setEnabled(!isCurrentUser);
    }


    public static class SyncObjectHolder extends BaseRecyclerHolder {

        @BindView(R.id.image_sync_obj_avatar) ImageView imageAvatar;
        @BindView(R.id.text_sync_obj_name) TextView textName;
        @BindView(R.id.check_sync_enabled) CheckBox checkSyncEnabled;

        public SyncObjectHolder(View itemView) {
            super(itemView);
        }
    }
}
