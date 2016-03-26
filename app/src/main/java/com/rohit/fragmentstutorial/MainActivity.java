package com.rohit.fragmentstutorial;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import Fragments.CoursesFragment;
import Fragments.GradesFragment;
import Fragments.NotificationsFragment;

public class MainActivity extends FragmentActivity {

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
}
