package com.example.vikas.loginsqlitedata.SportsProducts;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vikas.loginsqlitedata.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SportsProductsMainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private String checkPhone;

    @SuppressLint("ValidFragment")
    public SportsProductsMainFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public SportsProductsMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sports_products_main, container, false);
    }

}
