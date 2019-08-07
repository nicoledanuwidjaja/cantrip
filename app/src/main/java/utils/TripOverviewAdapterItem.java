package utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.narwhalcompany.cantrip.model.main.TripObject;

import java.util.Date;

public class TripOverviewAdapterItem {

//    private int imageResId;
//    private int startMonth;
//    private int startDay;
//    private int startYear;
//    private int endMonth;
//    private int endDay;
//    private int endYear;

    private String startLoc;
    private String endloc;
    private Date startDate;
    private Date endDate;

    public TripOverviewAdapterItem(String startLoc, String endloc, Date startDate, Date endDate) {
        this.startLoc = startLoc;
        this.endloc = endloc;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(String startLoc) {
        this.startLoc = startLoc;
    }

    public String getEndloc() {
        return endloc;
    }

    public void setEndloc(String endloc) {
        this.endloc = endloc;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
