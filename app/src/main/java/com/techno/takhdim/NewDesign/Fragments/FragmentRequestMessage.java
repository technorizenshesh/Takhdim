package com.techno.takhdim.NewDesign.Fragments;


import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdim.Adapter.NewOrderAdapter;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.Fragment.NewOrderFragment;
import com.techno.takhdim.NewDesign.Adapters.AdapterComments;
import com.techno.takhdim.NewDesign.Models.ModelComment;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.OrderResponse;
import com.techno.takhdim.databinding.FragmentRequestDetailsBinding;
import com.techno.takhdim.databinding.FragmentRequestMessageBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.App.MySharedPref.getData;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentRequestMessage extends Fragment {
    private String ID;
    private FragmentRequestMessageBinding binding;
    private ArrayList<ModelComment>arrayList=new ArrayList<>();
    private AdapterComments adapter;
    private String user_id;

    public FragmentRequestMessage(String id) {
        this.ID=id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_request_message, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        adapter=new AdapterComments(getActivity(),arrayList);
        binding.list.setAdapter(adapter);
        user_id = getData(getActivity(), "user_id", "");
        binding.swipeRefresh.setOnRefreshListener(this::getCommentList);
        binding.imgSend.setOnClickListener(v->AddComment());
        getCommentList();
    }
    private void getCommentList() {
        binding.swipeRefresh.setRefreshing(true);
        Call<ResponseBody> call = AppConfig.loadInterface().getComment(ID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.swipeRefresh.setRefreshing(false);
                try {
                    if (response.isSuccessful()) {
                        arrayList.clear();
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Comments:-" + object);
                        if (object.getString("status").equals("1")) {
                            JSONArray array=object.getJSONArray("result");
                            for (int i=0;i<array.length();i++){
                                JSONObject jsonObject=array.getJSONObject(i);
                                JSONObject user_details=jsonObject.getJSONObject("user_details");
                                ModelComment comment=new ModelComment();
                                comment.setId(jsonObject.getString("id"));
                                comment.setUserID(jsonObject.getString("user_id"));
                                comment.setComment(jsonObject.getString("comment"));
                                comment.setDate(jsonObject.getString("date_time"));
                                comment.setUserName(user_details.getString("username"));
                                comment.setUserImage(user_details.getString("image"));
                                arrayList.add(comment);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        binding.tvNoRecordFound.setVisibility(arrayList.size()>0?View.GONE:View.VISIBLE);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.swipeRefresh.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }
    private void AddComment() {
        if (binding.etComment.getText().toString().isEmpty()){
            binding.etComment.setError("Required");
            binding.etComment.requestFocus();
            return;
        }
        binding.swipeRefresh.setRefreshing(true);
        Call<ResponseBody> call = AppConfig.loadInterface().addComment(ID,user_id,binding.etComment.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.etComment.setText("");
                binding.swipeRefresh.setRefreshing(false);
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Comments:-" + object);
                        if (object.getString("status").equals("1")) {
                            getCommentList();
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.swipeRefresh.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }
}
