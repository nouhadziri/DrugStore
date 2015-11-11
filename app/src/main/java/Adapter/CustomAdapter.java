package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.example.nouha.mapharmacie.R;

import java.util.ArrayList;

import wrapper.ListItemWrapper;

/**
 * Created by Nouha on 07/03/2015.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    private ArrayList<ListItemWrapper> mObjectsList = new ArrayList<ListItemWrapper>();

    public CustomAdapter(ArrayList<ListItemWrapper> itemsList) {

        mObjectsList = itemsList;
    }

    @Override
    public int getItemCount() {

        return mObjectsList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_item_user, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ... Nothing to do
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mItemName.setText(mObjectsList.get(position).getName());
        holder.mItemLastName.setText(mObjectsList.get(position).getLastName());

        holder.mItemIcon.setImageResource(mObjectsList.get(position)
                .getItemImage());
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemName;
        public TextView mItemLastName;

        public ImageView mItemIcon;

        public ViewHolder(View view) {
            super(view);
            mItemName = (TextView) view.findViewById(R.id.Name);
            mItemLastName = (TextView) view.findViewById(R.id.LastName);
            mItemIcon = (ImageView) view.findViewById(R.id.image);

        }
    }

}
