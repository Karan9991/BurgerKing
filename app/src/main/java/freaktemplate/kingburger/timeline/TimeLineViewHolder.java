package freaktemplate.kingburger.timeline;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;

import freaktemplate.kingburger.R;

/**
 * Created by RedixbitUser on 3/22/2018.
 */

public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    final TextView mDate;
    final TextView mMessage;
    final TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        mDate = itemView.findViewById(R.id.tv_date);
        mMessage = itemView.findViewById(R.id.tv_orderStatus);
        mTimelineView = itemView.findViewById(R.id.time_marker);
        mTimelineView.initLine(viewType);
    }
}