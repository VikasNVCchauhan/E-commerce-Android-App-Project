package com.example.vikas.loginsqlitedata.MerchantHome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.MerchantAddProductToAppFragment;
import com.example.vikas.loginsqlitedata.MerchantOrders.MerchantOrdeFragment;
import com.example.vikas.loginsqlitedata.MerchantPayments.MerchantPaymengtsFragment;
import com.example.vikas.loginsqlitedata.MerchantProducts.MerchantProductsFragment;
import com.example.vikas.loginsqlitedata.Merchantprofile.MerchantProfileFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int noOfTabs;
    private String checkPhone;

    public ViewPagerAdapter(FragmentManager fm,int noOfTabs,String checkPhone) {
        super(fm);
        this.noOfTabs=noOfTabs;
        this.checkPhone=checkPhone;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new MerchantProductsFragment(checkPhone);
            case 1:
                return new MerchantOrdeFragment(checkPhone);
            case 2:
                return new MerchantAddProductToAppFragment(checkPhone);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
