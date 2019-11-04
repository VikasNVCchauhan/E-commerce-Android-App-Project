package com.example.vikas.loginsqlitedata.HomeScreen;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.Search_Item.SearchProductMainFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private String checkPhone;
    private EditText edit_text_search_bar;
    //--------------variables for sliding image view----------------//
    private MyTimeTask timeTask = new MyTimeTask();
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private int dotCount;
    private ImageView[] dots;
    private ViewPagerAdapaterClass viewPagerAdapaterClass;
    private Handler handler = new Handler();
    //-----------Variables for horizontal recycler view------------//
    private RecyclerView recyclerView;
    private ArrayList<String> images = new ArrayList<>();
    private String[] banners;
    private AppCompatActivity appCompatActivity;

    private int[] names = {R.string.WomenHorizontal,
            R.string.MenHorizontal,
            R.string.FashionHorizontal,
            R.string.SportsHorizontal,
            R.string.KidsHorizontal,
            R.string.ElectronicsHorizontal,
            R.string.DailyNeedHorizontal,
            R.string.BeautyHorizontal};

    public HomeFragment() {

        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();

        getImagesFromInterNet();
        banners = appCompatActivity.getResources().getStringArray(R.array.banner_array);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //--------------getting id's for sliding image view variables----------------//
        viewPager = (ViewPager) view.findViewById(R.id.sliding_image_view_pager);
        linearLayout = view.findViewById(R.id.linear_layout_sliding_dots);

        edit_text_search_bar = view.findViewById(R.id.searchBarMain);
        edit_text_search_bar.setOnClickListener(this);

        viewPagerAdapaterClass = new ViewPagerAdapaterClass(getContext(), banners, checkPhone);


        viewPager.setAdapter(viewPagerAdapaterClass);

        dotCount = viewPagerAdapaterClass.getCount();
        dots = new ImageView[dotCount];

        for (int i = 0; i < dotCount; i++) {
            dots[i] = new ImageView(getContext());

            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8, 0, 8, 0);

            linearLayout.addView(dots[i], layoutParams);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timeTask, 3000, 3000);

        //------------------******************------------------------//

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        HorizontalRecyclerViewAdapter recyclerViewAdspter = new HorizontalRecyclerViewAdapter(getActivity(), images, names, checkPhone);
        recyclerView.setAdapter(recyclerViewAdspter);

        return view;

    }

    private void getImagesFromInterNet() {

        images.add("https://image.ibb.co/dHsb2T/horizontal_recycler_view_women_jpg.jpg");
        images.add("https://image.ibb.co/hnRpp8/men_horizontal_recycler_view.jpg");
        images.add("https://preview.ibb.co/eg8Vwo/fashion_horizontal_recycler_view.jpg");
        images.add("https://preview.ibb.co/iuxUp8/sports_horizontal_recycler_view.jpg");
        images.add("https://preview.ibb.co/cXrYGo/kids_horizontal_recycler_view.jpg");
        images.add("https://preview.ibb.co/mDkwK8/electronics_products.jpg");
        images.add("https://preview.ibb.co/bA7vU8/daily_need_horizontal_recycler_view.jpg");
        images.add("https://image.ibb.co/hnfPp8/beauty_horizontal_recycler_view.jpg");
    }

    @Override
    public void onClick(View v) {
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchProductMainFragment(checkPhone)).addToBackStack(null).commit();
    }


    public class MyTimeTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3) {
                        viewPager.setCurrentItem(4);
                    } else if (viewPager.getCurrentItem() == 4) {
                        viewPager.setCurrentItem(0);
                    }
                }

            });
        }
    }

    //--------------------This method is essential in fragment to stop timer class if we want to jump from on activity to another --------------------//
    @Override
    public void onStop() {
        super.onStop();
        if (timeTask != null) {
            timeTask.cancel();
        }
    }
}
