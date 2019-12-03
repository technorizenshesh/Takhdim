package com.techno.takhdim.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.Activity.LoginActivity.loginActivity;
import static com.techno.takhdim.App.MySharedPref.saveData;

public class OTPVerificationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_verify;
    private ImageView img_cross;
    private String user_id, mVerificationId, otp, one, two, three, four, five, six, mobile;
    private EditText edt_one, edt_two, edt_three, edt_four, edt_five, edt_six;
    private FirebaseAuth mAuth;
    private TextView txt_ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        mAuth = FirebaseAuth.getInstance();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // ******for Key board hiding
        findId();
        mobile = getIntent().getExtras().getString("mobile");
        user_id = getIntent().getExtras().getString("user_id");
        mVerificationId = getIntent().getExtras().getString("mVerificationId");
        btn_verify.setOnClickListener(this);
        img_cross.setOnClickListener(this);

        txt_ex.setText("Please enter the 6-digit code sent to you at " + mobile);

    }

    private void findId() {
        btn_verify = findViewById(R.id.btn_verify);
        img_cross = findViewById(R.id.img_cross);

        edt_one = findViewById(R.id.edt_one);
        edt_two = findViewById(R.id.edt_two);
        edt_three = findViewById(R.id.edt_three);
        edt_four = findViewById(R.id.edt_four);
        edt_five = findViewById(R.id.edt_five);
        edt_six = findViewById(R.id.edt_six);
        txt_ex = findViewById(R.id.txt_ex);

        edt_one.addTextChangedListener(new GenericTextWatcher(edt_one));
        edt_two.addTextChangedListener(new GenericTextWatcher(edt_two));
        edt_three.addTextChangedListener(new GenericTextWatcher(edt_three));
        edt_four.addTextChangedListener(new GenericTextWatcher(edt_four));
        edt_five.addTextChangedListener(new GenericTextWatcher(edt_five));
        edt_six.addTextChangedListener(new GenericTextWatcher(edt_six));
    }


    @Override
    public void onClick(View v) {
        if (v == btn_verify) {
            one = edt_one.getText().toString();
            two = edt_two.getText().toString();
            three = edt_three.getText().toString();
            four = edt_four.getText().toString();
            five = edt_five.getText().toString();
            six = edt_six.getText().toString();
            if (one.equalsIgnoreCase("")) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Wrong OTP", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if (two.equalsIgnoreCase("")) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Wrong OTP", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if (three.equalsIgnoreCase("")) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Wrong OTP", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if (four.equalsIgnoreCase("")) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Wrong OTP", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if (five.equalsIgnoreCase("")) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Wrong OTP", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if (six.equalsIgnoreCase("")) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Wrong OTP", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                otp = one + "" + two + "" + three + "" + four + "" + five + "" + six;
                //otpCall();
                verifyVerificationCode(otp);
            }
        } else if (v == img_cross) {
            finish();
        }
    }

    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            otpCall();
                        } else {
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }

    private void otpCall() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(OTPVerificationActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().verify_otp(user_id, "9999");
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
                            startActivity(new Intent(OTPVerificationActivity.this, HomeActivity.class));
                            Animatoo.animateShrink(OTPVerificationActivity.this);
                            loginActivity.finish();
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


    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.edt_one:
                    if (text.length() == 1)
                        edt_two.requestFocus();
                    break;
                case R.id.edt_two:
                    if (text.length() == 1)
                        edt_three.requestFocus();
                    else if (text.length() == 0)
                        edt_one.requestFocus();
                    break;
                case R.id.edt_three:
                    if (text.length() == 1)
                        edt_four.requestFocus();
                    else if (text.length() == 0)
                        edt_two.requestFocus();
                    break;
                case R.id.edt_four:
                    if (text.length() == 1)
                        edt_five.requestFocus();
                    else if (text.length() == 0)
                        edt_three.requestFocus();
                    break;
                case R.id.edt_five:
                    if (text.length() == 1)
                        edt_six.requestFocus();
                    else if (text.length() == 0)
                        edt_four.requestFocus();
                    break;
                case R.id.edt_six:
                    if (text.length() == 0)
                        edt_five.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

}
