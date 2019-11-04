package com.example.vikas.loginsqlitedata.YourOrders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserOrdersClass;
import com.example.vikas.loginsqlitedata.MerchantOrders.OrderedProductDetailMerchantOrderClass;
import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterOrderClass extends RecyclerView.Adapter<RecyclerViewAdapterOrderClass.OrderViewHolderClass> {

    private Context context;
    private ArrayList<UserOrdersClass> userOrdersClassesArrayList;

    public RecyclerViewAdapterOrderClass(Context context, ArrayList<UserOrdersClass> userOrdersClassesArrayList) {
        this.context = context;
        this.userOrdersClassesArrayList = userOrdersClassesArrayList;
    }

    @NonNull
    @Override
    public OrderViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_lorders_list_iems_layout, parent, false);
        return new OrderViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolderClass holder, int position) {
        UserOrdersClass userOrdersClass=userOrdersClassesArrayList.get(position);
        holder.textViewProductBrandName.setText(userOrdersClass.getStringBrandname());
        holder.textViewProductPrice.setText("Price : "+userOrdersClass.getStringProductPrice());
        holder.textViewProductPaymentType.setText("Payment Type : ("+userOrdersClass.getStringPaymentType()+")");
        holder.textViewProductOrderDate.setText("Order Date : "+userOrdersClass.getStringProductOrderDelivery());
        holder.textViewProductDeliveryDate.setText("Expected Delivery Date : "+userOrdersClass.getStringProductExpectedDelivery());
        Picasso.with(context)
                .load(userOrdersClass.getImage())
                .placeholder(R.drawable.place_holder)
                .into(holder.imageViewProductImage);
    }

    @Override
    public int getItemCount() {
        return userOrdersClassesArrayList.size();
    }

    public class OrderViewHolderClass extends RecyclerView.ViewHolder {

        private TextView textViewProductBrandName, textViewProductPrice, textViewProductPaymentType, textViewProductOrderDate, textViewProductDeliveryDate;
        private ImageView imageViewProductImage;

        public OrderViewHolderClass(View itemView) {
            super(itemView);

            getIdForAllVariablea();
        }

        private void getIdForAllVariablea() {
            textViewProductBrandName = itemView.findViewById(R.id.product_name_order_list_item);
            textViewProductPrice = itemView.findViewById(R.id.product_price_order_list_item);
            textViewProductPaymentType = itemView.findViewById(R.id.payment_type_order_list_item);
            textViewProductOrderDate = itemView.findViewById(R.id.product_date_ordered_order_list_item);
            textViewProductDeliveryDate = itemView.findViewById(R.id.product_date_expected_delivery_order_list_item);
            imageViewProductImage = itemView.findViewById(R.id.order_list_view_image);
        }
    }
}
