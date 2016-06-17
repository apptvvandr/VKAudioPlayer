package github.y0rrrsh.vkaudioplayer.adapters.common;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artur Yorsh
 */
public abstract class BaseRecyclerAdapter<M, VH extends BaseRecyclerHolder> extends RecyclerView.Adapter<VH> {

    protected static final int VIEW_TYPE_DEFAULT = -1;

    protected List<M> items;

    protected ItemObserver itemObserver;
    protected Map<Integer, ItemClickListener> itemClickListeners = new HashMap<>();

    @LayoutRes
    protected abstract int getItemViewResId(int viewType);

    protected abstract VH onCreateViewHolder(View itemView, int viewType);

    protected abstract void onBindViewHolder(VH holder, M item, int position);

    protected int getItemViewType(M item, int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public int getItemViewType(int position) {
        M item = items.get(position);
        return getItemViewType(item, position);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemViewResId = getItemViewResId(viewType);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(itemViewResId, parent, false);

        return onCreateViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        M item = items.get(position);
        onBindViewHolder(holder, item, position);
        holder.itemView.setOnClickListener(v -> {
            ItemClickListener itemClickListener = itemClickListeners.get(holder.getItemViewType());
            if (itemClickListener != null) itemClickListener.onItemClicked(item, position, holder);
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<M> items) {
        this.items = items;
        notifyDataSetChanged();
        if (itemObserver != null) {
            itemObserver.onDataSizeChanged(getItemCount());
        }
    }

    public List<M> getItems() {
        return items;
    }

    public void setItemObserver(ItemObserver observer) {
        itemObserver = observer;
    }

    public void addItemClickListener(int viewType, ItemClickListener<M, VH> listener) {
        itemClickListeners.put(viewType, listener);
    }

    /**
     * Set listener for all items with {@link #VIEW_TYPE_DEFAULT} viewType
     */
    public void addItemClickListener(ItemClickListener<M, VH> listener) {
        itemClickListeners.put(VIEW_TYPE_DEFAULT, listener);
    }

    public interface ItemObserver {
        void onDataSizeChanged(int size);
    }

    public interface ItemClickListener<M, VH> {
        void onItemClicked(M item, int itemPosition, VH viewHolder);
    }
}
