package Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rohit.fragmentstutorial.R;

import java.util.List;

import ListItems.ThreadEntry;

/**
 * Created by Rohit on 3/28/16.
 */
public class ThreadListAdapter extends ArrayAdapter<ThreadEntry> {

    private Activity activity;
    private List<ThreadEntry> threadEntries;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param threadEntries  The objects to represent in the ListView.
     */
    public ThreadListAdapter(Context context, List<ThreadEntry> threadEntries) {
        super(context, R.layout.item_threads, threadEntries);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setThreadEntries(List<ThreadEntry> threadEntries) {
        this.threadEntries = threadEntries;
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
            itemView = activity.getLayoutInflater().inflate(R.layout.item_threads, parent, false);

        ThreadEntry threadEntry = threadEntries.get(position);

        TextView id = (TextView) itemView.findViewById(R.id.threads_id);
        TextView title = (TextView) itemView.findViewById(R.id.threads_title);
        TextView updatedOn = (TextView) itemView.findViewById(R.id.threads_updatedOn);
        if(position!=0) {
            id.setText(threadEntry.getId() + "");
            title.setText(threadEntry.getTitle().substring(0,1).toUpperCase()+threadEntry.getTitle().substring(1));
            updatedOn.setText(threadEntry.getUpdatedOn().substring(0,11)+"\n"+threadEntry.getUpdatedOn().substring(11));
        }else{
            id.setText("S.No");
            title.setText("Title");
            updatedOn.setText("Updated On");
            id.setTypeface(null, Typeface.BOLD);
            title.setTypeface(null, Typeface.BOLD);
            updatedOn.setTypeface(null, Typeface.BOLD);
        }
        return itemView;
    }
}
