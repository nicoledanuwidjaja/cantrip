package com.narwhalcompany.cantrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import utils.CustomPlanAdapter;
import utils.Plan;

public class DetailedTripActivity extends AppCompatActivity {

    ListView listOfPlans;
    ArrayList<Plan> planList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_trip);

        listOfPlans = findViewById(R.id.plan_item);

        // populate array list with character data
        populateList();

        // custom plan list adapter
        CustomPlanAdapter planAdapter = new CustomPlanAdapter(this, planList);

        listOfPlans.setAdapter(planAdapter);
        listOfPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // trigger second activity - character information
                Intent infoIntent = new Intent(DetailedTripActivity.this, PlanActivity.class);
                infoIntent.putExtra("plan_name", planList.get(i).getName());
                startActivity(infoIntent);
            }
        });
    }

    private void populateList() {
        planList.add(new Plan("Plan 1", "8:00", "Plan 1 Description", R.drawable.ic_launcher_background));
    }
}
