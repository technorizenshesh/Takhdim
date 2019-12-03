package com.techno.takhdim.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;
import com.techno.takhdim.Activity.AddRequestDeatilsActivity;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.R;
import com.techno.takhdim.Result.ServiceListResult;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {
    private Context activity;
    private List<ServiceListResult> result;

    public ServiceListAdapter(Context activity, List<ServiceListResult> result) {
        this.activity = activity;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_name.setText(result.get(position).getName());
        Picasso.get().load("http://www.takhdim-admin.com/uploads/images/"+result.get(position).getImage()).placeholder(R.drawable.plumbing).into(holder.img_cat);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_cat;
        TextView txt_name;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            img_cat = itemView.findViewById(R.id.img_cat);
            txt_name = itemView.findViewById(R.id.txt_name);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(activity, AddRequestDeatilsActivity.class);
            i.putExtra("id", "" + result.get(getAdapterPosition()).getId());
            i.putExtra("name", "" + result.get(getAdapterPosition()).getName());
            activity.startActivity(i);
            Animatoo.animateShrink(activity);
        }
    }
}