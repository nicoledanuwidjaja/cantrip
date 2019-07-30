package com.narwhalcompany.cantrip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
*/
public class AddFlightFragment extends Fragment {

//    ImageView airplaneImage;
//    TextView airlineText;
//    TextView flightText;
//
//    ImageView departImage;
//    TextView departLocation;
//    TextView departDate;
//    TextView departTime;
//
//    ImageView arriveImage;
//    TextView arriveLocation;
//    TextView arriveDate;
//    TextView arriveTime;
      Button saveButton;

    public AddFlightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_flight, container, false);

//        ImageView airplaneImage = view.findViewById(R.id.airplaneImage);
//        TextView airlineText = view.findViewById(R.id.airlineText);
//        TextView flightText = view.findViewById(R.id.flightText);
//
//        ImageView departImage = view.findViewById(R.id.departImage);
//        TextView departLocation = view.findViewById(R.id.departLocation);
//        TextView departDate = view.findViewById(R.id.departDate);
//        TextView departTime = view.findViewById(R.id.departDate);
//
//        ImageView arriveImage = view.findViewById(R.id.arriveImage);
//        TextView arriveLocation = view.findViewById(R.id.arriveLocation);
//        TextView arriveDate = view.findViewById(R.id.arriveDate);
//        TextView arriveTime = view.findViewById(R.id.arriveTime);

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFlightIntent = new Intent(getActivity(), DetailedTripActivity.class);
                startActivity(addFlightIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
