package com.narwhalcompany.cantrip;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import utils.CustomTripOverviewAdapter;

public class MainActivity extends AppCompatActivity {

    ArrayList<TripOverviewAdapterItem> tripList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        populateList();

        ListView listView = findViewById(R.id.myTripsList);
        CustomTripOverviewAdapter adapter = new CustomTripOverviewAdapter(this, tripList);

        // code for accessing view for each trip
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // trigger second activity - DetailedTripActivity
                Intent tripIntent = new Intent(MainActivity.this, DetailedTripActivity.class);
                tripIntent.putExtra("TRIP", tripList.get(i));
                startActivity(tripIntent);
            }
        });
        // hi this is a change

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTripIntent = new Intent(MainActivity.this, AddNewTripScreenActivity.class);
                startActivity(addTripIntent);
            }
        });
    }

    public void populateList() {
        tripList.add(new TripOverviewAdapterItem(R.drawable.sedan, 7, 4, 2019,
                7, 8, 2019, "Boston", "NYC"));
        tripList.add(new TripOverviewAdapterItem(R.drawable.commons, 7, 9, 2019,
                7, 30, 2019, "Sacramento", "Boston"));
        tripList.add(new TripOverviewAdapterItem(R.drawable.northeastern, 9, 4, 2019,
                4, 15, 2020, "Las Vegas", "Boston"));
    }
}