package com.example.vikas.loginsqlitedata.LoginSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GenrateOTP extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, mDatabaseReference;
    private EditText enerMobileNumber, password, re_Enterpassword;
    private Button signUp;
    private TextView login;
    private ProgressDialog progressDialog;
    private String userNamePhone, userPassword, userReEnterPassword;
    private String account_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_otp_phone_number);


        enerMobileNumber = (EditText) findViewById(R.id.editText_mobile_Number);
        password = (EditText) findViewById(R.id.editText_pasword);
        re_Enterpassword = (EditText) findViewById(R.id.editText_re_Enter_pasword);
        signUp = (Button) findViewById(R.id.signup_OtpActivity);
        login = (TextView) findViewById(R.id.otp_text_login);

        signUp.setOnClickListener(this);
        login.setOnClickListener(this);

        account_type = getIntent().getStringExtra("ACCOUNT_OPTION");    //buyer is 1,seller is 0

        firebaseDatabase = FirebaseDatabase.getInstance();

        if (account_type.equals("1")) {
            databaseReference = firebaseDatabase.getReference("LoginSignUp");
        } else if (account_type.equals("0")) {
             mDatabaseReference= firebaseDatabase.getReference("MerchantAccout");
             databaseReference = mDatabaseReference.child("MerchantAccoutLoginSignUp");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == signUp) {

            final int[] flagePhone = {0};
            userNamePhone = enerMobileNumber.getText().toString();
            userPassword = password.getText().toString();
            userReEnterPassword = re_Enterpassword.getText().toString();


            if (userNamePhone.equals("")) {
                Toast.makeText(this, "Please Enter phone number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userPassword.equals("")) {
                Toast.makeText(this, "Please Enter the password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userPassword.length() < 8 || userPassword.length() > 14) {
                Toast.makeText(this, "Password length must be greater than 7 and less then 14", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userNamePhone.length() < 10 || userNamePhone.length() > 12) {
                Toast.makeText(this, "Please Enter a valied Number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!userPassword.equals(userReEnterPassword)) {
                Toast.makeText(this, "Password Doesn't match", Toast.LENGTH_SHORT).show();
                return;
            }
            //---------------Here Firstly we are fetching data from data base to check weather user already exist or not------------------//
            final ArrayList<String> userNameLogin = new ArrayList<String>();
            final ArrayList<String> phoneNumber = new ArrayList<String>();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        phoneNumber.add(user.getPhoneDataBase());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            progressDialog = new ProgressDialog(GenrateOTP.this);
            progressDialog.setMessage("Please Wait checking user existence...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            CountDownTimer countDownTimer = new CountDownTimer(2000, 100) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    for (int i = 0; i < phoneNumber.size(); i++) {
                        if (phoneNumber.get(i).equals(userNamePhone)) {
                            flagePhone[0] = 1;
                        }
                    }
                    if (flagePhone[0] == 1) {
                        progressDialog.dismiss();
                        Toast.makeText(GenrateOTP.this, "User Already Exist", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (flagePhone[0] == 0) {
                        generateOTP();

                    }
                }
            };
            countDownTimer.start();
        }
///here we user is jumping to login activity
        else if (v == login) {
            Intent intent = new Intent(GenrateOTP.this, Login.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
            return;
        }
    }

    private void generateOTP() {
        final String userNamePhone = enerMobileNumber.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                userNamePhone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(GenrateOTP.this, "Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(GenrateOTP.this, "Invalied credential", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(GenrateOTP.this, "SMS Quota exceeded.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        final String code = s;

                        progressDialog = new ProgressDialog(GenrateOTP.this);
                        progressDialog.setMessage("Sending OTP To your Number...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        CountDownTimer countDownTimer = new CountDownTimer(2000, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                progressDialog.dismiss();
                                Toast.makeText(GenrateOTP.this, "code sent to your mobile number", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(GenrateOTP.this, Otp.class);
                                intent.putExtra("ACCOUNT_OPTION",account_type);
                                intent.putExtra("CODE", code);
                                intent.putExtra("PHONE", userNamePhone);
                                intent.putExtra("PASSWORD", userPassword);
                                startActivity(intent);
                                return;
                            }
                        };
                        countDownTimer.start();
                    }
                });
    }

    @Override
    public void onBackPressed() {

    }
}