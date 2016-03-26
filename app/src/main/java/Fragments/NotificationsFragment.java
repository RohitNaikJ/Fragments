package Fragments;


import android.app.Fragment;
import android.os.Bundle;
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
import com.rohit.fragmentstutorial.MainActivity;
import com.rohit.fragmentstutorial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.NotificationListAdapter;
import ListItems.NotificationEntry;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    List<NotificationEntry> notificationEntries;
    ListView listView;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView_notifications);

        String url = MainActivity.ip+"/default/notifications.json";
        Toast.makeText(getActivity(), "Retrieving Notifications", Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONArray notifications = response.getJSONArray("notifications");

                            notificationEntries = new ArrayList<>();
                            notificationEntries.add(new NotificationEntry());
                            for(int i=0; i< notifications.length(); i++)
                                notificationEntries.add(new NotificationEntry(i+1, notifications.getJSONObject(i).getString("description"), notifications.getJSONObject(i).getString("created_at")));

                            NotificationListAdapter adapter = new NotificationListAdapter(getActivity(), notificationEntries);
                            adapter.setActivity(getActivity());
                            adapter.setNotificationEntries(notificationEntries);
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
