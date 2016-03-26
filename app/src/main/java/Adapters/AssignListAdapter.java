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

import ListItems.AssignmentEntry;

/**
 * Created by Rohit on 3/24/16.
 */
public class AssignListAdapter extends ArrayAdapter<AssignmentEntry>{
    Activity activity;
    List<AssignmentEntry> assignEntries;

    public AssignListAdapter(Context context, List<AssignmentEntry> objects) {
        super(context, R.layout.item_assignment, objects);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setAssignEntries(List<AssignmentEntry> assignEntries) {
        this.assignEntries = assignEntries;
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
            itemView = activity.getLayoutInflater().inflate(R.layout.item_assignment, parent, false);

        AssignmentEntry assignmentEntry = assignEntries.get(position);

        if(position!=0) {
            TextView id = (TextView) itemView.findViewById(R.id.assign_id);
            id.setText(assignmentEntry.getId() + "");

            TextView name = (TextView) itemView.findViewById(R.id.assign_name);
            name.setText(assignmentEntry.getName());

            TextView deadline = (TextView) itemView.findViewById(R.id.assign_deadline);
            String d = assignmentEntry.getDeadline();
            deadline.setText(d.substring(0, 10) + "\n" + d.substring(11));
        }else{
            TextView id = (TextView) itemView.findViewById(R.id.assign_id);
            id.setTypeface(null, Typeface.BOLD);
            TextView name = (TextView) itemView.findViewById(R.id.assign_name);
            name.setTypeface(null, Typeface.BOLD);
            TextView deadline = (TextView) itemView.findViewById(R.id.assign_deadline);
            deadline.setTypeface(null, Typeface.BOLD);
        }
        return itemView;
    }
}
