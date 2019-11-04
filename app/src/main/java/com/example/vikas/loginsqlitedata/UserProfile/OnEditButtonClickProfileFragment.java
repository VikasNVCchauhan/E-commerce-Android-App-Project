package com.example.vikas.loginsqlitedata.UserProfile;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.YourOrders.OrderMainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class OnEditButtonClickProfileFragment extends Fragment implements View.OnClickListener {

    private SQLiteDatabase sqLiteDatabase;
    private String sQliteDataBaseAddressHouseNumber="", sQliteDataBaseAddresslandMark, sQliteDataBaseAddressStateName, sQliteDataBaseAddressCityName, sQliteDataBaseAddressAreaPinCode, sQliteDataBaseAddressPhoneNumber;
    private String emailAddresspass, phone, passwordPass, userCompleteName;
    private String imageURLFromSqliteDatabase = "";

    private TextView editProfileData, editAddressData, editProfileImage, viewAllOrdeers;

    private TextView textViewCardViewProfileUserNmae, textViewCardViewProfileUserEmail, textViewCardViewProfileUserPhone, textViewCardViewProfileUserPassword;

    private TextView textViewCardViewAddressHouseNumberAndHousename, textViewCardViewAddressLandMark, textViewCardViewAddressState, textViewCardViewAddressCity, textViewCardViewAddressAreaPinCode, textViewCardViewAddressPhone;

    private TextView textViewCardViewProfileImageUserNmae, textViewCardViewProfileImageUserEmail;

    private CircleImageView circleImageView;

    private TextView textViewCardViewUserOrderProductCompanyName;

    private AppCompatActivity appCompatActivity;
    private Toolbar toolbar;

    private String userPhoneKey;

    //-------------------constructors----------------------//
    @SuppressLint("ValidFragment")
    public OnEditButtonClickProfileFragment(String userPhoneKey) {
        this.userPhoneKey = userPhoneKey;
        // Required empty public constructor
    }

    public OnEditButtonClickProfileFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_edit_button_click_profile, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();

        toolbar = appCompatActivity.findViewById(R.id.action_toolbar);
        toolbar.setTitle("Edit Profile");

        getIdForDataField(view);

        sqLiteDatabase = getActivity().openOrCreateDatabase("galaxyCart", android.content.Context.MODE_PRIVATE, null);
        getAllDataFromSqliteDatabaseFireBase();

        setDataToTextViews();

        editAddressData.setOnClickListener(this);
        editProfileImage.setOnClickListener(this);
        editProfileImage.setOnClickListener(this);
        viewAllOrdeers.setOnClickListener(this);

        return view;
    }

    private void getAllDataFromSqliteDatabaseFireBase() {

        Cursor crUserProfileData = sqLiteDatabase.rawQuery("Select * from UserData where Phone='" + userPhoneKey + "'", null);
        if (crUserProfileData.getCount() == 0) {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            crUserProfileData.moveToFirst();
            userCompleteName = crUserProfileData.getString(0);
            emailAddresspass = crUserProfileData.getString(1);
            phone = crUserProfileData.getString(2);
            passwordPass = crUserProfileData.getString(3);
        }

        Cursor crUserAddressdata = sqLiteDatabase.rawQuery("Select * from UserAddressDataSQL where PhoneNumberKey='" + userPhoneKey + "'", null);
        if (crUserAddressdata.getCount() == 0) {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
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

    private void setDataToTextViews() {
        if(!sQliteDataBaseAddressHouseNumber.equals("")) {

            textViewCardViewAddressHouseNumberAndHousename.setText(sQliteDataBaseAddressHouseNumber);
            textViewCardViewAddressLandMark.setText(sQliteDataBaseAddresslandMark);
            textViewCardViewAddressState.setText(sQliteDataBaseAddressStateName);
            textViewCardViewAddressCity.setText(sQliteDataBaseAddressCityName);
            textViewCardViewAddressAreaPinCode.setText(sQliteDataBaseAddressAreaPinCode);
            textViewCardViewAddressPhone.setText(sQliteDataBaseAddressPhoneNumber);
        }

        textViewCardViewProfileUserNmae.setText(userCompleteName);
        textViewCardViewProfileUserEmail.setText(emailAddresspass);
        textViewCardViewProfileUserPhone.setText(phone);
        textViewCardViewProfileUserPassword.setText(passwordPass);

        textViewCardViewProfileImageUserNmae.setText(userCompleteName);
        textViewCardViewProfileImageUserEmail.setText(emailAddresspass);
        if (!imageURLFromSqliteDatabase.equals("")) {
            final ArrayList<String> profileImage = new ArrayList<>();
            profileImage.add(imageURLFromSqliteDatabase);

            Picasso.with(getContext())
                    .load(profileImage.get(0))
                    .placeholder(R.mipmap.women_horizontal_recycler_view)
                    .into(circleImageView);
        }
    }

    private void getIdForDataField(View view) {


        //---------------getting id's for edit text views ---------------//
        editProfileData = view.findViewById(R.id.text_view_user_profile_edit);
        editAddressData = view.findViewById(R.id.text_view_user_address_edit);
        editProfileImage = view.findViewById(R.id.text_view_user_image_edit);
        viewAllOrdeers = view.findViewById(R.id.text_view_user_view_all_orders);

        //-------------getting id's for address card view all edit text-------------//
        textViewCardViewAddressHouseNumberAndHousename = view.findViewById(R.id.text_view_user_House_address_or_house_no);
        textViewCardViewAddressLandMark = view.findViewById(R.id.text_view_user_address_landmark);
        textViewCardViewAddressState = view.findViewById(R.id.text_view_address_state);
        textViewCardViewAddressCity = view.findViewById(R.id.text_view_address_city);
        textViewCardViewAddressAreaPinCode = view.findViewById(R.id.text_view_address_area_pin_code);
        textViewCardViewAddressPhone = view.findViewById(R.id.text_view_address_phone);


        textViewCardViewProfileUserNmae = view.findViewById(R.id.text_view_user_name);
        textViewCardViewProfileUserEmail = view.findViewById(R.id.text_view_user_email_address);
        textViewCardViewProfileUserPhone = view.findViewById(R.id.text_view_user_phone);
        textViewCardViewProfileUserPassword = view.findViewById(R.id.text_view_user_password);

        circleImageView = view.findViewById(R.id.circuler_image_view_profile_image);
        textViewCardViewProfileImageUserNmae = view.findViewById(R.id.text_view_image_edit_name);
        textViewCardViewProfileImageUserEmail = view.findViewById(R.id.text_view_image_edit_email_addrrsss);

    }

    @Override
    public void onClick(View v) {
        if (v == editAddressData) {
            OnClickOnEditAddressInMainProfileFragment onClickOnEditAddressInMainProfileFragment = new OnClickOnEditAddressInMainProfileFragment(userPhoneKey);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, onClickOnEditAddressInMainProfileFragment).addToBackStack(null).commit();
        } else if (v == editProfileData) {

        } else if (v == editProfileImage) {
            OnClickOnEditImageInMainPrifilefragmentFragment onClickOnEditImageInMainPrifilefragmentFragment = new OnClickOnEditImageInMainPrifilefragmentFragment(phone);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, onClickOnEditImageInMainPrifilefragmentFragment).addToBackStack(null).commit();
        } else if (v == viewAllOrdeers) {
            OrderMainActivity orderMainActivity = new OrderMainActivity();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, orderMainActivity).addToBackStack(null).commit();
        }
    }
}
