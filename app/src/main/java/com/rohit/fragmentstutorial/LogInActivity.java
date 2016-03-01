package com.rohit.fragmentstutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;

public class LogInActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CookieManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = new CookieManager();
        CookieHandler.setDefault(manager);

        sharedPreferences = getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean("ISLOGIN", false)){
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            intent.putExtra("ALREADYLOGEDIN", true);
            startActivity(intent);
        }

        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = MainActivity.ip + "/default/login.json?userid=cs1110200&password=john";
                Toast.makeText(LogInActivity.this, "Connecting to the server", Toast.LENGTH_SHORT).show();
                JsonObjectRequest jsonRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // the response is already constructed as a JSONObject!
                                try {
                                    if (response.getBoolean("success")) {
                                        Toast.makeText(LogInActivity.this, "LogIn Successful", Toast.LENGTH_SHORT).show();
                                        JSONObject userDetails = response.getJSONObject("user");
                                        editor.putBoolean("ISLOGIN", true);
                                        editor.putString("NAME", userDetails.getString("first_name") + " " + userDetails.getString("last_name"));
                                        editor.putString("EMAIL", userDetails.getString("email"));
                                        editor.putString("USERNAME", userDetails.getString("username"));
                                        editor.putString("PASSWORD", userDetails.getString("password"));
                                        editor.putString("ENTRYNO", userDetails.getString("entry_no"));
                                        editor.commit();

                                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                        intent.putExtra("ALREADYLOGEDIN", false);
                                        startActivity(intent);
                                    } else
                                        Toast.makeText(LogInActivity.this, "LogIn Unsuccessful", Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    Toast.makeText(LogInActivity.this, "JSONObjectException:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LogInActivity.this, "OnErrorResponse:\n" + error.getMessage(), Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                            }
                        });
                Volley.newRequestQueue(LogInActivity.this).add(jsonRequest);
            }
        });
    }

}
