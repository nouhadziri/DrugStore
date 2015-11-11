package recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nouha.mapharmacie.R;

/**
 * Created by Nouha on 07/03/2015.
 */
public class FeedListRowHolder extends RecyclerView.ViewHolder {
    protected ImageView thumbnail;
    protected TextView title;
    protected TextView address;
    protected TextView type;

    public FeedListRowHolder(View view) {
        super(view);
        this.address = (TextView) view.findViewById(R.id.address);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.title = (TextView) view.findViewById(R.id.title);
        this.type = (TextView) view.findViewById(R.id.type);
    }

}

