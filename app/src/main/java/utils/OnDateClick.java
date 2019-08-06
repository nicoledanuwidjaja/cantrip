package utils;

import android.app.DatePickerDialog;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class OnDateClick implements View.OnClickListener {
    @Override
    public void onClick(final View view){
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                ((TextInputEditText) view).setText((month + 1) + "/" + day + "/" + year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}