package com.techno.takhdim.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdim.Adapter.ChatListAdapter;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.ChatListResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.App.MySharedPref.getData;

public class MessagesActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private RecyclerView RVmessage;
    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        findID();
        img_back.setOnClickListener(this);
        user_id = getData(this, "user_id", "");
        getChatCall(user_id);
    }

    private void findID() {
        img_back = findViewById(R.id.img_back);
        RVmessage = findViewById(R.id.RVmessage);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }

    private void getChatCall(String id) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MessagesActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().getConversetion(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Login Data :- " + object);
                        if (object.getString("status").equals("1")) {
                            Gson gson = new Gson();
                            ChatListResponse requestListResponse = gson.fromJson(responseData, ChatListResponse.class);
                            RVmessage.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MessagesActivity.this);
                            RVmessage.setLayoutManager(layoutManager);
                            RVmessage.setItemAnimator(new DefaultItemAnimator());
                            ChatListAdapter adapter = new ChatListAdapter(MessagesActivity.this, requestListResponse.getResult());
                            RVmessage.setAdapter(adapter);
                        } else {
                            Toast.makeText(MessagesActivity.this, "Request Not Found", Toast.LENGTH_SHORT).show();
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
                progressDialog.dismiss();
                Toast.makeText(MessagesActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
