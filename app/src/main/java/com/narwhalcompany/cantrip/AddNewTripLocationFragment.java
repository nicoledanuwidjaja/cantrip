package com.narwhalcompany.cantrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AddNewTripLocationFragment extends Fragment {


    private TextView tripFromDate;


    private TextView tripToDate;

    @NonNull
    private EditText startLocation;

    @NonNull
    private EditText endLocation;

    private Button completeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_trip_location, container, false);

        tripFromDate = view.findViewById(R.id.where_from);
        tripToDate = view.findViewById(R.id.where_to);

        startLocation = view.findViewById(R.id.start_location);
        endLocation = view.findViewById(R.id.end_location);

        completeButton = view.findViewById(R.id.add_location_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sets a fragment manager for managing all fragments (for adding new trips)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                AddNewTripDateFragment getDateFragment = new AddNewTripDateFragment();

                Bundle bundle = new Bundle();
                bundle.putString("startLocation", startLocation.getText().toString());
                bundle.putString("endLocation", endLocation.getText().toString());
                getDateFragment.setArguments(bundle);

                transaction.replace(R.id.fragment_container, getDateFragment);
                transaction.commit();
            }
        });
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_empty_recommended, container, false);
        return view;
    }
}

