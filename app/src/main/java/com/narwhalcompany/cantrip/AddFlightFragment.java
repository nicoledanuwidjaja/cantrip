package com.narwhalcompany.cantrip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import utils.OnDateClick;
import utils.OnTimeClick;
import utils.Plan;
import utils.Reservation;
import utils.Utils;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class AddFlightFragment extends DialogFragment {

    ImageView airplaneImage;
    TextInputEditText airlineText;
    TextInputEditText flightText;

    ImageView departImage;
    TextInputEditText departLocation;
    TextInputEditText departDate;
    TextInputEditText departTime;

    ImageView arriveImage;
    TextInputEditText arriveLocation;
    TextInputEditText arriveDate;
    TextInputEditText arriveTime;

    ImageView ticketImage;
    TextInputEditText confirmationNum;

    private Button saveButton;

    private String tripId;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public AddFlightFragment(String tripId) {
        this.tripId = tripId;
    }

    public AddFlightFragment() {
        // empty constructor
    }

    static AddFlightFragment newInstance() {
        return new AddFlightFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_flight, container, false);


        airplaneImage = view.findViewById(R.id.airplaneImage);
        airlineText = view.findViewById(R.id.airlineText);
        flightText = view.findViewById(R.id.flightText);

        departImage = view.findViewById(R.id.departImage);
        departLocation = view.findViewById(R.id.departLocation);

        departDate = view.findViewById(R.id.departDate);
        departDate.setOnClickListener(new OnDateClick());

        departTime = view.findViewById(R.id.departTime);
        departTime.setOnClickListener(new OnTimeClick());

        arriveImage = view.findViewById(R.id.arriveImage);
        arriveLocation = view.findViewById(R.id.arriveLocation);
        arriveDate = view.findViewById(R.id.arriveDate);
        arriveDate.setOnClickListener(new OnDateClick());

        arriveTime = view.findViewById(R.id.arriveTime);
        arriveTime.setOnClickListener(new OnTimeClick());

        ticketImage = view.findViewById(R.id.ticketImage);
        confirmationNum = view.findViewById(R.id.confirmationNum);
        String apiKey = getString(R.string.google_places_api);

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_empty_recommended, container, false);

        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Initialize the AutocompleteSupportFragment for start location
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.search_bar_flight);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                departLocation.setText(place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        departLocation = view.findViewById(R.id.departLocation);
        arriveLocation = view.findViewById(R.id.arriveLocation);

        // Initialize the AutocompleteSupportFragment for destination
        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.search_bar_flight_2);

        // Specify the types of place data to return.
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                arriveLocation.setText(place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFlightIntent = new Intent(getActivity(), DetailedTripActivity.class);

//                String tripId = getArguments().getString("trip id");
                DatabaseReference planRef = databaseReference.child("trips").child(tripId)
                        .child("plans").push();
                String planKey = planRef.getKey();


                Plan newFlight = new Plan(planKey,
                        "Flight to " + arriveLocation.getText().toString(),
                        Utils.stringToDate(departDate.getText().toString()),
                        Utils.stringToDate(arriveDate.getText().toString()),
                        tripId,
                        Reservation.FLIGHT, departLocation.getText().toString(),
                        Utils.stringToHours(departTime.getText().toString()),
                        Utils.stringToMins(departTime.getText().toString()),
                        Utils.stringToHours(arriveTime.getText().toString()),
                        Utils.stringToMins(arriveTime.getText().toString()),
                        arriveLocation.getText().toString());

                planRef.setValue(newFlight);

                startActivity(addFlightIntent);
            }
        });


        return view;
    }



}


