package socket.shriku.com.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;
import socket.shriku.com.SocketSingleton;
import socket.shriku.com.models.User;
import socket.shriku.com.socketandroid.R;
import socket.shriku.com.util.Preferences;


public class SignupActivity extends ActionBarActivity {

    private static final String TAG = "Main ACtivity";
    EditText userNameEditText;
    EditText passwordEditText;
    EditText phoneNumberEditText;
    Button signup;
    String userName, password, phoneNumber;
    TextView error;
    Activity activity = this;
    private Emitter.Listener onCreate = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            final JSONObject data = (JSONObject) args[0];
            Log.d(TAG, data.toString());
            if (data.has("error")) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        error.setVisibility(View.VISIBLE);
                        try {
                            error.setText(data.getString("error"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        setContentView(R.layout.activity_signup);
        userNameEditText = (EditText) findViewById(R.id.user_name);
        passwordEditText = (EditText) findViewById(R.id.password);
        phoneNumberEditText = (EditText) findViewById(R.id.phone_number);
        signup = (Button) findViewById(R.id.signup);
        error = (TextView) findViewById(R.id.error);
        try {
            SocketSingleton.getInstance().mSocket.on("user:create", onCreate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TelephonyManager phoneManager = (TelephonyManager)
                getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        phoneNumber = phoneManager.getLine1Number();
        phoneNumberEditText.setText(phoneNumber);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();
                error.setVisibility(View.INVISIBLE);
                JSONObject object = new JSONObject();
                try {
                    object.put("user_name", userName);
                    object.put("password", password);
                    object.put("phone_number", phoneNumber);

                    SocketSingleton.getInstance().mSocket.emit("user:create", object.toString());
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}