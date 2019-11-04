package com.example.vikas.loginsqlitedata.WomanProducts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vikas.loginsqlitedata.R;
public class WomansProductMainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private String checkPhone;

    @SuppressLint("ValidFragment")
    public WomansProductMainFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public WomansProductMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_womans_product_main, container, false);
        return view;
    }
}
