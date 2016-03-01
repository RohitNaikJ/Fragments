package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rohit.fragmentstutorial.CourseEntry;
import com.rohit.fragmentstutorial.R;

import java.util.List;

/**
 * Created by Rohit on 2/29/16.
 */
public class CourseListAdapter extends ArrayAdapter<CourseEntry> {
    Activity activity;
    List<CourseEntry> courseEntries;

    public CourseListAdapter(Context context, List<CourseEntry> courseEntries){
        super(context, R.layout.item_course, courseEntries);
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void setCourseEntries(List<CourseEntry> courseEntries){
        this.courseEntries = courseEntries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null)
            itemView = activity.getLayoutInflater().inflate(R.layout.item_course, parent, false);

        //find the course to work with using "position"
        CourseEntry currentCourse = courseEntries.get(position);

        //filling the view
        TextView id = (TextView)itemView.findViewById(R.id.course_id);
        id.setText(currentCourse.getId()+"");

        TextView code = (TextView)itemView.findViewById(R.id.course_code);
        code.setText(currentCourse.getCode());

        TextView name = (TextView)itemView.findViewById(R.id.course_name);
        name.setText(currentCourse.getName());

        TextView ltp = (TextView)itemView.findViewById(R.id.course_ltp);
        ltp.setText(currentCourse.getLtp());

        TextView credits = (TextView)itemView.findViewById(R.id.course_credits);
        credits.setText("("+currentCourse.getCredits()+")");
        
        return itemView;
    }
}
