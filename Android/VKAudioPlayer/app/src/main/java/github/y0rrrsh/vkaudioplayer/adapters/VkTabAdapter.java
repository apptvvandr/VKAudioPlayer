package github.y0rrrsh.vkaudioplayer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import github.y0rrrsh.vkaudioplayer.fragments.NewAudiosFragment;
import github.y0rrrsh.vkaudioplayer.fragments.UserAudiosFragmentBuilder;
import github.y0rrrsh.vkaudioplayer.fragments.UserFriendsFragment;
import github.y0rrrsh.vkaudioplayer.fragments.UserGroupsFragment;

import static github.y0rrrsh.vkapi.VKApi.USER_ID;

/**
 * @author Artur Yorsh
 */
public class VkTabAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = {"New", "Audio", "Groups", "Friends"};

    public static final int POSITION_NEW_AUDIOS = 0;
    public static final int POSITION_USER_AUDIO = 1;
    public static final int POSITION_USER_GROUPS = 2;
    public static final int POSITION_USER_FRIENDS = 3;

    public VkTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION_NEW_AUDIOS:
                return new NewAudiosFragment();
            case POSITION_USER_AUDIO:
                return new UserAudiosFragmentBuilder(USER_ID).build();
            case POSITION_USER_GROUPS:
                return new UserGroupsFragment();
            case POSITION_USER_FRIENDS:
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
