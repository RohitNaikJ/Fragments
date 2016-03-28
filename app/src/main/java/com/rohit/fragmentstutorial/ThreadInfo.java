package com.rohit.fragmentstutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.CommentsListAdapter;

public class ThreadInfo extends FragmentActivity {
    List<CommentEntry> commentEntries;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_info);

        Intent intent = getIntent();
        final int thread_no = intent.getIntExtra("thread_number", 0);

        final TextView title = (TextView) findViewById(R.id.thread_title);
        final TextView createdOn = (TextView) findViewById(R.id.thread_createdOn);
        final TextView updatedOn = (TextView) findViewById(R.id.thread_updatedOn);
        final TextView body = (TextView) findViewById(R.id.thread_body);
        final ListView listView = (ListView) findViewById(R.id.listView_thread_comments);

        String url = MainActivity.ip + "/threads/thread.json/"+thread_no;
        Toast.makeText(this, "Retrieving Thread Information", Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONObject thread = response.getJSONObject("thread");
                            title.setText(thread.getString("title"));
                            createdOn.setText(thread.getString("created_at"));
                            updatedOn.setText(thread.getString("updated_at"));
                            body.setText(thread.getString("description"));

                            JSONArray comments = response.getJSONArray("comments");
                            JSONArray comment_users = response.getJSONArray("comment  _users");
                            JSONArray times_readable = response.getJSONArray("times_readable");

                            commentEntries = new ArrayList<>();
                            for(int i=0; i<comments.length(); i++)
                                commentEntries.add(new CommentEntry(comment_users.getJSONObject(i).getString("first_name") + " " + comment_users.getJSONObject(i).getString("last_name"), comments.getJSONObject(i).getString("description"), times_readable.getString(i)));
                            CommentsListAdapter adapter = new CommentsListAdapter(ThreadInfo.this, commentEntries);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Toast.makeText(ThreadInfo.this, "JSONObjectException:\n"+e.getMessage()+"\nUser not loged in." +
                                    "\nPlease login and try again", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThreadInfo.this, "ErrorResponse:\n"+error.getMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonRequest);
    }

}
