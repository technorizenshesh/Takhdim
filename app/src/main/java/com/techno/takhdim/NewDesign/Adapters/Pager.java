package com.techno.takhdim.NewDesign.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.techno.takhdim.NewDesign.Models.ModelPager;

import java.util.ArrayList;

public class Pager extends FragmentStatePagerAdapter {
    ArrayList<ModelPager>arrayList;

    public Pager(FragmentManager fm, ArrayList<ModelPager> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayList.get(position).getTitle();
    }
}
