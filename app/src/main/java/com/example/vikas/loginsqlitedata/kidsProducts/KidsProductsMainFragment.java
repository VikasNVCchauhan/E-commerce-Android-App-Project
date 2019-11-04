package com.example.vikas.loginsqlitedata.kidsProducts;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.vikas.loginsqlitedata.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class KidsProductsMainFragment extends Fragment {


    private LinearLayout switchLayout,filterLayout,shortByLayout;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private BottomNavigationView bottomNavigationView;
    private String checkPhone;
    @SuppressLint("ValidFragment")
    public KidsProductsMainFragment(String checkPhone) {
        this.checkPhone=checkPhone;
        // Required empty public constructor
    }

    public KidsProductsMainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_kids_products_main, container, false);

        getIdForAllVariables(view);

        return view;
    }

    private void getIdForAllVariables(View view) {
        recyclerView=view.findViewById(R.id.recycler_view_kids_products);
        switchLayout=view.findViewById(R.id.switch_layout);
        filterLayout=view.findViewById(R.id.filter_layout);
        shortByLayout=view.findViewById(R.id.sort_layout);
    }

}
