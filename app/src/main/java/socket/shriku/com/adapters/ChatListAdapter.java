package socket.shriku.com.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import socket.shriku.com.models.Room;
import socket.shriku.com.socketandroid.R;

/**
 * Created by shrikanth on 19/4/15.
 */
public class ChatListAdapter extends BaseAdapter {

    private static final String TAG = "Chatlist adapter";
    ArrayList<Room> rooms;
    LayoutInflater inflater;

    public ChatListAdapter(Activity activity, ArrayList<Room> rooms) {
        this.rooms =rooms;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.room_item, null);
            holder = new ViewHolder();

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.room_name = (TextView) convertView.findViewById(R.id.room_name);
        holder.initial = (TextView) convertView.findViewById(R.id.initial);
        holder.room_name.setText(rooms.get(position).name.toString());
        holder.initial.setText(rooms.get(position).name.toString().substring(0, 2).toUpperCase());
        return convertView;
    }

    private class ViewHolder {
        public TextView room_name;
        public TextView initial;
    }
}

