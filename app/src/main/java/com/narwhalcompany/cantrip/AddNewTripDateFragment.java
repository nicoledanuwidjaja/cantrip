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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.narwhalcompany.cantrip.model.main.ItineraryItem;
import com.narwhalcompany.cantrip.model.main.TripObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import utils.Plan;
import utils.Utils;

public class AddNewTripDateFragment extends DialogFragment {

    private TextView tripFromDate;
    private TextView tripToDate;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;

    private Button completeButton;

    static AddNewTripDateFragment newInstance() {
        return new AddNewTripDateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_trip_date, container, false);
        tripFromDate = view.findViewById(R.id.date_from);
        tripToDate = view.findViewById(R.id.date_to);

        startDatePicker = view.findViewById(R.id.start_date_picker);
        endDatePicker = view.findViewById(R.id.end_date_picker);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        completeButton = view.findViewById(R.id.add_date_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                Bundle pastLocationArgs = getArguments();

               if (!Utils.checkEndDateValid(startDatePicker, endDatePicker)) {
                   Toast.makeText(getContext(), "End date cannot be before start date", Toast.LENGTH_SHORT).show();
               } else {
                   Date startDateParsed = Utils.getStaticDate(startDatePicker);
                   Date endDateParsed = Utils.getStaticDate(endDatePicker);

                   final TripObject newTrip = new TripObject();
                   newTrip.setStartLoc(pastLocationArgs.getString("startLocation"));
                   newTrip.setEndLoc(pastLocationArgs.getString("endLocation"));
                   newTrip.setStartDate(startDateParsed);
                   newTrip.setEndDate(endDateParsed);
                   newTrip.setPlans(new ArrayList<Plan>());

                   DatabaseReference pushedReference = databaseReference.child("trips").push();

                   final String tripKey = pushedReference.getKey();
                   newTrip.setId(tripKey);

                   pushedReference.setValue(newTrip);

                   Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                   startActivity(mainIntent);

                   MyTripListFragment newTripListFragment = new MyTripListFragment();
                   transaction.replace(R.id.fragment_container, newTripListFragment);
                   transaction.commit();
               }
            }
        });
        return view;
    }
}
