package utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.narwhalcompany.cantrip.R;

import java.util.ArrayList;

public class CustomPlanListAdapter extends BaseAdapter {

    // receive context from main activity
    private Context context;

    // contains data points to be populated on items
    private ArrayList<Plan> planList;

    public CustomPlanListAdapter(Context context, ArrayList<Plan> planList) {
        this.context = context;
        this.planList = planList;
    }

    @Override
    public int getCount() {
        return planList.size();
    }

    @Override
    public Object getItem(int i) {
        return planList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            // Create and return the view
            view = View.inflate(context, R.layout.activity_plan, null);
        }

        ImageView planImage = view.findViewById(R.id.plan_image);
        TextView planName = view.findViewById(R.id.plan_name);

        // overwrite values of child views based on input from MainActivity
        planImage.setImageResource(planList.get(i).getImageID());
        planName.setText(planList.get(i).getName());

        // returns view for current row
        return view;
    }
}
