package com.narwhalcompany.cantrip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FlightFragment extends AbstractPlanFragment {

    private TextView name;
    private TextView fromLocation;
    private TextView toLocation;
    private TextView fromTime;
    private TextView toTime;
    private TextView confirmation;

    public FlightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flight, container, false);


        name = view.findViewById(R.id.flight_name);
        fromLocation = view.findViewById(R.id.from_location);
        toLocation = view.findViewById(R.id.to_location);
        fromTime = view.findViewById(R.id.from_time);
        toTime = view.findViewById(R.id.to_time);
        confirmation = view.findViewById(R.id.confirmation_label);

        String planName = getArguments().getString("flightName");
        String planFromLocation = getArguments().getString("flightStartLoc");
        String planToLocation = getArguments().getString("flightEndLoc");
        String planFromTime = getArguments().getString("flightFromTime");
        String planToTime = getArguments().getString("flightToTime");
        String planConfirmation = getArguments().getString("flightConfirmation");

        name.setText(planName);
        fromLocation.setText(planFromLocation);
        toLocation.setText(planToLocation);
        fromTime.setText(planFromTime);
        toTime.setText(planToTime);
        confirmation.setText(planConfirmation);

        return view;
    }
}
