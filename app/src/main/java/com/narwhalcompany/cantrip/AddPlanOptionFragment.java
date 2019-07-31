package com.narwhalcompany.cantrip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import utils.CustomPlanListAdapter;
import utils.Plan;

public class AddPlanOptionFragment extends Fragment {

    private ImageButton flightButton;
    private ImageButton hotelButton;
    private ImageButton landmarkButton;

    public AddPlanOptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_plan_option, container, false);


        flightButton = view.findViewById(R.id.flight_button);

        // sends page to add flight screen
        flightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sets a fragment manager for managing all fragments (for adding new trips)
                AddFlightFragment newFlight = new AddFlightFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.add_plan_container, newFlight);
                transaction.isAddToBackStackAllowed();
                transaction.commit();
            }
        });

        hotelButton = view.findViewById(R.id.hotel_button);

        hotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sets a fragment manager for managing all fragments (for adding new trips)
                AddHotelFragment newHotel = new AddHotelFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.add_plan_container, newHotel);
                transaction.isAddToBackStackAllowed();
                transaction.commit();
            }
        });

        landmarkButton = view.findViewById(R.id.landmark_button);

        landmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sets a fragment manager for managing all fragments (for adding new trips)
//                AddLandmarkFragment newLandmark = new AddLandmarkFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.add(R.id.add_plan_container, newLandmark);
                transaction.isAddToBackStackAllowed();
                transaction.commit();
            }
        });

        return view;
    }
}
