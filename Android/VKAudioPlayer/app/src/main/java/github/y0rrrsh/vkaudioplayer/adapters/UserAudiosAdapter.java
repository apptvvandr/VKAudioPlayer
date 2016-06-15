package github.y0rrrsh.vkaudioplayer.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.utils.LocalStorage;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerHolder;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;

import static github.y0rrrsh.vkaudioplayer.utils.LocalStorage.DIR_SUB_MUSIC;
import static github.y0rrrsh.vkaudioplayer.utils.LocalStorage.FORMAT_MP3;

/**
 * @author Artur Yorsh
 */
public class UserAudiosAdapter extends BaseRecyclerAdapter<AudioModel, UserAudiosAdapter.AudioHolder> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("m:ss", Locale.getDefault());

    private VkItem owner;

    public UserAudiosAdapter(@NonNull VkItem owner) {
        this.owner = owner;
    }

    @Override
    protected int getItemViewResId(int viewType) {
        return R.layout.item_audio;
    }

    @Override
    protected AudioHolder onCreateViewHolder(View itemView, int viewType) {
        return new AudioHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(AudioHolder holder, AudioModel item, int position) {
        holder.textTitle.setText(item.getName());
        holder.textArtist.setText(item.getArtist());
        holder.textDuration.setText(dateFormat.format(new Date(item.getDuration() * 1000)));

        boolean isSynchronizedOwner = owner.isSyncEnabled();
        boolean availableOnDevice = LocalStorage.fileExists(DIR_SUB_MUSIC, item.toString() + FORMAT_MP3);
        boolean isNotSeenItem = item.getDate() >= VKAPPreferences.getLastLoginMillis(holder.itemView.getContext()) / 1000;
        holder.checkSyncItem.setChecked(isSynchronizedOwner && !availableOnDevice && isNotSeenItem);
        holder.checkSyncItem.setEnabled(!availableOnDevice);
    }

    public static class AudioHolder extends BaseRecyclerHolder {

        @BindView(R.id.text_audio_title) public TextView textTitle;
        @BindView(R.id.text_audio_artist) public TextView textArtist;
        @BindView(R.id.text_audio_duration) public TextView textDuration;
        @BindView(R.id.check_sync_item) public CheckBox checkSyncItem;

        public AudioHolder(View itemView) {
            super(itemView);
        }
    }
}