package com.narwhalcompany.cantrip.ui.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.narwhalcompany.cantrip.R;
import com.narwhalcompany.cantrip.model.main.TripObject;

public class CustomTripObjectAdapter extends BaseAdapter {

    private Context context;
    private DatabaseReference databaseReference;

    public CustomTripObjectAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        view = View.inflate(this.context, R.layout.activity_trip_overview_adapter_item, null);

        DatabaseReference childRef = databaseReference.child("trips");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TripObject tripObject = dataSnapshot.getValue(TripObject.class);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TripObject tripObject = dataSnapshot.getValue(TripObject.class);
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

        return view;
    }
}
