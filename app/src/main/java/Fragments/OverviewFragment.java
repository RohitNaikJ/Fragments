package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rohit.fragmentstutorial.CourseActivity;
import com.rohit.fragmentstutorial.MainActivity;
import com.rohit.fragmentstutorial.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {


    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        String url = MainActivity.ip + "/courses/course.json/"+ CourseActivity.code +"/assignments";
        Toast.makeText(getActivity(), "Retrieving Assignments", Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONObject reg = response.getJSONObject("registered");
                            JSONObject course = response.getJSONObject("course");

                            TextView code = (TextView) rootView.findViewById(R.id.overview_code);
                            TextView name = (TextView) rootView.findViewById(R.id.overview_name);
                            TextView ltp = (TextView) rootView.findViewById(R.id.overview_ltp);
                            TextView credits = (TextView) rootView.findViewById(R.id.overview_credits);
                            TextView dates= (TextView) rootView.findViewById(R.id.overview_dates);

                            code.setText(course.getString("code").toUpperCase());
                            name.setText(course.getString("name"));
                            ltp.setText(course.getString("l_t_p"));
                            credits.setText("("+course.getInt("credits")+")");
                            dates.setText(reg.getString("starting_date")+"\nto\n"+reg.getString("ending_date"));

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
