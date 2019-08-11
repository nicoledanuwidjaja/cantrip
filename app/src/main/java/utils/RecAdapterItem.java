package utils;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RecAdapterItem {
    private String imageID;
    private String title;

    public RecAdapterItem(String imageResID, String title) {
        this.imageID = imageResID;
        this.title = title;
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
}
