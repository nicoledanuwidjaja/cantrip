package utils;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RecAdapterItem {
    private String imageID;
    private String title;
    private double rating;

    public RecAdapterItem(String imageResID, String title, double rating) {
        this.imageID = imageResID;
        this.title = title;
        this.rating = rating;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageResID) {
        this.imageID = imageResID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getStars() { return rating; }

    public void setStars(double rating) { this.rating = rating; }
}
