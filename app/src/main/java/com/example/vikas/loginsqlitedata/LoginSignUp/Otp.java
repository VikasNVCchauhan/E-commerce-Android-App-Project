package com.example.vikas.loginsqlitedata.LoginSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Otp extends AppCompatActivity implements View.OnClickListener {

    //here data is comming from genrate otp
    private String mobileNumber;
    private String password;


    private FirebaseAuth auth;
    private String phoneNumber, code;
    private EditText enerOtp;
    private Button verify;
    private TextView login, reSendOtp;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private String account_type;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, mDatabaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity);

        mobileNumber = getIntent().getStringExtra("PHONE");
        password = getIntent().getStringExtra("PASSWORD");

        enerOtp = (EditText) findViewById(R.id.enterOTP);
        verify = (Button) findViewById(R.id.verifyOTP);
        login = (TextView) findViewById(R.id.login_main_activity);
        reSendOtp = (TextView) findViewById(R.id.resendOTP);

        verify.setOnClickListener(this);
        login.setOnClickListener(this);
        reSendOtp.setOnClickListener(this);


        account_type = getIntent().getStringExtra("ACCOUNT_OPTION");    //buyer is 1,seller is 0

        auth = FirebaseAuth.getInstance();

        if (account_type.equals("1")) {
            System.out.println("Account type is here "+account_type);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("LoginSignUp");
        } else if (account_type.equals("0")) {
            System.out.println("Account type is here "+account_type);
            firebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = firebaseDatabase.getReference("MerchantAccout");
            databaseReference = mDatabaseReference.child("MerchantAccoutLoginSignUp");
        }

    }

    @Override
    public void onClick(View v) {
        if (v == verify) {

            final ProgressDialog progressDialog = new ProgressDialog(Otp.this);
            progressDialog.setMessage("Please Wait we are verifying OTP ...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String userEnteredcode = enerOtp.getText().toString();

            if (userEnteredcode.trim().length() == 6) {
                code = getIntent().getStringExtra("CODE");
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code, userEnteredcode);
                auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Here we are putting data to firebase DataBase after otp verification successfully

                            putNewDataToFireBase(mobileNumber, password);

                            //Here we are calling signup activity after otp verification successfully
                            CountDownTimer countDownTimer = new CountDownTimer(1000, 100) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    progressDialog.dismiss();
                                    Toast.makeText(Otp.this, "Verification successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Otp.this, SignUp.class);
                                    intent.putExtra("ACCOUNT_OPTION", account_type);
                                    intent.putExtra("PHONE", mobileNumber);
                                    intent.putExtra("PASSWORD", password);
                                    startActivity(intent);
                                    return;
                                }
                            };
                            countDownTimer.start();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                progressDialog.dismiss();
                                Toast.makeText(Otp.this, "Incorrect verification", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
            }
        } else if (v == login) {
            Intent intent = new Intent(Otp.this, Login.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
        }

        ///Resend otp to phone number
        else if (v == reSendOtp) {
            final ProgressDialog progressDialog = new ProgressDialog(Otp.this);
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
            ResendOtp resendOTP = new ResendOtp(mobileNumber);
            code = resendOTP.getPhoneNumber();

        }
    }

    private void putNewDataToFireBase(String mobileNumber, String password) {

        String firstName = "";
        String lastName = "";
        this.mobileNumber = mobileNumber;
        this.password = password;
        String emails = "";

        User user = new User(firstName, lastName, emails, mobileNumber, password);
        databaseReference.child(mobileNumber).setValue(user);

    }

    @Override
    public void onBackPressed() {

    }
}
