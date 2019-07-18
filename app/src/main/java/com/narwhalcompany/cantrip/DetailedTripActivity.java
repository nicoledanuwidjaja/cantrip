package com.narwhalcompany.cantrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import utils.CustomPlanAdapter;
import utils.Plan;

public class DetailedTripActivity extends AppCompatActivity {

    ImageView tripImage;
    TextView tripText;

    ListView listOfPlans;
    ArrayList<Plan> planList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_trip);

        listOfPlans = findViewById(R.id.plan_item);

        // plan variable items
        tripImage = findViewById(R.id.tripOverviewAdapterItemImage);
        tripText = findViewById(R.id.trip_title);

        Intent detailedTripIntent = getIntent();
        TripOverviewAdapterItem selectedTrip = detailedTripIntent.getParcelableExtra("TRIP");

        // grab variables from intent
        int imageResID = selectedTrip.getImageResId();
        int startMonth = selectedTrip.getStartMonth();
        int startDay = selectedTrip.getStartDay();
        int endMonth = selectedTrip.getEndMonth();
        int endDay = selectedTrip.getEndDay();
        int year = selectedTrip.getYear();
        String startLoc = selectedTrip.getStartLoc();
        String endLoc = selectedTrip.getEndloc();

        // set views to resource values
//        tripImage.setImageResource(imageResID);
        tripText.setText(startLoc + " to " + endLoc + "\n"
                + startMonth + "/" + startDay + "-" + endMonth + "/" + endDay + " " + year);

        // populate array list with character data
        populateList();

        // custom plan list adapter
        CustomPlanAdapter planAdapter = new CustomPlanAdapter(this, planList);

        listOfPlans.setAdapter(planAdapter);
        listOfPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // trigger second activity - plan information
                Intent infoIntent = new Intent(DetailedTripActivity.this, PlanActivity.class);
                infoIntent.putExtra("PLAN", planList.get(i));
                startActivity(infoIntent);
            }
        });
    }

    private void populateList() {
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.",
                R.drawable.plane_horiz));
    }
}
