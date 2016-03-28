package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rohit.fragmentstutorial.CommentEntry;
import com.rohit.fragmentstutorial.R;

import java.util.List;

/**
 * Created by Rohit on 3/28/16.
 */
public class CommentsListAdapter extends ArrayAdapter<CommentEntry> {

    Activity activity;
    List<CommentEntry> commentEntries;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public CommentsListAdapter(Context context, List<CommentEntry> objects) {
        super(context, R.layout.item_thread_comment, objects);
        this.activity = (Activity) context;
        this.commentEntries = objects;
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
            itemView = activity.getLayoutInflater().inflate(R.layout.item_thread_comment, parent, false);

        CommentEntry commentEntry = commentEntries.get(position);

        TextView user = (TextView) itemView.findViewById(R.id.comment_user);
        TextView treadable = (TextView) itemView.findViewById(R.id.comment_treadable);
        TextView description = (TextView) itemView.findViewById(R.id.comment_desc);

        user.setText(commentEntry.getUser());
        treadable.setText(commentEntry.getTreadable());
        description.setText(commentEntry.getBody());

        return itemView;
    }
}
