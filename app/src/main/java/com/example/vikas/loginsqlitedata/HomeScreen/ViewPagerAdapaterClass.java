package com.example.vikas.loginsqlitedata.HomeScreen;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.vikas.loginsqlitedata.DailyNeedProducts.DailyNeedProductsFragment;
import com.example.vikas.loginsqlitedata.ElectronicsProducts.ElectronicsProductsMainFragment;
import com.example.vikas.loginsqlitedata.FashionProduct.FashionProductsFragment;
import com.example.vikas.loginsqlitedata.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPagerAdapaterClass extends PagerAdapter {

    private Toolbar toolbar;
    private LayoutInflater layoutInflater;
    private Context context;
    private String[] banners = {};
    private String checkPhone;

    public ViewPagerAdapaterClass(Context context, String[] banners, String checkPhone) {
        this.context = context;
        this.banners = banners;
        this.checkPhone=checkPhone;
    }


    @Override
    public int getCount() {
        return banners.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView imageView;

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.sliding_image_view_items_activity, null);

        imageView = (ImageView) view.findViewById(R.id.image_view_sliding_image);

        Picasso.with(context)
                .load(banners[position])
                .placeholder(R.drawable.banner_image1)
                .centerCrop()
                .fit()
                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //--------------this appCompatActivity is require to get getSupportFragmentManager() function--------------//
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                toolbar = appCompatActivity.findViewById(R.id.action_toolbar);

                if (position == 0) {
                    toolbar.setTitle("Electronics products");
                    ElectronicsProductsMainFragment myFragment = new ElectronicsProductsMainFragment(checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                } else if (position == 1) {
                    toolbar.setTitle("Fashion Products");
                    FashionProductsFragment myFragment = new FashionProductsFragment(checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                } else if (position == 2) {
                    toolbar.setTitle("DailyNeed Products");
                    DailyNeedProductsFragment myFragment = new DailyNeedProductsFragment(checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                } else if (position == 3) {
                    toolbar.setTitle("DailyNeed Products");
                    DailyNeedProductsFragment myFragment = new DailyNeedProductsFragment(checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                } else if (position == 4) {
                    toolbar.setTitle("DailyNeed Products");
                    DailyNeedProductsFragment myFragment = new DailyNeedProductsFragment(checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                }
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
