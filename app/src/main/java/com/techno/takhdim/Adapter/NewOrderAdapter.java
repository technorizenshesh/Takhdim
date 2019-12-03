package com.techno.takhdim.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;
import com.techno.takhdim.Activity.HomeActivity;
import com.techno.takhdim.Activity.LoginActivity;
import com.techno.takhdim.Activity.OTPVerificationActivity;
import com.techno.takhdim.Activity.OfferListActivity;
import com.techno.takhdim.Activity.OrderDetailsActivity;
import com.techno.takhdim.NewDesign.Interfaces.onclickRequest;
import com.techno.takhdim.R;
import com.techno.takhdim.Result.OrderResult;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {
    private Activity activity;
    private List<OrderResult> result;
    private onclickRequest callback;

    public NewOrderAdapter(Activity activity, List<OrderResult> result) {
        this.activity = activity;
        this.result = result;
    }
    public void CallBack(onclickRequest request){
        this.callback=request;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_order_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_detail.setOnClickListener(v->callback.View(result.get(position)));
        holder.txt_sname.setText(result.get(position).getServiceDetails().getName());
        holder.txt_datetime.setText(result.get(position).getTime()+" , "+result.get(position).getDate());
        holder.txt_orderid.setText("Request. #SRQ-"+result.get(position).getId());
        if (result.get(position).getStatus().equalsIgnoreCase("pending")){
            holder.img_status.setImageResource(R.drawable.img_new);
            holder.txt_status.setText("New");
        }else if (result.get(position).getStatus().equalsIgnoreCase("Accept")){
            holder.img_status.setImageResource(R.drawable.panding);
            holder.txt_status.setText("Pending");
        }else if (result.get(position).getStatus().equalsIgnoreCase("Complete")){
            holder.img_status.setImageResource(R.drawable.completed);
            holder.txt_status.setText("Completed");
        }else if (result.get(position).getStatus().equalsIgnoreCase("Cancel")){
            holder.img_status.setVisibility(View.GONE);
            holder.txt_status.setText("Canceled");
        }
        holder.txt_address.setText(result.get(position).getAddress());
        Picasso.get().load(result.get(position).getServiceDetails().getImage()).error(R.drawable.plumbing).into(holder.img_service);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_detail, txt_sname, txt_datetime, txt_orderid, txt_status,txt_address;
        CircleImageView img_service;
        ImageView img_status;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_detail = itemView.findViewById(R.id.txt_detail);
            txt_sname = itemView.findViewById(R.id.txt_sname);
            txt_datetime = itemView.findViewById(R.id.txt_datetime);
            txt_orderid = itemView.findViewById(R.id.txt_orderid);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_address = itemView.findViewById(R.id.txt_address);
            img_service = itemView.findViewById(R.id.img_service);
            img_status = itemView.findViewById(R.id.img_status);
        }
    }
}