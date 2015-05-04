package socket.shriku.com.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import socket.shriku.com.socketandroid.R;

/**
 * Created by shrikanth on 3/5/15.
 */
public class CustomProgressBar {
  private static final String TAG = "Progress Bar";
  static View progressView;
  static ViewGroup parentView;

  public static void show(Activity activity) {
    close();
    parentView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content).getRootView();
    LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    progressView = inflater.inflate(R.layout.custom_progress_bar, null);
    parentView.addView(progressView);
  }

  public static void close() {
    try {
      progressView.setVisibility(View.GONE);
      parentView.removeView(progressView);
      parentView = null;
      progressView = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    Log.d(TAG, "Closing progress bar");
  }
}
