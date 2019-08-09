package utils;

import android.text.Editable;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static Date stringToDate(String s) {

        String[] stringArr = s.split("/");
        return new Date(Integer.parseInt(stringArr[2]), Integer.parseInt(stringArr[1]), Integer.parseInt(stringArr[0]));
    }
// new method - nicole
//    public static Date textToDate(String s) {
//        try {
//            Date newDate;
//
//            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
//
//            newDate = format.parse(s);
//            return newDate;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        throw new IllegalArgumentException("Impossible.");
//    }

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

        SimpleDateFormat neededDateFormat = new SimpleDateFormat("MM/dd/yyyy");

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

        if (datePicker.getMonth() < 10) {
            startMonth = "0" + datePicker.getMonth();
        } else {
            startMonth = "" + datePicker.getMonth() + "";
        }

        date = "" + startMonth + "/" +
                datePicker.getDayOfMonth() + "/" + datePicker.getYear() + "";

        return date;
    }

    public static boolean checkEndDateValid(DatePicker start, DatePicker end) {
        if (start.getYear() > end.getYear()) {
            return false;
        } else if (start.getYear() == end.getYear()) {
            if (start.getMonth() > end.getMonth()) {
                return false;
            } else if (start.getMonth() == end.getMonth()) {
                return start.getDayOfMonth() <= end.getDayOfMonth();
            }
        }

        return true;
    }

    // formats a date to US calendar format
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return format.format(date);
    }
}
