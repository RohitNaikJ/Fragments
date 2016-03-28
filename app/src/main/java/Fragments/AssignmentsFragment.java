package Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.rohit.fragmentstutorial.AssignmentInfo;
import com.rohit.fragmentstutorial.CourseActivity;
import com.rohit.fragmentstutorial.MainActivity;
import com.rohit.fragmentstutorial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.AssignListAdapter;
import ListItems.AssignmentEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentsFragment extends Fragment {

    List<AssignmentEntry> assignEntries;

    public AssignmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_assignments, container, false);
        final ListView listView = (ListView) rootView.findViewById(R.id.listView_assignments);

        String url = MainActivity.ip + "/courses/course.json/"+ CourseActivity.code +"/assignments";
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONArray assignments = response.getJSONArray("assignments");

                            assignEntries = new ArrayList<>();
                            assignEntries.add(new AssignmentEntry());
                            for(int i=0; i<assignments.length(); i++)
                                assignEntries.add(new AssignmentEntry(assignments.getJSONObject(i).getInt("id"), assignments.getJSONObject(i).getString("name"), assignments.getJSONObject(i).getString("deadline")));

                            AssignListAdapter adapter = new AssignListAdapter(getActivity(), assignEntries);
                            adapter.setActivity(getActivity());
                            adapter.setAssignEntries(assignEntries);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        Intent intent = new Intent(getActivity(), AssignmentInfo.class);
                                        intent.putExtra("id", position - 1);
                                        startActivity(intent);
                                    }
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

        return rootView;
    }

}
