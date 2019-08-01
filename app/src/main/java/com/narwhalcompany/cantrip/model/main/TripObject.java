package com.narwhalcompany.cantrip.model.main;

import java.util.ArrayList;
import java.util.Date;

public class TripObject {

    private Date startDate;
    private Date endDate;
    private String startLoc;
    private String endLoc;
    private ArrayList<ItineraryItem> plans;

    public TripObject(Date startDate, Date endDate, String startLoc, String endLoc, ArrayList<ItineraryItem> plans) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startLoc = startLoc;
        this.endLoc = endLoc;
        this.plans = plans;
    }

    public TripObject() {

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

    public ArrayList<ItineraryItem> getPlans() {
        return plans;
    }
}
