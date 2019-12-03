package com.techno.takhdim.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techno.takhdim.Activity.GymDetailsActivity;
import com.techno.takhdim.Activity.SuccessActivity;
import com.techno.takhdim.R;

public class GymListAdapter extends RecyclerView.Adapter<GymListAdapter.ViewHolder> {
    private Activity activity;

    public GymListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gym_list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, GymDetailsActivity.class));
                Animatoo.animateShrink(activity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 25;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_view;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_view = itemView.findViewById(R.id.txt_view);
        }

        @Override
        public void onClick(View view) {

        }
    }
}