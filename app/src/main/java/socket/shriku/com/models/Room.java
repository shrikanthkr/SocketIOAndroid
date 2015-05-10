package socket.shriku.com.models;

import java.util.ArrayList;

/**
 * Created by shrikanth on 18/4/15.
 */
public class Room {
    public String _id;
    public String name;
    public String owner;
    public ArrayList<String> messages = new ArrayList<>();
    public ArrayList<User> members = new ArrayList<>();

}
