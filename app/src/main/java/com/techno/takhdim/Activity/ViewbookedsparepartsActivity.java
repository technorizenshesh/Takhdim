package com.techno.takhdim.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdim.Adapter.CategoryListPartsAdapter;
import com.techno.takhdim.Adapter.ViewBookedbookedPartsListAdapter;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.CategoryListResponse;
import com.techno.takhdim.Response.PartsBookingResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.App.MySharedPref.getData;

public class ViewbookedsparepartsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private RecyclerView Rvcat;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbookedspareparts);
        user_id = getData(this, "user_id", "");
        findId();
        img_back.setOnClickListener(this);
        get_part_booking();


    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        Rvcat = findViewById(R.id.Rvcat);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }

    private void get_part_booking() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_user_part_booking(user_id);
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
                            PartsBookingResponse requestListResponse = gson.fromJson(responseData, PartsBookingResponse.class);

                            Rvcat.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewbookedsparepartsActivity.this);
                            Rvcat.setLayoutManager(layoutManager);
                            Rvcat.setItemAnimator(new DefaultItemAnimator());
                            ViewBookedbookedPartsListAdapter adapter = new ViewBookedbookedPartsListAdapter(ViewbookedsparepartsActivity.this, requestListResponse.getResult());
                            Rvcat.setAdapter(adapter);
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
                Toast.makeText(ViewbookedsparepartsActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
