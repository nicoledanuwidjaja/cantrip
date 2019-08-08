package com.narwhalcompany.cantrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import utils.Plan;
import utils.Reservation;

public class AbstractPlanFragment extends DialogFragment {

    public AbstractPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        assert getArguments() != null;
        String planSelection = getArguments().getString("type");
        FragmentManager planManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = planManager.beginTransaction();

        Bundle planBundle = new Bundle();
        String planName = getArguments().getString("name");
        String planStartLoc = getArguments().getString("start location");
        String planEndLoc = getArguments().getString("end location");
        String planStartTime = getArguments().getString("start hour") + ":" +
                getArguments().getString("start min");
        String planEndTime = getArguments().getString("end hour") + ":" +
                getArguments().getString("end min");

        AbstractPlanFragment fragPlan;

        // loads the dialog fragment of plan type
        switch (planSelection) {
            case "flight":
                fragPlan = new FlightFragment();

                planBundle.putString("flightName", planName);
                planBundle.putString("flightStartLoc", planStartLoc);
                planBundle.putString("flightEndLoc", planEndLoc);
                planBundle.putString("flightStartTime", planStartTime);
                planBundle.putString("flightEndTime", planEndTime);

                fragPlan.setArguments(planBundle);
                fragPlan.show(planManager, "flight");
                break;
            case "hotel":
                fragPlan = new HotelFragment();
                planBundle.putString("hotelName", planName);
                fragPlan.setArguments(planBundle);
                fragPlan.show(planManager, "hotel");
                break;
            case "landmark":
                fragPlan = new LandmarkFragment();
                planBundle.putString("landmarkName", planName);
                fragPlan.setArguments(planBundle);
                fragPlan.show(planManager, "landmark");
                break;
            default:
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();

        return view;
    }
}
