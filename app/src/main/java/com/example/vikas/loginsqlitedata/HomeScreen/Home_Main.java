package com.example.vikas.loginsqlitedata.HomeScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vikas.loginsqlitedata.CartBag.CartBagMainFragment;
import com.example.vikas.loginsqlitedata.FashionProduct.FashionProductsFragment;
import com.example.vikas.loginsqlitedata.Help.HelpMainFragment;
import com.example.vikas.loginsqlitedata.MainActivity;
import com.example.vikas.loginsqlitedata.MenProducts.MenProducsFragment;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.TermAndConditions.TermAndConditinFragment;
import com.example.vikas.loginsqlitedata.UserProfile.UserProfileMainFragment;
import com.example.vikas.loginsqlitedata.WishList.WishListMainFragment;
import com.example.vikas.loginsqlitedata.WomanProducts.WomansProductMainFragment;
import com.example.vikas.loginsqlitedata.YourOrders.OrderMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener{
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private SQLiteDatabase sqLiteDatabase;
    //---------variable navigation drawer--------------//
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView name, email;
    private CircleImageView circleImageViewHome;
    private String imageURLFromSqliteDatabase = "";

    //---------variable navigation drawer--------------//
    private Fragment Selectedfragment = null;
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private Menu menu;
    //---------variable search BAr--------------//
    private EditText searchBar;

    //---------variable decleretion for data coming from login activity--------------//
    private String userCompleteName, emailAddresspass, phone, completeName;

    @SuppressLint("RestrictedApi")

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_main_layout);

        searchBar = findViewById(R.id.searchBarMain);
        //---------variable decleration data comming from login activity--------------//

        phone = getIntent().getStringExtra("PHONE");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setVisibility(View.VISIBLE);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        //---------toolbar--------------//
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        toolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(toolbar);

        //---------Setting working and visibility on toolbar--------------//
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Home ");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //-----------here we are setting checked a data in navigation drawer and also her fragment is set home fragment ------------//
        actionBarDrawerToggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(phone)).addToBackStack(null).commit();
            navigationView.setCheckedItem(R.id.home_menu);
        }

        //---------finding id of text view of navigation header(these all decleration must be at end )--------------//
        View header = navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.navigation_text_name);
        email = (TextView) header.findViewById(R.id.navigation_text_email);
        circleImageViewHome = header.findViewById(R.id.circulerImageView_navigationDarawe);

        sqLiteDatabase = openOrCreateDatabase("galaxyCart", Context.MODE_PRIVATE, null);
        getAllDataFromSqliteDatabaseFireBase();

        if (!imageURLFromSqliteDatabase.equals("")) {
            final ArrayList<String> profileImageHome = new ArrayList<>();
            profileImageHome.add(imageURLFromSqliteDatabase);

            Glide.with(this)
                    .load(imageURLFromSqliteDatabase)
                    .into(circleImageViewHome);

            Picasso.with(Home_Main.this)
                    .load(profileImageHome.get(0))
                    .placeholder(R.mipmap.women_horizontal_recycler_view)
                    .into(circleImageViewHome);
        }
        //-------------setting the data on navigatin drawer profile of use-------------//
        name.setText(userCompleteName);
        email.setText(emailAddresspass);

        actionBarDrawerToggle.syncState();
    }

    //---------------Navigation Drawer & bottom tabs item are here -----------------//
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.logout_menu) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(Home_Main.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "LogOut Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.bottom_home || id == R.id.home_menu) {
            toolbar.setTitle("Home");
            Selectedfragment = new HomeFragment(phone);
        } else if (id == R.id.address_menu) {
            toolbar.setTitle("Address");
            Selectedfragment = new UserProfileMainFragment(phone);
        } else if (id == R.id.user_profile_menu || id == R.id.profile_bottom) {
            toolbar.setTitle("Profile");
            Selectedfragment = new UserProfileMainFragment(phone);
        } else if (id == R.id.wishList_menu || id == R.id.wish_list_bottom_menu) {
            toolbar.setTitle("Wish List");
            Selectedfragment = new WishListMainFragment(phone);
        } else if (id == R.id.userCart_menu || id == R.id.user_cart_bottom_menu) {
            toolbar.setTitle("Your Cart");
            Selectedfragment = new CartBagMainFragment(phone);
        } else if (id == R.id.order_menu || id == R.id.user_order_bottom_menu) {
            toolbar.setTitle("Your Orders");
            Selectedfragment = new OrderMainActivity(phone);
        } else if (id == R.id.fashionProduct_menu) {
            toolbar.setTitle("Fashion Products");
            Selectedfragment = new FashionProductsFragment(phone);
        } else if (id == R.id.woman_products_menu) {
            toolbar.setTitle("Woman Products");
            Selectedfragment = new WomansProductMainFragment(phone);
        } else if (id == R.id.men_products_menu) {
            toolbar.setTitle("Men Products");
            Selectedfragment = new MenProducsFragment(phone);
        } else if (id == R.id.help_menu) {
            toolbar.setTitle("Galaxy Cart Help");
            Selectedfragment = new HelpMainFragment(phone);
        } else if (id == R.id.terms_conditions_menu) {
            toolbar.setTitle("Terms & Conditions");
            Selectedfragment = new TermAndConditinFragment(phone);
        }
        //-------------Here we are replacing older fragment with selected fragment -------------//
        int commit = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Selectedfragment).addToBackStack(null).commit();
        //----------Here we are closing our navigation drawer------------//
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getAllDataFromSqliteDatabaseFireBase() {

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from UserProfilePicture where Phone='" + phone + "'", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            cursor.getBlob(0);
            imageURLFromSqliteDatabase = cursor.getString(0);
        }

        Cursor crUserProfileData = sqLiteDatabase.rawQuery("Select * from UserData where Phone='" + phone + "'", null);
        if (crUserProfileData.getCount() == 0) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            crUserProfileData.moveToFirst();
            userCompleteName = crUserProfileData.getString(0);
            emailAddresspass = crUserProfileData.getString(1);
        }

    }
}
