package com.techno.takhdim.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techno.takhdim.R;

import static com.techno.takhdim.App.MySharedPref.saveData;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private RelativeLayout lay_logout, lay_contact, lay_about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findId();
        img_back.setOnClickListener(this);
        lay_logout.setOnClickListener(this);
        lay_contact.setOnClickListener(this);
        lay_about.setOnClickListener(this);
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        lay_logout = findViewById(R.id.lay_logout);
        lay_contact = findViewById(R.id.lay_contact);
        lay_about = findViewById(R.id.lay_about);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        } else if (v == lay_logout) {
            saveData(SettingActivity.this, "ldata", null);
            saveData(SettingActivity.this, "user_id", null);
            startActivity(new Intent(SettingActivity.this, WelcomeActivity.class));
            Animatoo.animateShrink(SettingActivity.this);
            finish();
        } else if (v == lay_contact) {
            startActivity(new Intent(SettingActivity.this, PrivacypolicyActivity.class));
            Animatoo.animateShrink(SettingActivity.this);
        } else if (v == lay_about) {
            startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
            Animatoo.animateShrink(SettingActivity.this);
        }
    }
}