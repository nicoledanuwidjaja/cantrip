package com.narwhalcompany.cantrip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.narwhalcompany.cantrip.model.main.ItineraryItem;
import com.narwhalcompany.cantrip.model.main.TripObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddNewTripDateFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextView tripFromDate;
    private TextView tripToDate;

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;


    private FirebaseDatabase db;
    private DatabaseReference databaseReference;

    // TODO: FIX DATA TRANSFER
    private Button completeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_trip_date_fragment, container, false);
        tripFromDate = view.findViewById(R.id.date_from);
        tripFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        tripToDate = view.findViewById(R.id.date_to);
        tripToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        startDatePicker = view.findViewById(R.id.start_date_picker);
        endDatePicker = view.findViewById(R.id.end_date_picker);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        completeButton = view.findViewById(R.id.add_date_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                Bundle pastLocationArgs = getArguments();

                String startMonth;
                String endMonth;

                if (startDatePicker.getMonth() < 9) {
                    int month = startDatePicker.getMonth() + 1;
                    startMonth = "0" + month;
                } else if (startDatePicker.getMonth() == 9) {
                    startMonth = "10";
                } else {
                    startMonth = "" + startDatePicker.getMonth() + 1 + "";
                }

                if (endDatePicker.getMonth() < 9) {
                    int month = (endDatePicker.getMonth()+1);
                    endMonth = "0" + "" + month + "";
                } else if (endDatePicker.getMonth() == 9) {
                    endMonth = "10";
                } else {
                    endMonth = "" + endDatePicker.getMonth() + 1 + "";
                }

                String startDate = "" + startMonth+"-" +
                        (startDatePicker.getDayOfMonth()) + "-" + startDatePicker.getYear() + "";
                String endDate = "" + endMonth+"-" +
                        (endDatePicker.getDayOfMonth()) + "-" + endDatePicker.getYear() + "";
                // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat neededDateFormat = new SimpleDateFormat("MM-dd-yyyy");

                Date startDateParsed = null;
                Date endDateParsed = null;

                try {
//                    DatabaseReference postRef = databaseReference.child("trips");
//                    DatabaseReference pushedPostRef = postRef.push();
//                    String postedRefKey =  databaseReference.child("trips").push().getKey();
//                    Log.d("DATABASE REFERENCE", postedRefKey);

                    startDateParsed = neededDateFormat.parse(startDate);
                    endDateParsed = neededDateFormat.parse(endDate);

                    System.out.println(startDateParsed);
                    System.out.println(endDateParsed);

//                    Map<String, Object> newTripMap = new HashMap<>();
//                    newTripMap.put(postedRefKey, newTrip);

//                    pushedPostRef.setValue(newTrip);



//                    DatabaseReference pushedRef = databaseReference.child("trips/").push();
//                    pushedRef.setValue(newTrip);
//
//                    String pushedKey = pushedRef.getKey();
//
//                    System.out.println(pushedKey);
//                    System.out.println(newTrip);
//
//                    Map<String, Object> newTripMap = new HashMap<>();
//                    newTripMap.put(pushedRef.getKey(), newTrip);
//
//                    databaseReference.child("trips").updateChildren(newTripMap);

                   // databaseReference.child(pushedKey).setValue(newTrip);
                    //pushedRef.setValue(newTrip);
//                    databaseReference.child("trips").child(postedRefKey).updateChildren(newTripMap);
                } catch (ParseException e) {
                    Log.d("PARSE EXCEPTION", e.getLocalizedMessage());
                }


                final TripObject newTrip = new TripObject();
                newTrip.setStartLoc(pastLocationArgs.getString("startLocation"));
                newTrip.setEndLoc(pastLocationArgs.getString("endLocation"));
                newTrip.setStartDate(startDateParsed);
                newTrip.setEndDate(endDateParsed);
                newTrip.setPlans(new ArrayList<ItineraryItem>());


                databaseReference.child("trips").push().setValue(newTrip);



//                bundle.putString("startLocation", pastLocationArgs.getString("startLocation"));
//                bundle.putString("endLocation", pastLocationArgs.getString("endLocation"));
//                bundle.putInt("startMonth", startDatePicker.getMonth());
//                bundle.putInt("startDay", startDatePicker.getDayOfMonth());
//                bundle.putInt("startYear", startDatePicker.getYear());
//                bundle.putInt("endMonth", endDatePicker.getMonth());
//                bundle.putInt("endDay", endDatePicker.getDayOfMonth());
//                bundle.putInt("endYear", endDatePicker.getYear());
                MyTripListFragment newTripListFragment = new MyTripListFragment();
                // newTripListFragment.setArguments(bundle);
                transaction.replace(R.id.fragment_container, newTripListFragment);
                transaction.commit();
            }
        });
        return view;
    }

    private void showDate() {
        DatePickerDialog datePicker = new DatePickerDialog(getContext(), this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = month + "/" + day + "/" + year;
        tripFromDate.setText(date);
        tripToDate.setText(date);
    }
}
