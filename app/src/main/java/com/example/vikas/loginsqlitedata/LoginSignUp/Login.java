package com.example.vikas.loginsqlitedata.LoginSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.HomeScreen.Home_Main;
import com.example.vikas.loginsqlitedata.MainActivity;
import com.example.vikas.loginsqlitedata.MerchantHome.MerchantMainHome;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.SignUpOptions;
import com.example.vikas.loginsqlitedata.UserProfile.UserFireBaseAddress;
import com.example.vikas.loginsqlitedata.UserProfile.UserFirebaseProfileImage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private SQLiteDatabase sqLiteDatabase;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, mDatabaseReference;
    private DatabaseReference databaseReferenceUserAddress, databaseReferenceUserProfileImage;
    private SharedPreferences sharedPreferences;
    private static final String SHARES_DATA_BASE_NAME = "GlaxyCart";
    private CheckBox remberPasswordCheck;
    private Button login;
    private EditText username, password;
    private ImageView facebook, google;
    private TextView forgottenPassword, signUp, googleText, facebookText;
    private String sQliteDataBaseAddressHouseNumber, sQliteDataBaseAddresslandMark, sQliteDataBaseAddressStateName, sQliteDataBaseAddressCityName, sQliteDataBaseAddressAreaPinCode, sQliteDataBaseAddressPhoneNumber = "", sQliteDataBaseAddressPhoneNumberKey = "",sQliteDataBaseAddressCHECKING_AVELABILITY;
    private String ProfileImageURLForSqlite, sqliteCheckedPhoneNumberUserProfilePicture = "";
    private String account_type;

    //---------variable decleratin for passing data to home navigation darawer------------//
    private String firstNamepass, lastNamepass, emailAddresspass, phone, passwordPass;
    private String sqlitefirstNamepass, sqlitelastNamepass, sqliteemailAddresspass, sqlitephone, sqlitepasswordPass, sqliteCheckedPhoneNumberUserProfile = "", sqlitecompleteName;
    private String houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, sqliteCheckedPhoneNumberUserAddress = "",checkingAvailbility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        sharedPreferences = getSharedPreferences(SHARES_DATA_BASE_NAME, MODE_PRIVATE);
        //---------------Set The Id of all variables------------------//
        login = (Button) findViewById(R.id.login_loginActivity);

        username = (EditText) findViewById(R.id.editText_username);
        password = (EditText) findViewById(R.id.editText_pasword);

        remberPasswordCheck = (CheckBox) findViewById(R.id.checkBox_remember);

        facebook = (ImageView) findViewById(R.id.facebook);
        google = (ImageView) findViewById(R.id.google);

        forgottenPassword = (TextView) findViewById(R.id.textview_forgottenPassword);
        signUp = (TextView) findViewById(R.id.text_sign_up);

        facebookText = (TextView) findViewById(R.id.textfacebook);
        googleText = (TextView) findViewById(R.id.textgoogle);

        //---------------Getting the instance of google firebase database and creating table------------------//

        account_type = getIntent().getStringExtra("ACCOUNT_OPTION");    //buyer is 1,seller is 0

        if (account_type.equals("1")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("LoginSignUp");
            databaseReferenceUserAddress = firebaseDatabase.getReference("UserAddress");
            databaseReferenceUserProfileImage = firebaseDatabase.getReference("UserProFilePicture");

        } else if (account_type.equals("0")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = firebaseDatabase.getReference("MerchantAccout");
            databaseReference = mDatabaseReference.child("MerchantAccoutLoginSignUp");
        }

        //---------------Set The listener on all  Buttons and textviews------------------//


        getPreferencesData();
        login.setOnClickListener(this);
        forgottenPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        facebook.setOnClickListener(this);
        google.setOnClickListener(this);
        facebookText.setOnClickListener(this);
        googleText.setOnClickListener(this);

    }

    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(SHARES_DATA_BASE_NAME, MODE_PRIVATE);
        if (sp.contains("user_phone")) {
            String phone = sp.getString("user_phone", "Not Found");
            username.setText(phone.toString());
        }
        if (sp.contains("user_password")) {
            String pass = sp.getString("user_password", "Not Found");
            password.setText(pass.toString());
        }
        if (sp.contains("pref_check")) {
            boolean pref_check = sp.getBoolean("pref_check", false);
            remberPasswordCheck.setChecked(pref_check);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == login) {

            final int[] flagePhone = {0};
            final int[] flagePassword = {0};
            final String userNamePhone = username.getText().toString();
            final String userPassword = password.getText().toString();
            if (userNamePhone.equals("")) {
                Toast.makeText(this, "User Name Can't be Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userPassword.equals("")) {
                Toast.makeText(this, "Please Enter the password", Toast.LENGTH_SHORT).show();
                return;
            }

            //----------user account-----------//
            if (account_type.equals("1")) {

                //---------------Here Firstly we are fetching data from data base to check weather user already exist or not------------------//
                ArrayList<String> userNameLogin = new ArrayList<String>();
                final ArrayList<String> phoneNumber = new ArrayList<String>();
                final ArrayList<String> passwordLogin = new ArrayList<String>();
                final ArrayList<String> emailAddress = new ArrayList<String>();
                final ArrayList<String> firstName = new ArrayList<String>();
                final ArrayList<String> lastName = new ArrayList<String>();


                final ArrayList<String> phonekeyFirebaseDataBase = new ArrayList<String>();
                final ArrayList<String> homenumberFirebaseDataBase = new ArrayList<String>();
                final ArrayList<String> landmarkFirebaseDataBase = new ArrayList<String>();
                final ArrayList<String> stateNameFirebaseDataBase = new ArrayList<String>();
                final ArrayList<String> cityNameFirebaseDataBase = new ArrayList<String>();
                final ArrayList<String> areaPinCodeNumberFirebaseDataBase = new ArrayList<String>();
                final ArrayList<String> phoneNumberFirebaseDataBase = new ArrayList<String>();
                final ArrayList<String> checkingAreaPinExistance=new ArrayList<>();

                final ArrayList<String> profileImageURL = new ArrayList<>();
                final ArrayList<String> userPhonekeyFromFireBaseForUserProfileImage = new ArrayList<>();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User user = ds.getValue(User.class);

                            phoneNumber.add(user.getPhoneDataBase());
                            passwordLogin.add(user.getPasswordDataBase());
                            emailAddress.add(user.getEmailAddressDataBase());
                            firstName.add(user.getFirstNameDataBase());
                            lastName.add(user.getLastNameDataBase());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                databaseReferenceUserAddress.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            phonekeyFirebaseDataBase.add(ds.getKey());
                            UserFireBaseAddress userFireBaseAddress = ds.getValue(UserFireBaseAddress.class);

                            homenumberFirebaseDataBase.add(userFireBaseAddress.getHouseNumberOrNameDataBase());
                            landmarkFirebaseDataBase.add(userFireBaseAddress.getLandMarkDataBase());
                            stateNameFirebaseDataBase.add(userFireBaseAddress.getStateDataBase());
                            cityNameFirebaseDataBase.add(userFireBaseAddress.getCityDataBase());
                            areaPinCodeNumberFirebaseDataBase.add(userFireBaseAddress.getAreaPinCodeDataBase());
                            phoneNumberFirebaseDataBase.add(userFireBaseAddress.getPhoneDataBaase());
                            checkingAreaPinExistance.add(userFireBaseAddress.getCHECKING_AVELABILITY());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                databaseReferenceUserProfileImage.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            userPhonekeyFromFireBaseForUserProfileImage.add(ds.getKey());
                            UserFirebaseProfileImage userFirebaseProfileImage = ds.getValue(UserFirebaseProfileImage.class);
                            profileImageURL.add(userFirebaseProfileImage.getImagrUrl());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Login.this, "Image FetchingFailed " + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
                    }
                });


                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Loging In...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                CountDownTimer countDownTimer = new CountDownTimer(1500, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            if (phoneNumber.get(i).equals(userNamePhone)) {
                                flagePhone[0] = 1;
                                emailAddresspass = emailAddress.get(i);
                                firstNamepass = firstName.get(i);
                                lastNamepass = lastName.get(i);
                                phone = phoneNumber.get(i);
                                passwordPass = passwordLogin.get(i);

                            }
                            if (passwordLogin.get(i).equals(userPassword)) {
                                flagePassword[0] = 1;
                            }

                        }
                        for (int i = 0; i < phonekeyFirebaseDataBase.size(); i++) {
                            if (phonekeyFirebaseDataBase.get(i).equals(userNamePhone)) {

                                houseNumberOrNameDataBase = homenumberFirebaseDataBase.get(i);
                                landMarkDataBase = landmarkFirebaseDataBase.get(i);
                                stateDataBase = stateNameFirebaseDataBase.get(i);
                                cityDataBase = cityNameFirebaseDataBase.get(i);
                                areaPinCodeDataBase = areaPinCodeNumberFirebaseDataBase.get(i);
                                phoneDataBaase = phoneNumberFirebaseDataBase.get(i);
                                checkingAvailbility=checkingAreaPinExistance.get(i);
                            }
                        }

                        if(userPhonekeyFromFireBaseForUserProfileImage.size()!=0) {
                            for (int i = 0; i < userPhonekeyFromFireBaseForUserProfileImage.size(); i++) {
                                if (userNamePhone.equals(userPhonekeyFromFireBaseForUserProfileImage.get(i))) {
                                    ProfileImageURLForSqlite = profileImageURL.get(i).toString();
                                }
                            }
                        }
                        //------------------Here we are using sqlite data base to save time--------------------//
                        sqLiteDatabase = openOrCreateDatabase("galaxyCart", MODE_PRIVATE, null); //This for data privacy insted of following type creationof data
                        sqLiteDatabase.execSQL("Create table if not exists UserData (UserName varchar,Email varchar,Phone varchar,Password varchar);");
                        sqLiteDatabase.execSQL("Create table if not exists UserAddressDataSQL (UserHouseNumber varchar,LandMark varchar,StateName varchar,CityName varchar,AreaPinCodeNumber varchar,PhoneNumber varchar,PhoneNumberKey varchar,CheckingAvailability varchar);");
                        sqLiteDatabase.execSQL("Create table if not exists UserProfilePicture(imageURL varchar,Phone varchar)");

                        viewAllData();


                        if (!sqliteCheckedPhoneNumberUserProfile.equals("")) {
                            if (!sqliteCheckedPhoneNumberUserProfile.equals(phone)) {
                                insertUserProFileData(firstNamepass, lastNamepass, emailAddresspass, phone, passwordPass);

                            } else {
                                updateUserProFileData(firstNamepass, lastNamepass, emailAddresspass, phone, passwordPass);
                            }
                        } else {
                            insertUserProFileData(firstNamepass, lastNamepass, emailAddresspass, phone, passwordPass);
                        }

                        if (!sqliteCheckedPhoneNumberUserAddress.equals("")) {
                            if (!sqliteCheckedPhoneNumberUserAddress.equals(phone)) {
                                insertUserAddressValuesToSqlitedataBase(houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, phone,checkingAvailbility);
                            } else {
                                updateUserAddressValuesToSqlitedataBase(houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, phone,checkingAvailbility);
                            }
                        } else {
                            insertUserAddressValuesToSqlitedataBase(houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, phone,checkingAvailbility);
                        }

                        if (!sqliteCheckedPhoneNumberUserProfilePicture.equals("")) {
                            if (!sqliteCheckedPhoneNumberUserAddress.equals(phone)) {
                                insertDataIntoSqlitedataBase(ProfileImageURLForSqlite, phone);
                            } else {
                                upDatedataInSqlitedatabase(ProfileImageURLForSqlite, phone);
                            }
                        } else {
                            insertDataIntoSqlitedataBase(ProfileImageURLForSqlite, phone);
                        }

                        //------------------Here we are using sqlite data base to save time--------------------//

                        viewAllData();

                        if (flagePhone[0] == 1 && flagePassword[0] == 1) {

                            //shareded prefrence for data saving
                            if (remberPasswordCheck.isChecked()) {
                                Boolean booleanisChecked = remberPasswordCheck.isChecked();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_phone", userNamePhone);
                                editor.putString("user_password", userPassword);
                                editor.putBoolean("pref_check", booleanisChecked);
                                editor.apply();
                            } else {
                                sharedPreferences.edit().clear().apply();
                            }

                            //calling home activity after login successfully
                            Intent intent = new Intent(Login.this, Home_Main.class);
                            intent.putExtra("ACCOUNT_OPTION", account_type);
                            intent.putExtra("FNAME", firstNamepass);
                            intent.putExtra("LNAME", lastNamepass);
                            intent.putExtra("EMAIL", emailAddresspass);
                            intent.putExtra("PHONE", phone);
                            startActivity(intent);
                            return;

                        } else if (flagePhone[0] != 1) {
                            Toast.makeText(Login.this, "You Have Entered wrong User Name", Toast.LENGTH_SHORT).show();
                        } else if (flagePassword[0] != 1) {
                            Toast.makeText(Login.this, "You Have Entered Wrong Password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                countDownTimer.start();
            }

            //----------merchant account-----------//
            else if (account_type.equals("0")) {

                //---------------Here Firstly we are fetching data from data base to check weather user already exist or not------------------//
                ArrayList<String> userNameLogin = new ArrayList<String>();
                final ArrayList<String> phoneNumber = new ArrayList<String>();
                final ArrayList<String> passwordLogin = new ArrayList<String>();
                final ArrayList<String> emailAddress = new ArrayList<String>();
                final ArrayList<String> firstName = new ArrayList<String>();
                final ArrayList<String> lastName = new ArrayList<String>();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User user = ds.getValue(User.class);

                            phoneNumber.add(user.getPhoneDataBase());
                            passwordLogin.add(user.getPasswordDataBase());
                            emailAddress.add(user.getEmailAddressDataBase());
                            firstName.add(user.getFirstNameDataBase());
                            lastName.add(user.getLastNameDataBase());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Loging In...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                CountDownTimer countDownTimer = new CountDownTimer(2000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            if (phoneNumber.get(i).equals(userNamePhone)) {
                                flagePhone[0] = 1;
                                emailAddresspass = emailAddress.get(i);
                                firstNamepass = firstName.get(i);
                                lastNamepass = lastName.get(i);
                                phone = phoneNumber.get(i);
                                passwordPass = passwordLogin.get(i);

                            }
                            if (passwordLogin.get(i).equals(userPassword)) {
                                flagePassword[0] = 1;
                            }

                        }

                        if (flagePhone[0] == 1 && flagePassword[0] == 1) {


                            //shareded prefrence for data saving
                            if (remberPasswordCheck.isChecked()) {
                                Boolean booleanisChecked = remberPasswordCheck.isChecked();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_phone", userNamePhone);
                                editor.putString("user_password", userPassword);
                                editor.putBoolean("pref_check", booleanisChecked);
                                editor.apply();
                            } else {
                                sharedPreferences.edit().clear().apply();
                            }
                            //calling home activity after login successfully
                            Intent intent = new Intent(Login.this, MerchantMainHome.class);
                            intent.putExtra("ACCOUNT_OPTION", account_type);
                            intent.putExtra("FNAME", firstNamepass);
                            intent.putExtra("LNAME", lastNamepass);
                            intent.putExtra("EMAIL", emailAddresspass);
                            intent.putExtra("PHONE", phone);
                            startActivity(intent);
                            return;

                        } else if (flagePhone[0] != 1) {
                            Toast.makeText(Login.this, "You Have Entered wrong User Name", Toast.LENGTH_SHORT).show();
                        } else if (flagePassword[0] != 1) {
                            Toast.makeText(Login.this, "You Have Entered Wrong Password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                countDownTimer.start();
            }
        }
        //---------------If user forgotten password listner is set here ------------------//

        else if (v == forgottenPassword) {
            Intent intent = new Intent(Login.this, ForgottenPassword.class);
            intent.putExtra("ACCOUNT_OPTION", account_type);
            startActivity(intent);
            return;
        }
        //---------------If user is new sign up here  ------------------//
        else if (v == signUp) {
            Intent intent = new Intent(Login.this, SignUpOptions.class);
            intent.putExtra("ACCOUNT_OPTION", account_type);
            startActivity(intent);
            return;
        }
        //---------------If user want to signup through socile media  ------------------//
        else if (v == facebook || v == facebookText) {
            Intent intent = new Intent(Login.this, LoginWithFacebook.class);
            startActivity(intent);
            return;
        } else if (v == google || v == googleText) {
            Intent intent = new Intent(Login.this, LoginWithGoogle.class);
            startActivity(intent);
            return;
        }
    }

    private void updateUserProFileData(String sqlitefirstNamepass, String sqlitelastNamepass, String sqliteemailAddresspass, String sqlitephone, String sqlitepasswordPass) {
        sqlitecompleteName = sqlitefirstNamepass + " " + sqlitelastNamepass;
        this.sqliteemailAddresspass = sqliteemailAddresspass;
        this.sqlitephone = sqlitephone;
        this.sqlitepasswordPass = sqlitepasswordPass;
        sqLiteDatabase.execSQL("update UserData set UserName='" + sqlitecompleteName + "',Email='" + sqliteemailAddresspass + "',Phone='" + sqlitephone + "',Password='" + sqlitepasswordPass + "' where Phone='" + phone + "'");
    }

    private void viewAllData() {
        Cursor cr = sqLiteDatabase.rawQuery("Select * from UserData where Phone='" + phone + "'", null);
        if (cr.getCount() != 0) {
            cr.moveToFirst();
            sqliteCheckedPhoneNumberUserProfile = cr.getString(2);

        }

        Cursor crUserAddressdata = sqLiteDatabase.rawQuery("Select * from UserAddressDataSQL where PhoneNumberKey='" + phone + "'", null);
        if (crUserAddressdata.getCount() != 0) {

            crUserAddressdata.moveToFirst();
            sqliteCheckedPhoneNumberUserAddress = crUserAddressdata.getString(6);

        }
    }

    private void insertUserProFileData(String sqlitefirstNamepass, String sqlitelastNamepass, String sqliteemailAddresspass, String sqlitephone, String sqlitepasswordPass)

    {
        sqlitecompleteName = sqlitefirstNamepass + " " + sqlitelastNamepass;
        this.sqliteemailAddresspass = sqliteemailAddresspass;
        this.sqlitephone = sqlitephone;
        this.sqlitepasswordPass = sqlitepasswordPass;
        sqLiteDatabase.execSQL("insert into UserData values('" + sqlitecompleteName + "','" + sqliteemailAddresspass + "','" + sqlitephone + "','" + sqlitepasswordPass + "')");
    }

    private void updateUserAddressValuesToSqlitedataBase(String houseNumberOrNameDataBase, String landMarkDataBase, String stateDataBase, String cityDataBase, String areaPinCodeDataBase, String phoneDataBaase, String userPhoneKey,String CHECKING_AVELABILITY) {

        sQliteDataBaseAddressHouseNumber = houseNumberOrNameDataBase;
        sQliteDataBaseAddresslandMark = landMarkDataBase;
        sQliteDataBaseAddressStateName = stateDataBase;
        sQliteDataBaseAddressCityName = cityDataBase;
        sQliteDataBaseAddressAreaPinCode = areaPinCodeDataBase;
        sQliteDataBaseAddressPhoneNumber = phoneDataBaase;
        sQliteDataBaseAddressPhoneNumberKey = userPhoneKey;
        sQliteDataBaseAddressCHECKING_AVELABILITY=CHECKING_AVELABILITY;

        sqLiteDatabase.execSQL("update UserAddressDataSQL set UserHouseNumber='" + sQliteDataBaseAddressHouseNumber + "',LandMark='" + sQliteDataBaseAddresslandMark + "',StateName='" + sQliteDataBaseAddressStateName + "',CityName='" + sQliteDataBaseAddressCityName + "',AreaPinCodeNumber='" + sQliteDataBaseAddressAreaPinCode + "',PhoneNumber='" + sQliteDataBaseAddressPhoneNumber + "',PhoneNumberKey='" + sQliteDataBaseAddressPhoneNumberKey + "',CheckingAvailability='"+sQliteDataBaseAddressCHECKING_AVELABILITY+"' where PhoneNumberKey='" + sQliteDataBaseAddressPhoneNumberKey + "'");
    }

    private void insertUserAddressValuesToSqlitedataBase(String houseNumberOrNameDataBase, String landMarkDataBase, String stateDataBase, String cityDataBase, String areaPinCodeDataBase, String phoneDataBaase, String userPhoneKey,String checkingAvailble) {

        sQliteDataBaseAddressHouseNumber = houseNumberOrNameDataBase;
        sQliteDataBaseAddresslandMark = landMarkDataBase;
        sQliteDataBaseAddressStateName = stateDataBase;
        sQliteDataBaseAddressCityName = cityDataBase;
        sQliteDataBaseAddressAreaPinCode = areaPinCodeDataBase;
        sQliteDataBaseAddressPhoneNumber = phoneDataBaase;
        sQliteDataBaseAddressPhoneNumberKey = userPhoneKey;
        sQliteDataBaseAddressCHECKING_AVELABILITY=checkingAvailble;

        sqLiteDatabase.execSQL("insert into UserAddressDataSQL values('" + sQliteDataBaseAddressHouseNumber + "','" + sQliteDataBaseAddresslandMark + "','" + sQliteDataBaseAddressStateName + "','" + sQliteDataBaseAddressCityName + "','" + sQliteDataBaseAddressAreaPinCode + "','" + sQliteDataBaseAddressPhoneNumber + "','" + sQliteDataBaseAddressPhoneNumberKey + "','"+sQliteDataBaseAddressCHECKING_AVELABILITY+"')");
    }

    private void insertDataIntoSqlitedataBase(String filePath, String phonenumber) {
        String filephoto = filePath;
        String Phonenumber = phonenumber;

        sqLiteDatabase.execSQL("insert into UserProfilePicture values('" + filephoto + "','" + Phonenumber + "')");
    }

    private void upDatedataInSqlitedatabase(String filePath, String phonenumber) {
        String filephoto = filePath;
        String Phonenumber = phonenumber;
        sqLiteDatabase.execSQL("update UserProfilePicture set imageURL='" + filephoto + "',Phone='" + Phonenumber + "'");

    }

    @Override
    public void onBackPressed() {

    }
}


