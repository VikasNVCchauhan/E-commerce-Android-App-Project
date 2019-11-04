package com.example.vikas.loginsqlitedata.CartBag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomGridAdapterClass extends ArrayAdapter implements View.OnClickListener {

    private onItemClicklistner onItemClicklistner;
    private TextView brandNametextView, productnametextView, productPricetextView,
            offOnProductInRupeesTextView, offOnProductInPercentageTextView;
    private ImageView imageView;
    private LinearLayout wishListlayout, userCartlayout;
    private int possionOnClick;

    private Context context;
    int resource;
    private List<UserMerchantUploadProductImage> userCartFirebaseDataList;

    public CustomGridAdapterClass(@NonNull Context context, int resource, @NonNull List<UserMerchantUploadProductImage> userCartFirebaseDataList) {
        super(context, resource, userCartFirebaseDataList);

        this.context = context;
        this.resource = resource;
        this.userCartFirebaseDataList = userCartFirebaseDataList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(resource, null);

        getIdForAllVariables(view);

        UserMerchantUploadProductImage userMerchantUploadProductImage = userCartFirebaseDataList.get(position);

        setValuesToVariables(userMerchantUploadProductImage);

        brandNametextView.setOnClickListener(this);
        productnametextView.setOnClickListener(this);
        productPricetextView.setOnClickListener(this);
        offOnProductInPercentageTextView.setOnClickListener(this);
        imageView.setOnClickListener(this);
        wishListlayout.setOnClickListener(this);
        userCartlayout.setOnClickListener(this);

        return view;
    }

    private void setValuesToVariables(UserMerchantUploadProductImage userMerchantUploadProductImage) {

        brandNametextView.setText(userMerchantUploadProductImage.getStringBrandname());
        productnametextView.setText(userMerchantUploadProductImage.getStringProductName());
        productPricetextView.setText(userMerchantUploadProductImage.getStringProductPrice());

        Picasso.with(context)
                .load(userMerchantUploadProductImage.getImage())
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .fit()
                .into(imageView);

    }


    private void getIdForAllVariables(View view) {
        brandNametextView = view.findViewById(R.id.text_view_product_ocmpany_name_cart_fragment_products);
        productnametextView = view.findViewById(R.id.text_view_product_detail_cart_fragment_products);
        productPricetextView = view.findViewById(R.id.text_view_product_price_cart_fragment_products);
        offOnProductInPercentageTextView = view.findViewById(R.id.text_view_product_discount_cart_fragment_products);

        imageView = view.findViewById(R.id.Image_View_cart_fragments_products);
        wishListlayout = view.findViewById(R.id.linear_layout_user_wish_list_cart_fragment);
        userCartlayout = view.findViewById(R.id.linear_layout_add_to_cart_cart_fragment);
    }

    @Override
    public void onClick(View v) {
        if (v == brandNametextView || v == productnametextView || v == productPricetextView || v == offOnProductInPercentageTextView || v == offOnProductInRupeesTextView || v == imageView) {

            Toast.makeText(context, "data is here", Toast.LENGTH_SHORT).show();
            onItemClicklistner.onItemClick();
        } else if (v == wishListlayout) {
            onItemClicklistner.onWishlistClick();
        } else if (v == userCartlayout) {
            onItemClicklistner.onCartClick();
            }
    }

    public interface onItemClicklistner {
        public void onItemClick();

        public void onWishlistClick();

        public void onCartClick();
    }

    public void setOnItemClicklistner(onItemClicklistner clicklistner) {
        onItemClicklistner = clicklistner;
    }

}
