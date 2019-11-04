package com.example.vikas.loginsqlitedata.MerchantOrders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserOrdersClass;
import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterMerchantOrder extends RecyclerView.Adapter<RecyclerViewAdapterMerchantOrder.MerchantOrderViewHolder> {

    private onItemClickListener itemClickListener;
    Context context;
    ArrayList<UserOrdersClass> userOrdersClassArrayList;

    public RecyclerViewAdapterMerchantOrder(Context context, ArrayList<UserOrdersClass> userOrdersClassArrayList) {
        this.context = context;
        this.userOrdersClassArrayList = userOrdersClassArrayList;
    }

    @NonNull
    @Override
    public MerchantOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_merchant_order_detail, parent, false);
        return new MerchantOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MerchantOrderViewHolder holder, int position) {

        UserOrdersClass userOrdersClass = userOrdersClassArrayList.get(position);

        holder.brand_Name_merchant_order_item.setText(userOrdersClass.getStringBrandname());
        holder.payment_type_merchant_order_item.setText(userOrdersClass.getStringPaymentType());
        holder.product_description_merchant_order_item.setText(userOrdersClass.getStringProductDescription());
        holder.product_price_merchant_order_item.setText(userOrdersClass.getStringProductPrice());
        holder.product_quantity_merchant_order_item.setText(userOrdersClass.getStringProductQuantity());
        holder.order_number_merchant_order_item.setText(userOrdersClass.getStringOrderNumber());
        holder.order_date_merchant_order_item.setText(userOrdersClass.getStringProductOrderDelivery());
        holder.size_merchant_order_item.setText(userOrdersClass.getStringProductSize());

        Picasso.with(context)
                .load(userOrdersClass.getImage())
                .centerCrop()
                .fit()
                .placeholder(R.drawable.place_holder)
                .into(holder.product_image_merchant_order_item);

    }

    @Override
    public int getItemCount() {
        return userOrdersClassArrayList.size();
    }

    public class MerchantOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Button productStatus;
        public TextView brand_Name_merchant_order_item, payment_type_merchant_order_item, product_price_merchant_order_item,
                product_description_merchant_order_item, order_number_merchant_order_item,
                order_date_merchant_order_item, size_merchant_order_item, product_quantity_merchant_order_item;
        public ImageView product_image_merchant_order_item;
        public RelativeLayout relativeLayout, relativeLayoutConfirm;

        public MerchantOrderViewHolder(View itemView) {
            super(itemView);
            getIdForAllVariables();

            relativeLayout.setOnClickListener(this);
            productStatus.setOnClickListener(this);
        }

        private void getIdForAllVariables() {

            brand_Name_merchant_order_item = itemView.findViewById(R.id.brand_name_merchant_order_detail);
            payment_type_merchant_order_item = itemView.findViewById(R.id.product_payment_type_merchant_order_detail);
            product_price_merchant_order_item = itemView.findViewById(R.id.product_price_merchant_order_detail);
            product_description_merchant_order_item = itemView.findViewById(R.id.product_name_merchant_order_detail);
            order_number_merchant_order_item = itemView.findViewById(R.id.product_order_no_merchant_order_detail);
            order_date_merchant_order_item = itemView.findViewById(R.id.product_date_of_order_merchant_order_detail);
            size_merchant_order_item = itemView.findViewById(R.id.product_size_merchant_order_detail);
            product_quantity_merchant_order_item = itemView.findViewById(R.id.product_quantity_of_order_merchant_order_detail);
            product_image_merchant_order_item = itemView.findViewById(R.id.pruduct_image_merchant_order_detail);

            productStatus = itemView.findViewById(R.id.product_status_button_merchant_order_detail);
            relativeLayout = itemView.findViewById(R.id.layout_order_merchant);
            relativeLayoutConfirm = itemView.findViewById(R.id.product_status_layout_merchant_order_detail);


            //  relativeLayout = itemView.findViewById(R.id.layout_order_merchant);
        }

        @Override
        public void onClick(View v) {
            if (v == relativeLayout) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemSelecte(position);
                }
            } else if (v == productStatus) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    relativeLayoutConfirm.setVisibility(View.GONE);
                    itemClickListener.onStatusButtonSelected(position);
                }
            }
        }
    }

    public interface onItemClickListener {
        public void onItemSelecte(int possition);

        public void onStatusButtonSelected(int positoin);
    }

    public void setItemClickListener(onItemClickListener merchantOrderFragment) {
        itemClickListener = merchantOrderFragment;
    }
}
