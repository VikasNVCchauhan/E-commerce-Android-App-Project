package com.example.vikas.loginsqlitedata.TermAndConditions;


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
public class TermAndConditinFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private String checkPhone;

    @SuppressLint("ValidFragment")
    public TermAndConditinFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public TermAndConditinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_term_and_conditin, container, false);
    }

}
