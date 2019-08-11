package com.narwhalcompany.cantrip;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddPlanOptionFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private ImageButton flightButton;
    private ImageButton hotelButton;
    private ImageButton landmarkButton;
    private String tripId;

    public AddPlanOptionFragment() {
        // Required empty public constructor
    }

    public AddPlanOptionFragment(String tripId) {
       this.tripId = tripId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_plan_option, container, false);

        flightButton = view.findViewById(R.id.flight_button);
        hotelButton = view.findViewById(R.id.hotel_button);
        landmarkButton = view.findViewById(R.id.landmark_button);

        flightButton.setOnClickListener(this);
        hotelButton.setOnClickListener(this);
        landmarkButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        // sets a fragment manager for managing all fragments (for adding new trips)
        FragmentManager planManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = planManager.beginTransaction();

        switch (view.getId()) {
            case R.id.flight_button:
                DialogFragment newFlight = new AddFlightFragment(tripId);
                newFlight.show(planManager, "flight");
                break;
            case R.id.hotel_button:
                DialogFragment newHotel = new AddHotelFragment(tripId);
                newHotel.show(planManager, "hotel");
                break;
            case R.id.landmark_button:
                DialogFragment newLandmark = new AddLandmarkFragment(tripId);
                newLandmark.show(planManager, "landmark");
                break;
            default:
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
