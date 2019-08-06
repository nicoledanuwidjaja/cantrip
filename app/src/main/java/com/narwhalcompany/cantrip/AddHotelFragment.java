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


/**
 * A simple {@link Fragment} subclass.
 */
public class AddHotelFragment extends DialogFragment {

    private Button saveButton;
    TextInputEditText checkInText;
    TextInputEditText checkOutText;

    static AddHotelFragment newInstance() {
        return new AddHotelFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_hotel, container, false);

        checkInText = view.findViewById(R.id.checkInDate);
        checkInText.setOnClickListener(new OnDateClick());

        checkOutText = view.findViewById(R.id.checkOutDate);
        checkOutText.setOnClickListener(new OnDateClick());

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addHotelIntent = new Intent(getActivity(), DetailedTripActivity.class);
                startActivity(addHotelIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
