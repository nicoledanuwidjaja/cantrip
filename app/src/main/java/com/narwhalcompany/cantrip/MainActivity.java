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

        // Nicole: onClick Listeners for switching fragment views

//        myPlans.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
                // attach my fragment to my container that's provided in my layout
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // add a function to your transaction
                // @param - container id = FrameLayout from MAIN XML
                // @param - which fragment should I add
                transaction.add(R.id.fragmentContainer, new MyTripsFragment());

                // Commit the changes
                transaction.commit();
//            }
//        });

        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // attach my fragment to my container that's provided in my layout
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // add a function to your transaction
                // @param - container id = FrameLayout from MAIN XML
                // @param - which fragment should I add
                transaction.replace(R.id.fragmentContainer, new EmptyRecommendedFragment());

                // Commit the changes
                transaction.commit();
            }
        });

        myPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // attach my fragment to my container that's provided in my layout
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentContainer, new MyTripsFragment());

                // Commit the changes
                transaction.commit();
            }
        });
    }
}

