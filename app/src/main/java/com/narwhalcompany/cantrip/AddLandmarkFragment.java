package com.narwhalcompany.cantrip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.narwhalcompany.cantrip.model.main.TripObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    private EditText landmarkAddress;
    private EditText startDate;
    private EditText endDate;
    private EditText startTime;
    private EditText endTime;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private String location;
    private String tripId;
    private String placeId;
    private View view;


    private String tripName = "";
    private String tripDuration = "";

    public AddLandmarkFragment() {

    }

    public AddLandmarkFragment(String tripId) {
        this.tripId = tripId;
    }

    public AddLandmarkFragment(String tripId, String placeId) {
        this.tripId = tripId;
        this.placeId = placeId;
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


        if (placeId != null) {
            view = inflater.inflate(R.layout.fragment_save_landmark, container, false);
            landmarkName = view.findViewById(R.id.landmarkName);

        }
        else {
            view = inflater.inflate(R.layout.fragment_add_landmark, container, false);
        }


        landmarkAddress = view.findViewById(R.id.landmarkAddress);
        startDate = view.findViewById(R.id.visit_start_date);
        endDate = view.findViewById(R.id.visit_end_date);
        startTime = view.findViewById(R.id.startTimeText);
        endTime = view.findViewById(R.id.endTimeText);

        startDate.setOnClickListener(new

                OnDateClick());
        endDate.setOnClickListener(new

                OnDateClick());
        startTime.setOnClickListener(new

                OnTimeClick());
        endTime.setOnClickListener(new

                OnTimeClick());

        // set up Places API
        String apiKey = getString(R.string.google_places_api);

        if (placeId != null) {
            // Specify the fields to return.
            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);

            // Construct a request object, passing the place ID and fields array.
            FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields)
                    .build();

            // Initialize Places.
            Places.initialize(getActivity().getApplicationContext(), apiKey);

            PlacesClient placesClient = Places.createClient(getContext());
            // Add a listener to handle the response.
            placesClient.fetchPlace(request
            ).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                @Override
                public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                    Place place = fetchPlaceResponse.getPlace();
                    location = place.getName();
                    landmarkName.setText(location);
                    landmarkName.setFocusable(false);
                    landmarkName.setClickable(false);

                    landmarkAddress.setText(place.getAddress());
                    System.out.println("address: " + place.getAddress());
                    landmarkAddress.setFocusable(false);
                    landmarkAddress.setClickable(false);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ApiException) {
                        ApiException apiException = (ApiException) e;
                        int statusCode = apiException.getStatusCode();
                        // Handle error with given status code.
                        Log.e(TAG, "Place not found: " + e.getMessage());
                    }
                }
            });
        } else {

            Places.initialize(getActivity().getApplicationContext(), apiKey);


            // Initialize the AutocompleteSupportFragment for start location
            AutocompleteSupportFragment landmarkLocation = (AutocompleteSupportFragment)
                    getFragmentManager().findFragmentById(R.id.search_bar_landmark);

            landmarkLocation.setTypeFilter(TypeFilter.ESTABLISHMENT);

            // Specify the types of place data to return.
            landmarkLocation.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));

            // Set up a PlaceSelectionListener to handle the response.
            landmarkLocation.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    placeId = place.getId();
                    location = place.getName();
                    landmarkAddress.setText(place.getAddress());
                }

                @Override
                public void onError(Status status) {
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        }

        databaseReference.child("trips").child(tripId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TripObject trip = dataSnapshot.getValue(TripObject.class);
                tripName = trip.getEndLoc();
                tripDuration = trip.formatDate(trip.getStartDate()) + " to "
                        + trip.formatDate(trip.getEndDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFieldsComplete()) {
                    Toast.makeText(getContext(), "All fields need to be complete.", Toast.LENGTH_LONG).show();
                } else if (!Utils.isDatePeriodValid(startDate.getText().toString(), endDate.getText().toString())
                        && startDate.getText().toString().compareTo(endDate.getText().toString()) == 0
                        && Utils.isTimePeriodValidGivenValidDates(startTime.getText().toString(), endTime.getText().toString())) {
                    Toast.makeText(getContext(), "Cannot end before starting.", Toast.LENGTH_LONG).show();
                } else {

                    Intent addLandmarkIntent = new Intent(getActivity(), DetailedTripActivity.class);

                    addLandmarkIntent.putExtra("trip id", tripId);
                    addLandmarkIntent.putExtra("tripName", tripName);
                    addLandmarkIntent.putExtra("tripDuration", tripDuration);
                    DatabaseReference planRef = databaseReference.child("plans" + tripId).push();
                    String planKey = planRef.getKey();

                    // create a new plan and save data
                    Plan newFlight = new Plan(planKey,
                            "Visit to " + location,
                            Utils.stringToDate(startDate.getText().toString()),
                            Utils.stringToDate(endDate.getText().toString()),
                            tripId, Reservation.LANDMARK, location,
                            Utils.stringToHours(startTime.getText().toString()),
                            Utils.stringToMins(startTime.getText().toString()),
                            Utils.stringToHours(endTime.getText().toString()),
                            Utils.stringToMins(endTime.getText().toString()), placeId);

                    planRef.setValue(newFlight);
                    startActivity(addLandmarkIntent);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // checks to see if all fields of form are complete
    private boolean allFieldsComplete() {
        return startTime != null && endTime != null
                && (landmarkName.getText().toString().equals("")
                && startDate.getText().toString().equals("")
                && endDate.getText().toString().equals(""));
    }
}