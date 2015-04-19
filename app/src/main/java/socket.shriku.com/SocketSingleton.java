package socket.shriku.com;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


/**
 * Created by shrikanth on 19/4/15.
 */
public class SocketSingleton {

    public static Socket mSocket = null;
    private static SocketSingleton mInstance;
    private static final String TAG = "Socket Songleton";
    private SocketSingleton() throws Exception {
            mSocket = IO.socket("http://androidsocketio.herokuapp.com");
            mSocket.connect();
    }

    public static SocketSingleton getInstance() throws Exception{
        if(mSocket == null){
            mInstance = new SocketSingleton();
            Log.d(TAG, "creating new isntance");
        }
    return mInstance;
    }

    public Socket getSocket(){
        return mSocket;
    }
}
