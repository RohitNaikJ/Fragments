package Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rohit.fragmentstutorial.R;

import java.util.List;

import ListItems.GradeEntry;

/**
 * Created by Rohit on 3/1/16.
 */
public class GradeListAdapter extends ArrayAdapter<GradeEntry> {

    Activity activity;
    List<GradeEntry> gradeEntries;

    public GradeListAdapter(Activity activity, List<GradeEntry> gradeEntries) {
        super(activity, R.layout.item_grade, gradeEntries);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setGradeEntries(List<GradeEntry> gradeEntries) {
        this.gradeEntries = gradeEntries;
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
            itemView = activity.getLayoutInflater().inflate(R.layout.item_grade, parent, false);

        //find the GradeEntry to work with using position
        GradeEntry gradeEntry = gradeEntries.get(position);

        //associating views of the layout item.grade with gradeEntry variable, i.e., populating the itemView
        TextView code = (TextView)itemView.findViewById(R.id.grade_code);
        code.setText(gradeEntry.getCode());

        TextView name = (TextView)itemView.findViewById(R.id.grade_name);
        name.setText(gradeEntry.getName());

        TextView score = (TextView)itemView.findViewById(R.id.grade_score);
        score.setText(gradeEntry.getScore()+"/"+gradeEntry.getOut_of());

        TextView weight = (TextView)itemView.findViewById(R.id.grade_weightage);
        weight.setText(gradeEntry.getWeightage()+"");

        TextView abs = (TextView)itemView.findViewById(R.id.grade_absolute);
        abs.setText(gradeEntry.getAbs()+"");

        return itemView;
    }
}
