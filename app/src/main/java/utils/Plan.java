package utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Plan object for a given trip activity.
 */
public class Plan  {

    // editable properties of a plan
    private String name;
    private String time;
    private String description;
    private int imageID;

    public Plan(String name, String time, String description, int imageID) {
        this.name = name;
        this.time = time;
        this.description = description;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
