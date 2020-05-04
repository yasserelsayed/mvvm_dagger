package com.example.travellernotebook.ui.base.views;

import com.example.travellernotebook.ui.trip.views.TripsListFrgment;
import com.example.travellernotebook.ui.trip.views.TripsMapFrgment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> lstFragment;

    public PagerAdapter(@NonNull FragmentManager fm, List<Fragment> lst) {
        super(fm);
        this.lstFragment = lst;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0) return lstFragment.get(position);
        else  return lstFragment.get(position);
    }


    @Override
    public int getCount() {
        return lstFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
        return "list";
        else return "map";
    }
}
