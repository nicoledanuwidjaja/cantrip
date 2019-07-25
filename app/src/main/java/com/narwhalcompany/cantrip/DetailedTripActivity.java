package com.narwhalcompany.cantrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.narwhalcompany.cantrip.ui.main.SectionsPagerAdapter;

public class DetailedTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_trip);

        // view and tab layout
        ViewPager viewPager = findViewById(R.id.view_pager);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

//        FloatingActionButton fab = findViewById(R.id.add_plan_button);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                popUpFragment(new AddPlanOptionFragment());
//            }
//        });

    }

    public void popUpFragment(Fragment fragment){

        FragmentTransaction popUpAddPlan = getSupportFragmentManager().beginTransaction();

        popUpAddPlan.show(fragment);
        popUpAddPlan.commit();
    }
}
