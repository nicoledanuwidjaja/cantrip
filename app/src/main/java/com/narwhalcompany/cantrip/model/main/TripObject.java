package com.narwhalcompany.cantrip.model.main;

import java.util.ArrayList;
import java.util.Date;

import utils.Plan;

public class TripObject {

    private Date startDate;
    private Date endDate;
    private String startLoc;
    private String endLoc;
    private String id;
    private ArrayList<Plan> plans;

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

    public String getId() { return id; }

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

    public void setId(String id) { this.id = id; }
}
