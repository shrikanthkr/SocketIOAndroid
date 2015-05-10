package socket.shriku.com.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by shrikanth on 18/4/15.
 */
public class User {
    private static final String TAG = "User Model";
    private static User mInstance = null;
    public String _id;
    public String user_name;
    public String password;
    public String token;


    public ArrayList<Room> rooms;

    private User(User user) {
        this.user_name = user.user_name;
        this.password = user.password;
        this.rooms = user.rooms;
        this.token = user.token;
    }

    public static User getInstance() {
        return mInstance;
    }

    public static User createInstance(User user) {
        mInstance = null;
            mInstance = new User(user);
            Log.d(TAG, "creating new user" + mInstance.user_name);
        return mInstance;
    }
}
