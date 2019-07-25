package com.narwhalcompany.cantrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class FakeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake);
//        AddFlightFragment addFlightFragment = new AddFlightFragment();
//
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.fragment_container, addFlightFragment);
//        fragmentTransaction.commit();


    }


    public void onBackPressed(View view) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new AddFlightFragment());
        fragmentTransaction.commit();
    }
}