package socket.shriku.com.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import socket.shriku.com.adapters.ChatListAdapter;
import socket.shriku.com.socketandroid.R;

/**
 * Created by shrikanth on 18/4/15.
 */
public class ChatListFragment extends Fragment {

    ChatListAdapter adapter;
    ListView lv;

    public ChatListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChatListFragment newInstance() {
        ChatListFragment fragment = new ChatListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.chat_list_fragment, container, false);
        lv = (ListView) v.findViewById(R.id.rooms_listview);
        adapter = new ChatListAdapter(getActivity());
        lv.setAdapter(adapter);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
