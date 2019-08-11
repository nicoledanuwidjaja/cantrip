package com.narwhalcompany.cantrip.model.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import utils.Plan;

public class TripObject {

    private Date startDate;
    private Date endDate;
    private String startLoc;
    private String endLoc;
    private String id;
    private ArrayList<Plan> plans;
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    public TripObject(Date startDate, Date endDate, String startLoc, String endLoc, String id, ArrayList<Plan> plans) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startLoc = startLoc;
        this.endLoc = endLoc;
        this.id = id;
        this.plans = plans;
    }

    public TripObject() {

    }

    // formats a date to US calendar format
    public String formatDate(Date date) {
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

    // formats the date based on the month
    private String monthFormat(String stringDate) {
        String month = stringDate.substring(0, 2);
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
        return month + " " + stringDate.substring(3, 5) + ", " + stringDate.substring(6);
    }

    @Override
    public String toString() {
        return startLoc + " to " + endLoc;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStartLoc() {
        return startLoc;
    }

    public String getEndLoc() {
        return endLoc;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Plan> getPlans() {
        return plans;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartLoc(String startLoc) {
        this.startLoc = startLoc;
    }

    public void setEndLoc(String endLoc) {
        this.endLoc = endLoc;
    }

    public void setPlans(ArrayList<Plan> plans) {
        this.plans = plans;
    }

    public void setId(String id) {
        this.id = id;
    }
}
