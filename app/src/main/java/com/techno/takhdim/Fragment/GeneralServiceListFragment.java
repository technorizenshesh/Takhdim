package com.techno.takhdim.Fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.techno.takhdim.Activity.AddRequestDeatilsActivity;
import com.techno.takhdim.Activity.LoginActivity;
import com.techno.takhdim.Activity.NearGymActivity;
import com.techno.takhdim.Activity.OTPVerificationActivity;
import com.techno.takhdim.Adapter.ServiceListAdapter;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.ServiceListResponse;

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
@SuppressLint("ValidFragment")
public class GeneralServiceListFragment extends Fragment {

    private View view;
    private String id;
    private RecyclerView RV_All;

    @SuppressLint("ValidFragment")
    public GeneralServiceListFragment(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_general_service_list, container, false);
        RV_All = view.findViewById(R.id.RV_All);
        getcategory_list();
        return view;
    }


    private void getcategory_list() {
        Call<ResponseBody> call = AppConfig.loadInterface().services_list(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Login Data :- " + object);
                        if (object.getString("status").equals("1")) {

                            Gson gson = new Gson();
                            ServiceListResponse successResponse = gson.fromJson(responseData, ServiceListResponse.class);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
                            RV_All.setLayoutManager(mLayoutManager);
                            RV_All.addItemDecoration(new GridSpacingItemDecoration(4, dpToPx(5), true));
                            RV_All.setItemAnimator(new DefaultItemAnimator());
                            ServiceListAdapter adapter = new ServiceListAdapter(getActivity(),successResponse.getResult());
                            RV_All.setAdapter(adapter);
                        } else {
                            Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
                        }
                    } else ;
                    // AppConfig.showToast("server error");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column; //spacing / spanCount; // spacing - column  ((1f / spanCount) * spacing)
                outRect.right = (column + 1);   //spacing / spanCount; // (column + 1)  ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column;   //spacing / spanCount; // column  ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1);  //spacing / spanCount; // spacing - (column + 1)  ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
