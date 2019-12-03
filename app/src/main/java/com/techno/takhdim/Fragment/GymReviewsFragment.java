package com.techno.takhdim.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techno.takhdim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GymReviewsFragment extends Fragment {

    private View view;

    public GymReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gym_reviews, container, false);



        return view;
    }

}
