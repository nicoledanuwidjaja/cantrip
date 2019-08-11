package com.narwhalcompany.cantrip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddNewTripLocationFragment extends DialogFragment {

    @NonNull
    private TextView startLocation;

    @NonNull
    private TextView endLocation;

    private Button completeButton;

    static AddNewTripLocationFragment newInstance() {
        return new AddNewTripLocationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_trip_location, container, false);

        String apiKey = getString(R.string.google_places_api);

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_empty_recommended, container, false);

        Places.initialize(getActivity().getApplicationContext(), apiKey);
        // Initialize the AutocompleteSupportFragment for start location
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                startLocation.setText(place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        startLocation = view.findViewById(R.id.start_location);
        endLocation = view.findViewById(R.id.end_location);

        // Initialize the AutocompleteSupportFragment for destination
        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment_2);

        autocompleteFragment2.setTypeFilter(TypeFilter.CITIES);

        // Specify the types of place data to return.
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                endLocation.setText(place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        completeButton = view.findViewById(R.id.add_location_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sets a fragment manager for managing all fragments (for adding new trips)
                FragmentManager tripManager = getActivity().getSupportFragmentManager();
                FragmentTransaction tripTransaction = tripManager.beginTransaction();
                AddNewTripDateFragment getDateFragment = new AddNewTripDateFragment();

                Bundle bundle = new Bundle();
                bundle.putString("startLocation", startLocation.getText().toString());
                bundle.putString("endLocation", endLocation.getText().toString());
                getDateFragment.setArguments(bundle);
                getDateFragment.show(tripManager, "add trip");
                tripTransaction.addToBackStack(null);
                tripTransaction.commit();
            }
        });

        return view;
    }
}

