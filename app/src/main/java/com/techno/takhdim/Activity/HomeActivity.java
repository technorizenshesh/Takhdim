package com.techno.takhdim.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.Fragment.GeneralServiceListFragment;
import com.techno.takhdim.NewDesign.FragmentHome;
import com.techno.takhdim.NewDesign.Fragments.FragmentProfile;
import com.techno.takhdim.NewDesign.Fragments.FragmentRequest;
import com.techno.takhdim.R;
import static com.techno.takhdim.App.MySharedPref.saveData;

public class HomeActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_home);
        FindViews();
        FragTrans(new FragmentHome());
    }

    private void FindViews() {
        BottomNavigationView navigationView=findViewById(R.id.nav_bottom);
        navigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    public void onBackPressed() {
      if (getSupportFragmentManager().getBackStackEntryCount()>1){
          getSupportFragmentManager().popBackStack();
      }else {
          System.exit(0);
      }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            FragTrans(new FragmentHome());
        }else if (id == R.id.nav_request) {
            FragTrans(new FragmentRequest());
        }else if (id == R.id.nav_search) {
            startActivity(new Intent(HomeActivity.this, SparePartsActivity.class));
        } else if (id == R.id.nav_profile) {
            FragTrans(new FragmentProfile());
        }else if (id == R.id.nav_parts) {
            startActivity(new Intent(HomeActivity.this, SparePartsActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        }else if (id == R.id.nav_partsbooking) {
            startActivity(new Intent(HomeActivity.this, ViewbookedsparepartsActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(HomeActivity.this, HelpActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(HomeActivity.this, SettingActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        } else if (id == R.id.nav_logout) {
            saveData(getApplicationContext(), "ldata", null);
            saveData(getApplicationContext(), "user_id", null);
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
            finish();
        } else if (id == R.id.nav_chat) {
            startActivity(new Intent(HomeActivity.this, MessagesActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        }

        return true;
    }


    public void FragTrans(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
