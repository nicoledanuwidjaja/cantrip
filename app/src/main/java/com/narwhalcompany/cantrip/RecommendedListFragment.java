package com.narwhalcompany.cantrip;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
    TextView textView;

    public RecommendedListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommended_list, container, false);

        listView = view.findViewById(R.id.recListView);
        textView = view.findViewById(R.id.text);

        PopulateList();
        CustomRecListAdapter myAdapter = new CustomRecListAdapter(getContext(), recArray);
        listView.setAdapter(myAdapter);

// ...

// Instantiate the RequestQueue.
        String apiKey = getString(R.string.google_places_api);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://maps.googleapis.com/maps/api/place/textsearch/xml?query=restaurants+in+Sydney&key=" + apiKey;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


        return view;

    }

    private void PopulateList() {
        recArray.add(new RecAdapterItem(R.drawable.statue_of_liberty, "Statue of Liberty"));
        recArray.add(new RecAdapterItem(R.drawable.central_park, "Central Park"));
        recArray.add(new RecAdapterItem(R.drawable.empire_state_building, "Empire State Building"));

    }
}
