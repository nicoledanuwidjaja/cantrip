package com.narwhalcompany.cantrip;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

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
import utils.Utils;

public class MyPlansFragment extends BottomSheetDialogFragment {

    private ListView listOfPlans;
    private ArrayList<Plan> planList = new ArrayList<>();
    private FloatingActionButton addNewPlanButton;
    private DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();

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
                    dataRef.child("plans" + tripId));
            listOfPlans.setAdapter(adapter);

            listOfPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Plan currentPlan = planList.get(i);

                    Bundle planBundle = new Bundle();
                    planBundle.putString("type", currentPlan.getPlanType().toString());
                    planBundle.putString("name", currentPlan.getName());
                    planBundle.putInt("start hour", currentPlan.getStartHour());
                    planBundle.putInt("start min", currentPlan.getStartMin());
                    planBundle.putInt("end hour", currentPlan.getEndHour());
                    planBundle.putInt("end min", currentPlan.getEndMin());
                    planBundle.putString("start time", Utils.formatDate(currentPlan.getStartTime()));
                    planBundle.putString("end time", Utils.formatDate(currentPlan.getEndTime()));
                    planBundle.putString("location", currentPlan.getLocation());

                    // edge cases is true for landmark or hotel which only have one location
                    if (currentPlan.getEndLocation() != null) {
                        planBundle.putString("end location", currentPlan.getEndLocation());
                    }

                    if (currentPlan.getPlaceId() != null) {
                        planBundle.putString("place", currentPlan.getPlaceId());
                    }

                    AbstractPlanFragment fragPlan = new AbstractPlanFragment();
                    fragPlan.setArguments(planBundle);

                    getFragmentManager().beginTransaction().replace(R.id.detailed_plan_container, fragPlan).commit();
                }
            });

            listOfPlans.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    final String currentPlanId = planList.get(i).getPlanId();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure you want to delete this plan "
                            + planList.get(i).getName() + "?" +
                            "\nChoosing \"Delete\" will result in permanent deletion of the current plan.");
                    builder.setTitle("Delete Plan Alert");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {
                            dataRef.child("plans" + tripId).child(currentPlanId).removeValue();
                            for (int k = 0; k < planList.size(); k++) {
                                if (planList.get(k).getPlanId().compareTo(currentPlanId) == 0) {
                                    planList.remove(k);
                                }
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {
                            Toast.makeText(getContext(), "Canceled delete.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
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
        dataRef.child("plans" + tripId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plan planObject = snapshot.getValue(Plan.class);
                    planList.add(planObject);
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
