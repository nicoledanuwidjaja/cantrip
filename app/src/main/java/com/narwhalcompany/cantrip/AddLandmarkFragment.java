package com.narwhalcompany.cantrip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import utils.OnDateClick;
import utils.OnTimeClick;
import utils.Plan;
import utils.Reservation;
import utils.Utils;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddLandmarkFragment extends DialogFragment {

    private Button saveButton;
    private EditText landmarkName;
    private EditText startDate;
    private EditText endDate;
    private EditText startTime;
    private EditText endTime;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private String location;
    private String tripId;

    public AddLandmarkFragment() {

    }

    public AddLandmarkFragment(String tripId) {
        this.tripId = tripId;
    }

    static AddLandmarkFragment newInstance() {
        return new AddLandmarkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_landmark, container, false);

        landmarkName = view.findViewById(R.id.landmarkName);
        startDate = view.findViewById(R.id.startDateText);
        endDate = view.findViewById(R.id.endDateText);
        startTime = view.findViewById(R.id.startTimeText);
        endTime = view.findViewById(R.id.endTimeText);

        startDate.setOnClickListener(new OnDateClick());
        endDate.setOnClickListener(new OnDateClick());
        startTime.setOnClickListener(new OnTimeClick());
        endTime.setOnClickListener(new OnTimeClick());

        // set up Places API
        String apiKey = getString(R.string.google_places_api);

        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Initialize the AutocompleteSupportFragment for start location
        AutocompleteSupportFragment landmarkLocation = (AutocompleteSupportFragment)
                getFragmentManager().findFragmentById(R.id.search_bar_landmark);

        // Specify the types of place data to return.
        landmarkLocation.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        landmarkLocation.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                location = place.getName();
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
                Intent addLandmarkIntent = new Intent(getActivity(), DetailedTripActivity.class);

                addLandmarkIntent.putExtra("trip id", tripId);
                DatabaseReference planRef = databaseReference.child("plans" + tripId).push();
                String planKey = planRef.getKey();

                // create a new plan and save data
                Plan newFlight = new Plan(planKey,
                        landmarkName.getText().toString(),
                        Utils.stringToDate(startDate.getText().toString()),
                        Utils.stringToDate(endDate.getText().toString()),
                        tripId, Reservation.LANDMARK, null,
                        Utils.stringToHours(startTime.getText().toString()),
                        Utils.stringToMins(startTime.getText().toString()),
                        Utils.stringToHours(endTime.getText().toString()),
                        Utils.stringToMins(endTime.getText().toString()),
                        location);

                planRef.setValue(newFlight);
                startActivity(addLandmarkIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}