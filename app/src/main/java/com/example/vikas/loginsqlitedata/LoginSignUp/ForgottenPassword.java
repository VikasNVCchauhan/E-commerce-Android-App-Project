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

public class ForgottenPassword extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,mDatabaseReference;
    private EditText phone;
    private Button generateOTP;
    private TextView loginHere, signUpHere;
    private String account_type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotten_password);

        phone = findViewById(R.id.editText_phone);
        generateOTP = findViewById(R.id.generateOTP);
        loginHere = findViewById(R.id.login_main_forgotten);
        signUpHere = findViewById(R.id.signUp_main_forgotten);

        account_type = getIntent().getStringExtra("ACCOUNT_OPTION");    //buyer is 1,seller is 0

        if (account_type.equals("1")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("LoginSignUp");
        } else if (account_type.equals("0")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = firebaseDatabase.getReference("MerchantAccout");
            databaseReference = mDatabaseReference.child("MerchantAccoutLoginSignUp");
        }

        generateOTP.setOnClickListener(this);
        loginHere.setOnClickListener(this);
        signUpHere.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == generateOTP) {
            final int[] flagePhone = {0};
            final String userNamePhone = phone.getText().toString();

            if (userNamePhone.equals("")) {
                Toast.makeText(this, "Please Enter phone number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userNamePhone.length() < 10 || userNamePhone.length() > 10) {
                Toast.makeText(this, "Please Enter a valied Number", Toast.LENGTH_SHORT).show();
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

            final ProgressDialog progressDialog = new ProgressDialog(ForgottenPassword.this);
            progressDialog.setMessage("Please Wait checking user existence...");
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
                        }
                    }
                    if (flagePhone[0] == 1) {
                        generateOTP(userNamePhone);
                        return;
                    } else if (flagePhone[0] == 0) {
                        Toast.makeText(ForgottenPassword.this, "This User Doesn't Exist", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            };
            countDownTimer.start();
        } else if (v == loginHere) {
            Intent intent = new Intent(ForgottenPassword.this, Login.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
            return;
        } else if (v == signUpHere) {
            Intent intent = new Intent(ForgottenPassword.this, GenrateOTP.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
            return;
        }
    }

    private void generateOTP(final String userNamePhone) {
        final String userPhone = userNamePhone;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                userPhone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(ForgottenPassword.this, "Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(ForgottenPassword.this, "Invalied credential", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(ForgottenPassword.this, "SMS Quota exceeded.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        final String code = s;
                        final ProgressDialog progressDialog = new ProgressDialog(ForgottenPassword.this);
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
                                Toast.makeText(ForgottenPassword.this, "code sent", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgottenPassword.this, AfterForgottenPassword.class);
                                intent.putExtra("ACCOUNT_OPTION",account_type);
                                intent.putExtra("CODE", code);
                                intent.putExtra("PHONE", userPhone);
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