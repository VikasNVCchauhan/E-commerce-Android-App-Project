package com.example.vikas.loginsqlitedata.MerchantProducts;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.MerchantAddProductToAppFragment;
import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantProductsFragment extends Fragment {


    private ArrayList<UserMerchantUploadProductImage> userMerchantUploadProductImagesArrayList;
    private RecyclerViewAdapterMerchantProduct recyclerViewAdapterMerchantProduct;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private String phoneKey;
    private AppCompatActivity appCompatActivity;

    public MerchantProductsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MerchantProductsFragment(String phoneKey) {
        this.phoneKey = phoneKey;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_products, container, false);
        appCompatActivity = (AppCompatActivity) view.getContext();

        getIdForAllVariables(view);
        userMerchantUploadProductImagesArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        databaseReference = FirebaseDatabase.getInstance().getReference("MerchantAccout").child("MerchantProducts");

        getAllDataFromFirebase();
        return view;
    }

    private void getAllDataFromFirebase() {

        Query firebaseQuery = databaseReference.orderByChild("phoneKey").startAt(phoneKey).endAt(phoneKey + "\uf8ff");
        firebaseQuery.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userMerchantUploadProductImagesArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserMerchantUploadProductImage userMerchantUploadProductImage = ds.getValue(UserMerchantUploadProductImage.class);
                    userMerchantUploadProductImagesArrayList.add(userMerchantUploadProductImage);
                }
                if (userMerchantUploadProductImagesArrayList.size() != 0) {
                    recyclerViewAdapterMerchantProduct = new RecyclerViewAdapterMerchantProduct(getActivity(), userMerchantUploadProductImagesArrayList);
                    recyclerView.setAdapter(recyclerViewAdapterMerchantProduct);
                    recyclerViewAdapterMerchantProduct.notifyDataSetChanged();
                } else {
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_merchant, new MerchantAddProductToAppFragment(phoneKey)).addToBackStack(null).commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getIdForAllVariables(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_merchant_product);
    }

}
