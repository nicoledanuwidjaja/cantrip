package com.narwhalcompany.cantrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import utils.OnDateClick;
import utils.OnTimeClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddLandmarkFragment extends DialogFragment {

    private Button saveButton;
    TextInputEditText startDateText;
    TextInputEditText startTimeText;
    TextInputEditText endDateText;
    TextInputEditText endTimeText;


    static AddLandmarkFragment newInstance() {
        return new AddLandmarkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_landmark, container, false);

        startDateText = view.findViewById(R.id.startDateText);
        startDateText.setOnClickListener(new OnDateClick());
        startTimeText = view.findViewById(R.id.startTimeText);
        startTimeText.setOnClickListener(new OnTimeClick());

        endDateText = view.findViewById(R.id.endDateText);
        endDateText.setOnClickListener(new OnDateClick());
        endTimeText = view.findViewById(R.id.endTimeText);
        endTimeText.setOnClickListener(new OnTimeClick());

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addLandmarkIntent = new Intent(getActivity(), DetailedTripActivity.class);
                startActivity(addLandmarkIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
