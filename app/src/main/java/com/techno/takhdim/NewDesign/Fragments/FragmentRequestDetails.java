package com.techno.takhdim.NewDesign.Fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.techno.takhdim.Activity.OrderDetailsActivity;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.databinding.FragmentDialogRequestDetailsBinding;
import com.techno.takhdim.databinding.FragmentRequestDetailsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentRequestDetails extends Fragment {


    private final String request_id;
    private FragmentRequestDetailsBinding binding;

    public FragmentRequestDetails(String id) {
        this.request_id=id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_request_details, container, false);
        getRequestDeatails();
        return binding.getRoot();
    }
    private void getRequestDeatails() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
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
                            binding.txtSname.setText(""+serviceObj.getString("name"));
                            binding.txtOrderId.setText("Request. #SRQ-"+object.getString("id"));
                            binding.txtUsername.setText(""+userObj.getString("username"));
                            binding.txtAddress.setText(""+object.getString("address"));
                            binding.txtMobileno.setText(""+userObj.getString("mobile"));
                            binding.txtDesc.setText(""+object.getString("details"));
                            binding.txtDate.setText(object.getString("time")+" "+object.getString("date"));

                            Picasso.get().load(serviceObj.getString("image")).error(R.drawable.plumbing).into(binding.imgService);
                            Picasso.get().load(userObj.getString("image")).error(R.drawable.user_new).into(binding.imgUser);

                        } else {
                            Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
