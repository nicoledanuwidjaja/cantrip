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
    private TextView phone;
    private TextView name;
    private TextView startTime;
    private TextView endTime;
    private TextView startDate;
    private TextView endDate;
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
        startTime = view.findViewById(R.id.hours_open);
        startDate = view.findViewById(R.id.visit_start_date);
        endDate = view.findViewById(R.id.visit_end_date);

        String planName = getArguments().getString("landmarkName");
        String planStartDate = getArguments().getString("landmarkStartDate");
        String planEndDate = getArguments().getString("landmarkEndDate");

        String placeId = getArguments().getString("landmarkPlace");

        name.setText(planName);
        startDate.setText(planStartDate);
        endDate.setText(planEndDate);

        // PLACES API
        List<Place.Field> fields = Arrays.asList(Place.Field.PHOTO_METADATAS);
        FetchPlaceRequest placeRequest = FetchPlaceRequest.builder(placeId, fields).build();

        // Initialize Places.
        Places.initialize(getActivity().getApplicationContext(), apiKey);

        // Create a new Places client instance.
        placesClient = Places.createClient(getContext());

        placesClient.fetchPlace(placeRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                Place place = fetchPlaceResponse.getPlace();

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

        // Place request for addresses
        List<Place.Field> fields2 = Arrays.asList(Place.Field.ADDRESS);
        FetchPlaceRequest placeRequest2 = FetchPlaceRequest.builder(placeId, fields2).build();

        // Initialize Places.
        Places.initialize(getActivity().getApplicationContext(), apiKey);

        placesClient = Places.createClient(getContext());

        placesClient.fetchPlace(placeRequest2).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                Place planLocation = fetchPlaceResponse.getPlace();
                location.setText(planLocation.getAddress());
            }
        });

        return view;
    }


}

