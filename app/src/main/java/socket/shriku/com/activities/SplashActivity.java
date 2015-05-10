package socket.shriku.com.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONObject;

import socket.shriku.com.SocketSingleton;
import socket.shriku.com.models.User;
import socket.shriku.com.util.CustomProgressBar;
import socket.shriku.com.util.Preferences;

/**
 * Created by shrikanth on 2/5/15.
 */
public class SplashActivity extends Activity {

    private final String TAG = "Splash Activity";
    Activity activity = this;
    private Emitter.Listener onTokenAuth = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            final JSONObject data = (JSONObject) args[0];
            if (data == null) {
                Intent i = new Intent(activity, SigninActivity.class);
                startActivity(i);
                finish();
            } else {
                Log.d(TAG, data.toString());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CustomProgressBar.close();
                        if (data.has("error")) {
                            Intent i = new Intent(activity, SigninActivity.class);
                            startActivity(i);
                            finish();
                        } else {

                            User.createInstance(new Gson().fromJson(args[0].toString(), User.class));
                            Log.d(TAG, User.getInstance().user_name);
                            Intent i = new Intent(activity, IndexActivity.class);
                            SharedPreferences.Editor editor = Preferences.INSTANCE.getPreferences(activity).edit();
                            editor.putString("user", args[0].toString());
                            editor.commit();
                            activity.startActivity(i);
                            activity.finish();

                        }
                    }
                });
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String user = Preferences.INSTANCE.getPreferences(this).getString("user", null);
        Intent i;
        Log.d(TAG, user + "");
        if (user != null) {
            User.createInstance(new Gson().fromJson(user.toString(), User.class));
            try {
                SocketSingleton.getInstance().mSocket.off("users:token_auth").on("users:token_auth", onTokenAuth);
                SocketSingleton.getInstance().mSocket.emit("users:token_auth", " {\"token\" : \"" + User.getInstance().token + "\"}");
                CustomProgressBar.show(activity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            i = new Intent(this, SigninActivity.class);
            startActivity(i);
            finish();
        }


    }


}
