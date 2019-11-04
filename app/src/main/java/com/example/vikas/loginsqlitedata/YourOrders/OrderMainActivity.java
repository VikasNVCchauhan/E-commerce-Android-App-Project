package com.example.vikas.loginsqlitedata.YourOrders;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.EmptyOrderFragment;
import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserOrdersClass;
import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserWishListAndCartFirebase;
import com.example.vikas.loginsqlitedata.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderMainActivity extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapterOrderClass recyclerViewAdapterOrderClass;
    private ArrayList<UserOrdersClass> userOrdersClassesArrayList;
    private BaseAdapter baseAdapter;
    private BottomNavigationView bottomNavigationView;
    private AppCompatActivity appCompatActivity;
    private String checkPhone, orderNumber, productKey;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Button trackYourProductButton, cancleOrdeButton;
    private String productImage, productName, productBrandName, productPrice, productMerchantPhoneKey, productGender, productCategory, productDescription;


    @SuppressLint("ValidFragment")
    public OrderMainActivity(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public OrderMainActivity() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public OrderMainActivity(String checkPhone, String orderNumber, String productKey) {
        this.checkPhone = checkPhone;
        this.orderNumber = orderNumber;
        this.productKey = productKey;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_main, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();
        getIdForAllVariables(view);
        bottomNavigationView.setVisibility(View.VISIBLE);

        userOrdersClassesArrayList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserOrder").child(checkPhone);
        getDataFromFirebase();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // cancleOrdeButton.setOnClickListener(this);
        return view;
    }

    private void getDataFromFirebase() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserOrdersClass userOrdersClass = ds.getValue(UserOrdersClass.class);
                    userOrdersClassesArrayList.add(userOrdersClass);
                }
                if (userOrdersClassesArrayList.size() == 0) {
                    EmptyOrderFragment emptyOrderFragment = new EmptyOrderFragment(checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, emptyOrderFragment).addToBackStack(null).commit();
                } else {
                    recyclerViewAdapterOrderClass = new RecyclerViewAdapterOrderClass(getContext(), userOrdersClassesArrayList);
                    recyclerView.setAdapter(recyclerViewAdapterOrderClass);
                    recyclerViewAdapterOrderClass.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(appCompatActivity, "There is some fault while fetching data from server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIdForAllVariables(View view) {
        bottomNavigationView = appCompatActivity.findViewById(R.id.bottom_navigation);
        trackYourProductButton = view.findViewById(R.id.product_track_order_list_item);
        cancleOrdeButton = view.findViewById(R.id.product_cancel_order_list_item);

        recyclerView = view.findViewById(R.id.recycler_view_order_main_activity);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cancleOrdeButton.setTag(position);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Toast.makeText(appCompatActivity, "OrdermainActivity : " + position, Toast.LENGTH_SHORT).show();
    }
}
