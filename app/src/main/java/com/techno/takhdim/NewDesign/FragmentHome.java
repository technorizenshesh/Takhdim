package com.techno.takhdim.NewDesign;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdim.Activity.HomeActivity;
import com.techno.takhdim.Adapter.ServiceListAdapter;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.Fragment.GeneralServiceListFragment;
import com.techno.takhdim.NewDesign.Adapters.AdapterCategory;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.CategoryListResponse;
import com.techno.takhdim.Response.ServiceListResponse;
import com.techno.takhdim.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    private FragmentHomeBinding binding;
    private AdapterCategory adapter;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        BindView();
        getServiceCategory();
        return binding.getRoot();
    }

    private void BindView() {
        adapter=new AdapterCategory(getContext());
        binding.list.setAdapter(adapter);
        binding.swipeRefresh.setOnRefreshListener(this::getServiceCategory);
    }
    private void getServiceCategory() {
        binding.swipeRefresh.setRefreshing(true);
        Call<ResponseBody> call = AppConfig.loadInterface().category();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.swipeRefresh.setRefreshing(false);
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("CategoryList" + object);
                        if (object.getString("status").equals("1")) {
                            Gson gson = new Gson();
                            CategoryListResponse requestListResponse = gson.fromJson(responseData, CategoryListResponse.class);
                            adapter.setData(requestListResponse.getResult());
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }

}
