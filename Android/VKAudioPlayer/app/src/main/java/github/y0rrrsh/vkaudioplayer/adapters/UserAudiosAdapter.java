package github.y0rrrsh.vkaudioplayer.adapters;

import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemHolder;
import github.y0rrrsh.vkaudioplayer.models.response.Audio;

/**
 * @author Artur Yorsh
 */
public class UserAudiosAdapter extends VkItemAdapter<Audio, UserAudiosAdapter.AudioHolder> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("m:ss", Locale.getDefault());

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
        holder.textTitle.setText(item.getTitle());
        holder.textArtist.setText(item.getArtist());
        holder.textDuration.setText(dateFormat.format(new Date(item.getDuration() * 1000)));
    }

    static class AudioHolder extends VkItemHolder {

        @BindView(R.id.text_audio_title) TextView textTitle;
        @BindView(R.id.text_audio_artist) TextView textArtist;
        @BindView(R.id.text_audio_duration) TextView textDuration;

        public AudioHolder(View itemView) {
            super(itemView);
        }
    }
}