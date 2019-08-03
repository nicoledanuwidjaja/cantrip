package com.narwhalcompany.cantrip;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.narwhalcompany.cantrip.model.main.TripObject;
import com.narwhalcompany.cantrip.ui.main.CustomTripObjectAdapter;

import java.util.ArrayList;


// import utils.CustomTripOverviewAdapter;
import utils.TripOverviewAdapterItem;



/**
 * A simple {@link Fragment} subclass.
 */
public class MyTripListFragment extends Fragment {


//    private Context context;

    private ArrayList<TripOverviewAdapterItem> tripList = new ArrayList<>();

    ArrayList<TripObject> tripObjectList = new ArrayList<>();
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();



    public MyTripListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_trip_list, container, false);

        populateList();


        if (getArguments() != null) {
            Bundle tripBundle = getArguments();
            tripList.add(new TripOverviewAdapterItem(R.drawable.commons, tripBundle.getInt("startMonth"), tripBundle.getInt("startDay"),
                    tripBundle.getInt("startYear"), tripBundle.getInt("endMonth"), tripBundle.getInt("endDay"),
                    tripBundle.getInt("endYear"), tripBundle.getString("startLocation"), tripBundle.getString("endLocation")));
        }


        ListView listView = view.findViewById(R.id.myTripsList);
        // CustomTripOverviewAdapter adapter = new CustomTripOverviewAdapter(getContext(), databaseRef.child("user"));

        CustomTripObjectAdapter adapter = new CustomTripObjectAdapter(getContext());

        // code for accessing view for each trip
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // trigger second activity - DetailedTripActivity

                Intent tripIntent = new Intent(getActivity().getApplicationContext(), DetailedTripActivity.class);
//                tripIntent.putExtra("TRIP", tripList.get(i));

                String tripName = tripList.get(i).getStartLoc() + " to " + tripList.get(i).getEndloc();
                System.out.println("THIS IS MY TRIP " + tripName);
                tripIntent.putExtra("tripName", tripName);

//                Intent tripIntent = new Intent(getContext(), DetailedTripActivity.class);
//                tripIntent.putExtra("TRIP", tripList.get(i).getEndLoc());

                startActivity(tripIntent);
            }
        });
        FloatingActionButton fab = view.findViewById(R.id.add_trip_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sets a fragment manager for managing all fragments (for adding new trips)
                AddNewTripLocationFragment newTripFragment = new AddNewTripLocationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newTripFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    public void populateList() {
//        tripList.add(new TripOverviewAdapterItem(R.drawable.sedan, 7, 4, 2019,
//                7, 8, 2019, "Boston", "NYC"));
//        tripList.add(new TripOverviewAdapterItem(R.drawable.commons, 7, 9, 2019,
//                7, 30, 2019, "Sacramento", "Boston"));
//        tripList.add(new TripOverviewAdapterItem(R.drawable.northeastern, 9, 4, 2019,
//                4, 15, 2020, "Las Vegas", "Boston"));
        ValueEventListener tripListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TripObject newTrip = dataSnapshot.getValue(TripObject.class);
                tripObjectList.add(newTrip);
                databaseRef.child(newTrip.toString()).setValue(newTrip);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseRef.push().setValue("hello world");

        databaseRef.addValueEventListener(tripListener);
    }

}
