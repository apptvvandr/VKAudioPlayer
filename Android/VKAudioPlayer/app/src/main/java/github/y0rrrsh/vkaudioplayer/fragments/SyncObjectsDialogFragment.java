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
import github.y0rrrsh.vkaudioplayer.adapters.SyncObjectsAdapter;
import github.y0rrrsh.vkaudioplayer.database.syncdb.SyncObjectsDB;
import github.y0rrrsh.vkaudioplayer.database.syncdb.SyncObjectsDB.DataType;
import github.y0rrrsh.vkaudioplayer.database.syncdb.Synchronized;

/**
 * @author Artur Yorsh. 09.06.16.
 */
public class SyncObjectsDialogFragment extends DialogFragment {

    private static final String ARG_TYPE = "type_items";

    @BindView(R.id.recycler_sync_objects) RecyclerView recyclerView;
    private SyncObjectsAdapter<Synchronized> adapter = new SyncObjectsAdapter<>();

    public static SyncObjectsDialogFragment newInstance(DataType type) {
        Bundle args = new Bundle();
        if (type != null) args.putString(ARG_TYPE, type.getName());

        SyncObjectsDialogFragment fragment = new SyncObjectsDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter.setItemClickListener((item, itemPosition, viewHolder) ->
                SyncObjectsDB.getInstance().setSyncForObjectEnabled(item, !item.isSyncEnabled()));
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
        SyncObjectsDB db = SyncObjectsDB.getInstance();
        List<Synchronized> items = type == null ? db.getAll() : db.getAll(DataType.forName(type));
        adapter.setItems(items);
    }
}
