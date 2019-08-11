package utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.narwhalcompany.cantrip.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomRecListAdapter extends BaseAdapter {

    Context context;
    ArrayList<RecAdapterItem> recItems;

    public CustomRecListAdapter(Context context, ArrayList<RecAdapterItem> recItems) {
        this.context = context;
        this.recItems = recItems;
    }

    @Override
    public int getCount() {
        return recItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if(view == null) {
            view = View.inflate(context, R.layout.fragment_recommended_item, null);
            viewHolder = new ViewHolder();
            viewHolder.recImage = view.findViewById(R.id.recImage);
            viewHolder.recTitle = view.findViewById(R.id.recTitle);
            viewHolder.recStarsText = view.findViewById(R.id.recStarsText);
            viewHolder.recStarsRatingBar = view.findViewById(R.id.recStarsRatingBar);
            viewHolder.recHours = view.findViewById(R.id.recHours);

            view.setTag(viewHolder);
            Log.d("VIEWLOG", "New view created!");
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        RecAdapterItem currItem = recItems.get(i);

        String imageID = currItem.getImageID();
        String apiKey = "AIzaSyChC1eDZHa54rv4rqdw7vJjnRBMxiXOpsM";
        viewHolder.recImage.setImageResource(R.drawable.airplane);
        Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference=" + imageID + "&key=" + apiKey).into(viewHolder.recImage);

        viewHolder.recTitle.setText(currItem.getTitle());

//        viewHolder.recStarsRatingBar.setNumStars(currItem.getStars());

        viewHolder.recStarsRatingBar.setStepSize((float) 0.25);
        viewHolder.recStarsRatingBar.setIsIndicator(true);
        viewHolder.recStarsRatingBar.setNumStars((int) currItem.getStars());
        viewHolder.recStarsText.setText(Double.toString(currItem.getStars()));

        return view;
    }

    static class ViewHolder {
        ImageView recImage;
        TextView recTitle;
        TextView recStarsText;
        RatingBar recStarsRatingBar;
        TextView recHours;
    }

}