package com.narwhalcompany.cantrip;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import utils.CustomTripOverviewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTripsFragment extends Fragment {

    ArrayList<TripOverviewAdapterItem> tripList = new ArrayList<>();

    public MyTripsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tripList.add(new TripOverviewAdapterItem(R.drawable.sedan, 7, 4,
                7, 8, 2019, "Boston", "NYC"));
        tripList.add(new TripOverviewAdapterItem(R.drawable.commons, 7, 9,
                7, 30, 2019, "Sacramento", "Boston"));
        tripList.add(new TripOverviewAdapterItem(R.drawable.northeastern, 9, 4,
                4, 15, 2020, "Las Vegas", "Boston"));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);

        ListView listView = view.findViewById(R.id.myTripsList);

        // to sharon: thank GOD you made this!
        CustomTripOverviewAdapter adapter = new CustomTripOverviewAdapter(getActivity(), tripList);

        // code for accessing view for each trip
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // trigger second activity - DetailedTripActivity
                Intent tripIntent = new Intent(getActivity(), DetailedTripActivity.class);
                tripIntent.putExtra("TRIP", tripList.get(i));
                startActivity(tripIntent);
            }
        });

        return view;
    }

}
