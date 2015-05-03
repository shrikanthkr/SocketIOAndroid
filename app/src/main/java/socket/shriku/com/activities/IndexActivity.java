package socket.shriku.com.activities;


import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import socket.shriku.com.adapters.IndexFragmentAdapter;
import socket.shriku.com.fragments.ChatFragment;
import socket.shriku.com.fragments.ChatListFragment;
import socket.shriku.com.models.Message;
import socket.shriku.com.socketandroid.R;


public class IndexActivity extends ActionBarActivity implements ChatListFragment.ChatSelecteListener {

    IndexFragmentAdapter adapter;
    ViewPager mViewPager;
    ActionBar actionBar = getSupportActionBar();
    PagerTitleStrip tabStrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_index);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        adapter = new IndexFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabStrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
            }
        });


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

    @Override
    public void onChatSelected(ArrayList<Message> messages, String selected_room) {
        ((ChatFragment) adapter.getRegisteredFragment(1)).updateView(this, messages, selected_room);
        mViewPager.setCurrentItem(1, true);
    }
}
