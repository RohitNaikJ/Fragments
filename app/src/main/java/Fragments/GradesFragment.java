package Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rohit.fragmentstutorial.MainActivity;
import com.rohit.fragmentstutorial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.GradeListAdapter;
import ListItems.GradeEntry;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradesFragment extends Fragment {

    List<GradeEntry> gradeEntries;

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        final ListView gradesListView = (ListView)rootView.findViewById(R.id.listView_grades);

        String url = MainActivity.ip+"/default/grades.json";
        Toast.makeText(getActivity(), "Retrieving Grades", Toast.LENGTH_SHORT).show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Parsing the response for arrays of courses and grades
                            JSONArray courses = response.getJSONArray("courses");
                            JSONArray grades = response.getJSONArray("grades");

                            //populating the grades array list
                            gradeEntries = new ArrayList<>();
                            for(int i=0; i<courses.length(); i++)
                                gradeEntries.add(new GradeEntry(courses.getJSONObject(i).getString("code"), grades.getJSONObject(i).getString("name"), grades.getJSONObject(i).getInt("score"), grades.getJSONObject(i).getInt("out_of"), grades.getJSONObject(i).getInt("weightage")));

                            //creating the GradeList Adapter and populating the listview
                            GradeListAdapter gradeListAdapter = new GradeListAdapter(getActivity(), gradeEntries);
                            gradeListAdapter.setActivity(getActivity());
                            gradeListAdapter.setGradeEntries(gradeEntries);
                            ArrayAdapter<GradeEntry> adapter = gradeListAdapter;
                            gradesListView.setAdapter(adapter);

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
