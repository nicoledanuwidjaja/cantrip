package com.narwhalcompany.cantrip;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;

import java.util.ArrayList;

import utils.CustomRecListAdapter;
import utils.RecAdapterItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendedListFragment extends Fragment {

    ListView listView;
    ArrayList<RecAdapterItem> recArray = new ArrayList<>();

    public RecommendedListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommended_list, container, false);

        listView = view.findViewById(R.id.recListView);

        PopulateList();
        CustomRecListAdapter myAdapter = new CustomRecListAdapter(getContext(), recArray);
        listView.setAdapter(myAdapter);



        return view;

    }

    private void PopulateList() {
        recArray.add(new RecAdapterItem(R.drawable.statue_of_liberty, "Statue of Liberty"));
        recArray.add(new RecAdapterItem(R.drawable.central_park, "Central Park"));
        recArray.add(new RecAdapterItem(R.drawable.empire_state_building, "Empire State Building"));
    }
}
