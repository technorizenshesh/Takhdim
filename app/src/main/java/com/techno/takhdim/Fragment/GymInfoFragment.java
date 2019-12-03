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
public class GymInfoFragment extends Fragment {


    public GymInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gym_info, container, false);
    }

}
