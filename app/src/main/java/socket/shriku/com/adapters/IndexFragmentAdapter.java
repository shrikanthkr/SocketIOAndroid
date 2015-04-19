package socket.shriku.com.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import socket.shriku.com.fragments.ChatFragment;
import socket.shriku.com.fragments.ChatListFragment;
import socket.shriku.com.fragments.UserDetailsFragment;

/**
 * Created by shrikanth on 19/4/15.
 */
public class IndexFragmentAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "Index Adapter";

    public IndexFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        Log.d(TAG, position + " Item");
        switch (position) {

            case 0:
                fragment = ChatListFragment.newInstance();
                break;
            case 1:
                fragment = ChatFragment.newInstance();
                break;
            case 2:
                fragment = UserDetailsFragment.newInstance();
                break;
            default:
                fragment = ChatListFragment.newInstance();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
