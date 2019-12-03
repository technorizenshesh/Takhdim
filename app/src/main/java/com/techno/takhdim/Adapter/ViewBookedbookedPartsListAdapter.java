package com.techno.takhdim.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;
import com.techno.takhdim.Activity.PartCatListActivity;
import com.techno.takhdim.R;
import com.techno.takhdim.Result.CategoryListResult;
import com.techno.takhdim.Result.PartsBookingResult;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewBookedbookedPartsListAdapter extends RecyclerView.Adapter<ViewBookedbookedPartsListAdapter.ViewHolder> {
    private Activity activity;
    private List<PartsBookingResult> result;

    public ViewBookedbookedPartsListAdapter(Activity activity, List<PartsBookingResult> result) {
        this.activity = activity;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_booking_part_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txt_name.setText(result.get(position).getPartDetails().getCompanyName());
        holder.txt_price.setText("Price : " + result.get(position).getPartDetails().getPrice() + " USD");
        holder.txt_warrenty.setText("Warrenty : " + result.get(position).getPartDetails().getWarranty() + " Months");
        holder.txt_detl.setText("Details : " + result.get(position).getPartDetails().getDescription());
        holder.txt_qunt.setText("Quantity : " + result.get(position).getQuantity());

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_name, txt_price, txt_warrenty, txt_detl,txt_qunt;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_warrenty = itemView.findViewById(R.id.txt_warrenty);
            txt_detl = itemView.findViewById(R.id.txt_detl);
            txt_qunt = itemView.findViewById(R.id.txt_qunt);

        }

        @Override
        public void onClick(View view) {

        }
    }
}