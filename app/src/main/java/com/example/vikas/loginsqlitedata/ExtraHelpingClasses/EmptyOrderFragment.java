package com.example.vikas.loginsqlitedata.ExtraHelpingClasses;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.HomeScreen.Home_Main;
import com.example.vikas.loginsqlitedata.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyOrderFragment extends Fragment implements View.OnClickListener {

    private String checkPhone;
    private TextView descriptionTextView, startShoppingTextView;

    @SuppressLint("ValidFragment")
    public EmptyOrderFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public EmptyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_order, container, false);
        setIdForAllVariables(view);

        descriptionTextView.setText(R.string.emptyOrderFragment);
        startShoppingTextView.setOnClickListener(this);
        return view;
    }

    private void setIdForAllVariables(View view) {
        descriptionTextView = view.findViewById(R.id.text_view_empty_order);
        startShoppingTextView = view.findViewById(R.id.text_view_empty_order_shop_now);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), Home_Main.class);
        intent.putExtra("PHONE", checkPhone);
        startActivity(intent);
    }
}
