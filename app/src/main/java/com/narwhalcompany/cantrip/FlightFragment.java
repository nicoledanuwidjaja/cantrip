package com.narwhalcompany.cantrip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FlightFragment extends AbstractPlanFragment {

    private TextView flightName;

    public FlightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flight, container, false);

        flightName = view.findViewById(R.id.flight_name);
        String planName = getArguments().getString("flightName");
        flightName.setText(planName);

        return view;
    }
}
