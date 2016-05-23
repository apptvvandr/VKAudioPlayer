package github.y0rrrsh.vkaudioplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import github.y0rrrsh.vkaudioplayer.adapters.UserAudiosAdapter;
import github.y0rrrsh.vkaudioplayer.fragments.common.VkTabFragment;
import github.y0rrrsh.vkaudioplayer.models.response.Audio;

/**
 * @author Artur Yorsh
 */
public class UserAudiosFragment extends VkTabFragment<UserAudiosAdapter> {

    public static UserAudiosFragment newInstance() {
        return new UserAudiosFragment();
    }

    @Override
    protected UserAudiosAdapter onCreateItemAdapter() {
        return new UserAudiosAdapter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.setItems(provideMockItems(25));
    }

    private List<Audio> provideMockItems(int amount) {
        List<Audio> items = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            items.add(new Audio(i, "Metallica", "For Whom The Bells Tools", 100 + new Random().nextInt(200)));
        }

        return items;
    }
}