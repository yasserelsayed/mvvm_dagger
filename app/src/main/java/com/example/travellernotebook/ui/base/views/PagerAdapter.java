package com.example.travellernotebook.ui.base.views;

import com.example.travellernotebook.ui.profile.views.ProfileFrgment;
import com.example.travellernotebook.ui.trip.views.TripsListFrgment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new TripsListFrgment();
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
        return "list";
        else return "map";
    }
}
