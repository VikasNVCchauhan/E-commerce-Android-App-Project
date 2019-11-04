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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChangePasswordAfterForgottenVerification extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,mDatabaseReference;
    private EditText password, reEnterPassword;
    private Button changePassword;
    private TextView loginhere;
    private String passwordForComparison, passwordChange, rePasswordChange, passwordDataBase, phoneNumberCombingFrom;
    private String account_type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_after_forgotten_activity);

        passwordForComparison = getIntent().getStringExtra("PASSWORD");
        phoneNumberCombingFrom = getIntent().getStringExtra("PHONE");

        password = findViewById(R.id.editText_pasword);
        reEnterPassword = findViewById(R.id.editText_re_Enter_pasword);
        changePassword = findViewById(R.id.change_password);
        loginhere = findViewById(R.id.password_change_text_login);

        account_type = getIntent().getStringExtra("ACCOUNT_OPTION");    //buyer is 1,seller is 0

        if (account_type.equals("1")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("LoginSignUp");
        } else if (account_type.equals("0")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference= firebaseDatabase.getReference("MerchantAccout");
            databaseReference = mDatabaseReference.child("MerchantAccoutLoginSignUp");
        }

        changePassword.setOnClickListener(this);
        loginhere.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == changePassword) {
            passwordChange = password.getText().toString();
            rePasswordChange = reEnterPassword.getText().toString();
            if (passwordChange.equals("")) {
                Toast.makeText(this, "Please Enter the password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (passwordChange.length() < 8 || passwordChange.length() > 14) {
                Toast.makeText(this, "Password length must be greater than 7 and less then 14", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rePasswordChange.equals("")) {
                Toast.makeText(this, "Please Enter the password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!passwordChange.equals(rePasswordChange)) {
                Toast.makeText(this, "Password Doesn't match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (passwordForComparison.equals(passwordChange)) {
                Toast.makeText(this, "You Can't Use older password ", Toast.LENGTH_SHORT).show();
            } else {
                putNewDataToFireBase(passwordChange);
            }
        } else if (v == loginhere) {
            Intent intent = new Intent(ChangePasswordAfterForgottenVerification.this, Login.class);
            startActivity(intent);
            return;
        }
    }

    private void putNewDataToFireBase(String passwordDataBase) {
        this.passwordDataBase = passwordDataBase;

        databaseReference.child(phoneNumberCombingFrom).child("passwordDataBase").setValue(passwordDataBase);
        Intent intent = new Intent(ChangePasswordAfterForgottenVerification.this, Login.class);
        intent.putExtra("ACCOUNT_OPTION", account_type);
        startActivity(intent);
        Toast.makeText(this, "Password Saved Successfully", Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void onBackPressed() {

    }
}
