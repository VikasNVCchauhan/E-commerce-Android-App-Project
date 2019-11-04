package com.example.vikas.loginsqlitedata.PayMentMethods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserOrdersClass;
import com.example.vikas.loginsqlitedata.HomeScreen.Home_Main;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.YourOrders.OrderMainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentPortal extends AppCompatActivity implements PaymentResultListener {

    private Date date;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceCustomer, databaseReferenceMerchant;
    private String intentProductPrice, intentProductSize = "S", intentProductBrandName, intentProductDescription, intentProductImage, intentProductCategory, intentProductName, intentProductGender, intentProductMerchantPhoneKey, intentProductDiscount, intentProductQuantity, intentProductCount, intentProductProductKey, CheckPhone;

    private String orderNumber, productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vikas.loginsqlitedata.R.layout.payment_portal);

        date = new Date();

        orderNumber = getIntent().getStringExtra("OrderNo");
        CheckPhone = getIntent().getStringExtra("CheckPhone");
        productPrice = getIntent().getStringExtra("ProductPayablePrice");
        intentProductMerchantPhoneKey = getIntent().getStringExtra("ProductMerchantPhoneKey");
        intentProductDescription = getIntent().getStringExtra("ProductDescription");
        intentProductImage = getIntent().getStringExtra("ProductImage");
        intentProductCategory = getIntent().getStringExtra("ProductCategory");
        intentProductName = getIntent().getStringExtra("ProductName");
        intentProductGender = getIntent().getStringExtra("ProductGender");
        intentProductBrandName = getIntent().getStringExtra("ProductBrandName");
        intentProductDiscount = getIntent().getStringExtra("Discount");
        intentProductQuantity = getIntent().getStringExtra("Quantity");
        intentProductCount = getIntent().getStringExtra("TotalProduct");
        intentProductProductKey = getIntent().getStringExtra("productKey");
        intentProductPrice=getIntent().getStringExtra("productPrice")

        ;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceCustomer = firebaseDatabase.getReference("UserOrder");
        databaseReferenceMerchant = firebaseDatabase.getReference("MerchantAccout").child("MerchantOrders").child(intentProductMerchantPhoneKey);
        startPayment();
    }

    private void startPayment() {

        int price = Integer.parseInt(productPrice);
        Checkout checkout = new Checkout();
        //        checkout.setImage(Picasso.with(this).load(intentProductImage).centerCrop().fit());
        final Activity activity = this;

        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("Description", orderNumber);
            jsonObject.put("amount", price * 100);
            jsonObject.put("Currency", "INR");

            checkout.open(activity, jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(this, "Payment Done Successfully...", Toast.LENGTH_SHORT).show();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String productOrderDate = simpleDateFormat.format(date);

        Toast.makeText(this, "" + productOrderDate, Toast.LENGTH_SHORT).show();
        try {
            UserOrdersClass userOrdersClass = new UserOrdersClass(intentProductImage, "Online Payment", orderNumber, intentProductBrandName, intentProductProductKey, intentProductName, intentProductPrice, productOrderDate, "12/3/2018", CheckPhone, intentProductMerchantPhoneKey, intentProductDescription, intentProductCategory, intentProductGender, intentProductDiscount, intentProductQuantity, intentProductCount, intentProductSize,"CONFIRMATION_PENDING");
            databaseReferenceCustomer.child(CheckPhone).child(orderNumber).setValue(userOrdersClass);
            databaseReferenceMerchant.child(orderNumber).setValue(userOrdersClass);

            OrderMainActivity orderMainActivity = new OrderMainActivity(CheckPhone);

            Intent intent = new Intent(PaymentPortal.this, Home_Main.class);
            intent.putExtra("PHONE", CheckPhone);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, "There is error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,orderMainActivity,orderMainActivity.getClass().getSimpleName()).addToBackStack(null).commit();
        return;
    }

    @Override
    public void onPaymentError(int i, String s) {
        Intent intent = new Intent(this, PaymentMain.class);
        startActivity(intent);
        Toast.makeText(this, "There is some problem during payment", Toast.LENGTH_SHORT).show();
    }
}
