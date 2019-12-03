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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryListPartsAdapter extends RecyclerView.Adapter<CategoryListPartsAdapter.ViewHolder> {
    private Activity activity;
    private List<CategoryListResult> result;

    public CategoryListPartsAdapter(Activity activity, List<CategoryListResult> result) {
        this.activity = activity;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_name.setText(result.get(position).getName());
        Picasso.get().load(result.get(position).getImagepath()).error(R.drawable.plumbing).into(holder.img_cat);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView img_cat;
        TextView txt_name;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            img_cat = itemView.findViewById(R.id.img_cat);
            txt_name = itemView.findViewById(R.id.txt_name);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(activity, PartCatListActivity.class);
            i.putExtra("cat_id", "" + result.get(getAdapterPosition()).getId());
            activity.startActivity(i);
            Animatoo.animateShrink(activity);
            activity.finish();
        }
    }
}