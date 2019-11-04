package com.example.vikas.loginsqlitedata.CartBag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListAdapterClass extends ArrayAdapter<UserCartFirebaseData> {

    TextView brandNametextView, productnametextView, sizeOfProductTextview, quantityOfproductTextView,
            numberOfPeopleRatedProductTextview, ratingOfProductTextView, dateOfDeliverycanbeTextView,
            grandTotalTextview, offOnProductInRupeesTextView, offOnProductInPercentageTextView;
    ImageView imageView,optionMenuImageView;

    private Context context;
    int resource;
    private List<UserCartFirebaseData> userCartFirebaseDataList;


    public CustomListAdapterClass(@NonNull Context context, int resource, List<UserCartFirebaseData> userCartFirebaseDataList) {
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

        UserCartFirebaseData userCartFirebaseData=userCartFirebaseDataList.get(position);

        setDataToDestination(userCartFirebaseData);

        return view;
    }

    private void setDataToDestination(UserCartFirebaseData userCartFirebaseData) {
        brandNametextView.setText(userCartFirebaseData.getStringBrandname());
        productnametextView.setText(userCartFirebaseData.getStringProductName());
        grandTotalTextview.setText(userCartFirebaseData.getStringProductPrice());

        Picasso.with(context)
                .load(userCartFirebaseData.getImage())
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .fit()
                .into(imageView);
    }

    private void getIdForAllVariables(View view) {
        brandNametextView = view.findViewById(R.id.brand_name_text_view_list_item_of_cart);
        productnametextView = view.findViewById(R.id.product_name_text_view_list_item_of_cart);
        sizeOfProductTextview = view.findViewById(R.id.size_of_product_text_view_list_item_of_cart);
        quantityOfproductTextView = view.findViewById(R.id.quantity_of_product_text_view_list_item_of_cart);
        numberOfPeopleRatedProductTextview = view.findViewById(R.id.number_of_people_rated_product_text_view_list_item_of_cart);
        ratingOfProductTextView = view.findViewById(R.id.text_view_ratings_of_product_list_items);
        dateOfDeliverycanbeTextView = view.findViewById(R.id.delivery_date_list_item);
        grandTotalTextview = view.findViewById(R.id.grand_total_cost);
        offOnProductInRupeesTextView = view.findViewById(R.id.cost_off_on_product);
        offOnProductInPercentageTextView = view.findViewById(R.id.cost_off_on_product_in_percentage);

        imageView = view.findViewById(R.id.image_view_list_item_of_cart);
        optionMenuImageView=view.findViewById(R.id.option_menu_cart_list_item);

        ///----------------here we will set listner also-------------------///
    }
}
