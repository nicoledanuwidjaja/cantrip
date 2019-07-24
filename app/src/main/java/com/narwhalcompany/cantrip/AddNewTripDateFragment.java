package com.narwhalcompany.cantrip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.Calendar;

public class AddNewTripDateFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextView tripFromDate;
    private TextView tripToDate;

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

        completeButton = view.findViewById(R.id.add_location_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTripIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(addTripIntent);
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
