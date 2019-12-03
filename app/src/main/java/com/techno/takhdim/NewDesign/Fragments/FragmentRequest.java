package com.techno.takhdim.NewDesign.Fragments;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdim.Activity.MyOrdersActivity;
import com.techno.takhdim.Adapter.NewOrderAdapter;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.Fragment.NewOrderFragment;
import com.techno.takhdim.NewDesign.Adapters.Pager;
import com.techno.takhdim.NewDesign.Models.ModelPager;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.OrderResponse;
import com.techno.takhdim.databinding.FragmentRequestBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.App.MySharedPref.getData;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRequest extends Fragment {
    private FragmentRequestBinding binding;
    private ArrayList<ModelPager>arrayList=new ArrayList<>();
    public FragmentRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_request, container, false);
        BindView();
        return binding.getRoot();
    }
    private void BindView() {
        arrayList.add(new ModelPager("New",new NewOrderFragment("Pending")));
        arrayList.add(new ModelPager("Schedule",new NewOrderFragment("Accept")));
        arrayList.add(new ModelPager("Completed",new NewOrderFragment("Complete")));
        arrayList.add(new ModelPager("Canceled",new NewOrderFragment("Cancel")));
        binding.viewpager.setAdapter(new Pager(getChildFragmentManager(),arrayList));
        binding.tabs.setupWithViewPager(binding.viewpager);
    }

}
