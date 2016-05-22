package github.y0rrrsh.vkaudioplayer.adapters.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author Artur Yorsh
 */
public class VkItemHolder extends RecyclerView.ViewHolder {

    public VkItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
