package com.narwhalcompany.cantrip;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);

        ListView listView = view.findViewById(R.id.myTripsList);

        CustomTripOverviewAdapter adapter = new CustomTripOverviewAdapter(getActivity(), tripList);

        listView.setAdapter(adapter);

        return view;
    }

}
