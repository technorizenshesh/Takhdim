package com.techno.takhdim.NewDesign.Adapters;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.techno.takhdim.Adapter.ServiceListAdapter;
import com.techno.takhdim.Fragment.GeneralServiceListFragment;
import com.techno.takhdim.R;
import com.techno.takhdim.Response.ServiceListResponse;
import com.techno.takhdim.Result.CategoryListResult;
import com.techno.takhdim.Result.ServiceListResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategory extends BaseAdapter {
    Context mContext;
    List<CategoryListResult>data;

    public AdapterCategory(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<CategoryListResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
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
        convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_category,parent,false);
        TextView tv_title=convertView.findViewById(R.id.tv_title);
        RecyclerView ry_service=convertView.findViewById(R.id.ry_service);
        tv_title.setText(data.get(position).getName());
      ServiceListAdapter adapter = new ServiceListAdapter(mContext,data.get(position).getServices());
        ry_service.setAdapter(adapter);

        return convertView;
    }
}
