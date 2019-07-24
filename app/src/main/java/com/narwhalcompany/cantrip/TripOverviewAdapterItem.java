package com.narwhalcompany.cantrip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

public class TripOverviewAdapterItem implements Parcelable {

    int imageResId;
    int startMonth;
    int startDay;
    int startYear;
    int endMonth;
    int endDay;
    int endYear;
    String startLoc;
    String endloc;

    public TripOverviewAdapterItem(int imageResId, int startMonth, int startDay, int startYear,
                                   int endMonth, int endDay, int endYear, String startLoc, String endloc) {
        this.imageResId = imageResId;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.startYear = startYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.endYear = endYear;
        this.startLoc = startLoc;
        this.endloc = endloc;
    }

    protected TripOverviewAdapterItem(Parcel in) {
        imageResId = in.readInt();
        startMonth = in.readInt();
        startDay = in.readInt();
        startYear = in.readInt();
        endMonth = in.readInt();
        endDay = in.readInt();
        endYear = in.readInt();
        startLoc = in.readString();
        endloc = in.readString();
    }

    public static final Creator<TripOverviewAdapterItem> CREATOR = new Creator<TripOverviewAdapterItem>() {
        @Override
        public TripOverviewAdapterItem createFromParcel(Parcel in) {
            return new TripOverviewAdapterItem(in);
        }

        @Override
        public TripOverviewAdapterItem[] newArray(int size) {
            return new TripOverviewAdapterItem[size];
        }
    };

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStartYear() { return startYear; }

    public void setStartYear(int startYear) { this.startYear = startYear; }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getEndYear() {
        return endYear;
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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(imageResId);
        parcel.writeInt(startMonth);
        parcel.writeInt(startDay);
        parcel.writeInt(startYear);
        parcel.writeInt(endMonth);
        parcel.writeInt(endDay);
        parcel.writeInt(endYear);
        parcel.writeString(startLoc);
        parcel.writeString(endloc);
    }
}
