package com.example.vikas.loginsqlitedata.FashionProduct;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vikas.loginsqlitedata.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FashionProductsFragment extends Fragment {

    private String checkPhone;

    @SuppressLint("ValidFragment")
    public FashionProductsFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public FashionProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fashion_products, container, false);
    }

}
