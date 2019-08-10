package com.narwhalcompany.cantrip;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class HotelFragment extends AbstractPlanFragment implements OnMapReadyCallback {


    private TextView checkIn;
    private TextView checkOut;
    private TextView hotelName;
    private TextView fromTime;
    private TextView toTime;
    private TextView location;
    private PlacesClient placesClient;
    private ImageView hotelImage;

    public HotelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        try {
            String apiKey = getString(R.string.google_maps_api);

            SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            hotelImage = view.findViewById(R.id.hotelImage);
            checkIn = view.findViewById(R.id.check_in_date);
            checkOut = view.findViewById(R.id.check_out_date);
            location = view.findViewById(R.id.address);
            hotelName = view.findViewById(R.id.hotel_name);
//            fromTime = view.findViewById(R.id.from_time);
//            toTime = view.findViewById(R.id.to_time);

            String planName = getArguments().getString("hotelName");
            String planLocation = getArguments().getString("hotelLoc");
            String planFromDate = getArguments().getString("hotelStartDate");
            String planToDate = getArguments().getString("hotelEndDate");
            String planStartTime = getArguments().getString("hotelStartTime");
            String planEndTime = getArguments().getString("hotelEndTime");

            hotelName.setText(planName);
            location.setText(planLocation);
            checkIn.setText(planFromDate);
            checkOut.setText(planToDate);


            List<Place.Field> fields = Arrays.asList(Place.Field.PHOTO_METADATAS);
            FetchPlaceRequest placeRequest = FetchPlaceRequest.builder(apiKey, fields).build();

            // TODO: FIX THIS!

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
                            .setMaxWidth(500) // Optional.
                            .setMaxHeight(300) // Optional.
                            .build();
                    placesClient.fetchPhoto(photoRequest).addOnSuccessListener(new OnSuccessListener<FetchPhotoResponse>() {
                        @Override
                        public void onSuccess(FetchPhotoResponse fetchPhotoResponse) {
                            Bitmap bitmap = fetchPhotoResponse.getBitmap();
                            hotelImage.setImageBitmap(bitmap);
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
        } catch (InflateException e) {
            e.printStackTrace();
        }

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }
}
