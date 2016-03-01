package com.rohit.fragmentstutorial;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        fragment = new CoursesFragment();
        fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.container, fragment);
        fragmentTransaction.commit();

        Button btn_courses = (Button)rootView.findViewById(R.id.btn_courses);
        Button btn_grades = (Button)rootView.findViewById(R.id.btn_grades);
        Button btn_notifications = (Button)rootView.findViewById(R.id.btn_notifications);

        btn_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CoursesFragment();
                fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });

        btn_grades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new GradesFragment();
                fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });

        btn_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new NotificationsFragment();
                fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

}
