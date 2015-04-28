package socket.shriku.com;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


/**
 * Created by shrikanth on 19/4/15.
 */
public class SocketSingleton {

    private static final String TAG = "Socket Singleton";
    public static Socket mSocket = null;
    private static SocketSingleton mInstance;

    private SocketSingleton() throws Exception {
        //192.168.56.1:3000
        //http://androidsocketio.herokuapp.com
        mSocket = IO.socket("http://192.168.56.1:3000");
        mSocket.connect();
    }

    public static SocketSingleton getInstance() throws Exception {
        if (mSocket == null) {
            mInstance = new SocketSingleton();
            Log.d(TAG, "creating new isntance");
        }
        return mInstance;
    }

    public Socket getSocket() {
        return mSocket;
    }
}
