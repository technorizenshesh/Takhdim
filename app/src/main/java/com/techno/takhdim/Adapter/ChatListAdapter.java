package com.techno.takhdim.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.techno.takhdim.Activity.ChatActivity;
import com.techno.takhdim.Activity.HomeActivity;
import com.techno.takhdim.R;
import com.techno.takhdim.Result.ChatListResult;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.techno.takhdim.App.MySharedPref.getData;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> implements View.OnClickListener {
    private Activity activity;
    private View view;
    private List<ChatListResult> result;
    private String user_id, request_id;

    public ChatListAdapter(Activity activity, List<ChatListResult> result) {
        this.activity = activity;
        this.result = result;
        user_id = getData(activity, "user_id", "");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txt_name.setText(result.get(position).getUsername());
        holder.txt_msg.setText(result.get(position).getLastMessage());
//
//        Glide.with(activity)
//                .load(result.get(position).getImage())
//                .into(holder.img_user);
//

        Picasso.get().load(result.get(position).getImage()).error(R.drawable.user_new).into(holder.img_user);

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView img_user;
        TextView txt_name, txt_msg;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            img_user = itemView.findViewById(R.id.img_user);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_msg = itemView.findViewById(R.id.txt_msg);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(activity, ChatActivity.class);
            i.putExtra("reciver_id", "" + result.get(getAdapterPosition()).getId());
            activity.startActivity(i);
        }
    }

}
