package com.narwhalcompany.cantrip;


import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import utils.CustomPlanListAdapter;
import utils.Plan;
import utils.Reservation;

public class MyPlansFragment extends BottomSheetDialogFragment {

    private ListView listOfPlans;
    private ArrayList<Plan> planList = new ArrayList<>();
    private FloatingActionButton addNewPlanButton;
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();

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

        if (getArguments() != null) {
            Bundle planBundle = getArguments();
            planList.add(new Plan(planBundle.getString("name"),
                    planBundle.getInt("startMonth"), planBundle.getInt("startDay"), planBundle.getInt("startYear"),
                    planBundle.getInt("endMonth"), planBundle.getInt("endDay"), planBundle.getInt("endYear"),
                    planBundle.getInt("startTime"), planBundle.getInt("endtime"), planBundle.getString("location"),
                    convertToType(planBundle.getString("type"))));
            ;
        }

        CustomPlanListAdapter adapter = new CustomPlanListAdapter(getContext(), planList);
        listOfPlans.setAdapter(adapter);

        listOfPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plan currentPlan = planList.get(i);

                Bundle planBundle = new Bundle();
                planBundle.putString("type", currentPlan.getPlanType().toString());
                planBundle.putString("name", currentPlan.getName());
                planBundle.putInt("startMonth", currentPlan.getStartMonth());
                planBundle.putInt("startDay", currentPlan.getStartDay());
                planBundle.putInt("startYear", currentPlan.getStartYear());
                planBundle.putInt("endMonth", currentPlan.getEndMonth());
                planBundle.putInt("endDay", currentPlan.getEndDay());
                planBundle.putInt("endYear", currentPlan.getEndYear());
                planBundle.putInt("startTime", currentPlan.getStartTime());
                planBundle.putInt("endTime", currentPlan.getEndTime());
                planBundle.putString("location", currentPlan.getLocation());

                AbstractPlanFragment fragPlan = new AbstractPlanFragment();
                fragPlan.setArguments(planBundle);

                getFragmentManager().beginTransaction().replace(R.id.detailed_plan_container, fragPlan).commit();

                System.out.println("I'M PRINTING OUT A BUNDLE " + planBundle);

            }
        });


        addNewPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sets a fragment manager for managing all fragments (for adding new trips)
                FragmentManager planManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = planManager.beginTransaction();
                BottomSheetDialogFragment optionFragment = new AddPlanOptionFragment();
                optionFragment.show(planManager, "add plan");
                transaction.addToBackStack(null);
                transaction.commit();
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
//                Intent infoIntent = new Intent(getActivity(), AbstractPlanFragment.class);
//                infoIntent.putExtra("PLAN", currentPlan);
//                startActivity(infoIntent);
//            }
//        });


        return view;
    }

    private void populateList() {

    }

    private Reservation convertToType(String typeString) {
        switch (typeString) {
            case "flight":
                return Reservation.FLIGHT;
            case "hotel":
                return Reservation.HOTEL;
            case "landmark":
                return Reservation.LANDMARK;
            default:
                break;
        }
        return null;
    }
}
