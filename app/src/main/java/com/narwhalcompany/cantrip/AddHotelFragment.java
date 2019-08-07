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

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import utils.Plan;
import utils.Reservation;
import utils.Utils;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddHotelFragment extends DialogFragment {

    private Button saveButton;
    private EditText checkInText;
    private EditText checkOutText;
    private String location;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

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
        checkInText = view.findViewById(R.id.check_in_text);
        checkOutText = view.findViewById(R.id.check_out_text);

        String apiKey = getString(R.string.google_places_api);

        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Initialize the AutocompleteSupportFragment for start location
        AutocompleteSupportFragment hotelLocation = (AutocompleteSupportFragment)
                getFragmentManager().findFragmentById(R.id.search_bar_hotel);

        // Specify the types of place data to return.
        hotelLocation.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        hotelLocation.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                location = place.getName();
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        final Calendar hotelStartDate = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // sets the calendar date of the hotel start date
                hotelStartDate.set(Calendar.YEAR, year);
                hotelStartDate.set(Calendar.MONTH, monthOfYear);
                hotelStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat date = new SimpleDateFormat(myFormat, Locale.US);

                checkInText.setText(date.format(hotelStartDate.getTime()));
            }

        };

        checkInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), datePicker, hotelStartDate
                        .get(Calendar.YEAR), hotelStartDate.get(Calendar.MONTH),
                        hotelStartDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Calendar hotelEndDate = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datePicker2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // sets the calendar date of the hotel end date
                hotelEndDate.set(Calendar.YEAR, year);
                hotelEndDate.set(Calendar.MONTH, monthOfYear);
                hotelEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat date = new SimpleDateFormat(myFormat, Locale.US);

                checkOutText.setText(date.format(hotelEndDate.getTime()));
            }

        };

        checkOutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), datePicker2, hotelEndDate
                        .get(Calendar.YEAR), hotelEndDate.get(Calendar.MONTH),
                        hotelEndDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addHotelIntent = new Intent(getActivity(), DetailedTripActivity.class);
                String tripId = getArguments().getString("trip id");
                DatabaseReference planRef = databaseReference.child("trips").child(tripId)
                        .child("plans").push();
                String planKey = planRef.getKey();

//                Plan newHotel = new Plan(planKey,
//                        "Hotel at " + location,
//                        Utils.stringToDate(departDate.getText().toString()),
//                        Utils.stringToDate(arriveDate.getText().toString()),
//                        tripId,
//                        Reservation.HOTEL, null,
//                        Utils.stringToHours(departTime.getText().toString()),
//                        Utils.stringToMins(departTime.getText().toString()),
//                        Utils.stringToHours(arriveTime.getText().toString()),
//                        Utils.stringToMins(arriveTime.getText().toString()),
//                        location);

//                planRef.setValue(newHotel);

                startActivity(addHotelIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
