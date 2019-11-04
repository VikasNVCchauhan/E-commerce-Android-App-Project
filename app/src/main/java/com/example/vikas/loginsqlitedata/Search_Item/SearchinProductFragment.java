package com.example.vikas.loginsqlitedata.Search_Item;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchinProductFragment extends Fragment {

    private RelativeLayout relativeLayout;
    private FrameLayout frameLayout;
    private String searchText, phoneKey;
    private AppCompatActivity appCompatActivity;
    private DatabaseReference databaseReference;
    private List<UserMerchantUploadProductImage> userMerchantUploadProductImageList;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public SearchinProductFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SearchinProductFragment(String searchText, String phoneKey) {
        this.searchText = searchText;
        this.phoneKey = phoneKey;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searchin_product, container, false);
        appCompatActivity = (AppCompatActivity) view.getContext();
        getIdForAllVariables(view);

        progressBar.setVisibility(View.VISIBLE);
        userMerchantUploadProductImageList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("MerchantAccout").child("MerchantProducts");

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        searchRecyclerViewAdapter=new SearchRecyclerViewAdapter(getContext(),userMerchantUploadProductImageList);
        recyclerView.setAdapter(searchRecyclerViewAdapter);


        SerarchData(searchText);
        return view;
    }

    private void getIdForAllVariables(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_search_product_fragment);
        progressBar = view.findViewById(R.id.progressBarInmenProducts);

        frameLayout = view.findViewById(R.id.fragment_search);
        relativeLayout = view.findViewById(R.id.relative_layout_search_fragment);
    }

    private void SerarchData(String searchText) {
//
//        FirebaseRecyclerAdapter<UserMerchantUploadProductImage,SearchRecyclerViewAdapter.SearchProductViewHolderClass> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<UserMerchantUploadProductImage, SearchRecyclerViewAdapter.SearchProductViewHolderClass>(
//                UserMerchantUploadProductImage.class,
//                R.layout.product_view_for_all_fraggments_layout,
//                SearchRecyclerViewAdapter.SearchProductViewHolderClass.class,
//                databaseReference
//        ) {
//            @Override
//            protected void populateViewHolder(SearchRecyclerViewAdapter.SearchProductViewHolderClass viewHolder, UserMerchantUploadProductImage model, int position) {
//                viewHolder.setDetail(appCompatActivity,model.getImage(),model.getStringBrandname(),
//                        model.getStringProductPrice(),model.getStringProductName());
//            }
//        };
//        firebaseRecyclerAdapter.notifyDataSetChanged();
//        recyclerView.setAdapter(firebaseRecyclerAdapter);
        Query firebaseQuery = databaseReference.orderByChild("stringBrandname").startAt(searchText).endAt(searchText + "\uf8ff");
        firebaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userMerchantUploadProductImageList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserMerchantUploadProductImage userMerchantUploadProductImage = ds.getValue(UserMerchantUploadProductImage.class);
                    userMerchantUploadProductImageList.add(userMerchantUploadProductImage);
                }
                if (userMerchantUploadProductImageList.size() != 0) {
                    progressBar.setVisibility(View.INVISIBLE);
                    searchRecyclerViewAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getContext(), "No data Found...", Toast.LENGTH_SHORT).show();
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchProductMainFragment(phoneKey)).addToBackStack(null).commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(frameLayout, "Some Error found during data loading", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
