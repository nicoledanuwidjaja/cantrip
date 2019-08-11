package com.narwhalcompany.cantrip.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.narwhalcompany.cantrip.MyPlansFragment;
import com.narwhalcompany.cantrip.R;
import com.narwhalcompany.cantrip.RecommendedListFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.plan_activity, R.string.recommended_tab};
    private final Context mContext;
    private final String tripId;
    private final String tripDestination;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String tripId, String tripDestination) {
        super(fm);
        mContext = context;
        this.tripId = tripId;
        this.tripDestination = tripDestination;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment selectedFragment = null;
        Bundle bundle = new Bundle();
        bundle.putString("trip id", this.tripId);

        switch (position) {
            case 0:
                selectedFragment = new MyPlansFragment(bundle);
                break;
            case 1:
                selectedFragment = new RecommendedListFragment(tripDestination);
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