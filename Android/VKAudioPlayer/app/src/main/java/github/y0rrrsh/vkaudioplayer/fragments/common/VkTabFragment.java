package github.y0rrrsh.vkaudioplayer.fragments.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemAdapter;

/**
 * @author Artur Yorsh
 */
public abstract class VkTabFragment<A extends VkItemAdapter> extends BaseFragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    protected A adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vk_tab;
    }

    protected abstract A onCreateItemAdapter();

    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = getLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = onCreateItemAdapter();
        recyclerView.setAdapter(adapter);

        return contentView;
    }
}
