package com.example.vikas.loginsqlitedata.Search_Item;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchProductViewHolderClass> {

    private List<UserMerchantUploadProductImage> userMerchantUploadProductImagesList;
    private Context context;

    public SearchRecyclerViewAdapter(Context context,List<UserMerchantUploadProductImage> userMerchantUploadProductImagesList) {
        this.userMerchantUploadProductImagesList = userMerchantUploadProductImagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchProductViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_view_for_all_fraggments_layout, parent, false);
        return new SearchProductViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProductViewHolderClass holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return userMerchantUploadProductImagesList.size();
    }

    public class SearchProductViewHolderClass extends RecyclerView.ViewHolder {

        private ImageView imageView, wishList, userCart;
        private CardView cardView;
        private TextView brandName, productName, productPrice, offOnProductInnPercentage;
        private LinearLayout layout_price_off_on_product, linear_layout_wish_list, linear_layout_cart;

        public SearchProductViewHolderClass(View itemView) {
            super(itemView);

            getIdForAllVariables();
        }


        private void getIdForAllVariables() {
            cardView = itemView.findViewById(R.id.card_view_product_view_for_all_fragment);
            imageView = itemView.findViewById(R.id.Image_View_all_fragments_products);
            brandName = itemView.findViewById(R.id.text_view_product_ocmpany_name_all_fragment_products);
            productName = itemView.findViewById(R.id.text_view_product_detail_all_fragment_products);
            productPrice = itemView.findViewById(R.id.text_view_product_price_all_fragment_products);
            layout_price_off_on_product = itemView.findViewById(R.id.layout_price);

            wishList = itemView.findViewById(R.id.image_view_wish_list_all_product);
            userCart = itemView.findViewById(R.id.image_view_user_cart_all_product);

            linear_layout_wish_list = itemView.findViewById(R.id.linear_layout_all_fragment_products_wish_list);
            linear_layout_cart = itemView.findViewById(R.id.linear_layout_all_fragment_products_cart);
        }
    }
}
