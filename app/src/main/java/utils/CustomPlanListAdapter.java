package utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.narwhalcompany.cantrip.R;

import java.util.ArrayList;

import static utils.Utils.formatDate;

public class CustomPlanListAdapter extends BaseAdapter {

    // receive context from main activity
    private Context context;

    // contains data points to be populated on items
    private ArrayList<Plan> planList;

    private ArrayList<DataSnapshot> snapshots = new ArrayList<>();
    private DatabaseReference databaseReference;

    public CustomPlanListAdapter(Context context, ArrayList<Plan> planList,
                                 DatabaseReference databaseReference) {
        this.context = context;
        this.planList = planList;
        this.databaseReference = databaseReference;

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                snapshots.add(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("REMOVED", dataSnapshot.toString());

                String removeKey = dataSnapshot.getKey();
                for (int i = 0; i < snapshots.size(); i++) {
                    if (snapshots.get(i).getKey().compareTo(removeKey) == 0) {
                        snapshots.remove(i);
                        break;
                    }
                }

                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Database error: ", databaseError.getMessage());
            }
        });
    }

    @Override
    public int getCount() {
        return snapshots.size();
    }

    @Override
    public Object getItem(int i) {
        return snapshots.get(i).getValue(Plan.class);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        Plan retrieved = (Plan) getItem(i);
        ViewHolder viewHolder;

        if (view == null) {
            // Create and return the view
            view = View.inflate(context, R.layout.plan_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.planImage = view.findViewById(R.id.plan_image);
            viewHolder.planDate = view.findViewById(R.id.plan_date);
            viewHolder.planName = view.findViewById(R.id.plan_name);
            viewHolder.removeIcon = view.findViewById(R.id.remove_icon);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // sets images for each plan type
        if (retrieved != null) {
            if (retrieved.getPlanType() == Reservation.FLIGHT) {
                viewHolder.planImage.setImageResource(R.drawable.plane_horiz);
            } else if (retrieved.getPlanType() == Reservation.HOTEL) {
                viewHolder.planImage.setImageResource(R.drawable.hotel);
            } else {
                viewHolder.planImage.setImageResource(R.drawable.landmark);
            }
        }

        viewHolder.planName.setText(retrieved.getName());
        viewHolder.planDate.setText(Utils.formatDate(planList.get(i).getStartTime())
                + " - " + Utils.formatDate(planList.get(i).getEndTime()));

        viewHolder.removeIcon.setImageResource(R.drawable.trash);

        viewHolder.removeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plan removePlan = (Plan) getItem(i);
                databaseReference.child(removePlan.getPlanId()).removeValue();
            }
        });

        // returns view for current row
        return view;
    }

    static class ViewHolder {
        TextView planName;
        ImageView planImage;
        TextView planDate;
        ImageView removeIcon;
    }
}
