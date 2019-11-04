package com.example.vikas.loginsqlitedata.UserProfile;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.CartBag.CartBagMainFragment;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.WishList.WishListMainFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class UserProfileMainFragment extends Fragment implements View.OnClickListener {


    private Fragment Selectedfragment = null;

    private BottomNavigationView bottomNavigationView;

    private SQLiteDatabase sqLiteDatabase;

    private String sQliteDataBaseAddressHouseNumber = "", sQliteDataBaseAddresslandMark, sQliteDataBaseAddressStateName, sQliteDataBaseAddressCityName, sQliteDataBaseAddressAreaPinCode, sQliteDataBaseAddressPhoneNumber;

    private AppCompatActivity appCompatActivity;
    private String emailAddresspass, phone, passwordPass, userCompleteName, checkPhone;

    private String imageURLFromSqliteDatabase = "";
    private ListView listView;
    private Button editProfile;

    private int[] images = {R.mipmap.men, R.mipmap.email, R.mipmap.mobile, R.mipmap.lock};
    private String[] ProfileDataObject;
    private String[] ProfileDataSubject = {"Name", "Email Address", "Phone Number", "Password"};


    //-----------------variables for top card view---------------------//
    private CircleImageView circleImageView;
    private TextView cardViewEditImage, cardViewUserName, cardViewUserEmail;

    //------------------variable for address card view --------------------//
    private TextView AddressCardViewHomeAddressAndHouseNumber, AddressCardViewLandMark, AddressCardViewState, AddressCardViewAreaPinCode, AddressCardViewMobileNumber, AddressCardViewEditAddress, AddressCardViewCity;

    @SuppressLint("ValidFragment")
    public UserProfileMainFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public UserProfileMainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile_main, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();
        //to open database in fragment this line is assential
        bottomNavigationView = appCompatActivity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);

        sqLiteDatabase = getActivity().openOrCreateDatabase("galaxyCart", android.content.Context.MODE_PRIVATE, null);

        getAllDataFromSqliteDatabaseFireBase();

        ProfileDataObject = new String[]{userCompleteName, emailAddresspass, phone, passwordPass};

        setIdToAllVeriables(view);

        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), images, ProfileDataObject, ProfileDataSubject);
        listView.setAdapter(listViewAdapter);

        cardViewEditImage.setOnClickListener(this);
        AddressCardViewEditAddress.setOnClickListener(this);
        editProfile.setOnClickListener(this);
        return view;
    }

    private void getAllDataFromSqliteDatabaseFireBase() {

        Cursor crUserProfileData = sqLiteDatabase.rawQuery("Select * from UserData where Phone='" + checkPhone + "'", null);
        if (crUserProfileData.getCount() == 0) {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            crUserProfileData.moveToFirst();
            userCompleteName = crUserProfileData.getString(0);
            emailAddresspass = crUserProfileData.getString(1);
            phone = crUserProfileData.getString(2);
            passwordPass = crUserProfileData.getString(3);
        }

        Cursor crUserAddressdata = sqLiteDatabase.rawQuery("Select * from UserAddressDataSQL where PhoneNumberKey='" + checkPhone + "'", null);
        if (crUserAddressdata.getCount() == 0) {
            Toast.makeText(getContext(), "No Address data found", Toast.LENGTH_SHORT).show();
        } else {
            crUserAddressdata.moveToFirst();
            sQliteDataBaseAddressHouseNumber = crUserAddressdata.getString(0);
            sQliteDataBaseAddresslandMark = crUserAddressdata.getString(1);
            sQliteDataBaseAddressStateName = crUserAddressdata.getString(2);
            sQliteDataBaseAddressCityName = crUserAddressdata.getString(3);
            sQliteDataBaseAddressAreaPinCode = crUserAddressdata.getString(4);
            sQliteDataBaseAddressPhoneNumber = crUserAddressdata.getString(5);

        }


        Cursor cursor = sqLiteDatabase.rawQuery("Select * from UserProfilePicture where Phone='" + phone + "'", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            cursor.getBlob(0);
            imageURLFromSqliteDatabase = cursor.getString(0);
        }

    }

    private void setIdToAllVeriables(View view) {
        circleImageView = view.findViewById(R.id.circulerImageView_user_profile);
        cardViewEditImage = view.findViewById(R.id.add_image_text_view);
        cardViewUserName = view.findViewById(R.id.user_name_user_profile);
        cardViewUserEmail = view.findViewById(R.id.email_address_user_profile);

        AddressCardViewHomeAddressAndHouseNumber = view.findViewById(R.id.address_line1);
        AddressCardViewLandMark = view.findViewById(R.id.address_line2);
        AddressCardViewState = view.findViewById(R.id.address_line3);
        AddressCardViewCity = view.findViewById(R.id.address_line4);
        AddressCardViewAreaPinCode = view.findViewById(R.id.address_line5);
        AddressCardViewMobileNumber = view.findViewById(R.id.address_line6);
        AddressCardViewEditAddress = view.findViewById(R.id.edit_address_card_view);

        editProfile = view.findViewById(R.id.edit_profile);
        listView = view.findViewById(R.id.list_view_user_profile);

        setDataInTopCardView();
    }

    private void setDataInTopCardView() {
        cardViewUserName.setText(userCompleteName);
        cardViewUserEmail.setText(emailAddresspass);

        AddressCardViewMobileNumber.setText(phone);

        if (!sQliteDataBaseAddressHouseNumber.equals("")) {

            AddressCardViewHomeAddressAndHouseNumber.setText(sQliteDataBaseAddressHouseNumber);
            AddressCardViewLandMark.setText(sQliteDataBaseAddresslandMark);
            AddressCardViewState.setText(sQliteDataBaseAddressStateName);
            AddressCardViewCity.setText(sQliteDataBaseAddressCityName);
            AddressCardViewAreaPinCode.setText(sQliteDataBaseAddressAreaPinCode);
            AddressCardViewMobileNumber.setText(sQliteDataBaseAddressPhoneNumber);
        }

        if (!imageURLFromSqliteDatabase.equals("")) {
            final ArrayList<String> profileImage = new ArrayList<>();
            profileImage.add(imageURLFromSqliteDatabase);

            Picasso.with(getContext())
                    .load(profileImage.get(0))
                    .placeholder(R.mipmap.women_horizontal_recycler_view)
                    .into(circleImageView);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == cardViewEditImage) {
            OnClickOnEditImageInMainPrifilefragmentFragment onClickOnEditImageInMainPrifilefragmentFragment = new OnClickOnEditImageInMainPrifilefragmentFragment(checkPhone);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, onClickOnEditImageInMainPrifilefragmentFragment).addToBackStack(null).commit();
        } else if (v == AddressCardViewEditAddress) {
            OnClickOnEditAddressInMainProfileFragment onClickOnEditAddressInMainProfileFragment = new OnClickOnEditAddressInMainProfileFragment(phone);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, onClickOnEditAddressInMainProfileFragment).addToBackStack(null).commit();
        } else if (v == editProfile) {
            OnEditButtonClickProfileFragment onEditButtonClickProfileFragment = new OnEditButtonClickProfileFragment(phone);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, onEditButtonClickProfileFragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_search_wish_list_cart_icons, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_search) {
            Toast.makeText(getContext(), "We are still working on it", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.toolbar_wish_list) {
            Selectedfragment = new WishListMainFragment(checkPhone);
        } else if (id == R.id.toolbar_cart) {
            Selectedfragment = new CartBagMainFragment(checkPhone);
        }
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Selectedfragment).commit();
        return true;
    }
}
