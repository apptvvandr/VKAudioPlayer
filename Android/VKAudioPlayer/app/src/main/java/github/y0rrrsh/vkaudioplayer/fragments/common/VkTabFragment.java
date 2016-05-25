package github.y0rrrsh.vkaudioplayer.fragments.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.BindView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemAdapter;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.network.service.VkApi;

/**
 * @author Artur Yorsh
 */
public abstract class VkTabFragment<A extends VkItemAdapter> extends BaseFragment {

    protected static final String TAG = "VkTabFragment";

    protected VKAPService api = VkApi.getServiceInstance();

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);

        adapter = onCreateItemAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = getLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return contentView;
    }
}
