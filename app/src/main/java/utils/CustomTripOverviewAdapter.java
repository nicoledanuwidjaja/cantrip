package utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.narwhalcompany.cantrip.R;
import com.narwhalcompany.cantrip.TripOverviewAdapterItem;

import java.util.ArrayList;

public class CustomTripOverviewAdapter extends BaseAdapter {

    Context context;
    ArrayList<TripOverviewAdapterItem> tripAdapterItems;

    public CustomTripOverviewAdapter(Context context, ArrayList<TripOverviewAdapterItem> tripAdapterItems) {
        this.context = context;
        this.tripAdapterItems = tripAdapterItems;
    }

    @Override
    public int getCount() {
        return tripAdapterItems.size();
    }

    @Override
    public Object getItem(int i) {
        return tripAdapterItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            // create and return the view
            view = View.inflate(context, R.layout.activity_trip_overview_adapter_item, null);
        }

        ImageView imageView = view.findViewById(R.id.tripOverviewAdapterItemImage);
        TextView destView = view.findViewById(R.id.tripOverviewAdapterItemDestination);
        TextView durView = view.findViewById(R.id.tripOverviewAdapterItemDuration);

        TripOverviewAdapterItem curr = tripAdapterItems.get(i);

        // overwrite values of child views based on input from MainActivity
        imageView.setImageResource(curr.getImageResId());
        destView.setText(curr.getStartLoc() + " to " + curr.getEndloc());
        durView.setText(curr.getStartMonth() + "/" + curr.getStartDay() + "/" + curr.getStartYear()
                + " to " + curr.getEndMonth() + "/" + curr.getEndDay() + "/" + curr.getEndYear());

        // returns view for current row
        return view;
    }
}
