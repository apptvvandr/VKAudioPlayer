package github.y0rrrsh.vkaudioplayer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragmentBuilder;
import github.y0rrrsh.vkaudioplayer.fragments.UserFriendsFragment;
import github.y0rrrsh.vkaudioplayer.fragments.UserGroupsFragment;

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
        switch (position) {
            case 0:
                return new UserAudiosFragmentBuilder().build();
            case 1:
                return new UserGroupsFragment();
            case 2:
                return new UserFriendsFragment();
        }
        return null;
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
