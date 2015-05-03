package socket.shriku.com.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import socket.shriku.com.SocketSingleton;
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
    Button send_button;
    ArrayList<Message> messages;
    EditText message;
    TextView to_id;
    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONObject data = (JSONObject) args[0];
            Log.d(TAG, data.toString());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateChat(new Gson().fromJson(data.toString(), Message.class));
                }
            });
        }
    };

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
        send_button = (Button) v.findViewById(R.id.send);
        message = (EditText) v.findViewById(R.id.message);
        to_id = (TextView) v.findViewById(R.id.to_id);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message_string = message.getText().toString().trim();
                String to = to_id.getText().toString();
                if (message_string.length() > 0 && to != null && to.length() > 0) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("to", to);
                        object.put("message", message_string);
                        SocketSingleton.getInstance().mSocket.emit("messages:new", object.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please select a contact", Toast.LENGTH_LONG).show();
                }
            }
        });
        try {

            SocketSingleton.getInstance().mSocket.off("messages:new").on("messages:new", onNewMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void updateView(Activity activity, ArrayList<Message> messages, String selected_room) {
        Log.d(TAG, "******************updateView**************");
        this.messages = messages;
        adapter = new ChatAdapter(activity, this.messages);
        lv = (ListView) v.findViewById(R.id.chats_listview);
        to_id = (TextView) v.findViewById(R.id.to_id);
        lv.setAdapter(adapter);
        to_id.setText(selected_room);
    }

    public void updateChat(Message message) {
        Log.d(TAG, "******************Update Chat View**************");
        this.messages.add(message);
        adapter.notifyDataSetChanged();
        this.message.setText("");
    }

}
