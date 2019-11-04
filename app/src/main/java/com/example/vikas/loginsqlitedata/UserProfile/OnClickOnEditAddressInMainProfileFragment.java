package com.example.vikas.loginsqlitedata.UserProfile;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.HomeScreen.HomeFragment;
import com.example.vikas.loginsqlitedata.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class OnClickOnEditAddressInMainProfileFragment extends Fragment implements View.OnClickListener {

    private SQLiteDatabase sqLiteDatabase;

    private String sQliteDataBaseAddressHouseNumber, sQliteDataBaseAddresslandMark, sQliteDataBaseAddressStateName, sQliteDataBaseAddressCityName, sQliteDataBaseAddressAreaPinCode, sQliteDataBaseAddressPhoneNumber, sQliteDataBaseAddressPhoneNumberKey, sQliteDataBaseAddressCHECKING_AVELABILITY;

    private String addressLine1, AddressLine2LandMark, AddressLine3State, AddressLine4City, AddressLine5AreaPinCode, AddressLine6AdditionPhoneNumber;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceAddress, databaseReferenceAreaPinCode;
    private String houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, sqliteCheckedPhoneNumber;


    private int areaPinflag = 0;

    private AppCompatActivity appCompatActivity;
    private Toolbar toolbar;

    private int flag;
    private String CHECKING_AVELABILITY;
    private String[] citynames;
    private String[] stateName;

    private String userPhoneKey;

    private EditText edittextHouseAddressHousenumber, edittextLandmark, edittextState, edittextCityName, edittextAreaPinCode, edittextMobileNumber;
    private Button saveData;


    public OnClickOnEditAddressInMainProfileFragment(int flag) {
        this.flag = flag;
    }

    @SuppressLint("ValidFragment")
    public OnClickOnEditAddressInMainProfileFragment(String userPhoneKey) {
        this.userPhoneKey = userPhoneKey;

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_click_on_edit_address_in_main_profile, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceAddress = firebaseDatabase.getReference("UserAddress");
        databaseReferenceAreaPinCode = firebaseDatabase.getReference("AreaPinCode");


        citynames = appCompatActivity.getResources().getStringArray(R.array.cityNames);
        stateName = appCompatActivity.getResources().getStringArray(R.array.StateNames);

        toolbar = appCompatActivity.findViewById(R.id.action_toolbar);
        toolbar.setTitle("Enter Your Address");

        getIdForAllVariables(view);

        sqLiteDatabase = getActivity().openOrCreateDatabase("galaxyCart", MODE_PRIVATE, null); //This for data privacy insted of following type creationof data
        sqLiteDatabase.execSQL("Create table if not exists UserAddressDataSQL (UserHouseNumber varchar,LandMark varchar,StateName varchar,CityName varchar,AreaPinCodeNumber varchar,PhoneNumber varchar,PhoneNumberKey varchar,CheckingAvailability varchar);");

        getAddressFromSQLiteDatabase();
        edittextState.setOnClickListener(this);
        edittextCityName.setOnClickListener(this);

        saveData.setOnClickListener(this);


        return view;
    }

    private void setAddressToField() {
        edittextHouseAddressHousenumber.setText(addressLine1);
        edittextLandmark.setText(AddressLine2LandMark);
        edittextState.setText(AddressLine3State);
        edittextCityName.setText(AddressLine4City);
        edittextAreaPinCode.setText(AddressLine5AreaPinCode);
        edittextMobileNumber.setText(AddressLine6AdditionPhoneNumber);
    }

    private void getAddressFromSQLiteDatabase() {

        Cursor cursor=sqLiteDatabase.rawQuery("Select * from UserAddressDataSQL where PhoneNumberKey='" + userPhoneKey + "'", null);
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            addressLine1=cursor.getString(0);
            AddressLine2LandMark=cursor.getString(1);
            AddressLine3State=cursor.getString(2);
            AddressLine4City=cursor.getString(3);
            AddressLine5AreaPinCode=cursor.getString(4);
            AddressLine6AdditionPhoneNumber=cursor.getString(5);
        }
        setAddressToField();
    }

    private void viewValuesToSqlitedataBase() {
        Cursor cr = sqLiteDatabase.rawQuery("Select * from UserAddressDataSQL where PhoneNumberKey='" + userPhoneKey + "'", null);
        if (cr.getCount() != 0) {
            cr.moveToFirst();

            sqliteCheckedPhoneNumber = cr.getString(2);
        }
    }

    private void updateValuesToSqlitedataBase(String houseNumberOrNameDataBase, String landMarkDataBase, String stateDataBase, String cityDataBase, String areaPinCodeDataBase, String phoneDataBaase, String userPhoneKey, String CHECKING_AVELABILITY) {
        sQliteDataBaseAddressHouseNumber = houseNumberOrNameDataBase;
        sQliteDataBaseAddresslandMark = landMarkDataBase;
        sQliteDataBaseAddressStateName = stateDataBase;
        sQliteDataBaseAddressCityName = cityDataBase;
        sQliteDataBaseAddressAreaPinCode = areaPinCodeDataBase;
        sQliteDataBaseAddressPhoneNumber = phoneDataBaase;
        sQliteDataBaseAddressPhoneNumberKey = userPhoneKey;
        sQliteDataBaseAddressCHECKING_AVELABILITY = CHECKING_AVELABILITY;

        Toast.makeText(getContext(), "" + sQliteDataBaseAddressCHECKING_AVELABILITY, Toast.LENGTH_SHORT).show();
        sqLiteDatabase.execSQL("update UserAddressDataSQL set UserHouseNumber='" + sQliteDataBaseAddressHouseNumber + "',LandMark='" + sQliteDataBaseAddresslandMark + "',StateName='" + sQliteDataBaseAddressStateName + "',CityName='" + sQliteDataBaseAddressCityName + "',AreaPinCodeNumber='" + sQliteDataBaseAddressAreaPinCode + "',PhoneNumber='" + sQliteDataBaseAddressPhoneNumber + "',PhoneNumberKey='" + sQliteDataBaseAddressPhoneNumberKey + "',CheckingAvailability='" + sQliteDataBaseAddressCHECKING_AVELABILITY + "' where PhoneNumberKey='" + sQliteDataBaseAddressPhoneNumberKey + "'");
    }

    private void insertValuesToSqlitedataBase(String houseNumberOrNameDataBase, String landMarkDataBase, String stateDataBase, String cityDataBase, String areaPinCodeDataBase, String phoneDataBaase, String userPhoneKey, String CHECKING_AVELABILITY) {
        sQliteDataBaseAddressHouseNumber = houseNumberOrNameDataBase;
        sQliteDataBaseAddresslandMark = landMarkDataBase;
        sQliteDataBaseAddressStateName = stateDataBase;
        sQliteDataBaseAddressCityName = cityDataBase;
        sQliteDataBaseAddressAreaPinCode = areaPinCodeDataBase;
        sQliteDataBaseAddressPhoneNumber = phoneDataBaase;
        sQliteDataBaseAddressPhoneNumberKey = userPhoneKey;
        sQliteDataBaseAddressCHECKING_AVELABILITY = CHECKING_AVELABILITY;

        Toast.makeText(getContext(), "" + sQliteDataBaseAddressCHECKING_AVELABILITY, Toast.LENGTH_SHORT).show();

        sqLiteDatabase.execSQL("insert into UserAddressDataSQL values('" + sQliteDataBaseAddressHouseNumber + "','" + sQliteDataBaseAddresslandMark + "','" + sQliteDataBaseAddressStateName + "','" + sQliteDataBaseAddressCityName + "','" + sQliteDataBaseAddressAreaPinCode + "','" + sQliteDataBaseAddressPhoneNumber + "','" + sQliteDataBaseAddressPhoneNumberKey + "','" + sQliteDataBaseAddressCHECKING_AVELABILITY + "')");
    }

    private void getIdForAllVariables(View view) {

        edittextHouseAddressHousenumber = view.findViewById(R.id.editText_edit_address_fragment_House_number);
        edittextLandmark = view.findViewById(R.id.editText_edit_address_fragment_landmark);
        edittextState = view.findViewById(R.id.editText_edit_address_fragment_state);
        edittextCityName = view.findViewById(R.id.editText_edit_address_fragment_city);
        edittextAreaPinCode = view.findViewById(R.id.editText_edit_address_fragment_areapin_code);
        edittextMobileNumber = view.findViewById(R.id.editText_edit_address_fragment_mobile_number);

        saveData = view.findViewById(R.id.save_address);
    }

    @Override
    public void onClick(View v) {

        if (v == edittextCityName) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setCancelable(true);
            builder.setTitle("Select Your City");
            builder.setIcon(R.mipmap.city_address);

            builder.setSingleChoiceItems(citynames, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String cityNmae = citynames[which];
                    edittextCityName.setText(cityNmae);
                }
            });

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    int possition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    edittextCityName.setText(citynames[possition]);

                }
            });

            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else if (v == edittextState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setCancelable(true);
            builder.setTitle("Select Your State");
            builder.setIcon(R.mipmap.state_address);

            builder.setSingleChoiceItems(stateName, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String cityNmae = stateName[which];
                    edittextState.setText(cityNmae);
                }
            });

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int possition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    edittextState.setText(stateName[possition]);
                }
            });

            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        } else if (v == saveData) {

            houseNumberOrNameDataBase = edittextHouseAddressHousenumber.getText().toString();
            landMarkDataBase = edittextLandmark.getText().toString();
            stateDataBase = edittextState.getText().toString();
            cityDataBase = edittextCityName.getText().toString();
            areaPinCodeDataBase = edittextAreaPinCode.getText().toString();
            phoneDataBaase = edittextMobileNumber.getText().toString();

            if (houseNumberOrNameDataBase.equals("")) {
                Toast.makeText(appCompatActivity, "House address can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (houseNumberOrNameDataBase.length() < 8) {
                Toast.makeText(appCompatActivity, "Please enter valied house number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (landMarkDataBase.equals("")) {
                Toast.makeText(appCompatActivity, "LandMark can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (landMarkDataBase.length() < 4) {
                Toast.makeText(appCompatActivity, "Please enter valied LandMark", Toast.LENGTH_SHORT).show();
                return;
            }

            if (stateDataBase.equals("")) {
                Toast.makeText(appCompatActivity, "Please select your state", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cityDataBase.equals("")) {
                Toast.makeText(appCompatActivity, "Please select your city", Toast.LENGTH_SHORT).show();
                return;
            }

            if (areaPinCodeDataBase.equals("")) {
                Toast.makeText(appCompatActivity, "Please select your city", Toast.LENGTH_SHORT).show();
                return;
            }
            if (areaPinCodeDataBase.length() < 6 || areaPinCodeDataBase.length() > 6) {
                Toast.makeText(appCompatActivity, "Please enter a valied pin code", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phoneDataBaase.equals("")) {
                Toast.makeText(appCompatActivity, "Phone number field can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phoneDataBaase.length() < 10 || phoneDataBaase.length() > 10) {
                Toast.makeText(appCompatActivity, "Please enter a valied phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            final ArrayList<String> areaPinArrayList = new ArrayList<String>();
            databaseReferenceAreaPinCode.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        areaPinArrayList.add(ds.getKey());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("We are verifying your area pin code...");
            progressDialog.show();
            CountDownTimer countDownTimer = new CountDownTimer(2000, 100) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {

                    progressDialog.dismiss();

                    for (int i = 0; i < areaPinArrayList.size(); i++) {
                        if (areaPinCodeDataBase.equals(areaPinArrayList.get(i))) {
                            areaPinflag = 1;
                        } else {
                            areaPinflag = 0;
                        }
                    }

                    if (areaPinflag == 1) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Congratulation, Your area pin Code is verified click 'OK' to save data ");
                        builder.setIcon(R.mipmap.area_pin_code_address);
                        builder.setTitle("Area Pin Code Verified ");

                        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CHECKING_AVELABILITY = "YES";
                                UserFireBaseAddress userFireBaseAddress = new UserFireBaseAddress(houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, CHECKING_AVELABILITY);
                                databaseReferenceAddress.child(userPhoneKey).setValue(userFireBaseAddress);
                                //-----------------Here We are using sqlite database-----------------//
                                viewValuesToSqlitedataBase();

                                if (!sqliteCheckedPhoneNumber.equals("")) {
                                    updateValuesToSqlitedataBase(houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, userPhoneKey, CHECKING_AVELABILITY);
                                } else {
                                    insertValuesToSqlitedataBase(houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, userPhoneKey, CHECKING_AVELABILITY);
                                }
                                //-----------------Here We ending sqlite database-----------------//

                                HomeFragment homeFragment = new HomeFragment();
                                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).addToBackStack(null).commit();

                                Toast.makeText(appCompatActivity, "Address saved successfully", Toast.LENGTH_SHORT).show();

                            }
                        });
                        builder.show();
                    } else if (areaPinflag == 0) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Sorry, \nProduct Delivery is not available in this area." +
                                " Still if you want to save this address.\nPress 'OK' to save this address ");
                        builder.setIcon(R.mipmap.area_pin_code_address);
                        builder.setTitle("Area Pin Verification");

                        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CHECKING_AVELABILITY = "NO";
                                UserFireBaseAddress userFireBaseAddress = new UserFireBaseAddress(houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, CHECKING_AVELABILITY);
                                databaseReferenceAddress.child(userPhoneKey).setValue(userFireBaseAddress);

                                //-----------------Here We are using sqlite database-----------------//
                                viewValuesToSqlitedataBase();

                                if (!sqliteCheckedPhoneNumber.equals("0")) {
                                    Toast.makeText(appCompatActivity, "Data is Updating", Toast.LENGTH_SHORT).show();

                                    updateValuesToSqlitedataBase(houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, userPhoneKey, CHECKING_AVELABILITY);
                                } else {
                                    Toast.makeText(appCompatActivity, "Data is inserting", Toast.LENGTH_SHORT).show();
                                    insertValuesToSqlitedataBase(houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase, userPhoneKey, CHECKING_AVELABILITY);
                                }
                                //-----------------Here We ending sqlite database-----------------//

                                HomeFragment homeFragment = new HomeFragment();
                                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).addToBackStack(null).commit();

                                Toast.makeText(appCompatActivity, "Address saved successfully", Toast.LENGTH_SHORT).show();
                            }

                        });

                        builder.show();
                    }
                }

            };
            countDownTimer.start();
        }
    }
}
