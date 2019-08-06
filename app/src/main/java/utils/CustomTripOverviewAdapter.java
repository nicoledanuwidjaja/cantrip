package utils;

import android.content.Context;
import android.util.Log;
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
    ArrayList<TripOverviewAdapterItem> tripAdapterItems;
    DatabaseReference databaseChildRef;
    ArrayList<DataSnapshot> snapshots = new ArrayList<>();

//
    public CustomTripOverviewAdapter(Context context, DatabaseReference dataFromDB,
                                     ArrayList<TripOverviewAdapterItem> tripAdapterItems) {
        this.context = context;
        this.databaseChildRef = dataFromDB;
        this.tripAdapterItems = tripAdapterItems;
        dataFromDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("CHILD ADDED", dataSnapshot.getValue(TripObject.class).getStartLoc());
                TripObject dataTrip = dataSnapshot.getValue(TripObject.class);
                snapshots.add(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return snapshots.size();
    }

    @Override
    public Object getItem(int i) {
        return snapshots.get(i).getValue(TripObject.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            view = View.inflate(context, R.layout.trip_object_adapter_item, null);

            viewHolder = new ViewHolder();
            viewHolder.tripLocations = view.findViewById(R.id.trip_locations);
            viewHolder.tripDates = view.findViewById(R.id.trip_dates);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        TripObject tripObject = (TripObject)getItem(i);
        viewHolder.tripLocations.setText(tripObject.getStartLoc() + " to " + tripObject.getEndLoc());
        viewHolder.tripDates.setText(tripObject.getStartDate() + " to " + tripObject.getEndDate());


        return view;
    }

    static class ViewHolder {
        TextView tripLocations;
        TextView tripDates;
    }

}



