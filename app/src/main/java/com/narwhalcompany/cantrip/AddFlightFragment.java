package com.narwhalcompany.cantrip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import utils.OnDateClick;
import utils.OnTimeClick;
import utils.Plan;
import utils.Reservation;
import utils.Utils;


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

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

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

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFlightIntent = new Intent(getActivity(), DetailedTripActivity.class);

                String tripId = getArguments().getString("trip id");
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


