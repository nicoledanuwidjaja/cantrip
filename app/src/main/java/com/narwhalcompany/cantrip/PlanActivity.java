package com.narwhalcompany.cantrip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import utils.Plan;

public class PlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        //extract resource IDs - image, text, and audio files
        // Using getParcelableExtra(String key) method
        Plan plan = getIntent().getParcelableExtra("plan_details");
    }
}
