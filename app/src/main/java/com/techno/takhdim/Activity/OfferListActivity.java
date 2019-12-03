package com.techno.takhdim.Activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdim.Adapter.NewOrderAdapter;
import com.techno.takhdim.Adapter.OrderOfferAdapter;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.OfferListResponse;
import com.techno.takhdim.Response.OrderResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferListActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img_back;
    private RecyclerView RV_All;
    private String request_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);
        findId();
        request_id = getIntent().getExtras().getString("request_id");
        Log.e("request_id","---------> "+request_id);
        img_back.setOnClickListener(this);
        getOrderlist();
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        RV_All = findViewById(R.id.RV_All);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }


    private void getOrderlist() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_request_offer(request_id);
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
                            OfferListResponse requestListResponse = gson.fromJson(responseData, OfferListResponse.class);
                            RV_All.setHasFixedSize(true);
                            OrderOfferAdapter adapter = new OrderOfferAdapter(OfferListActivity.this,requestListResponse.getResult());
                            RV_All.setLayoutManager(new LinearLayoutManager(OfferListActivity.this, LinearLayoutManager.VERTICAL, false));
                            RV_All.setAdapter(adapter);
                        } else {
                            Toast.makeText(OfferListActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                            finish();
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
                Toast.makeText(OfferListActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
