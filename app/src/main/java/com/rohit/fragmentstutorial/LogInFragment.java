package com.rohit.fragmentstutorial;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = null;  //inflater.inflate(R.layout.fragment_login, container, false);

        sp = getActivity().getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
        editor = sp.edit();

        Button login = (Button)rootView.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = MainActivity.ip+"/default/login.json?userid=cs1110200&password=john";
                Toast.makeText(getActivity(), "Connecting to the server", Toast.LENGTH_LONG).show();
                JsonObjectRequest jsonRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // the response is already constructed as a JSONObject!
                                try {
                                    if(response.getBoolean("success")) {
                                        Toast.makeText(getActivity(), "LogIn Successful", Toast.LENGTH_SHORT).show();
                                        JSONObject userDetails = response.getJSONObject("user");
                                        editor.putBoolean("ISLOGIN", true);
                                        editor.putString("NAME", userDetails.getString("first_name")+" "+userDetails.getString("last_name"));
                                        editor.putString("EMAIL", userDetails.getString("email"));
                                        editor.putString("USERNAME", userDetails.getString("username"));
                                        editor.putString("ENTRYNO", userDetails.getString("entry_no"));
                                        editor.commit();
                                    }
                                    else
                                        Toast.makeText(getActivity(), "LogIn Unsuccessful", Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    Toast.makeText(getActivity(), "JSONObjectException:\n"+e.getMessage(), Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "OnErrorResponse:\n"+error.getMessage(), Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                            }
                        });
                Volley.newRequestQueue(getActivity()).add(jsonRequest);
            }
        });

        return rootView;
    }

}
