package socket.shriku.com.socketandroid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shrikanth on 22/3/15.
 */
public class ChatAdapter  extends BaseAdapter{
    Activity activity;
    ArrayList<Chat> chats;
    private LayoutInflater inflater;
    ListView chatListView;

    public ChatAdapter(Activity activity, ArrayList<Chat> chats,  ListView chatListView) {
        this.activity = activity;
        this.chats = chats;
        this.inflater = activity.getLayoutInflater();
        this.chatListView = chatListView;
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder{
        public TextView username;
        public TextView message;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Chat chat = chats.get(position);
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.chat_adapter, null);
            vh = new ViewHolder();
            vh.username = (TextView) convertView.findViewById(R.id.username);
            vh.message = (TextView) convertView.findViewById(R.id.message);


            convertView.setTag(vh);
        }else{
            vh =(ViewHolder) convertView.getTag();
        }
        vh.username.setText(chat.getUsername());
        vh.message.setText(chat.getMessage());
        return convertView;


    }
}
