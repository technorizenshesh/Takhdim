package com.techno.takhdim.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.techno.takhdim.Adapter.GymListAdapter;
import com.techno.takhdim.Adapter.OrderOfferAdapter;
import com.techno.takhdim.R;

public class NearGymActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img_back;
    private RecyclerView RV_All;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_gym);

        findId();
        img_back.setOnClickListener(this);

        RV_All.setHasFixedSize(true);
        GymListAdapter adapter = new GymListAdapter(NearGymActivity.this);
        RV_All.setLayoutManager(new LinearLayoutManager(NearGymActivity.this, LinearLayoutManager.VERTICAL, false));
        RV_All.setAdapter(adapter);
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        RV_All = findViewById(R.id.RV_All);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }

}
