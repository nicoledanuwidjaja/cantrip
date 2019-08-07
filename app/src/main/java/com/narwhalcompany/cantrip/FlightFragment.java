package com.narwhalcompany.cantrip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FlightFragment extends AbstractPlanFragment {

    private TextView flightName;
    private TextView fromLocation;
    private TextView toLocation;
    private TextView fromTime;
    private TextView toTime;

    public FlightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flight, container, false);

        flightName = view.findViewById(R.id.flight_name);
        fromLocation = view.findViewById(R.id.from_location);
        toLocation = view.findViewById(R.id.to_location);
        fromTime = view.findViewById(R.id.from_time);
        toTime = view.findViewById(R.id.to_time);


        // TODO: ADD MORE OF THESE LOL
        String planName = getArguments().getString("flightName");
        flightName.setText(planName);

        return view;
    }
}
