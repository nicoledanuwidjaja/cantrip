package utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Plan object for a given trip activity.
 */
public class Plan implements Parcelable {

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

    // collects properties of plan in a parcel
    public Plan(Parcel in) {
        name = in.readString();
        time = in.readString();
        description = in.readString();
        imageID = in.readInt();
    }

    // builder pattern
    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel in) {
            return new Plan(in);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };

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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(time);
        parcel.writeString(description);
        parcel.writeInt(imageID);
    }
}
