package socket.shriku.com.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shrikanth on 2/5/15.
 */
public enum Preferences {
    INSTANCE;
    private static final String PREF_NAME = "socket.shriku.com";
    SharedPreferences preferences = null;

    public SharedPreferences getPreferences(Activity activity) {
        if (preferences == null) {
            preferences = activity.getPreferences(Context.MODE_PRIVATE);
        }
        return preferences;
    }
}
