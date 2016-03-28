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
import com.rohit.fragmentstutorial.CourseActivity;
import com.rohit.fragmentstutorial.MainActivity;
import com.rohit.fragmentstutorial.R;
import com.rohit.fragmentstutorial.ThreadInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.ThreadListAdapter;
import ListItems.ThreadEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreadsFragment extends Fragment {

    private List<ThreadEntry> threadEntries;
    private ListView listView;

    public ThreadsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_threads, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView_threads);

        String url = MainActivity.ip+"/courses/course.json/"+ CourseActivity.code+"/threads";
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            final JSONArray courseThreads = response.getJSONArray("course_threads");

                            threadEntries = new ArrayList<>();
                            threadEntries.add(new ThreadEntry());
                            for(int i=0; i<courseThreads.length(); i++)
                                threadEntries.add(new ThreadEntry(i + 1, courseThreads.getJSONObject(i).getString("title"), courseThreads.getJSONObject(i).getString("updated_at")));

                            ThreadListAdapter adapter = new ThreadListAdapter(getActivity(), threadEntries);
                            adapter.setActivity(getActivity());
                            adapter.setThreadEntries(threadEntries);
                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    try {
                                        int threadNo = courseThreads.getJSONObject(position - 1).getInt("id");
                                        Toast.makeText(getActivity(), "Clicked on Thread with ID : " + threadNo, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), ThreadInfo.class);
                                        intent.putExtra("thread_number", threadNo);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "JSONObjectException:\n" + e.getMessage() + "\nUser not loged in." +
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

        return  rootView;
    }

}
