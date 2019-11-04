package com.example.vikas.loginsqlitedata;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AbstractRunTimePermissions implements View.OnClickListener {

    private int checkPermissionGranted;
    private static final int REQEST_PERMISSION = 10;
    private Button buyerButtonOption, sellerButtonOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buyerButtonOption = findViewById(R.id.buyer_account);
        sellerButtonOption = findViewById(R.id.seller_account);

        buyerButtonOption.setOnClickListener(this);
        sellerButtonOption.setOnClickListener(this);
        requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS}, R.string.permissionMessage, REQEST_PERMISSION);

    }

    @Override
    public void onClick(View v) {
        if (v == buyerButtonOption) {
            if (checkPermissionGranted == 1) {
                Intent intent = new Intent(MainActivity.this, OnlyLoginSignUp.class);
                intent.putExtra("ACCOUNT_OPTION", "1");
                startActivity(intent);
                return;
            } else {
                requestForPermissions();
            }
        } else if (v == sellerButtonOption) {
            if (checkPermissionGranted == 1) {
                Intent intent = new Intent(MainActivity.this, OnlyLoginSignUp.class);
                intent.putExtra("ACCOUNT_OPTION", "0");
                startActivity(intent);
                return;
            } else {
                requestForPermissions();
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        checkPermissionGranted = 1;
    }

    public void requestForPermissions() {
        requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS}, R.string.permissionMessage, REQEST_PERMISSION);

    }
}
