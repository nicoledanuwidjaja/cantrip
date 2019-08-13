package com.narwhalcompany.cantrip;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.ValueEventListener;
import com.narwhalcompany.cantrip.model.main.TripObject;

import java.util.Arrays;
import java.util.Date;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class AddFlightFragment extends DialogFragment {

    private TextView airplaneLabel;
    private EditText departDate;
    private EditText departTime;
    private TextView flightNumber;
    private EditText arriveDate;
    private EditText arriveTime;

    private Button saveButton;
    private String tripId;
    private String placeId;
    private String startLoc = "";
    private String endLoc = "";

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private String tripName = "";
    private String tripTime = "";
    private String tripDestination;

    private AutocompleteSupportFragment flightStartLocation;

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
    public void onDestroyView() {
        super.onDestroyView();
        if (flightStartLocation != null) {
            getFragmentManager().beginTransaction().remove(flightStartLocation).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_flight, container, false);

        OnDateClick departDatePicker = new OnDateClick();
        OnDateClick arriveDatePicker = new OnDateClick();
        OnTimeClick departTimePicker = new OnTimeClick();
        OnTimeClick arriveTimePicker = new OnTimeClick();

        flightNumber = view.findViewById(R.id.flightText);
        airplaneLabel = view.findViewById(R.id.airlineText);
        departDate = view.findViewById(R.id.departDate);
        arriveDate = view.findViewById(R.id.arriveDate);
        departTime = view.findViewById(R.id.departTime);
        arriveTime = view.findViewById(R.id.arriveTime);

        departDate.setOnClickListener(departDatePicker);
        arriveDate.setOnClickListener(arriveDatePicker);
        departTime.setOnClickListener(departTimePicker);
        arriveTime.setOnClickListener(arriveTimePicker);


        String apiKey = getString(R.string.google_places_api);

        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Initialize the AutocompleteSupportFragment for start location
        flightStartLocation = (AutocompleteSupportFragment)
                getFragmentManager().findFragmentById(R.id.home_flight_search);

        flightStartLocation.setTypeFilter(TypeFilter.ESTABLISHMENT);

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

        flightEndLocation.setTypeFilter(TypeFilter.ESTABLISHMENT);

        // Specify the types of place data to return.
        flightEndLocation.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        flightEndLocation.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                placeId = place.getId();
                endLoc = place.getName();
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        databaseReference.child("trips").child(tripId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TripObject tripInfo = dataSnapshot.getValue(TripObject.class);

                tripName = tripInfo.getStartLoc() + " to " + tripInfo.getEndLoc();
                tripTime = tripInfo.formatDate(tripInfo.getStartDate()) + " to "
                        + tripInfo.formatDate(tripInfo.getEndDate());
                tripDestination = tripInfo.getEndLoc();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String departDateString = departDate.getText().toString();
                String arriveDateString = arriveDate.getText().toString();
                String departTimeString = departTime.getText().toString();
                String arriveTimeString = arriveTime.getText().toString();


                if (areFieldsEmpty()) {
                    Toast.makeText(getContext(), "All fields need to be complete.", Toast.LENGTH_LONG).show();
                } else if (!Utils.isDateAndTimeValid(departDateString, arriveDateString, departTimeString, arriveTimeString)) {
                    Toast.makeText(getContext(), "Depart date and time must be after arrive date and time.", Toast.LENGTH_LONG).show();
                } else {
                    Intent addFlightIntent = new Intent(getActivity(), DetailedTripActivity.class);
                    addFlightIntent.putExtra("trip id", tripId);
                    addFlightIntent.putExtra("tripName", tripName);
                    addFlightIntent.putExtra("tripDuration", tripTime);
                    addFlightIntent.putExtra("tripDestination", tripDestination);

                    Date departDateFormat = Utils.stringToDate(departDate.getText().toString());
                    Date arriveDateFormat = Utils.stringToDate(arriveDate.getText().toString());

                    DatabaseReference planRef = databaseReference.child("plans" + tripId).push();
                    String planKey = planRef.getKey();

                    // create a new plan and save data
                    Plan newFlight = new Plan(planKey,
                            airplaneLabel.getText().toString() + " #" + flightNumber.getText(),
                            departDateFormat, arriveDateFormat,
                            tripId, Reservation.FLIGHT, startLoc,
                            Utils.stringToHours(departTime.getText().toString()),
                            Utils.stringToMins(departTime.getText().toString()),
                            Utils.stringToHours(arriveTime.getText().toString()),
                            Utils.stringToMins(arriveTime.getText().toString()),
                            endLoc, placeId);

                    planRef.setValue(newFlight);

                    startActivity(addFlightIntent);
                }
            }
        });

        return view;
    }

    // returns true if any field is empty
    private boolean areFieldsEmpty() {
        return airplaneLabel.getText().toString().equals("")
                || flightNumber.getText().toString().equals("")
                || startLoc.equals("")
                || endLoc.equals("")
                || departDate.getText().toString().equals("")
                || departTime.getText().toString().equals("")
                || arriveDate.getText().toString().equals("")
                || arriveTime.getText().toString().equals("");
    }
}


