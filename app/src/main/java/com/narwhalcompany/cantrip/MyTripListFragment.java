package com.narwhalcompany.cantrip;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


// import utils.CustomTripOverviewAdapter;
import utils.CustomTripOverviewAdapter;
import utils.Plan;
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

        for (TripOverviewAdapterItem tripOverviewAdapterItem : tripList) {
            Log.d("trip overview", tripOverviewAdapterItem.getStartLoc());
        }

        System.out.println("trip length" + "" + tripList.size() + "");

        ListView listView = view.findViewById(R.id.myTripsList);
        // CustomTripOverviewAdapter adapter = new CustomTripOverviewAdapter(getContext(), databaseRef.child("user"));

        CustomTripOverviewAdapter adapter = new CustomTripOverviewAdapter(getContext(), databaseRef.child("trips"), tripList);

        // code for accessing view for each trip
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // trigger second activity - DetailedTripActivity

                Intent tripIntent = new Intent(getActivity().getApplicationContext(), DetailedTripActivity.class);
                tripIntent.putExtra("trip id", tripObjectList.get(i).getId());

                String tripName = tripObjectList.get(i).getStartLoc() + " to " + tripObjectList.get(i).getEndLoc();
                tripIntent.putExtra("tripName", tripName);

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

        tripList.add(new TripOverviewAdapterItem("place 1", "place2", new Date(), new Date()));


        databaseRef.child("trips").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TripObject newTrip = snapshot.getValue(TripObject.class);

                    tripObjectList.add(newTrip);
                    tripList.add(new TripOverviewAdapterItem(newTrip.getStartLoc(), newTrip.getEndLoc(),
                            newTrip.getStartDate(), newTrip.getEndDate()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("CANCELLED", databaseError.getMessage());
            }
        });
    }

}
