package com.techno.takhdim.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back, img_one, img_two, img_three, img_four;
    private String request_id;
    private Button btn_done;

    private CircleImageView img_service, img_user;
    private TextView txt_sname, txt_order_id, txt_username, txt_address, txt_mobileno, txt_desc, txt_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        findId();
        request_id = getIntent().getExtras().getString("request_id");
        img_back.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        getRequestDeatails();
    }


    private void findId() {
        img_back = findViewById(R.id.img_back);
        btn_done = findViewById(R.id.btn_done);
        img_service = findViewById(R.id.img_service);
        img_user = findViewById(R.id.img_user);
        txt_sname = findViewById(R.id.txt_sname);
        txt_order_id = findViewById(R.id.txt_order_id);
        txt_username = findViewById(R.id.txt_username);
        txt_address = findViewById(R.id.txt_address);
        txt_mobileno = findViewById(R.id.txt_mobileno);
        txt_desc = findViewById(R.id.txt_desc);
        txt_status = findViewById(R.id.txt_status);
        img_one = findViewById(R.id.img_one);
        img_two = findViewById(R.id.img_two);
        img_three = findViewById(R.id.img_three);
        img_four = findViewById(R.id.img_four);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        } else if (v == btn_done) {
            finish();
        }
    }

    private void getRequestDeatails() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(OrderDetailsActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().get_request_details(request_id);
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
                            object=object.getJSONObject("result");
                            JSONObject serviceObj=object.getJSONObject("sevice_details");
                            JSONObject userObj=object.getJSONObject("user_details");
                            txt_sname.setText(""+serviceObj.getString("name"));
                            txt_order_id.setText("Order. tak-"+object.getString("id"));
                            txt_username.setText(""+userObj.getString("username"));
                            txt_address.setText(""+object.getString("address"));
                            txt_mobileno.setText(""+userObj.getString("mobile"));
                            txt_desc.setText(""+object.getString("details"));
                            txt_status.setText(""+object.getString("status"));
                            Picasso.get().load(serviceObj.getString("image")).error(R.drawable.plumbing).into(img_service);
                            Picasso.get().load(userObj.getString("image")).error(R.drawable.user_new).into(img_user);
                            Picasso.get().load(object.getString("img1")).into(img_one);
                            Picasso.get().load(object.getString("img2")).into(img_two);
                            Picasso.get().load(object.getString("img3")).into(img_three);
                            Picasso.get().load(object.getString("img4")).into(img_four);
                        } else {
                            Toast.makeText(OrderDetailsActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(OrderDetailsActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
