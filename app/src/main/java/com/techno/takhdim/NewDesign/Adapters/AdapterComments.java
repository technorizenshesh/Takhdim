package com.techno.takhdim.NewDesign.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techno.takhdim.NewDesign.Models.ModelComment;
import com.techno.takhdim.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.techno.takhdim.App.MySharedPref.getData;

public class AdapterComments extends BaseAdapter {
    private String user_id;
    Context mContext;
    ArrayList<ModelComment>data;

    public AdapterComments(Context mContext, ArrayList<ModelComment> data) {
        this.mContext = mContext;
        this.data = data;
        user_id = getData(mContext, "user_id", "");
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(user_id.equals(data.get(position).getUserID())?R.layout.layout_comment_right:R.layout.layout_comment_left,parent,false);
        CircleImageView user_image=convertView.findViewById(R.id.user_image);
        if (!data.get(position).getUserImage().isEmpty()){
            Picasso.get().load(data.get(position).getUserImage()).placeholder(R.drawable.user_new).into(user_image);
        }
        TextView tv_user_name=convertView.findViewById(R.id.tv_user_name);
        TextView tv_message=convertView.findViewById(R.id.tv_message);
        TextView tv_date=convertView.findViewById(R.id.tv_date);
        tv_user_name.setText(data.get(position).getUserName());
        tv_message.setText(data.get(position).getComment());
        tv_date.setText(data.get(position).getDate());
        return convertView;
    }
}
