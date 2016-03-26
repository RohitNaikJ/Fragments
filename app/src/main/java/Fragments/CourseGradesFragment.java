package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rohit.fragmentstutorial.CourseActivity;
import com.rohit.fragmentstutorial.MainActivity;
import com.rohit.fragmentstutorial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.CourseGradeListAdapter;
import ListItems.CourseGradeEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseGradesFragment extends Fragment {

    List<CourseGradeEntry> gradeEntries;
    ListView listView;

    public CourseGradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_course_grades, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView_course_grades);

        String url = MainActivity.ip+"/courses/course.json/"+ CourseActivity.code+"/grades";
        Toast.makeText(getActivity(), "Retrieving Grades for the course "+CourseActivity.code, Toast.LENGTH_SHORT).show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Parsing the response for arrays of courses and grades
                            JSONArray grades = response.getJSONArray("grades");

                            //populating the grades array list
                            gradeEntries = new ArrayList<>();
                            for(int i=0; i<grades.length(); i++)
                                gradeEntries.add(new CourseGradeEntry(grades.getJSONObject(i).getInt("id"), grades.getJSONObject(i).getString("name"), grades.getJSONObject(i).getInt("score"), grades.getJSONObject(i).getInt("out_of"), grades.getJSONObject(i).getInt("weightage")));

                            //creating the GradeList Adapter and populating the listview
                            CourseGradeListAdapter adapter = new CourseGradeListAdapter(getActivity(), gradeEntries);
                            adapter.setActivity(getActivity());
                            adapter.setGradeEntries(gradeEntries);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "JSONObjectException:\n"+e.getMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "ErrorResponse:\n"+error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(getActivity()).add(jsonRequest);

        return rootView;
    }

}
