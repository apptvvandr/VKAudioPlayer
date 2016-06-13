package github.y0rrrsh.vkaudioplayer.adapters;

import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.BaseRecyclerHolder;
import github.y0rrrsh.vkaudioplayer.models.AudioModel;

/**
 * @author Artur Yorsh
 */
public class UserAudiosAdapter extends BaseRecyclerAdapter<AudioModel, UserAudiosAdapter.AudioHolder> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("m:ss", Locale.getDefault());

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
    }

    public static class AudioHolder extends BaseRecyclerHolder {

        @BindView(R.id.text_audio_title) public TextView textTitle;
        @BindView(R.id.text_audio_artist) public TextView textArtist;
        @BindView(R.id.text_audio_duration) public TextView textDuration;

        public AudioHolder(View itemView) {
            super(itemView);
        }
    }
}