package com.narwhalcompany.cantrip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import utils.OnDateClick;
import utils.OnTimeClick;
import utils.Plan;
import utils.Reservation;
import utils.Utils;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddHotelFragment extends DialogFragment {

    private Button saveButton;
    private EditText checkInText;
    private EditText checkOutText;
    private EditText checkInTime;
    private EditText checkOutTime;
    private EditText hotelAddress;
    private String location;

    private String tripId;
    private String placeId;
    private String startLoc;
    private String endLoc;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public AddHotelFragment() {

    }

    public AddHotelFragment(String tripId) {
        this.tripId = tripId;
    }


    static AddHotelFragment newInstance() {
        return new AddHotelFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_hotel, container, false);

        saveButton = view.findViewById(R.id.saveButton);
        hotelAddress = view.findViewById(R.id.hotel_address);
        checkInText = view.findViewById(R.id.check_in_text);
        checkOutText = view.findViewById(R.id.check_out_text);
        checkInTime = view.findViewById(R.id.check_in_time);
        checkOutTime = view.findViewById(R.id.check_out_time);

        checkInText.setOnClickListener(new OnDateClick());
        checkOutText.setOnClickListener(new OnDateClick());
        checkInTime.setOnClickListener(new OnTimeClick());
        checkOutTime.setOnClickListener(new OnTimeClick());

        String apiKey = getString(R.string.google_places_api);

        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Initialize the AutocompleteSupportFragment for start location
        AutocompleteSupportFragment hotelLocation = (AutocompleteSupportFragment)
                getFragmentManager().findFragmentById(R.id.search_bar_hotel);

        hotelLocation.setTypeFilter(TypeFilter.ESTABLISHMENT);

        // Specify the types of place data to return.
        hotelLocation.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));

        // Set up a PlaceSelectionListener to handle the response.
        hotelLocation.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                placeId = place.getId();
                hotelAddress.setText(place.getAddress());
                location = place.getName();
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Utils.isDatePeriodValid(checkInText.getText().toString(), checkOutText.getText().toString())) {
                    Toast.makeText(getContext(), "Cannot check out  before check-in.", Toast.LENGTH_LONG).show();
                } else if (checkInText.getText().toString().compareTo(checkOutText.getText().toString()) == 0
                        && Utils.isTimePeriodValidGivenValidDates(checkInTime.getText().toString(), checkOutTime.getText().toString())){
                    Toast.makeText(getContext(), "Cannot check out before check-in.", Toast.LENGTH_LONG).show();
                } else {

                    Intent addHotelIntent = new Intent(getActivity(), DetailedTripActivity.class);
                    addHotelIntent.putExtra("trip id", tripId);
                    addHotelIntent.putExtra("place id", placeId);

                    DatabaseReference planRef = databaseReference.child("plans" + tripId).push();
                    String planKey = planRef.getKey();

                    Plan newHotel = new Plan(planKey,
                            "Stay at " + location,
                            Utils.stringToDate(checkInText.getText().toString()),
                            Utils.stringToDate(checkOutText.getText().toString()),
                            tripId,
                            Reservation.HOTEL, location,
                            Utils.stringToHours(checkInTime.getText().toString()),
                            Utils.stringToMins(checkInTime.getText().toString()),
                            Utils.stringToHours(checkOutTime.getText().toString()),
                            Utils.stringToMins(checkOutTime.getText().toString()),
                            placeId);

                    planRef.setValue(newHotel);

                    startActivity(addHotelIntent);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}