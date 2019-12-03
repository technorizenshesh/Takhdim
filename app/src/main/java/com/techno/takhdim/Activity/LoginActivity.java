package com.techno.takhdim.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.CountryCodePicker;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.App.GPSTracker;
import com.techno.takhdim.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.App.MySharedPref.getData;
import static com.techno.takhdim.App.MySharedPref.saveData;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_submit;
    private ImageView img_cross;
    private TextView txt_signup;
    private EditText edt_number;
    private String mobile, regId, user_id, code;
    private Double lat, lng;
    public static Activity loginActivity;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private static final String TAG = "LoginActivity";
    private CountryCodePicker ccp;
    private String mVerificationId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        findId();
        btn_submit.setOnClickListener(this);
        img_cross.setOnClickListener(this);
        txt_signup.setOnClickListener(this);
        Callback();
    }

    private void findId() {
        btn_submit = findViewById(R.id.btn_submit);
        img_cross = findViewById(R.id.img_cross);
        txt_signup = findViewById(R.id.txt_signup);
        edt_number = findViewById(R.id.edt_number);
        ccp = findViewById(R.id.ccp);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_submit) {
            mobile = edt_number.getText().toString();
            code = ccp.getSelectedCountryCode();
            regId = getData(this, "regId", "");
            System.out.println("---------regId-------- " + regId);
            if (mobile.equalsIgnoreCase("")) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Mobile Number", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                GPSTracker tracker = new GPSTracker(this);
                if (tracker.canGetLocation()) {
                    lat = tracker.getLatitude();
                    lng = tracker.getLongitude();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // ******for Key board hiding
                    loginCall();
                } else {
                    tracker.showSettingsAlert();
                }
            }
        } else if (v == img_cross) {
            finish();
        } else if (v == txt_signup) {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            Animatoo.animateShrink(LoginActivity.this);
        }
    }

    private void loginCall() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().login(code + "" + mobile, "" + lat, "" + lng, "Users", "" + regId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("signup" + object);
                        if (object.getString("status").equals("1")) {
                            user_id = object.getJSONObject("result").getString("id");
                            mobile="+"+code + "" + mobile;
                            Log.e("Mobile New","-----> "+mobile);
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    mobile,        // Phone number to verify
                                    60,                 // Timeout duration
                                    TimeUnit.SECONDS,   // Unit of timeout
                                    LoginActivity.this,               // Activity (for callback binding)
                                    mCallbacks);        // OnVerificationStateChangedCallbacksPhoneAuthActivity.java;

                        } else {
                            progressDialog.dismiss();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "" + object.getString("result"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } else {
                        progressDialog.dismiss();
                        Log.e("Error", "" + response);
                        Log.e("Error", "" + response.body());
                        Log.e("Error", "" + response.errorBody());
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Server Error", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
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

    void Callback() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                Toast.makeText(LoginActivity.this, "Your number will verify automatically.", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressDialog.dismiss();
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(LoginActivity.this, "the the phone number format is not valid", Toast.LENGTH_LONG).show();
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(LoginActivity.this, "The SMS quota for the project has been exceeded", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, OTPVerificationActivity.class);
                i.putExtra("user_id", user_id);
                i.putExtra("mVerificationId", mVerificationId);
                i.putExtra("mobile", ""+mobile);
                startActivity(i);
                Animatoo.animateShrink(LoginActivity.this);
            }
        };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            otpCall();
                        } else {
                            progressDialog.dismiss();
                            //verification unsuccessful.. display an error message
                            String message = "Something went wrong, please try later";
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
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            Animatoo.animateShrink(LoginActivity.this);
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
}
