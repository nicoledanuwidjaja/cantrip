package utils;

import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Date stringToDate(String s) {

        SimpleDateFormat neededDateFormat = new SimpleDateFormat("MM-dd-yyyy");

        Date dateParsed = null;

        try {

            dateParsed = neededDateFormat.parse(s);
        } catch (ParseException e) {
            Log.d("PARSE EXCEPTION", e.getLocalizedMessage());
        }

        return dateParsed;
    }

    public static int stringToHours(String s) {
                String[] stringArr = s.split(":");
                return Integer.parseInt(stringArr[0]);
    }

    public static int stringToMins(String s) {
        String[] stringArr = s.split(":");
        return Integer.parseInt(stringArr[1]);
    }

    public static Date getStaticDate(DatePicker datePicker) {

        String date = formatDateToString(datePicker);

        SimpleDateFormat neededDateFormat = new SimpleDateFormat("MM-dd-yyyy");

        Date dateParsed = null;

        try {

            dateParsed = neededDateFormat.parse(date);
        } catch (ParseException e) {
            Log.d("PARSE EXCEPTION", e.getLocalizedMessage());
        }

        return dateParsed;
    }

    public static String formatDateToString(DatePicker datePicker) {
        String date = "";
        String startMonth;

        if (datePicker.getMonth() < 9) {
            int month = datePicker.getMonth() + 1;
            startMonth = "0" + month;
        } else if (datePicker.getMonth() == 9) {
            startMonth = "10";
        } else {
            startMonth = "" + datePicker.getMonth() + 1 + "";
        }

        date = "" + startMonth+"-" +
                (datePicker.getDayOfMonth()) + "-" + datePicker.getYear() + "";

        return date;
    }
}
