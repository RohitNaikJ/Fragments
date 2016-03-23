package Fragments;


import android.os.Bundle;
import android.app.Fragment;
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
        Toast.makeText(getActivity(), "AssignmentsFragment", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_assignments, container, false);
        final ListView listView = (ListView) rootView.findViewById(R.id.listView_assignments);

        String url = MainActivity.ip + "/courses/course.json/"+ CourseActivity.code +"/assignments";
        Toast.makeText(getActivity(), "Retrieving Assignments", Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONArray assignments = response.getJSONArray("assignments");

                            assignEntries = new ArrayList<>();
                            for(int i=0; i<assignments.length(); i++)
                                assignEntries.add(new AssignmentEntry(assignments.getJSONObject(i).getInt("id"), assignments.getJSONObject(i).getString("name"), assignments.getJSONObject(i).getString("deadline")));

                            AssignListAdapter adapter = new AssignListAdapter(getActivity(), assignEntries);
                            adapter.setActivity(getActivity());
                            adapter.setAssignEntries(assignEntries);
                            listView.setAdapter(adapter);
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
