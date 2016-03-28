package com.rohit.fragmentstutorial;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import Fragments.CoursesFragment;
import Fragments.GradesFragment;
import Fragments.NotificationsFragment;

public class MainActivity extends AppCompatActivity {

    public static String ip = "http://10.192.57.238:8000";
    private FragmentTabHost mTabHost;
    SharedPreferences sharedPreferences;
//    public static CookieManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
//        manager = new CookieManager();
//        CookieHandler.setDefault(manager);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("courses").setIndicator("Courses", null),
                CoursesFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("grades").setIndicator("Grades", null),
                GradesFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("notifications").setIndicator("Notificaitons", null),
                NotificationsFragment.class, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getTitle().toString().equalsIgnoreCase("logout")) {
            sharedPreferences.edit().clear().commit();
            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
