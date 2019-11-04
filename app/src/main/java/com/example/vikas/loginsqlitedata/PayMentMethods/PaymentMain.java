package com.example.vikas.loginsqlitedata.PayMentMethods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserOrdersClass;
import com.example.vikas.loginsqlitedata.HomeScreen.Home_Main;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.YourOrders.OrderMainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PaymentMain extends AppCompatActivity implements View.OnClickListener {

    private Date date;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceCustomer,databaseReferenceMerchant;
    private String uniqueID;
    private Button onlinePayment, cashOnDelivery;
    private TextView productNameTextView, productCountTextView, gstTextView, quantityTextView, offOnProductTextView, actualCostTextView, totalPayableAmountTextView, payAmountBoldTextView, promoCodeTextView;
    private String intentProductPrice,intentProductSize="S",intentProductBrandName,intentProductDescription,intentProductImage,intentProductCategory,intentProductName,intentProductGender,intentProductMerchantPhoneKey, intentProductDiscount, intentProductQuantity, intentProductCount,intentProductProductKey,intentProductCheckPhone;
    private int totalPayAbleAmount;
    private String productOrderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_option_activity);

        date = new Date();
        intentProductMerchantPhoneKey = getIntent().getStringExtra("ProductMerchantPhoneKey");
        intentProductDescription=getIntent().getStringExtra("ProductDescription");
        intentProductImage = getIntent().getStringExtra("ProductImage");
        intentProductCategory = getIntent().getStringExtra("ProductCategory");
        intentProductName = getIntent().getStringExtra("ProductName");
        intentProductGender = getIntent().getStringExtra("ProductGender");
        intentProductBrandName=getIntent().getStringExtra("ProductBrandName");
        intentProductPrice = getIntent().getStringExtra("ProductPayablePrice");
        intentProductDiscount = getIntent().getStringExtra("Discount");
        intentProductQuantity = getIntent().getStringExtra("Quantity");
        intentProductCount = getIntent().getStringExtra("TotalProduct");
        intentProductProductKey= getIntent().getStringExtra("productKey");;
        intentProductCheckPhone= getIntent().getStringExtra("checkPhone");;

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReferenceCustomer=firebaseDatabase.getReference("UserOrder");
        databaseReferenceMerchant=firebaseDatabase.getReference("MerchantAccout").child("MerchantOrders").child(intentProductMerchantPhoneKey);


        getIdForAllVariables();
        setDataToTheVariables();

        uniqueID = UUID.randomUUID().toString();

        onlinePayment.setOnClickListener(this);
        cashOnDelivery.setOnClickListener(this);
        promoCodeTextView.setOnClickListener(this);
    }

    private void setDataToTheVariables() {

        int discount = Integer.parseInt(intentProductDiscount);
        int price = Integer.parseInt(intentProductPrice.replace(",","")); //to remove comma

        int discountAmount = price * (discount/100);
        int gst = price * (12 / 100);
        totalPayAbleAmount = price + gst;
        productCountTextView.setText(intentProductCount);
        gstTextView.setText(intentProductCount);
        quantityTextView.setText(intentProductQuantity);
        offOnProductTextView.setText(String.valueOf(discountAmount));
        actualCostTextView.setText(String.valueOf(price));
        totalPayableAmountTextView.setText(String.valueOf(totalPayAbleAmount));
        payAmountBoldTextView.setText(String.valueOf("PAY "+totalPayAbleAmount+" Rs."));
    }

    private void getIdForAllVariables() {
        onlinePayment = (Button) findViewById(R.id.online_payment);
        cashOnDelivery = (Button) findViewById(R.id.cod);

        productCountTextView = (TextView) findViewById(R.id.product_name_payment_activity);
        gstTextView = (TextView) findViewById(R.id.gst_payment_activity);
        quantityTextView = (TextView) findViewById(R.id.quantity_payment_activity);
        offOnProductTextView = (TextView) findViewById(R.id.off_on_product_payment_activity);
        actualCostTextView = (TextView) findViewById(R.id.actual_cost_payment_activity);
        totalPayableAmountTextView = (TextView) findViewById(R.id.total_payable_amount_payment_activity);
        payAmountBoldTextView = (TextView) findViewById(R.id.product_amount_payment_activity);
        promoCodeTextView = (TextView) findViewById(R.id.promocode_payment_activity);


    }

    @Override
    public void onClick(View v) {
        if (v == onlinePayment) {

            Intent intent=new Intent(PaymentMain.this,PaymentPortal.class);

            intent.putExtra("OrderNo",uniqueID);
            intent.putExtra("CheckPhone",intentProductCheckPhone);
            intent.putExtra("ProductPayablePrice",String.valueOf(totalPayAbleAmount));
            intent.putExtra("ProductBrandName", intentProductBrandName);
            intent.putExtra("ProductDescription", intentProductDescription);
            intent.putExtra("ProductImage", intentProductImage);
            intent.putExtra("ProductCategory", intentProductCategory);
            intent.putExtra("Quantity", "1");
            intent.putExtra("ProductName", intentProductName);
            intent.putExtra("ProductGender", intentProductGender);
            intent.putExtra("ProductMerchantPhoneKey", intentProductMerchantPhoneKey);
            intent.putExtra("productKey", intentProductProductKey);
            intent.putExtra("productPrice", intentProductProductKey);

            startActivity(intent);

        } else if (v == cashOnDelivery) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            String productOrderDate = simpleDateFormat.format(date);
            UserOrdersClass userOrdersClass = new UserOrdersClass(intentProductImage,"COD",uniqueID, intentProductBrandName, intentProductProductKey, intentProductName, intentProductPrice, productOrderDate, "12/3/2018", intentProductCheckPhone, intentProductMerchantPhoneKey, intentProductDescription, intentProductCategory, intentProductGender, intentProductDiscount, intentProductQuantity, intentProductCount,intentProductSize,"CONFIRMATION_PENDING");
            databaseReferenceCustomer.child(intentProductCheckPhone).child(uniqueID).setValue(userOrdersClass);
            databaseReferenceMerchant.child(uniqueID).setValue(userOrdersClass);

            Toast.makeText(this, "Order Done Successfully", Toast.LENGTH_SHORT).show();

            Intent intent =new Intent(PaymentMain.this,Home_Main.class);
            intent.putExtra("PHONE",intentProductCheckPhone);
            startActivity(intent);
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new OrderMainActivity(intentProductCheckPhone,productOrderNumber,intentProductProductKey)).addToBackStack(null).commit();
        } else if (v == promoCodeTextView) {
            Toast.makeText(this, "We are still working on it", Toast.LENGTH_SHORT).show();
        }
    }
}
