package socket.shriku.com.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import socket.shriku.com.SocketSingleton;
import socket.shriku.com.adapters.ChatListAdapter;
import socket.shriku.com.models.Message;
import socket.shriku.com.models.Room;
import socket.shriku.com.socketandroid.R;
import socket.shriku.com.util.CustomProgressBar;

/**
 * Created by shrikanth on 18/4/15.
 */
public class ChatListFragment extends Fragment {

    private static final String TAG = "Chatlist Fragment";
    ArrayList<Room> rooms;
    ChatSelecteListener chatSelectListener;
    ChatListAdapter adapter;
    ListView lv;
    String selected_room;
    View v;
    private Emitter.Listener onMessageIndex = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            JSONArray data = (JSONArray) args[0];
            Log.d(TAG, data.toString());
            if (data == null) {

            } else {
                @SuppressWarnings("serial")
                Type collectionType = new TypeToken<ArrayList<Message>>() {
                }.getType();
                final ArrayList<Message> messages = new Gson().fromJson(data.toString(), collectionType);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatSelectListener.onChatSelected(messages, selected_room);
                    }
                });

            }

        }
    };
    private Emitter.Listener onRoomsContacts = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            final JSONArray data = (JSONArray) args[0];
            Log.d(TAG, data.toString());

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (data == null || data.length() <= 0) {

                    } else {
                        Type collectionType = new TypeToken<ArrayList<Room>>() {
                        }.getType();
                        rooms = new Gson().fromJson(data.toString(), collectionType);
                        adapter = new ChatListAdapter(getActivity(), rooms);
                        lv.setAdapter(adapter);

                        selected_room = rooms.get(0).name;
                        Log.d(TAG, rooms.size() + "");
                    }
                    CustomProgressBar.close();

                }
            });

        }
    };

    public ChatListFragment() {
        // Required empty public constructor
        try {
            SocketSingleton.getInstance().mSocket.off("messages:index").on("messages:index", onMessageIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        v = inflater.inflate(R.layout.chat_list_fragment, container, false);
        lv = (ListView) v.findViewById(R.id.rooms_listview);

        try {
            SocketSingleton.getInstance().mSocket.off("rooms:contacts").on("rooms:contacts", onRoomsContacts);
            SocketSingleton.getInstance().mSocket.emit("rooms:contacts", getPhoneNumbers());
            CustomProgressBar.show(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.room_name);
                JSONObject object = new JSONObject();
                try {
                    object.put("room_name", tv.getText().toString());
                    selected_room = tv.getText().toString();
                    SocketSingleton.getInstance().mSocket.emit("messages:index", object.toString());
                    Log.d(TAG, object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }

            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        chatSelectListener = (ChatSelecteListener) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "******************Resuming**************");


    }

    private String getPhoneNumbers() {
        ContentResolver cr = getActivity().getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ArrayList<String> alContacts = new ArrayList<String>();
        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactNumber = contactNumber.replaceAll("[()\\s-]", "");
                        alContacts.add(contactNumber);
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext());
        }
        Log.d(TAG, "******************Gettign contacts**************");
        return new Gson().toJson(alContacts).toString();
    }

    public interface ChatSelecteListener {
        void onChatSelected(ArrayList<Message> messages, String selected_room);

    }
}
