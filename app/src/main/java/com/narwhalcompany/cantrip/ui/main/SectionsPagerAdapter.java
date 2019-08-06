package com.narwhalcompany.cantrip.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.narwhalcompany.cantrip.EmptyRecommendedFragment;
import com.narwhalcompany.cantrip.MyPlansFragment;
import com.narwhalcompany.cantrip.R;
import com.narwhalcompany.cantrip.RecommendedFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.plan_activity, R.string.recommended_tab};
    private final Context mContext;
    private final String tripId;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String tripId) {
        super(fm);
        mContext = context;
        this.tripId = tripId;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment selectedFragment = null;

        switch (position) {
            case 0:
                selectedFragment = new MyPlansFragment();
                Bundle bundle = new Bundle();
                bundle.putString("trip id", this.tripId);
                selectedFragment.setArguments(bundle);
                break;
            case 1:
                selectedFragment = new RecommendedFragment();
                break;
        }
        return selectedFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "My Plans";
            case 1:
                return "Recommended";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}