package com.example.vikas.loginsqlitedata.MerchantProducts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterMerchantProduct extends RecyclerView.Adapter<RecyclerViewAdapterMerchantProduct.MerchantProductViewHolder> {

    private Context context;
    private ArrayList<UserMerchantUploadProductImage> userMerchantUploadProductImagesArrayList;

    public RecyclerViewAdapterMerchantProduct(Context context, ArrayList<UserMerchantUploadProductImage> userMerchantUploadProductImagesArrayList) {
        this.context = context;
        this.userMerchantUploadProductImagesArrayList = userMerchantUploadProductImagesArrayList;
    }

    @NonNull
    @Override
    public MerchantProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_merchant_product, parent, false);
        return new MerchantProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantProductViewHolder holder, int position) {
        UserMerchantUploadProductImage userMerchantUploadProductImage = userMerchantUploadProductImagesArrayList.get(position);
        holder.textViewBrandName.setText(userMerchantUploadProductImage.getStringBrandname());
        holder.textViewProductCoat.setText(userMerchantUploadProductImage.getStringProductPrice());
        holder.textViewProductDescription.setText(userMerchantUploadProductImage.getStringProductDescription());
        Picasso.with(context)
                .load(userMerchantUploadProductImage.getImage())
                .fit()
                .placeholder(R.drawable.place_holder)
                .centerCrop()
                .into(holder.imageViewProductImage);
    }

    @Override
    public int getItemCount() {
        return userMerchantUploadProductImagesArrayList.size();
    }

    public class MerchantProductViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewBrandName, textViewProductDescription, textViewProductCoat;
        private ImageView imageViewProductImage, imageViewDeletionProduct, imageViewProductFavourite;

        public MerchantProductViewHolder(View itemView) {
            super(itemView);
            getIdForAllVariables();
        }

        private void getIdForAllVariables() {
            textViewBrandName = itemView.findViewById(R.id.text_view_product_ocmpany_nametext_view_product_detail_item_view_merchant_product);
            textViewProductDescription = itemView.findViewById(R.id.text_view_product_detail_item_view_merchant_product);
            textViewProductCoat = itemView.findViewById(R.id.text_view_product_price_item_view_merchant_product);
            imageViewProductImage = itemView.findViewById(R.id.Image_View_all_fragments_products);
            imageViewDeletionProduct = itemView.findViewById(R.id.image_view_delete_item_item_view_merchant_product);
            imageViewProductFavourite = itemView.findViewById(R.id.image_view_favourite_item_view_merchant_product);
        }
    }
}
