package github.y0rrrsh.vkaudioplayer.adapters.common;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import github.y0rrrsh.vkaudioplayer.models.response.VkItem;

/**
 * @author Artur Yorsh
 */
public abstract class VkItemAdapter<M extends VkItem, VH extends VkItemHolder> extends RecyclerView.Adapter<VH> {

    protected List<M> items;

    @LayoutRes
    protected abstract int getItemViewResId();

    protected abstract VH onCreateViewHolder(View itemView, int viewType);

    protected abstract void onBindViewHolder(VH holder, M item, int position);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemViewResId = getItemViewResId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(itemViewResId, parent, false);

        return onCreateViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        M item = items.get(position);
        onBindViewHolder(holder, item, position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<M> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
