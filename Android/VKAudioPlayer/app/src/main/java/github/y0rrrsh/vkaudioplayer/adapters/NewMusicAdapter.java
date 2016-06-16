package github.y0rrrsh.vkaudioplayer.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerHolder;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.models.NewMusicAdapterItem;

/**
 * @author Artur Yorsh. 16.06.16.
 */
public class NewMusicAdapter extends BaseRecyclerAdapter<NewMusicAdapterItem, BaseRecyclerHolder> {

    public static final int ITEM_TYPE_AUDIO = 0;
    public static final int ITEM_TYPE_HEADER = 1;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("m:ss", Locale.getDefault());

    @Override
    protected int getItemViewType(NewMusicAdapterItem item, int position) {
        return item.getItemType();
    }

    @Override
    protected int getItemViewResId(int viewType) {
        return viewType == ITEM_TYPE_AUDIO ? R.layout.item_audio : R.layout.item_new_audio_header;
    }

    @Override
    protected BaseRecyclerHolder onCreateViewHolder(View itemView, int viewType) {
        return viewType == ITEM_TYPE_AUDIO ? new UserAudiosAdapter.AudioHolder(itemView) : new HeaderHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(BaseRecyclerHolder holder, NewMusicAdapterItem item, int position) {
        if (holder.getItemViewType() == ITEM_TYPE_AUDIO) {
            UserAudiosAdapter.AudioHolder audioHolder = (UserAudiosAdapter.AudioHolder) holder;
            AudioModel audioModel = (AudioModel) item;

            audioHolder.textTitle.setText(audioModel.getName());
            audioHolder.textArtist.setText(audioModel.getArtist());
            audioHolder.textDuration.setText(dateFormat.format(new Date(audioModel.getDuration() * 1000)));
            return;
        }

        if (holder.getItemViewType() == ITEM_TYPE_HEADER) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            VkItem headerItem = (VkItem) item;

            Picasso.with(headerHolder.itemView.getContext()).load(headerItem.getAvatarUrl())
                    .into(headerHolder.imageOwnerAvatar);
            headerHolder.textOwnerName.setText(headerItem.getName());
        }
    }

    public static class HeaderHolder extends BaseRecyclerHolder {
        @BindView(R.id.image_owner_avatar) ImageView imageOwnerAvatar;
        @BindView(R.id.text_owner_name) TextView textOwnerName;

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }
}