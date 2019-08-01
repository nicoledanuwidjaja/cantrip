package utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.narwhalcompany.cantrip.R;
//import com.narwhalcompany.cantrip.TripOverviewAdapterItem;
import com.narwhalcompany.cantrip.model.main.TripObject;

import java.util.ArrayList;
import java.util.Date;

public class CustomTripOverviewAdapter extends BaseAdapter {
//
    Context context;
//    ArrayList<TripOverviewAdapterItem> tripAdapterItems;
    DatabaseReference databaseChildRef;
    ArrayList<DataSnapshot> snapshots;

//
    public CustomTripOverviewAdapter(Context context, DatabaseReference dataFromDB) {
        this.context = context;
//        // this.tripAdapterItems = tripAdapterItems;
        this.databaseChildRef = dataFromDB;
        dataFromDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                snapshots.add(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getCount() {
        return snapshots.size();
    }

    @Override
    public Object getItem(int i) {
        return snapshots.get(i).getValue();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            view = view.inflate(context, R.layout.trip_object_adapter_item, null);

            viewHolder = new ViewHolder();
            viewHolder.tripLocations = view.findViewById(R.id.trip_locations);
            viewHolder.tripDates = view.findViewById(R.id.trip_dates);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.tripLocations.setText(((TripObject)getItem(i).getStartLoc()) + " to " + ((TripObject)getItem(i).getEndLoc));
        viewHolder.tripDates.setText(((TripObject)getItem(i).getStartDate()) + " to " + ((TripObject)getItem(i).getEndDate));

        return view;
    }

    static class ViewHolder {
        TextView tripLocations;
        TextView tripDates;
    }
//
//    @Override
//    public int getCount() {
//        return tripAdapterItems.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return tripAdapterItems.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        if (view == null) {
//            // create and return the view
//            view = View.inflate(context, R.layout.activity_trip_overview_adapter_item, null);
//        }
//
//        ImageView imageView = view.findViewById(R.id.tripOverviewAdapterItemImage);
//        TextView destView = view.findViewById(R.id.tripOverviewAdapterItemDestination);
//        TextView durView = view.findViewById(R.id.tripOverviewAdapterItemDuration);
//
//        TripOverviewAdapterItem curr = TripOverviewAdapterItem
//                .createAdapterItemFromTripObject(dataFromDB.child("trips")., R.drawable.commons);
//
//        // overwrite values of child views based on input from MainActivity
//        imageView.setImageResource(curr.getImageResId());
//        destView.setText(curr.getStartLoc() + " to " + curr.getEndloc());
//        durView.setText(curr.getStartMonth() + "/" + curr.getStartDay() + "/" + curr.getStartYear()
//                + " to " + curr.getEndMonth() + "/" + curr.getEndDay() + "/" + curr.getEndYear());
//
//        // returns view for current row
//        return view;
//    }
}



