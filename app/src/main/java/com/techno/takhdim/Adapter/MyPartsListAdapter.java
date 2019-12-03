package com.techno.takhdim.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdim.Activity.ViewAllPartsActivity;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.MyPartsResponse;
import com.techno.takhdim.Result.MyPartsResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.App.MySharedPref.getData;


public class MyPartsListAdapter extends RecyclerView.Adapter<MyPartsListAdapter.ViewHolder> {
    private Activity activity;
    private List<MyPartsResult> result;
    private String user_id;

    public MyPartsListAdapter(Activity activity, List<MyPartsResult> result) {
        this.activity = activity;
        this.result = result;
        user_id = getData(activity, "user_id", "");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_parts_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txt_name.setText(result.get(position).getCompanyName());
        holder.txt_price.setText("Price : " + result.get(position).getPrice() + " USD");
        holder.txt_warrenty.setText("Warrenty : " + result.get(position).getWarranty() + " Months");
        holder.txt_detl.setText("Details : " + result.get(position).getDescription());
        holder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(result.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_name, txt_price, txt_warrenty, txt_detl;
        Button btn_buy;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_warrenty = itemView.findViewById(R.id.txt_warrenty);
            txt_detl = itemView.findViewById(R.id.txt_detl);
            btn_buy = itemView.findViewById(R.id.btn_buy);


        }

        @Override
        public void onClick(View view) {

        }
    }


    private void showDialog(MyPartsResult myPartsResult) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.buy_part_dialog);
        dialog.setTitle("Title...");
        TextView txt_name, txt_price, txt_warrenty, txt_detl, txt_count;
        Button btn_buy, btn_add, btn_remove;

        ImageView img_close = dialog.findViewById(R.id.img_close);
        txt_name = dialog.findViewById(R.id.txt_name);
        txt_price = dialog.findViewById(R.id.txt_price);
        txt_warrenty = dialog.findViewById(R.id.txt_warrenty);
        txt_count = dialog.findViewById(R.id.txt_count);
        txt_detl = dialog.findViewById(R.id.txt_detl);
        btn_buy = dialog.findViewById(R.id.btn_buy);
        btn_add = dialog.findViewById(R.id.btn_add);
        btn_remove = dialog.findViewById(R.id.btn_remove);

        txt_name.setText(myPartsResult.getCompanyName());
        txt_price.setText("Price : " + myPartsResult.getPrice() + " USD");
        txt_warrenty.setText("Warrenty : " + myPartsResult.getWarranty() + " Months");
        txt_detl.setText("Details : " + myPartsResult.getDescription());


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book_part(user_id, myPartsResult.getId(), myPartsResult.getProviderId(), txt_count.getText().toString());
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(txt_count.getText().toString());
                if (count > 1) {
                    count--;
                    txt_count.setText("" + count);
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(txt_count.getText().toString());
                count++;
                txt_count.setText("" + count);
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }


    private void book_part(String user_id, String part_id, String provider_id, String quantity) {
        Call<ResponseBody> call = AppConfig.loadInterface().book_part(user_id, part_id, provider_id, quantity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Login Data :- " + object);
                        if (object.getString("status").equals("1")) {
                            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                            activity.finish();
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
                Toast.makeText(activity, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


}