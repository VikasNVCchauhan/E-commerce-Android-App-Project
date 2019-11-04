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

import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserWishListAndCartFirebase;
import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomGridAdapterClassForWishLis extends ArrayAdapter {

    private TextView brandNametextView, productnametextView,productPricetextView,
            offOnProductInRupeesTextView, offOnProductInPercentageTextView;
    private ImageView imageView;
    private LinearLayout wishListlayout,userCartlayout;

    private Context context;
    int resource;
    private List<UserWishListAndCartFirebase> listHoldingAllDataForWishList;

    public CustomGridAdapterClassForWishLis(@NonNull Context context, int resource, @NonNull List<UserWishListAndCartFirebase> listHoldingAllDataForWishList) {
        super(context, resource,listHoldingAllDataForWishList);

        this.context=context;
        this.resource=resource;
        this.listHoldingAllDataForWishList=listHoldingAllDataForWishList;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(context).inflate(resource,null);

        getIdforAllVariables(view);

        UserWishListAndCartFirebase userWishListAndCartFirebase=listHoldingAllDataForWishList.get(position);

        setValuesToVariables(userWishListAndCartFirebase);

        return view;
    }

    private void setValuesToVariables(UserWishListAndCartFirebase userWishListAndCartFirebase) {

        brandNametextView.setText(userWishListAndCartFirebase.getStringBrandname());
        productnametextView.setText(userWishListAndCartFirebase.getStringProductName());
        productPricetextView.setText(userWishListAndCartFirebase.getStringProductPrice());

        Picasso.with(context)
                .load(userWishListAndCartFirebase.getImage())
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .fit()
                .into(imageView);

    }
    private void getIdforAllVariables(View view) {

        brandNametextView = view.findViewById(R.id.text_view_product_ocmpany_name_wish_list_grid_view_cart_fragment_products);
        productnametextView = view.findViewById(R.id.text_view_product_detail_wish_list_grid_view_cart_fragment_products);
        productPricetextView = view.findViewById(R.id.text_view_product_price_wish_list_grid_view_cart_fragment_products);
        offOnProductInPercentageTextView = view.findViewById(R.id.text_view_product_discount_wish_list_grid_view_cart_fragment_products);

        imageView = view.findViewById(R.id.Image_View_wish_list_grid_view_cart_fragments_products);
        wishListlayout= view.findViewById(R.id.linear_layout_user_wish_list_wish_list_grid_view_cart_fragment);
        userCartlayout= view.findViewById(R.id.linear_layout_add_to_cart_wish_list_grid_view_cart_fragment);
    }
}
