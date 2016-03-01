package com.rohit.fragmentstutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.net.CookieHandler;
import java.net.CookieManager;

public class MainActivity extends AppCompatActivity {

    public static String ip = "http://192.168.1.6:8000";
    public static CookieManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            getFragmentManager().beginTransaction().add(R.id.containerMenu, new MenuFragment()).commit();
        }

        manager = new CookieManager();
        CookieHandler.setDefault(manager);
    }
}
