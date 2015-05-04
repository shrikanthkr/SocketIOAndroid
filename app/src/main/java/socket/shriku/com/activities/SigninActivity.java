package socket.shriku.com.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;
import socket.shriku.com.SocketSingleton;
import socket.shriku.com.models.User;
import socket.shriku.com.socketandroid.R;
import socket.shriku.com.util.CustomProgressBar;
import socket.shriku.com.util.Preferences;


public class SigninActivity extends ActionBarActivity {

    private static final String TAG = "Main ACtivity";
    Activity activity = this;
    EditText userNameEditText;
    EditText passwordEditText;
    Button signin;
    Button newUser;
    String userName, password;
    TextView error;
    private Emitter.Listener onAuth = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            Log.d(TAG, data.toString());
            if (data.has("error")) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        error.setVisibility(View.VISIBLE);
                        Toast.makeText(activity.getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                        CustomProgressBar.close();
                    }
                });

            } else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        User.createInstance(new Gson().fromJson(args[0].toString(), User.class));
                        Log.d(TAG, User.getInstance().user_name);
                        Intent i = new Intent(activity, IndexActivity.class);
                        SharedPreferences.Editor editor = Preferences.INSTANCE.getPreferences(activity).edit();
                        editor.putString("user", args[0].toString());
                        editor.commit();
                        CustomProgressBar.close();
                        activity.startActivity(i);
                        activity.finish();
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_signin);
        userNameEditText = (EditText) findViewById(R.id.user_name);
        passwordEditText = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        newUser = (Button) findViewById(R.id.new_user);
        error = (TextView) findViewById(R.id.error);
        try {
            SocketSingleton.getInstance().mSocket.on("auth", onAuth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, SignupActivity.class);
                startActivity(i);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString();
                error.setVisibility(View.INVISIBLE);
                JSONObject object = new JSONObject();
                try {
                    object.put("user_name", userName);
                    object.put("password", password);

                    SocketSingleton.getInstance().mSocket.emit("auth", object.toString());
                    CustomProgressBar.show(activity);
                    Log.d(TAG, object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        });

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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}