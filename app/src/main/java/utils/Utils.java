package utils;

import android.text.Editable;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {


    // formats a date to US calendar format
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        String stringToReturn = "";

        try {
            if (date != null) {
                stringToReturn = format.format(date);
                return monthFormat(stringToReturn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringToReturn;
    }

    private static String monthFormat(String date) {
        String month = date.substring(0, 2);
        switch(month) {
            case "01":
                month = "Jan";
                break;
            case "02":
                month = "Feb";
                break;
            case "03":
                month = "Mar";
                break;
            case "04":
                month = "Apr";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "Jun";
                break;
            case "07":
                month = "Jul";
                break;
            case "08":
                month = "Aug";
                break;
            case "09":
                month = "Sep";
                break;
            case "10":
                month = "Oct";
                break;
            case "11":
                month = "Nov";
                break;
            case "12":
                month = "Dec";
                break;
            default:
                break;
        }
        return month + " " + date.substring(3, 5) + ", " + date.substring(6);
    }

    public static Date stringToDate(String s) {
        try {
            return new SimpleDateFormat("MM/dd/yyyy", Locale.US).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Not a valid string.");
    }

    public static Date stringToDateWithTime(String s) {
        try {
            return new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US).parse(s);
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


    // returns true if date period is valid
    public static boolean isDateValid(String startDate, String endDate) {
        return Utils.stringToDate(startDate).compareTo(Utils.stringToDate(endDate)) < 0;
    }

    // returns true if date and time period is valid
    public static boolean isDateAndTimeValid(String startDate, String endDate, String startTime, String endTime) {
        if (Utils.stringToDate(startDate).compareTo(Utils.stringToDate(endDate)) < 0) {
            return true;
        } else if (Utils.stringToDate(startDate).compareTo(Utils.stringToDate(endDate)) == 0) {
            String startTimeStrT = startDate + " " + startTime;
            String endTimeStrT = endDate + " " + endTime;

            return Utils.stringToDateWithTime(startTimeStrT).before(Utils.stringToDateWithTime(endTimeStrT));
        } else {
            return false;
        }
    }

}
