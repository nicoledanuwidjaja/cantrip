package utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.narwhalcompany.cantrip.AddLandmarkFragment;
import com.narwhalcompany.cantrip.DetailedTripActivity;
import com.narwhalcompany.cantrip.MainActivity;
import com.narwhalcompany.cantrip.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomRecListAdapter extends BaseAdapter {

    Context context;
    ArrayList<RecAdapterItem> recItems;
    String tripId;
    String placeId;
    int recItemsPos;

    public CustomRecListAdapter(Context context, ArrayList<RecAdapterItem> recItems, String tripId) {
        this.context = context;
        this.recItems = recItems;
        this.tripId = tripId;

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

        final ViewHolder viewHolder;
        recItemsPos = i;

        if(view == null) {
            view = View.inflate(context, R.layout.fragment_recommended_item, null);
            viewHolder = new ViewHolder();
            viewHolder.recImage = view.findViewById(R.id.recImage);
            viewHolder.recTitle = view.findViewById(R.id.recTitle);
            viewHolder.recStarsText = view.findViewById(R.id.recStarsText);
            viewHolder.recStarsRatingBar = view.findViewById(R.id.recStarsRatingBar);
            viewHolder.recHours = view.findViewById(R.id.recHours);
            viewHolder.recHoursImage = view.findViewById(R.id.recHoursImage);
            viewHolder.addButton = view.findViewById(R.id.addButton);

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
        switch ( currItem.getOpeningHours() ) {
            case "true":
                viewHolder.recHours.setText("Open now");
                viewHolder.recHours.setTextColor(Color.rgb(37, 181, 27));
                viewHolder.recHoursImage.setImageResource(R.drawable.time_green);
                break;
            case "false":
                viewHolder.recHours.setText("Closed now");
                viewHolder.recHours.setTextColor(Color.rgb(255, 82, 82));
                viewHolder.recHoursImage.setImageResource(R.drawable.time_red);
                break;
            default:
                viewHolder.recHours.setText("N/A");
                viewHolder.recHours.setTextColor(Color.GRAY);
                viewHolder.recHoursImage.setImageResource(R.drawable.time);
                break;
        }


        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager planManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction transaction = planManager.beginTransaction();

                View parentRow = (View) view.getParent();
                ListView listView = (ListView) parentRow.getParent().getParent().getParent().getParent();
                final int position = listView.getPositionForView(parentRow);

                DialogFragment newLandmark = new AddLandmarkFragment(tripId, recItems.get(position).getPlaceID());
                newLandmark.show(planManager, "landmark");

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        return view;
    }

    public void AdapterViewListClicked() {

    }

    static class ViewHolder {
        ImageView recImage;
        TextView recTitle;
        TextView recStarsText;
        RatingBar recStarsRatingBar;
        TextView recHours;
        ImageView recHoursImage;
        ImageView addButton;
    }

}