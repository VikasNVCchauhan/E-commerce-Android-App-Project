package com.example.vikas.loginsqlitedata.Help;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vikas.loginsqlitedata.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class HelpMainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private String checkPhone;
    @SuppressLint("ValidFragment")
    public HelpMainFragment(String checkPhone) {
        this.checkPhone=checkPhone;
        // Required empty public constructor
    }

    public HelpMainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_help_main, container, false);
        AppCompatActivity appCompatActivity=(AppCompatActivity)view.getContext();
        bottomNavigationView=appCompatActivity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        return view;
    }

}
