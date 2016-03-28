package com.rohit.fragmentstutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AssignmentInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_info);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 0);

        String url = MainActivity.ip + "/courses/course.json/"+ CourseActivity.code +"/assignments";
        Toast.makeText(this, "Retrieving Assignment description", Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONObject assignment = response.getJSONArray("assignments").getJSONObject(id);
                            TextView name = (TextView) findViewById(R.id.assgn_name);
                            TextView description = (TextView) findViewById(R.id.assgn_description);
                            TextView createdAt = (TextView) findViewById(R.id.assgn_createdAt);
                            TextView deadline = (TextView) findViewById(R.id.assgn_deadline);
                            TextView days_allowed = (TextView) findViewById(R.id.assgn_days_allowed);
                            name.setText(assignment.getString("name"));
                            description.setText(Html.fromHtml(assignment.getString("description")));
                            createdAt.setText(assignment.getString("created_at"));
                            deadline.setText(assignment.getString("deadline"));
                            days_allowed.setText(assignment.getInt("late_days_allowed")+"");
                            getSupportActionBar().setTitle(assignment.getString("name").substring(0,assignment.getString("name").indexOf(":")));
                        } catch (JSONException e) {
                            Toast.makeText(AssignmentInfo.this, "JSONObjectException:\n"+e.getMessage()+"\nUser not loged in." +
                                    "\nPlease login and try again", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AssignmentInfo.this, "ErrorResponse:\n"+error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonRequest);
    }
}
