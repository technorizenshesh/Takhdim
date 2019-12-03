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
public class AutomobileServiceListFragment extends Fragment {

    private View view;

    public AutomobileServiceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_automobile_service_list, container, false);

        return view;
    }

}
