package socket.shriku.com.activities;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import socket.shriku.com.adapters.IndexFragmentAdapter;
import socket.shriku.com.socketandroid.R;


public class IndexActivity extends ActionBarActivity {

    IndexFragmentAdapter adapter;
    ViewPager mViewPager;
    ActionBar actionBar = getSupportActionBar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_index);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new IndexFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
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
}
