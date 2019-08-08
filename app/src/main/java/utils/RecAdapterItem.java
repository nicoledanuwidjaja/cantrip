package utils;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RecAdapterItem {
    int imageID;
    String title;

    public RecAdapterItem(int imageResID, String title) {
        this.imageID = imageResID;
        this.title = title;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageResID) {
        this.imageID = imageResID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
