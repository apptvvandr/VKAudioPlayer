package github.y0rrrsh.vkaudioplayer.fragments.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @author Artur Yorsh
 */
public abstract class BaseFragment extends Fragment {

    @LayoutRes
    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        int layoutId = getLayoutId();
        View contentView = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, contentView);

        return contentView;
    }
}
