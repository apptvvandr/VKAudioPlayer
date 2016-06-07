package github.y0rrrsh.vkaudioplayer.fragments.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.BindView;
import github.y0rrrsh.emptyview.EmptyView;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemAdapter;
import github.y0rrrsh.vkaudioplayer.adapters.common.VkItemAdapter.ItemObserver;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPService;
import github.y0rrrsh.vkaudioplayer.network.service.VKAPServiceImpl;

/**
 * @author Artur Yorsh
 */
public abstract class VkTabFragment<A extends VkItemAdapter> extends BaseFragment implements ItemObserver {

    protected static final String TAG = "VkTabFragment";

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.progress_content_loading)
    protected ProgressBar progressBar;

    @BindView(R.id.empty_view)
    protected EmptyView emptyView;

    protected A adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vk_tab;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);

        adapter = onCreateItemAdapter();
        adapter.setItemObserver(this);
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

    protected abstract A onCreateItemAdapter();

    protected RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //noinspection ConstantConditions
        onDataRequest(VKAPServiceImpl.getInstance());
    }

    protected abstract void onDataRequest(@NonNull VKAPService api);

    @Override
    public void onDataSizeChanged(int size) {
        progressBar.setVisibility(View.GONE);
        emptyView.hide();
        if (size == 0) {
            onEmpty();
        }
    }

    protected void onEmpty() {
        emptyView.show();
    }
}
