package utils;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class OnTimeClick implements View.OnClickListener {
    @Override
    public void onClick(final View view) {
        int mHour, mMinute;

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                ((TextInputEditText) view).setText(hour + ":" + String.format("%02d", minute));
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
