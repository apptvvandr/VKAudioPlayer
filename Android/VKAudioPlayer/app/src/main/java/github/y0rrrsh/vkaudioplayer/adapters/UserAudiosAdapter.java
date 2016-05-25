package github.y0rrrsh.vkaudioplayer.adapters;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemHolder;
import github.y0rrrsh.vkaudioplayer.models.Audio;

/**
 * @author Artur Yorsh
 */
public class UserAudiosAdapter extends VkItemAdapter<Audio, UserAudiosAdapter.AudioHolder> {

    @Override
    protected int getItemViewResId() {
        return R.layout.item_audio;
    }

    @Override
    protected AudioHolder onCreateViewHolder(View itemView, int viewType) {
        return new AudioHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(AudioHolder holder, Audio item, int position) {
        String audioInfo = String.format("%s - %s", item.getArtist(), item.getTitle());
        holder.textId.setText(audioInfo);
    }

    static class AudioHolder extends VkItemHolder {

        @BindView(R.id.text_item_id) TextView textId;

        public AudioHolder(View itemView) {
            super(itemView);
        }
    }
}