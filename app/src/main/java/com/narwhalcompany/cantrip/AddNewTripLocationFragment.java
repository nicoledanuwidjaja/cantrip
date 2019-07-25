package com.narwhalcompany.cantrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AddNewTripLocationFragment extends Fragment {

    private TextView tripFromDate;
    private TextView tripToDate;

    // TODO: FIX DATA TRANSFER
    private Button completeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_trip_location, container, false);

        tripFromDate = view.findViewById(R.id.where_from);
        tripToDate = view.findViewById(R.id.where_to);

        completeButton = view.findViewById(R.id.add_location_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sets a fragment manager for managing all fragments (for adding new trips)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new AddNewTripDateFragment());
                transaction.commit();
            }
        });
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_empty_recommended, container, false);
        return view;
    }
}

