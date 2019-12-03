package com.techno.takhdim.NewDesign.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import com.techno.takhdim.Fragment.NewOrderFragment;
import com.techno.takhdim.NewDesign.Adapters.Pager;
import com.techno.takhdim.NewDesign.Models.ModelPager;
import com.techno.takhdim.R;
import com.techno.takhdim.Result.OrderResult;
import com.techno.takhdim.databinding.FragmentDialogRequestDetailsBinding;

import java.util.ArrayList;

public class FragmentDialogRequestDetails extends AppCompatActivity {
    private FragmentDialogRequestDetailsBinding binding;
    private ArrayList<ModelPager> arrayList=new ArrayList<>();
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =DataBindingUtil.setContentView(this,R.layout.fragment_dialog_request_details);
        ID=getIntent().getExtras().getString("id");
        BindView();
    }

    private void BindView() {
        arrayList.add(new ModelPager("Details",new FragmentRequestDetails(ID)));
        arrayList.add(new ModelPager("Message",new FragmentRequestMessage(ID)));
        binding.viewpager.setAdapter(new Pager(getSupportFragmentManager(),arrayList));
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.imgBack.setOnClickListener(v->finish());
    }

}
