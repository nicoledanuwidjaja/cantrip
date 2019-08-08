package com.narwhalcompany.cantrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.narwhalcompany.cantrip.ui.main.SectionsPagerAdapter;

public class DetailedTripActivity extends AppCompatActivity {

    private TextView tripTitle;
    private TextView tripDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_trip);

        // view and tab layout
        ViewPager viewPager = findViewById(R.id.view_pager);

        // unique trip id for each trip
        String tripId = getIntent().getStringExtra("trip id");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager(), tripId);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        tripTitle = findViewById(R.id.trip_title);
        tripDuration = findViewById(R.id.trip_duration);

        // populates data from List of Trips
        String tripName = getIntent().getStringExtra("tripName");
        String tripTime = getIntent().getStringExtra("tripDuration");

        tripTitle.setText("Trip from " + tripName);
        tripDuration.setText("Date: " +  tripTime);
    }

    @Override
    public void onBackPressed() {
        Intent tripListIntent = new Intent(this, MainActivity.class);
        startActivity(tripListIntent);
    }
}
