package socket.shriku.com.socketandroid;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends ActionBarActivity {

    Activity activity = this;
    private Socket mSocket;
    EditText message;
    Button send_button;
    String username;
    ArrayList<Chat> chats;
    ChatAdapter adapter;
    ListView chatListView;
    {
        try {

            mSocket = IO.socket("http://androidsocketio.herokuapp.com");
            //mSocket = IO.socket("http://192.168.56.1:3000");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chats = new ArrayList<Chat>();
        message = (EditText)findViewById(R.id.message);
        send_button = (Button)findViewById(R.id.send);
        chatListView = (ListView)findViewById(R.id.chat_view);
        adapter = new ChatAdapter(this,chats,chatListView);
        mSocket.connect();
        username = getIntent().getExtras().getString("name");
        Toast.makeText(this,username,Toast.LENGTH_LONG).show();
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("message", "{\""+username+"\":\""+message.getText().toString().trim()+ "\"}");
            }
        });
        mSocket.on("receive",onNewMessage);
        mSocket.on("intial_data",intialData);
        chatListView.setAdapter(adapter);
        getSupportActionBar().setTitle("Chat");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private Emitter.Listener intialData = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String text = (String) args[0];

                    try {
                        JSONArray jsonArray = new JSONArray(text);

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObj =  new JSONObject(jsonArray.getString(i).replaceAll("\\\\", ""));
                            Iterator keys = jsonObj.keys();
                            while(keys.hasNext()){
                                String key = (String)keys.next();
                                Chat chat = new Chat();
                                chat.setUsername(key);
                                chat.setMessage((String)jsonObj.get(key));
                                chats.add(chat);
                                adapter.notifyDataSetChanged();

                                // Toast.makeText(activity,chats.size()+"",Toast.LENGTH_LONG).show();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String text = (String) args[0];
                    text = text.replaceAll("\\\\", "");
                    text = text.substring(1);
                    text = text.substring(0, text.length()-1);
                    try {
                        JSONObject jsonObj = new JSONObject(text);

                        Iterator keys = jsonObj.keys();
                        while(keys.hasNext()){
                            String key = (String)keys.next();
                            Chat chat = new Chat();
                                chat.setUsername(key);
                                chat.setMessage((String)jsonObj.get(key));
                            chats.add(chat);
                            adapter.notifyDataSetChanged();

                           // Toast.makeText(activity,chats.size()+"",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Toast.makeText(activity,text,Toast.LENGTH_LONG).show();


                    // add the message to view
                    //addMessage(username, message);
                }
            });
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("message", onNewMessage);
    }
}