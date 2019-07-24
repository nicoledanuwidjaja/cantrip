package com.narwhalcompany.cantrip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddNewTripScreenActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private TextView tripFromDate;
    private TextView tripToDate;

    // TODO: FIX DATA TRANSFER
    private Button completeButton;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_add_new_trip);

        tripFromDate = findViewById(R.id.date_from);
        findViewById(R.id.date_from).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        tripToDate = findViewById(R.id.date_to);
        findViewById(R.id.date_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        completeButton = findViewById(R.id.add_trip_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTripIntent = new Intent(AddNewTripScreenActivity.this, MainActivity.class);
                startActivity(addTripIntent);
            }
        });

    }

    private void showDate() {
        DatePickerDialog datePicker = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR),
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
