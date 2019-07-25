package com.narwhalcompany.cantrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import utils.Plan;

public class PlanActivity extends AppCompatActivity {

    TextView name;
    TextView time;
    TextView description;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        name = findViewById(R.id.plan_name);
        time = findViewById(R.id.plan_time);
        description = findViewById(R.id.plan_description);
        image = findViewById(R.id.plan_image);

        // Collect the values for a Plan from DetailedTripActivity to MainActivity
        Intent tripIntent = getIntent();

        //extract resource IDs - image, text, and audio files
        // Using getParcelableExtra(String key) method
        Plan selectedPlan = tripIntent.getParcelableExtra("PLAN");

        String planName = tripIntent.getStringExtra("name");
        String planTime = tripIntent.getStringExtra("time");
        String planDescription = tripIntent.getStringExtra("description");
        int planImageID = tripIntent.getIntExtra("imageId", 0);

        name.setText(planName);
        time.setText(planTime);
        description.setText(planDescription);
        image.setImageResource(planImageID);
    }


}
