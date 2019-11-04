package com.example.vikas.loginsqlitedata.LoginSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,mDatabaseReference;
    private String phoneNumberComingFromOTPActivity;
    private String rePasswordComingFromOTPActivity;
    private Button signUp;
    private EditText firstName, lastName, emailAddress, phoneNumber, password, reEnterPassword;
    private String account_type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        phoneNumberComingFromOTPActivity = getIntent().getStringExtra("PHONE");
        rePasswordComingFromOTPActivity = getIntent().getStringExtra("PASSWORD");
        //---------------Set The Id of all variables------------------//
        signUp = (Button) findViewById(R.id.signup_Activty_signup);

        firstName = (EditText) findViewById(R.id.editText_SignupActivity_firstName);
        lastName = (EditText) findViewById(R.id.editText_SignupActivity_LastName);
        emailAddress = (EditText) findViewById(R.id.editText_SignupActivity_email);
        phoneNumber = (EditText) findViewById(R.id.editText_SignupActivity_Phone);
        password = (EditText) findViewById(R.id.editText_SignupActivity_password);
        reEnterPassword = (EditText) findViewById(R.id.editText_SignupActivity_Resetpassword);

        //---------------Getting the instance of google firebase database and creating table------------------//

        account_type = getIntent().getStringExtra("ACCOUNT_OPTION");    //buyer is 1,seller is 0

        if (account_type.equals("1")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("LoginSignUp");
        } else if (account_type.equals("0")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = firebaseDatabase.getReference("MerchantAccout");
            databaseReference = mDatabaseReference.child("MerchantAccoutLoginSignUp");
        }

        //---------------Here we are fetchin data from data base and set this data on edit text fields------------------//
        getDataAndSetOnEditText();
        //---------------Set The listner on all  Buttons and textviews------------------//
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == signUp) {
            //---------------Here we are taking data from EditText Field ------------------//
            final String firstNameDataBase = firstName.getText().toString();
            final String lastNameDataBase = lastName.getText().toString();
            final String emailAddressDataBase = emailAddress.getText().toString().trim();
            final String phoneDataBase = phoneNumber.getText().toString();
            final String passwordDataBase = password.getText().toString();
            String reEnterPasswordDataBase = reEnterPassword.getText().toString();

            //---------------Here we check whether Fields are empty or not ------------------//
            if (firstNameDataBase.equals("")) {
                Toast.makeText(this, "Please Enter First name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (firstNameDataBase.length() < 3) {
                Toast.makeText(this, "Please Enter Correct Name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (lastNameDataBase.equals("")) {
                Toast.makeText(this, "Please Enter lastName name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (emailAddressDataBase.equals("")) {
                Toast.makeText(this, "Please Enter email address", Toast.LENGTH_SHORT).show();
                return;
            }

            //---------------Here we checking whether email is correct or not ------------------//

            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern;
            pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(emailAddressDataBase);
            if (matcher.matches() != true) {
                emailAddress.setError("Entered wrong email address");
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            //---------------Here email verification completed ------------------//
            if (phoneDataBase.equals("")) {
                Toast.makeText(this, "Please Enter phone number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (passwordDataBase.equals("")) {
                Toast.makeText(this, "Please Enter the password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (passwordDataBase.length() < 8 || passwordDataBase.length() > 14) {
                Toast.makeText(this, "Password length must be greater than 7 and less then 14", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!passwordDataBase.equals(reEnterPasswordDataBase)) {
                Toast.makeText(this, "Password Doesn't match", Toast.LENGTH_SHORT).show();
                return;
            }
            //---------------This is a class which return all data from data fromm data base and into data base------------------//
            final User[] user = new User[1];
            putNewDataToFireBase(firstNameDataBase, lastNameDataBase, emailAddressDataBase, phoneDataBase, passwordDataBase);

        }
    }

    //---------------Here we are setting data on edittext of signUp data activity ------------------//

    private void getDataAndSetOnEditText() {

        final ArrayList<String> usernames = new ArrayList<String>();
        final ArrayList<String> firstNameDataBase = new ArrayList<String>();
        final ArrayList<String> lastNameDataBase = new ArrayList<String>();
        final ArrayList<String> phoneDataBase = new ArrayList<String>();
        final ArrayList<String> emailDataBase = new ArrayList<String>();
        final ArrayList<String> passwordDataBase = new ArrayList<String>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    usernames.add(ds.getKey()); //phone number is here extracted because phone is key

                    User user = ds.getValue(User.class);
                    firstNameDataBase.add(user.getFirstNameDataBase());
                    lastNameDataBase.add(user.getLastNameDataBase());
                    emailDataBase.add(user.getEmailAddressDataBase());
                    passwordDataBase.add(user.getPasswordDataBase());
                    phoneDataBase.add(user.getPhoneDataBase());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SignUp.this, "Data Fetching failed", Toast.LENGTH_SHORT).show();
            }
        });
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Data is Fetching wait...");
        progressDialog.show();
        CountDownTimer countDownTimer = new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();

                int index = usernames.indexOf(phoneNumberComingFromOTPActivity);
                if (index == -1) {
                    Toast.makeText(SignUp.this, "No Data Found ", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    firstName.setText(firstNameDataBase.get(index));
                    lastName.setText(lastNameDataBase.get(index));
                    emailAddress.setText(emailDataBase.get(index));
                    phoneNumber.setText(phoneDataBase.get(index));
                    password.setText(passwordDataBase.get(index));
                    reEnterPassword.setText(rePasswordComingFromOTPActivity);
                }
            }
        };
        countDownTimer.start();
    }

    //here we are putting data to database
    private void putNewDataToFireBase(String firstNameDataBase, String lastNameDataBase, String emailAddressDataBase, String phoneDataBase, String passwordDataBase) {
        String firstName = firstNameDataBase;
        String lastName = lastNameDataBase;
        String mobileNumber = phoneDataBase;
        String password = passwordDataBase;
        String emails = emailAddressDataBase;

        User user = new User(firstName, lastName, emails, phoneNumberComingFromOTPActivity, password);

        databaseReference.child(phoneNumberComingFromOTPActivity).setValue(user);

        Intent intent = new Intent(SignUp.this, Login.class);
        intent.putExtra("ACCOUNT_OPTION",account_type);
        startActivity(intent);
        return;
    }

    @Override
    public void onBackPressed() {

    }
}