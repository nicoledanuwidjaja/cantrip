package com.narwhalcompany.cantrip;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlusCode;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class HotelFragment extends AbstractPlanFragment implements OnMapReadyCallback {
    private TextView checkIn;
    private TextView checkOut;
    private TextView hotelName;
    private TextView location;
    private PlacesClient placesClient;
    private SupportMapFragment mapFragment;
    private String apiKey;
    private LatLng mapLocation;

    public HotelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        apiKey = getString(R.string.google_places_api);

        mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        checkIn = view.findViewById(R.id.check_in_date);
        checkOut = view.findViewById(R.id.check_out_date);
        location = view.findViewById(R.id.address);
        hotelName = view.findViewById(R.id.hotel_name);

        String planName = getArguments().getString("hotelName");
        String planFromDate = getArguments().getString("hotelStartDate");
        String planToDate = getArguments().getString("hotelEndDate");
        String placeId = getArguments().getString("hotelPlace");

        hotelName.setText("Stay at " + planName);
        checkIn.setText(planFromDate);
        checkOut.setText(planToDate);

        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        FetchPlaceRequest placeRequest = FetchPlaceRequest.builder(placeId, fields).build();

        // Initialize Places.
        Places.initialize(getActivity().getApplicationContext(), apiKey);

        placesClient = Places.createClient(getContext());

        placesClient.fetchPlace(placeRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                Place place = fetchPlaceResponse.getPlace();
                location.setText(place.getAddress());
                System.out.println("HELP: " + location.getText().toString());
                System.out.println("HELP: " + place.getLatLng().latitude);
                mapLocation = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    int statusCode = apiException.getStatusCode();
                    System.out.println("WE'RE FAILING");
                }
            }
        });


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(mapLocation)
                .snippet("Hotel Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapLocation, 50));
    }
}
