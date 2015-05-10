package socket.shriku.com.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import socket.shriku.com.models.Message;
import socket.shriku.com.socketandroid.R;

/**
 * Created by shrikanth on 28/4/15.
 */
public class ChatAdapter extends BaseAdapter {

    private static final String TAG = "Chat adapter";
    ArrayList<Message> messages;
    LayoutInflater inflater;

    public ChatAdapter(Activity activity, ArrayList<Message> messages) {
        this.messages = messages;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return messages.size();
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
            convertView = inflater.inflate(R.layout.chat_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.sender = (TextView) convertView.findViewById(R.id.sender);
        holder.to = (TextView) convertView.findViewById(R.id.to);
        holder.message = (TextView) convertView.findViewById(R.id.message);

        Message message = messages.get(position);
        holder.sender.setText(message.from);
        holder.to.setText(message.to);
        holder.message.setText(message.message);

        return convertView;
    }

    private class ViewHolder {
        public TextView sender;
        public TextView to;
        public TextView message;
    }
}
