package com.example.vikas.loginsqlitedata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.LoginSignUp.GenrateOTP;
import com.example.vikas.loginsqlitedata.LoginSignUp.Login;
import com.example.vikas.loginsqlitedata.LoginSignUp.LoginWithFacebook;
import com.example.vikas.loginsqlitedata.LoginSignUp.LoginWithGoogle;

public class SignUpOptions extends AppCompatActivity implements View.OnClickListener {

    private String account_type;
    private Button signUpWithPhone,SignInWithFaceBook,SignInWithGoogle;
    private TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup_option);

        signUpWithPhone=findViewById(R.id.phone_option);
        SignInWithFaceBook=findViewById(R.id.facebook_option);
        SignInWithGoogle=findViewById(R.id.google_option);
        login=findViewById(R.id.text_Login_signup_option);

        account_type=getIntent().getStringExtra("ACCOUNT_OPTION");

        signUpWithPhone.setOnClickListener(this);
        SignInWithFaceBook.setOnClickListener(this);
        SignInWithGoogle.setOnClickListener(this);
        login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v==signUpWithPhone)
        {
            Intent intent=new Intent(SignUpOptions.this,GenrateOTP.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
            return;
        }
        else if(v==SignInWithFaceBook)
        {
            Intent intent=new Intent(SignUpOptions.this,LoginWithFacebook.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
            return;
        }
        else if(v== SignInWithGoogle)
        {
            Intent intent=new Intent(SignUpOptions.this,LoginWithGoogle.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
            return;
        }
        else if(v==login)
        {
            Intent intent=new Intent(SignUpOptions.this,Login.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
            return;
        }
    }

    @Override
    public void onBackPressed() {

    }
}