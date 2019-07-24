package com.narwhalcompany.cantrip;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import utils.CustomPlanAdapter;
import utils.CustomTripOverviewAdapter;
import utils.Plan;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlansFragment extends Fragment {

    ArrayList<TripOverviewAdapterItem> tripList = new ArrayList<>();
    ListView listOfPlans;
    ArrayList<Plan> planList = new ArrayList<>();

    ImageView tripImage;
    TextView tripText;

    public MyPlansFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_plans, container, false);

        // plan variable items
        listOfPlans = view.findViewById(R.id.plan_item);
        tripImage = view.findViewById(R.id.tripOverviewAdapterItemImage);
        tripText = view.findViewById(R.id.trip_title);

        Intent detailedTripIntent = getActivity().getIntent();
        TripOverviewAdapterItem selectedTrip = detailedTripIntent.getParcelableExtra("TRIP");

        // grab variables from intent
//        int imageResID = selectedTrip.getImageResId();
        int startMonth = selectedTrip.getStartMonth();
        int startDay = selectedTrip.getStartDay();
        int startYear = selectedTrip.getStartYear();
        int endMonth = selectedTrip.getEndMonth();
        int endDay = selectedTrip.getEndDay();
        int endYear = selectedTrip.getEndYear();
        String startLoc = selectedTrip.getStartLoc();
        String endLoc = selectedTrip.getEndloc();

        // set views to resource values
//        tripImage.setImageResource(imageResID);
        tripText.setText(startLoc + " to " + endLoc + "\n"
                + startMonth + "/" + startDay + "/" + startYear +
                " to " + endMonth + "/" + endDay + "/" + endYear);

        // populate array list with character data
        populateList();

        // TODO: FIX THIS ADAPTER
        // custom plan list adapter
//        CustomPlanAdapter planAdapter = new CustomPlanAdapter(getActivity(), planList);
//
//        listOfPlans.setAdapter(planAdapter);
//        listOfPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // trigger second activity - plan information
//                Intent infoIntent = new Intent(getActivity(), PlanActivity.class);
//                infoIntent.putExtra("PLAN", planList.get(i));
//                startActivity(infoIntent);
//            }
//        });

        return view;
    }

    private void populateList() {
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.",
                R.drawable.plane_horiz));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.",
                R.drawable.plane_horiz));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.",
                R.drawable.plane_horiz));
    }
}
