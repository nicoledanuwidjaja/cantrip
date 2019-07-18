package utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.narwhalcompany.cantrip.R;

import java.util.ArrayList;

public class CustomPlanAdapter extends BaseAdapter {

    // receive context from main activity
    private Context characterContext;

    // contains data points to be populated on items
    private ArrayList<Plan> planList;

    public CustomPlanAdapter(Context characterContext, ArrayList<Plan> planList) {
        this.characterContext = characterContext;
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
            view = View.inflate(characterContext, R.layout.activity_plan, null);
        }

        ImageView characterImage = view.findViewById(R.id.plan_image);
        TextView characterName = view.findViewById(R.id.plan_name);

        // overwrite values of child views based on input from MainActivity
        characterImage.setImageResource(planList.get(i).getImageID());
        characterName.setText(planList.get(i).getName());

        // returns view for current row
        return view;
    }
}
