package com.techno.takhdim.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdim.Adapter.MyPartsListAdapter;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.MyPartsResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllPartsActivity extends AppCompatActivity implements View.OnClickListener {

    private String part_id;
    private RecyclerView Rvviewparts;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_parts);
        findId();
        part_id = getIntent().getExtras().getString("part_id");
        img_back.setOnClickListener(this);
        getmyParts();
        Log.e("part_id", "----------->" + part_id);
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        Rvviewparts = findViewById(R.id.Rvviewparts);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }

    private void getmyParts() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_part_by_id(part_id);
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
                            MyPartsResponse requestListResponse = gson.fromJson(responseData, MyPartsResponse.class);
                            Rvviewparts.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewAllPartsActivity.this);
                            Rvviewparts.setLayoutManager(layoutManager);
                            Rvviewparts.setItemAnimator(new DefaultItemAnimator());
                            MyPartsListAdapter adapter = new MyPartsListAdapter(ViewAllPartsActivity.this, requestListResponse.getResult());
                            Rvviewparts.setAdapter(adapter);
                        } else {

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
                Toast.makeText(ViewAllPartsActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
