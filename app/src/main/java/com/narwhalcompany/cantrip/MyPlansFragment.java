package com.narwhalcompany.cantrip;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import utils.CustomPlanListAdapter;
import utils.Plan;
import utils.Reservation;

public class MyPlansFragment extends BottomSheetDialogFragment {

    private ListView listOfPlans;
    private ArrayList<Plan> planList = new ArrayList<>();
    private FloatingActionButton addNewPlanButton;
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();

    private Bundle bundle;

    public MyPlansFragment() {
        // Required empty public constructor
    }

    public MyPlansFragment(Bundle bundle) {
        this.bundle = bundle;
    }

     private String tripId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (bundle != null) {
            tripId = bundle.getString("trip id");
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_plans, container, false);
        listOfPlans = view.findViewById(R.id.myPlansList);
        addNewPlanButton = view.findViewById(R.id.add_plan_button);

        // populate array list with character data
        populateList();

        // IF NONE ADDED, DON'T GET DATA REF
        if (tripId != null) {

            CustomPlanListAdapter adapter = new CustomPlanListAdapter(getContext(), planList,
                    dataRef.child("trips").child(tripId).child("plans"));
            listOfPlans.setAdapter(adapter);

            listOfPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Plan currentPlan = planList.get(i);

                    Bundle planBundle = new Bundle();
                    planBundle.putString("type", currentPlan.getPlanType().toString());
                    planBundle.putString("name", currentPlan.getName());
                    planBundle.putString("start time", currentPlan.getStartTime().toString());
                    planBundle.putInt("start hour", currentPlan.getStartHour());
                    planBundle.putInt("start min", currentPlan.getStartMin());
                    planBundle.putString("end time", currentPlan.getEndTime().toString());
                    planBundle.putInt("end hour", currentPlan.getEndHour());
                    planBundle.putInt("end min", currentPlan.getEndMin());
                    planBundle.putString("location", currentPlan.getLocation());

                    if (currentPlan.getEndLocation() != null) {
                        planBundle.putString("end location", currentPlan.getEndLocation());
                    }

                    AbstractPlanFragment fragPlan = new AbstractPlanFragment();
                    fragPlan.setArguments(planBundle);

                    getFragmentManager().beginTransaction().replace(R.id.detailed_plan_container, fragPlan).commit();
                }
            });


            addNewPlanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // sets a fragment manager for managing all fragments (for adding new trips)
                    FragmentManager planManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = planManager.beginTransaction();
                    BottomSheetDialogFragment optionFragment = new AddPlanOptionFragment(tripId);
                    optionFragment.setArguments(getArguments());
                    optionFragment.show(planManager, "add plan");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        return view;
    }

    private void populateList() {
        dataRef.child("trips").child(tripId).child("plans").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Plan planObject = snapshot.getValue(Plan.class);
//                    planList.add(planObject);
                    Log.d("snapshot", snapshot.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Cancelled: ", databaseError.getMessage());
            }
        });
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
