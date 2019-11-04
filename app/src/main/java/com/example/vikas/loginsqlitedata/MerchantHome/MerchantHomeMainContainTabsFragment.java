package com.example.vikas.loginsqlitedata.MerchantHome;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vikas.loginsqlitedata.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MerchantHomeMainContainTabsFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private AppCompatActivity appCompatActivity;
    private TabLayout tabLayout;
    private TabItem tabProduct, tabOrders, tabAddProducts;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private String phoneKey;
    @SuppressLint("ValidFragment")
    public MerchantHomeMainContainTabsFragment(String phonekey) {
        // Required empty public constructor
        this.phoneKey=phonekey;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_home_main_contain_tabs, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();

        getIdforAllVariables(view);
        viewPagerAdapter = new ViewPagerAdapter(appCompatActivity.getSupportFragmentManager(), tabLayout.getTabCount(),phoneKey);
        viewPager.setAdapter(viewPagerAdapter);

        //sliding tab-----------------------------|
        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //sliding tab-----------------------------|

        return view;
    }

    private void getIdforAllVariables(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout_merchant);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager_merchant);
        tabProduct = view.findViewById(R.id.merchant_sliding_tabs_products);
        tabOrders = view.findViewById(R.id.merchant_sliding_tabs_orders);
        tabAddProducts = view.findViewById(R.id.merchant_sliding_tabs_addProducts);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
