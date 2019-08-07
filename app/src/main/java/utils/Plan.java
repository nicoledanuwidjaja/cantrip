package utils;

import java.util.Date;

/**
 * Plan object for a given trip activity.
 */
public class Plan {

    // editable properties of a planType
    private String planId;
    private String name;
    private Date startTime;
    private Date endTime;
    private String tripId;
    private Reservation planType;
    private String location;
    private String endLocation;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;

// throwaway test constructor
    public Plan(String planId, String name, Date startTime, Date endTime, String tripId, Reservation planType,
                String location, int startHour, int startMin, int endHour, int endMin) {
        this.planId = planId;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tripId = tripId;
        this.planType = planType;
        this.location = location;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }

    // General Plan Object
    public Plan(String planId, String name, Date startTime, Date endTime, String tripId, Reservation planType,
                String location, int startHour, int startMin, int endHour, int endMin, String endLocation) {
        this(planId, name, startTime, endTime, tripId, planType, location, startHour, startMin, endHour, endMin);
        this.endLocation = endLocation;
    }

    // empty constructor for adapter
    public Plan() {

    }

    public String getPlanId() { return planId; }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Reservation getPlanType() {
        return planType;
    }

    public void setPlanType(Reservation planType) {
        this.planType = planType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

}
