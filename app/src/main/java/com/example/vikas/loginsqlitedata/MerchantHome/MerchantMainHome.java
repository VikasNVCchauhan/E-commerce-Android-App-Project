package com.example.vikas.loginsqlitedata.MerchantHome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.MainActivity;
import com.example.vikas.loginsqlitedata.MerchantAnalytics.MerchantAnalyticaFragment;

import com.example.vikas.loginsqlitedata.MerchantPayments.MerchantPaymengtsFragment;
import com.example.vikas.loginsqlitedata.Merchantprofile.MerchantProfileFragment;
import com.example.vikas.loginsqlitedata.R;


public class MerchantMainHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbarMerchant;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment Selectedfragment = null;
    private String phoneKey;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_merchant_home_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_merchant);
        navigationView = findViewById(R.id.navigation_view_merchant);


        phoneKey=getIntent().getStringExtra("PHONE");



        toolbarMerchant = findViewById(R.id.toolbar_merchant);
        setSupportActionBar(toolbarMerchant);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarMerchant.setTitle("Merchant Home");

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbarMerchant,R.string.open,R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_merchant, new MerchantHomeMainContainTabsFragment(phoneKey)).commit();
            navigationView.setCheckedItem(R.id.home_menu_merchant);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_merchant) {
            Intent intent = new Intent(MerchantMainHome.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.setting_merchant) {
            toolbarMerchant.setTitle("Settings");
//            Selectedfragment = new HomeFragment();
        }  else if (id == R.id.user_profile_menu_merchant) {
            toolbarMerchant.setTitle("Profile");
            Selectedfragment = new MerchantProfileFragment();
        } else if (id == R.id.analytics_merchant) {
            toolbarMerchant.setTitle("Analytics");
            Selectedfragment = new MerchantAnalyticaFragment();
        } else if (id == R.id.payment_merchant) {
            toolbarMerchant.setTitle("Payments");
            Selectedfragment = new MerchantPaymengtsFragment();
        } else if (id == R.id.home_menu_merchant) {
            toolbarMerchant.setTitle("Home");
            Selectedfragment = new MerchantHomeMainContainTabsFragment(phoneKey);
        }
        //-------------Here we are replacing older fragment with selected fragment -------------//
        int commit = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_merchant, Selectedfragment).commit();
        //----------Here we are closing our navigation drawer------------//
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
