package utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;
import java.util.Date;

/**
 * Plan object for a given trip activity.
 */
public class Plan {

    // editable properties of a planType
    private String name;
    private String time;
    private String description;
    private Reservation planType;

    private int startDay;
    private int startMonth;
    private int startYear;
    private int endDay;
    private int endMonth;
    private int endYear;
    private int startTime;
    private int endTime;

    private String location;

    // throwaway test constructor
    public Plan(String name, String time, String description, Reservation planType) {
        this.name = name;
        this.time = time;
        this.description = description;
        // this.imageID = imageID;
        this.planType = planType;
    }

    // New Basic Plan Constructor
    public Plan(String name, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear,
                int startTime, int endTime, String location, Reservation planType) {
        this.name = name;
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.endYear = endYear;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.planType = planType;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    // collects properties of planType in a parcel

    public Plan(Parcel in) {
        name = in.readString();
        time = in.readString();
        description = in.readString();
        // imageID = in.readInt();
    }
    // builder pattern

//    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
//        @Override
//        public Plan createFromParcel(Parcel in) {
//            return new Plan(in);
//        }
//
//        @Override
//        public Plan[] newArray(int size) {
//            return new Plan[size];
//        }
//    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public Reservation getPlanType() {
        return planType;
    }

    public void setPlanType(Reservation planType) {
        this.planType = planType;
    }

//    @Override
//    public int describeContents() {
//        return hashCode();
//    }

//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(name);
//        parcel.writeString(time);
//        parcel.writeString(description);
//        parcel.writeInt(imageID);
//    }
}
