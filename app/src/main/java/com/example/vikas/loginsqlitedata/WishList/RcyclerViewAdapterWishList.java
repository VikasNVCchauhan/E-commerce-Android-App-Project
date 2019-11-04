package com.example.vikas.loginsqlitedata.WishList;

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
import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RcyclerViewAdapterWishList extends RecyclerView.Adapter<RcyclerViewAdapterWishList.ViewHolderWishListClass>  {

    private onItemClickListener itemClickListener;
    private List<UserWishListAndCartFirebase> userWishListAndCartFirebasesList;
    private Context context;

    public RcyclerViewAdapterWishList(Context context,List<UserWishListAndCartFirebase> userWishListAndCartFirebasesList) {
        this.context=context;
        this.userWishListAndCartFirebasesList = userWishListAndCartFirebasesList;
    }

    @NonNull
    @Override
    public ViewHolderWishListClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wish_list_card_view_item_holder_layout, parent, false);
        return new ViewHolderWishListClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWishListClass holder, int position) {

        UserWishListAndCartFirebase userWishListAndCartFirebase=userWishListAndCartFirebasesList.get(position);
        holder.brandName.setText(userWishListAndCartFirebase.getStringBrandname());
        holder.productPrice.setText(userWishListAndCartFirebase.getStringProductPrice());
        holder.productName.setText(userWishListAndCartFirebase.getStringProductName());

        Picasso.with(context)
                .load(userWishListAndCartFirebase.getImage())
                .placeholder(R.drawable.place_holder)
                .centerCrop()
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return userWishListAndCartFirebasesList.size();
    }

    public class ViewHolderWishListClass extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView brandName, productName, productPrice, offOnProductInnPercentage;
        private LinearLayout layout_price_off_on_product,linear_layout_delete,linear_layout_cart;

        public ViewHolderWishListClass(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.Image_View_wish_list_fragments_products);
            brandName = itemView.findViewById(R.id.text_view_product_ocmpany_name_wish_list_fragment_products);
            productName = itemView.findViewById(R.id.text_view_product_detail_wish_list_fragment_products);
            productPrice = itemView.findViewById(R.id.text_view_product_price_wish_list_fragment_products);
            layout_price_off_on_product = itemView.findViewById(R.id.layout_price);


            linear_layout_delete=itemView.findViewById(R.id.image_view_remove_item_from_wish_list_wish_list_product);
            linear_layout_cart=itemView.findViewById(R.id.image_view_user_cart_wish_list_product);

            linear_layout_delete.setOnClickListener(this);
            linear_layout_cart.setOnClickListener(this);
            imageView.setOnClickListener(this);
            brandName.setOnClickListener(this);
            productName.setOnClickListener(this);
            productPrice.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==linear_layout_delete)
            {
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION)
                {
                    itemClickListener.onDeleteItemClick(position);
                }
            }
            else if(v==linear_layout_cart)
            {
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION)
                {
                    itemClickListener.onAddCartItemClick(position);
                }
            }
            else if(v==imageView || v==brandName || v==productName || v==productPrice)
            {
                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION)
                {
                    itemClickListener.onItemClick(position);
                }
            }
        }
    }
///--------we can set any name to this interfare which is implemented in wishlist fragment------//
    public interface onItemClickListener {
        public void onItemClick(int possition);
        public void onDeleteItemClick(int possition);
        public void onAddCartItemClick(int possition);
    }
    public void setItemClickListener(onItemClickListener wishListMainFragment)
    {
        itemClickListener=wishListMainFragment;
    }

}
