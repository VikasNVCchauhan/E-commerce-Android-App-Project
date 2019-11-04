package com.example.vikas.loginsqlitedata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.vikas.loginsqlitedata.LoginSignUp.Login;

public class OnlyLoginSignUp extends AppCompatActivity implements View.OnClickListener {

    private String account_type;
    private Button signup,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_only_login_signup);

        signup=findViewById(R.id.signUp_option_only_login_sign_up);
        login=findViewById(R.id.loginHere_option_only_login_sign_up);

        account_type=getIntent().getStringExtra("ACCOUNT_OPTION");

        signup.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==signup)
        {
            Intent intent=new Intent(OnlyLoginSignUp.this,SignUpOptions.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
            return;
        }
        else if(v==login)
        {
            Intent intent=new Intent(OnlyLoginSignUp.this,Login.class);
            intent.putExtra("ACCOUNT_OPTION",account_type);
            startActivity(intent);
            return;
        }
    }
}