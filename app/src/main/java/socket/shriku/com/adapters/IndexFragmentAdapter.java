package socket.shriku.com.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;

import socket.shriku.com.fragments.ChatFragment;
import socket.shriku.com.fragments.ChatListFragment;
import socket.shriku.com.fragments.UserDetailsFragment;

/**
 * Created by shrikanth on 19/4/15.
 */
public class IndexFragmentAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "Index Adapter";
    private static String title[] = {"Contacts", "Chat", "Details"};
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    public IndexFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        ChatListFragment chatListFragment = null;
        ChatFragment chatFragment = null;
        UserDetailsFragment userDetailsFragment = null;
        Log.d(TAG, position + " Item");
        switch (position) {

            case 0:
                if (chatListFragment == null)
                    fragment = chatListFragment = ChatListFragment.newInstance();
                else
                    fragment = chatListFragment;

                break;
            case 1:
                if (chatListFragment == null)
                    fragment = chatFragment = ChatFragment.newInstance();
                else
                    fragment = chatFragment;
                break;
            case 2:
                if (userDetailsFragment == null)
                    fragment = userDetailsFragment = UserDetailsFragment.newInstance();
                else
                    fragment = userDetailsFragment;

                break;
            default:
                fragment = ChatListFragment.newInstance();
                break;

        }
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
