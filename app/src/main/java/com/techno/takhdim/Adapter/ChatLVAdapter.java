package com.techno.takhdim.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.techno.takhdim.App.MySharedPref;
import com.techno.takhdim.Holder.DataHolder;
import com.techno.takhdim.R;
import com.techno.takhdim.Result.ChatResult;

import java.util.List;


/**
 * Created by Techno122 on 8/17/2017.
 */

public class ChatLVAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    List<ChatResult> results;
    private Activity activity;
    private String id = "", user_id = "";

    public ChatLVAdapter(Activity activity, List<ChatResult> results) {
        this.activity = activity;
        this.results = results;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return results.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (convertView == null)
            itemView = inflater.inflate(R.layout.item_chat, null);
        LinearLayout receiverLayout = itemView.findViewById(R.id.receiver_chat_box);
        TextView receiverText = itemView.findViewById(R.id.receiver_chat_message);
        TextView receiveTimeText = itemView.findViewById(R.id.receive_time);
        ImageView receiverPic = itemView.findViewById(R.id.receiver_profile_pic);

        LinearLayout senderLayout = itemView.findViewById(R.id.sender_chat_box);
        TextView senderText = itemView.findViewById(R.id.sender_chat_message);
        TextView sendTimeText = itemView.findViewById(R.id.send_time);
        ImageView senderPic = itemView.findViewById(R.id.sender_profile_pic);
        final ChatResult chatResponse = this.results.get(position);
        try {
            user_id = MySharedPref.getData(activity, "user_id", null);
            if (chatResponse.getSenderId().equals(DataHolder.getSender())) {
                senderLayout.setVisibility(View.VISIBLE);
                receiverLayout.setVisibility(View.GONE);
                senderText.setText(chatResponse.getChatMessage());
                sendTimeText.setText(chatResponse.getDate());
                try {
                    Picasso.get().load(chatResponse.getSenderDetail().getImage()).error(R.drawable.user_new).into(senderPic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                receiverLayout.setVisibility(View.VISIBLE);
                senderLayout.setVisibility(View.GONE);
                receiverText.setText(chatResponse.getChatMessage());
                receiveTimeText.setText(chatResponse.getDate());
                try {
                    Picasso.get().load(chatResponse.getSenderDetail().getImage()).error(R.drawable.user_new).into(receiverPic);
                } catch (Exception e) {
                    //receiverPic.setImageResource(R.drawable.example_default_chat);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemView;
    }

}