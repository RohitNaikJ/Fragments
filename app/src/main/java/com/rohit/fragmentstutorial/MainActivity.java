package com.rohit.fragmentstutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static String ip = "http://192.168.1.6:8000";
    SharedPreferences sharedPreferences;
//    public static CookieManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
//        manager = new CookieManager();
//        CookieHandler.setDefault(manager);

        Intent intent = getIntent();
        boolean alreadyLogedIn = intent.getBooleanExtra("ALREADYLOGEDIN", false);
        if(alreadyLogedIn){
            final String url = MainActivity.ip + "/default/login.json?userid="+sharedPreferences.getString("USERNAME", "123")+"&password="+sharedPreferences.getString("PASSWORD", "123");
            Toast.makeText(MainActivity.this, "Logging into a previously logged in user", Toast.LENGTH_SHORT).show();
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                if (response.getBoolean("success")) {
                                    Toast.makeText(MainActivity.this, "LogIn Successful", Toast.LENGTH_SHORT).show();
                                    getFragmentManager().beginTransaction().add(R.id.containerMenu, new MenuFragment()).commit();
                                } else
                                    Toast.makeText(MainActivity.this, "LogIn Unsuccessful", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Toast.makeText(MainActivity.this, "JSONObjectException:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "OnErrorResponse:\n" + error.getMessage(), Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    });
            Volley.newRequestQueue(MainActivity.this).add(jsonRequest);
        }else if(savedInstanceState==null){
            getFragmentManager().beginTransaction().add(R.id.containerMenu, new MenuFragment()).commit();
        }
    }
}
