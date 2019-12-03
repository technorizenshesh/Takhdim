package com.techno.takhdim.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.CategoryListResponse;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.App.MySharedPref.getData;
import static com.techno.takhdim.App.MySharedPref.saveData;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back, img_cam;
    private CircleImageView user_img;
    private EditText ed_fullname, ed_email, ed_address, ed_mobile;
    private Button btn_save;
    private String user_id, name, emai, mobile, address, final_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // ******for Key board hiding
        findId();
        user_id = getData(AccountActivity.this, "user_id", "");
        img_back.setOnClickListener(this);
        img_cam.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        get_profile();
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        img_cam = findViewById(R.id.img_cam);
        user_img = findViewById(R.id.user_img);
        ed_fullname = findViewById(R.id.ed_fullname);
        ed_email = findViewById(R.id.ed_email);
        ed_address = findViewById(R.id.ed_address);
        ed_mobile = findViewById(R.id.ed_mobile);
        btn_save = findViewById(R.id.btn_save);
    }


    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        } else if (v == img_cam) {
            final PickImageDialog dialog = PickImageDialog.build(new PickSetup());
            dialog.setOnPickCancel(new IPickCancel() {
                @Override
                public void onCancelClick() {
                    dialog.dismiss();
                }
            }).setOnPickResult(new IPickResult() {
                @Override
                public void onPickResult(PickResult r) {
                    //TODO: do what you have to...
                    final_path = r.getPath();
                    user_img.setImageBitmap(r.getBitmap());
                }
            }).show(AccountActivity.this);
        } else if (v == btn_save) {
            vsilidate();
        }
    }

    private void vsilidate() {

        name = ed_fullname.getText().toString();
        emai = ed_email.getText().toString();
        mobile = ed_mobile.getText().toString();
        address = ed_address.getText().toString();
        if (name.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Full Name", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (emai.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Email Address", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (mobile.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Mobile Number", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (address.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Address", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            updateProfileCall();
        }
    }


    private void get_profile() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_profile(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Login Data :- " + object);
                        if (object.getString("status").equals("1")) {
                            object = object.getJSONObject("result");
                            Picasso.get().load(object.getString("image")).error(R.drawable.plumbing).into(user_img);
                            ed_fullname.setText(object.getString("username"));
                            ed_email.setText(object.getString("email"));
                            ed_mobile.setText(object.getString("mobile"));
                            ed_address.setText(object.getString("address"));
                        } else {
                            Toast.makeText(AccountActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AccountActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfileCall() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(AccountActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        RequestBody requestFile;
        MultipartBody.Part body;
        if (final_path != null) {
            File file = new File(final_path);
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        } else {
            body = MultipartBody.Part.createFormData("image", "");
        }

        Call<ResponseBody> call = AppConfig.loadInterface().updateProfile(user_id, name, mobile, emai, address, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("signup" + object);
                        if (object.getString("status").equals("1")) {
                            object = object.getJSONObject("result");
                            saveData(getApplicationContext(), "ldata", "" + object);
                            saveData(getApplicationContext(), "user_id", "" + object.getString("id"));
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Success", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "" + object.getString("result"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } else {
                        Log.e("Error", "" + response);
                        Log.e("Error", "" + response.body());
                        Log.e("Error", "" + response.errorBody());
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Server Error", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Check Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

}