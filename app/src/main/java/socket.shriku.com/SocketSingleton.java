package socket.shriku.com;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by shrikanth on 19/4/15.
 */
public class SocketSingleton {

    private static Socket mSocket;
    private static SocketSingleton mInstance;

    private SocketSingleton() throws Exception {
            mSocket = IO.socket("http://androidsocketio.herokuapp.com");
    }

    public static SocketSingleton getInstance() throws Exception{
        if(mSocket == null){
            mInstance = new SocketSingleton();
        }
    return mInstance;
    }

    public Socket getSocket(){
        return mSocket;
    }
}
