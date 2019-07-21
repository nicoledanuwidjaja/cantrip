package com.narwhalcompany.cantrip;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView myPlans;
    TextView rec;

    Button addNewTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPlans = findViewById(R.id.MyPlans);
        rec = findViewById(R.id.Recommended);

        addNewTrip = findViewById(R.id.add_trip_button);

        addNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewTripScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}

