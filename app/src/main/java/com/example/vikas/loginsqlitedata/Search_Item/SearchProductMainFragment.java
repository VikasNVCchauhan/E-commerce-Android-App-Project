package com.example.vikas.loginsqlitedata.Search_Item;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.MenProducts.RecyclerViewAdapterClass;
import com.example.vikas.loginsqlitedata.R;


@SuppressLint("ValidFragment")
public class SearchProductMainFragment extends Fragment implements View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private RecyclerViewAdapterClass recyclerViewAdapterClass;
    private ImageView imageView;
    private String searchedString, phoneKey;
    private RelativeLayout relativeLayout;
    private AppCompatActivity appCompatActivity;
    private EditText editTextSearchItem;
    private ListView listViewSearchedItem;

    @SuppressLint("ValidFragment")
    public SearchProductMainFragment(String phoneKey) {
        this.phoneKey = phoneKey;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_item_activity, container, false);
        appCompatActivity = (AppCompatActivity) view.getContext();
        getIdForAllVariables(view);

        bottomNavigationView.setVisibility(View.INVISIBLE);
        imageView.setOnClickListener(this);
        return view;
    }

    private void getIdForAllVariables(View view) {
        editTextSearchItem = view.findViewById(R.id.searchBar_search_activity);
//        listViewSearchedItem = findViewById(R.id.list_view_search_activity);

        imageView = view.findViewById(R.id.image_button_search);
        relativeLayout = view.findViewById(R.id.relative_layout_search_fragment);
        bottomNavigationView = appCompatActivity.findViewById(R.id.bottom_navigation);
    }

    @Override
    public void onClick(View v) {
        searchedString = editTextSearchItem.getText().toString();
        if (!searchedString.equals("")) {
            searchProduct(searchedString);
        } else {
            Toast.makeText(getContext(), "Please Enter Search Item", Toast.LENGTH_SHORT).show();
            Snackbar.make(relativeLayout, "Please Enter Search Item ", Snackbar.LENGTH_LONG).show();
        }
    }

    private void searchProduct(String searchedString) {
        SearchinProductFragment searchinProductFragment = new SearchinProductFragment(searchedString, phoneKey);
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchinProductFragment).addToBackStack(null).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        appCompatActivity.getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        appCompatActivity.getSupportActionBar().show();
    }
}
