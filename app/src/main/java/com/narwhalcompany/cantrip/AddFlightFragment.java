package com.narwhalcompany.cantrip;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class AddFlightFragment extends DialogFragment {

    @NonNull
    private TextView departLocation;

    @NonNull
    private TextView arriveLocation;
    private Button saveButton;

    PlacesClient placesClient;

    static AddFlightFragment newInstance() {
        return new AddFlightFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_flight, container, false);

        String apiKey = getString(R.string.google_places_api);

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_empty_recommended, container, false);

        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Initialize the AutocompleteSupportFragment for start location
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.search_bar_flight);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                departLocation.setText(place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        departLocation = view.findViewById(R.id.departLocation);
        arriveLocation = view.findViewById(R.id.arriveLocation);

        // Initialize the AutocompleteSupportFragment for destination
        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.search_bar_flight_2);

        // Specify the types of place data to return.
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                arriveLocation.setText(place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addFlightIntent = new Intent(getActivity(), DetailedTripActivity.class);
                startActivity(addFlightIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
