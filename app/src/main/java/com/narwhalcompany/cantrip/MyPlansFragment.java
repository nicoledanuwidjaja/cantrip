package com.narwhalcompany.cantrip;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import utils.CustomPlanListAdapter;
import utils.Plan;
import utils.Reservation;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlansFragment extends Fragment {

    ListView listOfPlans;
    ArrayList<Plan> planList = new ArrayList<>();

    FloatingActionButton addNewPlanButton;

    public MyPlansFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_plans, container, false);
        listOfPlans = view.findViewById(R.id.myPlansList);
        addNewPlanButton = view.findViewById(R.id.add_plan_button);

        // populate array list with character data
        populateList();

        CustomPlanListAdapter adapter = new CustomPlanListAdapter(getContext(), planList);
        listOfPlans.setAdapter(adapter);

        listOfPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), PlanActivity.class);
                Plan currentPlan = planList.get(i);

                intent.putExtra("name", currentPlan.getName());
                intent.putExtra("time", currentPlan.getTime());
                intent.putExtra("description", currentPlan.getDescription());

                startActivity(intent);
            }
        });


        addNewPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpFragment(new AddPlanOptionFragment());
            }
        });


        // TODO: FIX THIS ADAPTER
        // custom plan list adapter
//        CustomPlanListAdapter planAdapter = new CustomPlanListAdapter(getActivity(), planList);
//
//        listOfPlans.setAdapter(planAdapter);
//        listOfPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // trigger second activity - plan information
//                Intent infoIntent = new Intent(getActivity(), PlanActivity.class);
//                infoIntent.putExtra("PLAN", currentPlan);
//                startActivity(infoIntent);
//            }
//        });


        return view;
    }

    private void populateList() {
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.PLANE));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.LANDMARK));
        planList.add(new Plan("Flight to New Delhi", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.HOTEL));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.PLANE));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.LANDMARK));
        planList.add(new Plan("Flight to New Delhi", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.HOTEL));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.PLANE));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.LANDMARK));
        planList.add(new Plan("Flight to New Delhi", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.HOTEL));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.PLANE));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.LANDMARK));
        planList.add(new Plan("Flight to New Delhi", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.HOTEL));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.PLANE));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.LANDMARK));
        planList.add(new Plan("Flight to New Delhi", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.HOTEL));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.PLANE));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.LANDMARK));
        planList.add(new Plan("Flight to New Delhi", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.HOTEL));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.PLANE));
        planList.add(new Plan("Flight to NYC", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.LANDMARK));
        planList.add(new Plan("Flight to New Delhi", "8:00",
                "Tevere River. The capital of the Lazio region is Italy's largest city " +
                        "with a population of 2,654,100 and over 2600 years of richness in art, " +
                        "history, architecture, monuments and culture.", Reservation.HOTEL));
    }

    public void popUpFragment(Fragment fragment){

        FragmentTransaction popUpAddPlan = getFragmentManager().beginTransaction();

        popUpAddPlan.show(fragment);
        popUpAddPlan.commit();
    }
}
