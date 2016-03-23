package com.rohit.fragmentstutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CourseActivity extends Activity {

    public static String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent intent = getIntent();
        code = intent.getStringExtra("COURSE_CODE");

        //getFragmentManager().beginTransaction().add(R.id.frameLayout, new AssignmentsFragment());
    }
}
