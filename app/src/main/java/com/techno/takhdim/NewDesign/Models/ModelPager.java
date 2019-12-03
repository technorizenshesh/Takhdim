package com.techno.takhdim.NewDesign.Models;

import android.support.v4.app.Fragment;

public class ModelPager {
    String Title;
    Fragment fragment;

    public ModelPager(String title, Fragment fragment) {
        Title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

}
