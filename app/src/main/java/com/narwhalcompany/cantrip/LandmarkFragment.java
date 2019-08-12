package com.narwhalcompany.cantrip;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class LandmarkFragment extends AbstractPlanFragment {
    private TextView location;
    private TextView name;
    private TextView startDate;
    private TextView endDate;
    private TextView hours;
    private PlacesClient placesClient;
    private ImageView attractionImage;

    public LandmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_landmark, container, false);

        String apiKey = getString(R.string.google_places_api);

        attractionImage = view.findViewById(R.id.landmark_image);
        location = view.findViewById(R.id.address_label);
        name = view.findViewById(R.id.landmark_name);
        startDate = view.findViewById(R.id.visit_start_date);
        endDate = view.findViewById(R.id.visit_end_date);
        hours = view.findViewById(R.id.hours_open);

        String planName = getArguments().getString("landmarkName");
        String planStartDate = getArguments().getString("landmarkStartDate");
        String planEndDate = getArguments().getString("landmarkEndDate");
        String planStartTime = getArguments().getString("landmarkStartTime");
        String planEndTime = getArguments().getString("landmarkEndTime");
        String placeId = getArguments().getString("landmarkPlace");

        name.setText("Visit to " + planName);
        startDate.setText(planStartDate + ", " + planStartTime);
        endDate.setText(planEndDate + ", " + planEndTime);

        // PLACES API
        List<Place.Field> fields = Arrays.asList(Place.Field.PHOTO_METADATAS, Place.Field.ADDRESS, Place.Field.OPENING_HOURS);
        FetchPlaceRequest placeRequest = FetchPlaceRequest.builder(placeId, fields).build();

        // Initialize Places.
        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Create a new Places client instance.
        placesClient = Places.createClient(getContext());

        placesClient.fetchPlace(placeRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                Place place = fetchPlaceResponse.getPlace();
                location.setText(place.getAddress());
                OpeningHours locationHours = place.getOpeningHours();

                StringBuilder hoursString = new StringBuilder();
                for (String s : locationHours.getWeekdayText()) {
                    hoursString.append(s + "\n");
                }
                hours.setText(hoursString);

                // Get the photo metadata.
                PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(0);

                // Get the attribution text.
                String attributions = photoMetadata.getAttributions();

                // Create a FetchPhotoRequest.
                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener(new OnSuccessListener<FetchPhotoResponse>() {
                    @Override
                    public void onSuccess(FetchPhotoResponse fetchPhotoResponse) {
                        Bitmap bitmap = fetchPhotoResponse.getBitmap();
                        attractionImage.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            int statusCode = apiException.getStatusCode();
                        }
                    }
                });
            }
        });

        return view;
    }


}

