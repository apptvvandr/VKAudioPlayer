package github.y0rrrsh.vkaudioplayer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragmentBuilder;

/**
 * @author Artur Yorsh
 */
public class VkTabAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = {"Audio", "Groups", "Friends"};

    public VkTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new UserAudiosFragmentBuilder().build();
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
