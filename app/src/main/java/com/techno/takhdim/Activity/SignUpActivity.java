package com.techno.takhdim.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.rilixtech.CountryCodePicker;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.App.MySharedPref.getData;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_cross;
    private EditText edt_username, edt_email, edt_mobile;
    private String username, email, mobile, regId, code;
    private Button btn_submit;
    private TextView txt_login;
    private CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findId();
        img_cross.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        txt_login.setOnClickListener(this);
    }

    private void findId() {
        img_cross = findViewById(R.id.img_cross);
        btn_submit = findViewById(R.id.btn_submit);
        txt_login = findViewById(R.id.txt_login);

        edt_username = findViewById(R.id.edt_username);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile = findViewById(R.id.edt_mobile);
        ccp = findViewById(R.id.ccp);
    }

    @Override
    public void onClick(View v) {
        if (v == img_cross) {
            finish();
        } else if (v == btn_submit) {
            vailidate();
        } else if (v == txt_login) {
            finish();
        }
    }

    private void vailidate() {
        regId = getData(this, "regId", "");
        System.out.println("---------regId-------- " + regId);
        username = edt_username.getText().toString();
        email = edt_email.getText().toString();
        code = ccp.getSelectedCountryCode();
        mobile = edt_mobile.getText().toString();
        if (username.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Name", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (email.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Email", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (mobile.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Number", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            signUpCall();
        }

    }


    private void signUpCall() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().user_signup(username, code + "" + mobile, email, "Users", regId);
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
                            Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Animatoo.animateShrink(SignUpActivity.this);
                            Toast.makeText(SignUpActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                            finish();
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
