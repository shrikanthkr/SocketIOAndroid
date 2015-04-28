package socket.shriku.com.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import socket.shriku.com.adapters.ChatAdapter;
import socket.shriku.com.models.Message;
import socket.shriku.com.socketandroid.R;

/**
 * Created by shrikanth on 18/4/15.
 */
public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";
    ChatAdapter adapter;
    ListView lv;
    View v;
    public ChatFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.chat_fragment, container, false);

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


    public void updateView(Activity activity, ArrayList<Message> messages) {
        Log.d(TAG, "******************updateView**************");
        adapter = new ChatAdapter(activity, messages);
        lv = (ListView) v.findViewById(R.id.chats_listview);
        lv.setAdapter(adapter);
    }



}
