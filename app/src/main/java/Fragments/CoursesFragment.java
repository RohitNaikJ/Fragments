package Fragments;


import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import Adapters.CourseListAdapter;
import ListItems.CourseEntry;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    List<CourseEntry> courseEntries;

    public CoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_courses, container, false);
        final ListView listView = (ListView)rootView.findViewById(R.id.listView_courses);

        sp = getActivity().getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
        editor = sp.edit();

        if(sp.getBoolean("ISLOGIN", true)){
            String url = MainActivity.ip+"/courses/list.json";
            //final CourseList[] c = new CourseList[1];
            Toast.makeText(getActivity(), "Retrieving Courses", Toast.LENGTH_SHORT).show();
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                //Toast.makeText(con, "in try " + (jsonObjectReturn[0] == null), Toast.LENGTH_LONG).show();
                                JSONArray coursesJSONList = response.getJSONArray("courses");

                                //creating and populating the array list of courseEntries
                                courseEntries = new ArrayList<>();
                                for(int i=0; i<coursesJSONList.length(); i++)
                                    courseEntries.add(new CourseEntry(coursesJSONList.getJSONObject(i).getString("code"), coursesJSONList.getJSONObject(i).getString("name"), coursesJSONList.getJSONObject(i).getString("description"), coursesJSONList.getJSONObject(i).getInt("credits"), coursesJSONList.getJSONObject(i).getString("l_t_p"), coursesJSONList.getJSONObject(i).getInt("id")));

                                //populating the list view
                                CourseListAdapter courseListAdapter = new CourseListAdapter(getActivity(), courseEntries);
                                courseListAdapter.setActivity(getActivity());
                                courseListAdapter.setCourseEntries(courseEntries);
                                listView.setAdapter(courseListAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Toast.makeText(getActivity(), "You clicked the course : "+courseEntries.get(position).getCode(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), CourseActivity.class);
                                        intent.putExtra("COURSE_CODE", courseEntries.get(position).getCode());
                                        startActivity(intent);
                                    }
                                });
                            } catch (JSONException e) {
                                Toast.makeText(getActivity(), "JSONObjectException:\n"+e.getMessage()+"\nUser not loged in." +
                                        "\nPlease login and try again", Toast.LENGTH_LONG).show();
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
        }else{
            Toast.makeText(getActivity(), "Not Loged in. Login and try again.", Toast.LENGTH_LONG);
        }
        return rootView;
    }

}
