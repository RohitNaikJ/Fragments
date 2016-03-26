package com.rohit.fragmentstutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import Fragments.AssignmentsFragment;
import Fragments.CourseGradesFragment;
import Fragments.OverviewFragment;
import Fragments.ThreadsFragment;

public class CourseActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;

    public static String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        code = intent.getStringExtra("COURSE_CODE");
        setContentView(R.layout.activity_course);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("overview").setIndicator("Overview", null),
                OverviewFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("assignments").setIndicator("Assignments", null),
                AssignmentsFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("threads").setIndicator("Threads", null),
                ThreadsFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("grades").setIndicator("Grades", null),
                CourseGradesFragment.class, null);
    }
}
