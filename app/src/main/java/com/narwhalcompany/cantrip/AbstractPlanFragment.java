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
        String planStartDate = getArguments().getString("start date");
        String planEndDate = getArguments().getString("end date");
        String planStartTime = getArguments().getInt("start hour") + ":" +
                getArguments().getInt("start min");
        String planEndTime = getArguments().getInt("end hour") + ":" +
                getArguments().getInt("end min");

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
                planBundle.putString("flightStartDate", planStartDate);
                planBundle.putString("flightEndDate", planEndDate);
                fragPlan.setArguments(planBundle);
                fragPlan.show(planManager, "flight");
                break;
            case "hotel":
                fragPlan = new HotelFragment();
                planBundle.putString("hotelName", planName);
                planBundle.putString("hotelLoc", planEndLoc);
                planBundle.putString("hotelStartTime", planStartTime);
                planBundle.putString("hotelEndTime", planEndTime);
                planBundle.putString("hotelStartDate", planStartDate);
                planBundle.putString("hotelEndDate", planEndDate);
                fragPlan.setArguments(planBundle);
                fragPlan.show(planManager, "hotel");
                break;
            case "landmark":
                fragPlan = new LandmarkFragment();
                planBundle.putString("landmarkName", planName);
                planBundle.putString("landmarkLoc", planEndLoc);
                planBundle.putString("landmarkStartTime", planStartTime);
                planBundle.putString("landmarkEndTime", planEndTime);
                planBundle.putString("landmarkStartDate", planStartDate);
                planBundle.putString("landmarkEndDate", planEndDate);
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