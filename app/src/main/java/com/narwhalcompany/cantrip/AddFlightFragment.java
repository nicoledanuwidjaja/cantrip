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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

    private ImageView airplaneImage;
    private EditText airlineText;
    private EditText flightText;
    private TextView airplaneLabel;

    private ImageView departImage;
    private TextView departLocation;
    private EditText departDate;
    private EditText departTime;

    private ImageView arriveImage;
    private TextView arriveLocation;
    private EditText arriveDate;
    private EditText arriveTime;

    private ImageView ticketImage;
    private EditText confirmationNum;

    private Button saveButton;
    private String tripId;
    private String startLoc;
    private String endLoc;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public AddFlightFragment() {
        // empty constructor
    }

    public AddFlightFragment(String tripId) {
        this.tripId = tripId;
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


        airplaneLabel = view.findViewById(R.id.airplane_label);
        airplaneImage = view.findViewById(R.id.airplaneImage);
        airlineText = view.findViewById(R.id.airlineText);
        flightText = view.findViewById(R.id.flightText);

        departImage = view.findViewById(R.id.departImage);

        departDate = view.findViewById(R.id.departDate);
        departDate.setOnClickListener(new OnDateClick());

        departTime = view.findViewById(R.id.departTime);
        departTime.setOnClickListener(new OnTimeClick());

        arriveImage = view.findViewById(R.id.arriveImage);
        arriveDate = view.findViewById(R.id.arriveDate);
        arriveDate.setOnClickListener(new OnDateClick());

        arriveTime = view.findViewById(R.id.arriveTime);
        arriveTime.setOnClickListener(new OnTimeClick());

        ticketImage = view.findViewById(R.id.ticketImage);
        confirmationNum = view.findViewById(R.id.confirmationNum);
        String apiKey = getString(R.string.google_places_api);

        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Initialize the AutocompleteSupportFragment for start location
        AutocompleteSupportFragment flightStartLocation = (AutocompleteSupportFragment)
                getFragmentManager().findFragmentById(R.id.home_flight_search);

        // Specify the types of place data to return.
        flightStartLocation.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        flightStartLocation.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                startLoc = place.getName();
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        AutocompleteSupportFragment flightEndLocation = (AutocompleteSupportFragment)
                getFragmentManager().findFragmentById(R.id.destination_flight_search);

        // Specify the types of place data to return.
        flightEndLocation.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        flightEndLocation.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                endLoc = place.getName();
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

                addFlightIntent.putExtra("trip id", tripId);
                DatabaseReference planRef = databaseReference.child("plans" + tripId).push();
                String planKey = planRef.getKey();

                // create a new plan and save data
                Plan newFlight = new Plan(planKey, airplaneLabel.getText().toString(),
                        Utils.stringToDate(departDate.getText().toString()),
                        Utils.stringToDate(arriveDate.getText().toString()),
                        tripId, Reservation.FLIGHT, startLoc,
                        Utils.stringToHours(departTime.getText().toString()),
                        Utils.stringToMins(departTime.getText().toString()),
                        Utils.stringToHours(arriveTime.getText().toString()),
                        Utils.stringToMins(arriveTime.getText().toString()),
                        endLoc, departDate.getText().toString());

                planRef.setValue(newFlight);

                startActivity(addFlightIntent);
            }
        });


        return view;
    }


}


