package utils;

import android.text.Editable;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Utils {


    // formats a date to US calendar format
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return format.format(date);
    }

    public static Date stringToDate(String s) {
        try {
            return new SimpleDateFormat("MM/dd/yyyy", Locale.US).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Not a valid string.");
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

        SimpleDateFormat neededDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

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

        date = "" + startMonth + "/" +
                (datePicker.getDayOfMonth()) + "/" + datePicker.getYear() + "";

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

    public static boolean isDatePeriodValid(String startDate, String endDate) {
        return Utils.stringToDate(startDate).getTime() <= Utils.stringToDate(endDate).getTime();
    }

    public static boolean isTimePeriodValidGivenValidDates(String startTime, String endTime) {
        String[] startTimes = startTime.split(":");
        String[] endTimes = endTime.split(":");

        return (Integer.parseInt(startTimes[0]) < Integer.parseInt(endTimes[0])) ?
                ((Integer.parseInt(startTimes[0]) == Integer.parseInt(endTimes[0]))
                        && Integer.parseInt(startTimes[1]) < Integer.parseInt(endTimes[0])) : false;
    }
}
