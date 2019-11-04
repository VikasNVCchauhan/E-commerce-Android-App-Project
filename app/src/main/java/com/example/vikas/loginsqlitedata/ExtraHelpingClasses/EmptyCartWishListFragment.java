package com.example.vikas.loginsqlitedata.ExtraHelpingClasses;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.HomeScreen.Home_Main;
import com.example.vikas.loginsqlitedata.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class EmptyCartWishListFragment extends Fragment implements View.OnClickListener {

    private AppCompatActivity appCompatActivity;
    private BottomNavigationView bottomNavigationView;
    private TextView selectionOfCartOrWishList, shopNow;
    private String textViewData;
    private String checkPhone;


    @SuppressLint("ValidFragment")
    public EmptyCartWishListFragment(String textViewData, String checkPhone) {
        this.textViewData = textViewData;
        this.checkPhone = checkPhone;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_cart_wish_list, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();
        bottomNavigationView = appCompatActivity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        selectionOfCartOrWishList = view.findViewById(R.id.text_view_empty);
        shopNow = view.findViewById(R.id.text_view_empty_shop_now);

        selectionOfCartOrWishList.setText(textViewData);
        shopNow.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == shopNow) {
            Intent intent = new Intent(getContext(), Home_Main.class);
            intent.putExtra("PHONE", checkPhone);
            startActivity(intent);
        }
    }
}
