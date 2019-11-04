package com.example.vikas.loginsqlitedata.MerchantOrders;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderedProductDetailMerchantOrderClass extends Fragment {


    private AppCompatActivity appCompatActivity;
    private ImageView productImage,productThreeDotMenuOption;
    private TextView productDescription, productBrandName, productPrice, productQuantity, productOrderDate, productPaymentType, productOrderArea, productAddressLine1, productAddressLine2, productAddressLine3, productAddressLine4;
    private String stringProductDescription, stringProductBrandName, stringProductPrice, stringProductQuantity, stringProductOrderDate, stringProductPaymentType, stringProductOrderArea, stringProductImage, stringProductAddressLine1, stringProductAddressLine2, stringProductAddressLine3, stringProductAddressLine4;

    @SuppressLint("ValidFragment")
    public OrderedProductDetailMerchantOrderClass(String stringProductDescription, String stringProductBrandName, String stringProductPrice, String stringProductQuantity, String stringProductOrderDate, String stringProductPaymentType, String stringProductOrderArea, String stringProductImage) {
        this.stringProductDescription = stringProductDescription;
        this.stringProductBrandName = stringProductBrandName;
        this.stringProductPrice = stringProductPrice;
        this.stringProductQuantity = stringProductQuantity;
        this.stringProductOrderDate = stringProductOrderDate;
        this.stringProductPaymentType = stringProductPaymentType;
        this.stringProductOrderArea = stringProductOrderArea;
        this.stringProductImage = stringProductImage;
    }

    public OrderedProductDetailMerchantOrderClass() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.after_click_on_ordered_product_merchant_order_layout, container, false);
        appCompatActivity = (AppCompatActivity) view.getContext();

        getIdForAllvariables(view);
        setDataToVariables();
        return view;
    }

    private void setDataToVariables() {
        productDescription.setText(stringProductDescription);
        productBrandName.setText(stringProductBrandName);
        productPrice.setText("Actual Cost : "+stringProductPrice);
        productQuantity.setText(stringProductQuantity);
        productOrderDate.setText(stringProductOrderDate);
        productPaymentType.setText(stringProductPaymentType);
        productOrderArea.setText(stringProductOrderArea);

        Picasso.with(appCompatActivity)
                .load(stringProductImage)
                .centerCrop()
                .fit()
                .into(productImage);
//                productAddressLine1,
//                productAddressLine2,
//                productAddressLine3,
//                productAddressLine4;

    }

    private void getIdForAllvariables(View view) {

        productDescription = view.findViewById(R.id.product_description_aftr_click_on_merchant_order_fragment);
        productBrandName = view.findViewById(R.id.brand_name_aftr_click_on_merchant_order_fragment);
        productPrice = view.findViewById(R.id.product_actual_cost_aftr_click_on_merchant_order_fragment);
        productQuantity = view.findViewById(R.id.product_quantity_aftr_click_on_merchant_order_fragment);
        productOrderDate = view.findViewById(R.id.product_orderded_date_click_on_merchant_order_fragment);
        productPaymentType = view.findViewById(R.id.product_payment_type_aftr_click_on_merchant_order_fragment);
        productOrderArea = view.findViewById(R.id.ordered_product_area_aftr_click_on_merchant_order_fragment);

        productImage = view.findViewById(R.id.image_view_after_click_on_merchant_order_fragment);
        productThreeDotMenuOption = view.findViewById(R.id.three_dot_option_aftr_click_on_merchant_order_fragment);
        productAddressLine1 = view.findViewById(R.id.address_line1_click_on_merchant_order_fragment);
        productAddressLine2 = view.findViewById(R.id.address_line2_click_on_merchant_order_fragment);
        productAddressLine3 = view.findViewById(R.id.address_line3_click_on_merchant_order_fragment);
        productAddressLine4 = view.findViewById(R.id.address_line4_click_on_merchant_order_fragment);
    }
}
