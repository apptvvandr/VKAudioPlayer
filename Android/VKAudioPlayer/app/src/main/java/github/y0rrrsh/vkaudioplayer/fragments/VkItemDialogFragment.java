package github.y0rrrsh.vkaudioplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.VkItemAdapter;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItem;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB;
import github.y0rrrsh.vkaudioplayer.database.vkitem.VkItemDB.DataType;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public class VkItemDialogFragment extends DialogFragment {

    private static final String ARG_TYPE = "type_items";

    @BindView(R.id.recycler_sync_objects) RecyclerView recyclerView;
    private VkItemAdapter<VkItem> adapter = new VkItemAdapter<>();

    public static VkItemDialogFragment newInstance(DataType type) {
        Bundle args = new Bundle();
        if (type != null) args.putString(ARG_TYPE, type.getName());

        VkItemDialogFragment fragment = new VkItemDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter.addItemClickListener((item, itemPosition, viewHolder) ->
                item.setSyncEnabled(!item.isSyncEnabled()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.dialog_sync_objects, container, false);
        ButterKnife.bind(this, contentView);
        recyclerView.setAdapter(adapter);

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String type = getArguments().getString(ARG_TYPE);

        VkItemDB db = VkItemDB.getInstance();
        List<VkItem> items = type == null ? db.getAll() : db.getAll(DataType.forName(type));
        adapter.setItems(items);
    }
}
