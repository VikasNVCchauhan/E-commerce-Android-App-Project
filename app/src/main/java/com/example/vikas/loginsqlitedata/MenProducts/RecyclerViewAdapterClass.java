package com.example.vikas.loginsqlitedata.MenProducts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserWishListAndCartFirebase;
import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterClass extends RecyclerView.Adapter<RecyclerViewAdapterClass.ViewHolderClass> {

    private onItemClickListener itemClickListener;
    private List<UserMerchantUploadProductImage> userMerchantUploadProductImagesList;
    private List<UserWishListAndCartFirebase> listHoldingAllDataForWishList;

    private Context context;

    public RecyclerViewAdapterClass(Context context, List<UserMerchantUploadProductImage> userMerchantUploadProductImageList,List<UserWishListAndCartFirebase> listHoldingAllDataForWishList) {
        this.context = context;
        this.userMerchantUploadProductImagesList = userMerchantUploadProductImageList;
        this.listHoldingAllDataForWishList=listHoldingAllDataForWishList;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_view_for_all_fraggments_layout, parent, false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

        UserMerchantUploadProductImage userMerchantUploadProductImage = userMerchantUploadProductImagesList.get(position);

        holder.brandName.setText(userMerchantUploadProductImage.getStringBrandname());
        holder.productName.setText(userMerchantUploadProductImage.getStringProductName());
        holder.productPrice.setText(userMerchantUploadProductImage.getStringProductPrice());

        Picasso.with(context)
                .load(userMerchantUploadProductImage.getImage())
                .placeholder(R.drawable.place_holder)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        for (int i = 0; i < listHoldingAllDataForWishList.size(); i++) {

            UserWishListAndCartFirebase userWishListAndCartFirebase=listHoldingAllDataForWishList.get(i);
            if (userMerchantUploadProductImage.getStringProductKey().equals(userWishListAndCartFirebase.getStringProductKey())) {
                holder.wishList.setTag("wishlist_added");
                holder.wishList.setImageResource(R.drawable.wishlist_added);
            }
        }
    }

    @Override
    public int getItemCount() {
        return userMerchantUploadProductImagesList.size();
    }

    class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView, wishList, userCart;
        private CardView cardView;
        private TextView brandName, productName, productPrice, offOnProductInnPercentage;
        private LinearLayout layout_price_off_on_product,linear_layout_wish_list,linear_layout_cart;

        public ViewHolderClass(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_product_view_for_all_fragment);
            imageView = itemView.findViewById(R.id.Image_View_all_fragments_products);
            brandName = itemView.findViewById(R.id.text_view_product_ocmpany_name_all_fragment_products);
            productName = itemView.findViewById(R.id.text_view_product_detail_all_fragment_products);
            productPrice = itemView.findViewById(R.id.text_view_product_price_all_fragment_products);
            layout_price_off_on_product = itemView.findViewById(R.id.layout_price);

            wishList = itemView.findViewById(R.id.image_view_wish_list_all_product);
            userCart = itemView.findViewById(R.id.image_view_user_cart_all_product);

            linear_layout_wish_list=itemView.findViewById(R.id.linear_layout_all_fragment_products_wish_list);
            linear_layout_cart=itemView.findViewById(R.id.linear_layout_all_fragment_products_cart);


            imageView.setOnClickListener(this);
            brandName.setOnClickListener(this);
            productPrice.setOnClickListener(this);
            productName.setOnClickListener(this);
            linear_layout_cart.setOnClickListener(this);
            linear_layout_wish_list.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v == brandName || v == imageView || v == productName || v == productPrice || v == layout_price_off_on_product) {
                if (itemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(position);
                    }
                }
            } else if (v == linear_layout_wish_list) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if(wishList.getTag().equals("wishlist"))
                    {
                        wishList.setImageResource(R.drawable.wishlist_added);
                        wishList.setTag("wishlist_added");
                    }
                    else if(wishList.getTag().equals("wishlist_added"))
                    {
                        wishList.setImageResource(R.drawable.wishlist);
                        wishList.setTag("wishlist");
                    }
                    itemClickListener.onItemWishList(position);
                }

            } else if (v == linear_layout_cart) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemCart(position);
                }
            }
        }
    }

    public interface onItemClickListener {

        void onItemClick(int position);

        void onItemWishList(int position);

        void onItemCart(int position);
    }

    public void setOnItemClickListener(onItemClickListener menProducsFragment) {
        itemClickListener = menProducsFragment;
    }
}
