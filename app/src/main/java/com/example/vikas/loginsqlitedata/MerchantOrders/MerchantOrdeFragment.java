package com.example.vikas.loginsqlitedata.MerchantOrders;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserOrdersClass;
import com.example.vikas.loginsqlitedata.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantOrdeFragment extends Fragment implements RecyclerViewAdapterMerchantOrder.onItemClickListener {

    private RecyclerViewAdapterMerchantOrder recyclerViewAdapterMerchantOrder;
    private ArrayList<UserOrdersClass> userOrdersClassArrayList;
    private DatabaseReference databaseReferenceMerchant, databaseReferenceClint;
    private ValueEventListener valueEventListener;
    private ProgressBar progressBar;
    private AppCompatActivity appCompatActivity;
    private String phoneKey;
    private RecyclerView recyclerView;
    private RelativeLayout mRelativeLayout;
    private String stringProductDescription, stringProductBrandName, stringProductPrice, stringProductQuantity, stringProductOrderDate, stringProductPaymentType, stringProductOrderArea, stringProductImage;

    public MerchantOrdeFragment() {

        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MerchantOrdeFragment(String phoneKey) {
        this.phoneKey = phoneKey;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_orde, container, false);
        appCompatActivity = (AppCompatActivity) view.getContext();

        databaseReferenceMerchant = FirebaseDatabase.getInstance().getReference("MerchantAccout").child("MerchantOrders").child(phoneKey);
        databaseReferenceClint = FirebaseDatabase.getInstance().getReference("UserOrder");
        userOrdersClassArrayList = new ArrayList<>();

        getIdForAllVariables(view);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setDataTORecyclerView();

        recyclerViewAdapterMerchantOrder = new RecyclerViewAdapterMerchantOrder(getActivity(), userOrdersClassArrayList);
        recyclerView.setAdapter(recyclerViewAdapterMerchantOrder);
        recyclerViewAdapterMerchantOrder.setItemClickListener(MerchantOrdeFragment.this);

        return view;
    }

    private void setDataTORecyclerView() {
        getDataFromFireBase();

    }

    private void getDataFromFireBase() {
        valueEventListener = databaseReferenceMerchant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userOrdersClassArrayList.clear();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        UserOrdersClass userOrdersClass = ds.getValue(UserOrdersClass.class);
                        userOrdersClassArrayList.add(userOrdersClass);
                    }
                    recyclerViewAdapterMerchantOrder.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(appCompatActivity, "There is few problem in fetching data from firebase ", Toast.LENGTH_SHORT).show();
                Snackbar.make(mRelativeLayout, "Data Fetching Problem...", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getIdForAllVariables(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_merchant_order_detail);
        mRelativeLayout = view.findViewById(R.id.mainrl);
        progressBar = view.findViewById(R.id.progress_circuler_bar_merchant_order_fragment);
    }

    @Override
    public void onItemSelecte(int possition) {
        UserOrdersClass userOrdersClass = userOrdersClassArrayList.get(possition);

        stringProductDescription = userOrdersClass.getStringProductDescription();
        stringProductBrandName = userOrdersClass.getStringBrandname();
        stringProductPrice = userOrdersClass.getStringProductPrice();
        stringProductQuantity = userOrdersClass.getStringProductQuantity();
        stringProductOrderDate = userOrdersClass.getStringProductOrderDelivery();
        stringProductPaymentType = userOrdersClass.getStringPaymentType();
        stringProductOrderArea = "Chandigarh";
        stringProductImage = userOrdersClass.getImage();

        OrderedProductDetailMerchantOrderClass orderedProductDetailMerchantOrderClass = new OrderedProductDetailMerchantOrderClass(stringProductDescription, stringProductBrandName, stringProductPrice, stringProductQuantity, stringProductOrderDate, stringProductPaymentType, stringProductOrderArea, stringProductImage);
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_merchant, orderedProductDetailMerchantOrderClass).addToBackStack(null).commit();
    }

    @Override
    public void onStatusButtonSelected(int positoin) {
//        databaseReferenceMerchant.updateChildren();
//        databaseReferenceMerchant.updateChildren("")
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseReferenceMerchant.removeEventListener(valueEventListener);
    }
}
