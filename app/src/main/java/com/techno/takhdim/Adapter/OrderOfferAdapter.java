package com.techno.takhdim.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.picasso.Picasso;
import com.techno.takhdim.Activity.ChatActivity;
import com.techno.takhdim.Activity.LoginActivity;
import com.techno.takhdim.Activity.OfferListActivity;
import com.techno.takhdim.Activity.OrderDetailsActivity;
import com.techno.takhdim.Activity.SuccessActivity;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.Result.OfferListResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderOfferAdapter extends RecyclerView.Adapter<OrderOfferAdapter.ViewHolder> {
    private Activity activity;
    private List<OfferListResult> result;

    public OrderOfferAdapter(Activity activity, List<OfferListResult> result) {
        this.activity = activity;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_order_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOfferCall(result.get(position).getId());
            }
        });

        holder.btn_requesdiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ChatActivity.class);
                i.putExtra("reciver_id", "" + result.get(position).getUsersDetails().getId());
                activity.startActivity(i);
                Animatoo.animateShrink(activity);
            }
        });

        holder.txt_username.setText(result.get(position).getUsersDetails().getUsername());
        holder.txt_date.setText(result.get(position).getDateTime());
        holder.txt_amount.setText(result.get(position).getCost() + " QR");
        Picasso.get().load(result.get(position).getUsersDetails().getImage()).error(R.drawable.plumbing).into(holder.img_user);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btn_accept, btn_requesdiscount;
        CircleImageView img_user;
        TextView txt_username, txt_date, txt_amount;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            btn_accept = itemView.findViewById(R.id.btn_accept);

            img_user = itemView.findViewById(R.id.img_user);
            txt_username = itemView.findViewById(R.id.txt_username);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            btn_requesdiscount = itemView.findViewById(R.id.btn_requesdiscount);
        }

        @Override
        public void onClick(View view) {

        }
    }


    private void acceptOfferCall(String id) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().accept_offer(id);
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
                            activity.startActivity(new Intent(activity, SuccessActivity.class));
                            Animatoo.animateShrink(activity);
                            activity.finish();
                        } else {
                            Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "" + object.getString("result"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } else {
                        Log.e("Error", "" + response);
                        Log.e("Error", "" + response.body());
                        Log.e("Error", "" + response.errorBody());
                        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "Server Error", Snackbar.LENGTH_LONG);
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
                Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "Please Check Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}