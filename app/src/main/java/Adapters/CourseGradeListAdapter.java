package Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rohit.fragmentstutorial.R;

import java.util.List;

import ListItems.CourseGradeEntry;

/**
 * Created by Rohit on 3/26/16.
 */
public class CourseGradeListAdapter extends ArrayAdapter<CourseGradeEntry> {
    Activity activity;
    List<CourseGradeEntry> gradeEntries;

    public CourseGradeListAdapter(Activity activity, List<CourseGradeEntry> gradeEntries) {
        super(activity, R.layout.item_coursegrade, gradeEntries);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setGradeEntries(List<CourseGradeEntry> gradeEntries) {
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
            itemView = activity.getLayoutInflater().inflate(R.layout.item_coursegrade, parent, false);

        //find the CourseGradeEntry to work with
        CourseGradeEntry gradeEntry = gradeEntries.get(position);

        //associate the view with the fields of the CourseGradeEntry class
        TextView id = (TextView) itemView.findViewById(R.id.course_grade_code);
        id.setText(gradeEntry.getid()+"");

        TextView name = (TextView) itemView.findViewById(R.id.course_grade_name);
        name.setText(gradeEntry.getName());

        TextView score = (TextView) itemView.findViewById(R.id.course_grade_score);
        score.setText(gradeEntry.getScore()+"/"+gradeEntry.getOut_of());

        TextView weightage = (TextView) itemView.findViewById(R.id.course_grade_weightage);
        weightage.setText(gradeEntry.getWeightage()+"");

        TextView abs = (TextView) itemView.findViewById(R.id.course_grade_absolute);
        abs.setText(gradeEntry.getAbs()+"");

        return itemView;
    }
}
