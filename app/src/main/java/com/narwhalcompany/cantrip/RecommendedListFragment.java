package com.narwhalcompany.cantrip;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.CustomRecListAdapter;
import utils.RecAdapterItem;

public class RecommendedListFragment extends Fragment {

    private ListView listView;
    private ArrayList<RecAdapterItem> recArray = new ArrayList<>();
    private TextView textView;
    private String tripDestination;
    private String tripId;

    public RecommendedListFragment() {
        // Required empty public constructor
    }

    public RecommendedListFragment(Bundle bundle) {
        if (bundle != null) {
            this.tripId = bundle.getString("trip id");
            this.tripDestination = bundle.getString("tripDestination");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Instantiate the RequestQueue.
        String apiKey = getString(R.string.google_places_api);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=attractions+in+" + tripDestination + "&key=" + apiKey;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                if (jo.getJSONArray("photos") != null) {
                                    JSONObject photos = jo.getJSONArray("photos").getJSONObject(0);
                                    String photoURI = photos.getString("photo_reference");
                                    String openHours = "N/A";
                                    if (!jo.isNull("opening_hours")) {
                                        openHours = jo.getJSONObject("opening_hours").getString("open_now");
                                    }
                                    recArray.add(new RecAdapterItem(photoURI, jo.getString("name"), jo.getDouble("rating"), openHours, jo.getString("place_id")));
                                }
                            }
                            CustomRecListAdapter myAdapter = new CustomRecListAdapter(getContext(), recArray, tripId);
                            listView.setAdapter(myAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);

        View view = inflater.inflate(R.layout.fragment_recommended_list, container, false);

        listView = view.findViewById(R.id.recListView);
        textView = view.findViewById(R.id.text);


        return view;

    }

}
