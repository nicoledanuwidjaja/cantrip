
package com.narwhalcompany.cantrip;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment smf;
    private GoogleMap googleMap;
    private MapView map;
    private View v;

    public MapsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map, container, false);
        smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (smf == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            smf = new SupportMapFragment();
            ft.replace(R.id.map, smf).commit();
        }
        smf.getMapAsync(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        map = v.findViewById(R.id.map);
        if (map != null) {
            map.onCreate(null);
            map.onResume();
            map.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap gm) {
        MapsInitializer.initialize(getContext());

        googleMap = gm;
        gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        gm.addMarker(new MarkerOptions().position(new LatLng(12.1232323, 123232323)).title("Unknown").snippet("Random"));

        CameraPosition position = CameraPosition.builder().target(new LatLng(12.1232323, 123232323)).zoom(16).bearing(0).tilt(45).build();

        gm.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }

}