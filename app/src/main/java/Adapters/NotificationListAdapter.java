package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rohit.fragmentstutorial.R;

import java.util.List;

import ListItems.NotificationEntry;

/**
 * Created by Rohit on 3/27/16.
 */
public class NotificationListAdapter extends ArrayAdapter<NotificationEntry> {

    private Activity activity;
    private List<NotificationEntry> notificationEntries;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setNotificationEntries(List<NotificationEntry> notificationEntries) {
        this.notificationEntries = notificationEntries;
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param notificationEntries  The objects to represent in the ListView.
     */
    public NotificationListAdapter(Context context, List<NotificationEntry> notificationEntries) {
        super(context, R.layout.item_notification, notificationEntries);
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null)
            itemView = activity.getLayoutInflater().inflate(R.layout.item_notification, parent, false);

        TextView id = (TextView) itemView.findViewById(R.id.notification_id);
        TextView not = (TextView) itemView.findViewById(R.id.notification_not);
        TextView date = (TextView) itemView.findViewById(R.id.notification_date);

        if(position!=0) {
            NotificationEntry notificationEntry = notificationEntries.get(position);

            id.setText(notificationEntry.getId() + "");
            not.setText(Html.fromHtml(notificationEntry.getNotification()));
            date.setText(notificationEntry.getCreatedOn().substring(0, 11) + "\n" + notificationEntry.getCreatedOn().substring(12));
        }else{
            id.setTypeface(null, Typeface.BOLD);
            not.setTypeface(null, Typeface.BOLD);
            date.setTypeface(null, Typeface.BOLD);
        }
        return itemView;
    }
}
