package com.example.vikas.loginsqlitedata.LoginSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.HomeScreen.Home_Main;
import com.example.vikas.loginsqlitedata.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AfterForgottenPassword extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,mDatabaseReference;
    private FirebaseAuth auth;
    private String resendCode = null;
    private EditText enterOtp;
    private Button verify;
    private TextView reSendOtp, loginHere;
    private String otpComeFromForgottenActivity, userEnteredcode, phoneNumberComeFromeForgottenActivity, passwordForComparison;
    private String account_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_forgotten_password_activity);

        otpComeFromForgottenActivity = getIntent().getStringExtra("CODE");
        phoneNumberComeFromeForgottenActivity = getIntent().getStringExtra("PHONE");

        enterOtp = findViewById(R.id.enterOTP);
        verify = findViewById(R.id.verifyOTP);
        reSendOtp = findViewById(R.id.resendOTP);
        loginHere = findViewById(R.id.login_main_activity);

        account_type = getIntent().getStringExtra("ACCOUNT_OPTION");    //buyer is 1,seller is 0

        if (account_type.equals("1")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("LoginSignUp");
        } else if (account_type.equals("0")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference= firebaseDatabase.getReference("MerchantAccout");
            databaseReference = mDatabaseReference.child("MerchantAccoutLoginSignUp");
        }
        auth = FirebaseAuth.getInstance();
        verify.setOnClickListener(this);
        reSendOtp.setOnClickListener(this);
        loginHere.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == verify) {
            userEnteredcode = enterOtp.getText().toString();
            if (userEnteredcode.trim().length() == 6) {
                getOlderPassword(phoneNumberComeFromeForgottenActivity);
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpComeFromForgottenActivity, userEnteredcode);
                auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(AfterForgottenPassword.this, ChangePasswordAfterForgottenVerification.class);
                            intent.putExtra("ACCOUNT_OPTION", account_type);
                            intent.putExtra("PASSWORD", passwordForComparison);
                            intent.putExtra("PHONE", phoneNumberComeFromeForgottenActivity);
                            startActivity(intent);
                            return;

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(AfterForgottenPassword.this, "Incorrect verification", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
            }
        } else if (v == reSendOtp) {

            final ProgressDialog progressDialog = new ProgressDialog(AfterForgottenPassword.this);
            progressDialog.setMessage("Please Wait we are sending OTP to your registered mobile number...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            CountDownTimer countDownTimer = new CountDownTimer(2000, 100) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    progressDialog.dismiss();
                }
            };
            countDownTimer.start();
            phoneNumberComeFromeForgottenActivity = getIntent().getStringExtra("PHONE");
            ResendOtp resendOtp = new ResendOtp(phoneNumberComeFromeForgottenActivity);
            resendCode = resendOtp.getPhoneNumber();
        } else if (v == loginHere) {
            Intent intent = new Intent(AfterForgottenPassword.this, Login.class);
            startActivity(intent);
            return;
        }
    }

    private void getOlderPassword(final String phoneNumberComeFromeForgottenActivity) {
        this.phoneNumberComeFromeForgottenActivity = phoneNumberComeFromeForgottenActivity;
        final ArrayList<String> passwordDataBase = new ArrayList<>();
        final ArrayList<String> phoneDataBase = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    phoneDataBase.add(ds.getKey());
                    User user = ds.getValue(User.class);
                    passwordDataBase.add(user.getPasswordDataBase());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait we are sending otp to your registered mobile number...");
        progressDialog.show();
        CountDownTimer countDownTimer = new CountDownTimer(2000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
                int index = phoneDataBase.indexOf(phoneNumberComeFromeForgottenActivity);
                passwordForComparison = passwordDataBase.get(index);
            }
        };
        countDownTimer.start();

    }

    @Override
    public void onBackPressed() {

    }
}
