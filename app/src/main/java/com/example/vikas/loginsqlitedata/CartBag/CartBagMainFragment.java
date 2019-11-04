package com.example.vikas.loginsqlitedata.CartBag;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.EmptyCartWishListFragment;
import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserWishListAndCartFirebase;
import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.UserBuyAndAddToCartOption.BuyProductFragment;
import com.example.vikas.loginsqlitedata.WishList.WishListMainFragment;
import com.example.vikas.loginsqlitedata.YourOrders.OrderMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

////here we have to use custom list view --------------------//
public class CartBagMainFragment extends Fragment implements CustomGridAdapterClass.onItemClicklistner {

    private SQLiteDatabase sqLiteDatabas;
    private ProgressDialog progressDialog;
    private GridView gridViewNewProduct, gridViewWishList;
    private CustomGridAdapterClass customGridAdapterClass;
    private CustomListAdapterClass customListAdapterClass;
    private CustomGridAdapterClassForWishLis customGridAdapterClassForWishLis;
    private ListView listView;
    private Fragment Selectedfragment;
    private ValueEventListener valueEventListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceCart, mDatabaseReferenceMerchant, databaseReferenceForUserWishList;
    private List<UserCartFirebaseData> userCartFirebaseDataList;
    private List<UserMerchantUploadProductImage> userMerchantUploadProductImageList;
    private List<UserWishListAndCartFirebase> listHoldingAllDataForWishList;
    private AppCompatActivity appCompatActivity;
    private BottomNavigationView bottomNavigationView;
    private String checkPhone;
    private ProgressBar progressBar;
    private String textViewData = "Your Cart is Empty";
    private String productKey, productImage, productName, productBrandName, productPrice, productMerchantPhoneKey, productGender, productCategory, productDescription,Tag;

    @SuppressLint("ValidFragment")
    public CartBagMainFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public CartBagMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart_bag_main, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();
        getIdForAllVariables(view);
        bottomNavigationView.setVisibility(View.INVISIBLE);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Data is loading...");
        progressDialog.show();
        progressBar.setVisibility(View.INVISIBLE);

        userCartFirebaseDataList = new ArrayList<>();
        userMerchantUploadProductImageList = new ArrayList<>();
        listHoldingAllDataForWishList = new ArrayList<>();

        sqLiteDatabas = getActivity().openOrCreateDatabase("GalaxyCartProduct", android.content.Context.MODE_PRIVATE, null);
        sqLiteDatabas.execSQL("Create table if not exists UserWishList(ProductImage varchar, ProductBrandName varchar, ProductDescription varchar, ProductName varchar, ProductPrice varchar, ProductGender varchar, ProductCategory varchar, ProductMerchantPhoneKey varchar, ProductKey varchar, TAG varchar)");
        sqLiteDatabas.execSQL("Create table if not exists UserCart(ProductImage varchar, ProductBrandName varchar, ProductDescription varchar, ProductName varchar, ProductPrice varchar, ProductGender varchar, ProductCategory varchar, ProductMerchantPhoneKey varchar, ProductKey varchar, TAG varchar)");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReferenceMerchant = firebaseDatabase.getReference("MerchantAccout").child("MerchantProducts");
        databaseReferenceCart = firebaseDatabase.getReference("UserCart").child(checkPhone);
        databaseReferenceForUserWishList = FirebaseDatabase.getInstance().getReference("UserWishList").child(checkPhone);

       getDataFromFirebaseDatabaseForListView();
//***********************        getDataFromFirebaseDatabaseForGridViewNewItem();
//        getDataFromFirebaseDatabaseForWishList();

//        getSqliteDataForListView();

        customListAdapterClass = new CustomListAdapterClass(getContext(), R.layout.cart_layout_list_view_item_holding, userCartFirebaseDataList);
//        customGridAdapterClass = new CustomGridAdapterClass(getContext(), R.layout.cart_gridview_item_holding_layout, userMerchantUploadProductImageList);
//        customGridAdapterClassForWishLis = new CustomGridAdapterClassForWishLis(getContext(), R.layout.cart_grid_view_item_holding_for_wish_list, listHoldingAllDataForWishList);

        listView.setAdapter(customListAdapterClass);
        gridViewNewProduct.setAdapter(customGridAdapterClass);

//        customGridAdapterClass.setOnItemClicklistner(CartBagMainFragment.this);

//        gridViewWishList.setAdapter(customGridAdapterClassForWishLis);
        //Utility.setListViewHeightBasedOnChildren(listView);
        return view;
    }

    private void getSqliteDataForListView() {
        Cursor cursor=sqLiteDatabas.rawQuery("Select * from UserCart",null);
        if(cursor.getCount()==0)
        {
            Toast.makeText(appCompatActivity, "Your Cart is empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext()) {
                productImage = cursor.getString(0);
                productBrandName = cursor.getString(1);
                productDescription = cursor.getString(2);
                productName = cursor.getString(3);
                productPrice = cursor.getString(4);
                productGender = cursor.getString(5);
                productCategory = cursor.getString(6);
                productMerchantPhoneKey = cursor.getString(7);
                productKey = cursor.getString(8);
                Tag = cursor.getString(9);

                UserCartFirebaseData userCartFirebaseData = new UserCartFirebaseData(productImage, productBrandName, productDescription, productName, productPrice, productGender, productCategory, productMerchantPhoneKey, productKey, Tag);
                userCartFirebaseDataList.add(userCartFirebaseData);
            }

        }
        Toast.makeText(appCompatActivity, ""+userCartFirebaseDataList.size(), Toast.LENGTH_SHORT).show();
        //customListAdapterClass.notifyDataSetChanged();
        progressDialog.dismiss();
    }

//    private void getDataFromFirebaseDatabaseForWishList() {
//        databaseReferenceForUserWishList.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                listHoldingAllDataForWishList.clear();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    UserWishListAndCartFirebase userWishListAndCartFirebase = ds.getValue(UserWishListAndCartFirebase.class);
//                    listHoldingAllDataForWishList.add(userWishListAndCartFirebase);
//                }
//                customGridAdapterClassForWishLis.notifyDataSetChanged();
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                progressDialog.dismiss();
//                Toast.makeText(appCompatActivity, "There is some problem please try latter", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void getDataFromFirebaseDatabaseForGridViewNewItem() {
        valueEventListener = mDatabaseReferenceMerchant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userMerchantUploadProductImageList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    UserMerchantUploadProductImage userMerchantUploadProductImage = ds.getValue(UserMerchantUploadProductImage.class);
                    userMerchantUploadProductImageList.add(userMerchantUploadProductImage);
                }
                customGridAdapterClass.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Data not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataFromFirebaseDatabaseForListView() {
        valueEventListener = databaseReferenceCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userCartFirebaseDataList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (userCartFirebaseDataList.size() <= 4) {
                        UserCartFirebaseData userCartFirebaseData = ds.getValue(UserCartFirebaseData.class);
                        userCartFirebaseDataList.add(userCartFirebaseData);
                    }
                }
                Toast.makeText(appCompatActivity, ""+userCartFirebaseDataList.size(), Toast.LENGTH_SHORT).show();
                if (userCartFirebaseDataList.size() == 0) {
                    EmptyCartWishListFragment emptyCartWishListFragment = new EmptyCartWishListFragment(textViewData, checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, emptyCartWishListFragment).addToBackStack(null).commit();
                }
                progressBar.setVisibility(View.INVISIBLE);
                customListAdapterClass.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(appCompatActivity, "Something went wrong please try again latter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIdForAllVariables(View view) {
        bottomNavigationView = appCompatActivity.findViewById(R.id.bottom_navigation);

        gridViewWishList = view.findViewById(R.id.grid_view_bag_wish_list);
        gridViewNewProduct = view.findViewById(R.id.grid_view_new_products);
        progressBar = view.findViewById(R.id.progressBar_cart);
        listView = view.findViewById(R.id.custom_list_view_for_cart);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.oncreate_option_menu_item_for_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_search) {
            Toast.makeText(getContext(), "We are still working on it", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.toolbar_wish_list) {
            Selectedfragment = new WishListMainFragment(checkPhone);
        } else if (id == R.id.toolbar_order) {
            Selectedfragment = new OrderMainActivity(checkPhone);
        }
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Selectedfragment).commit();
        return true;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        databaseReferenceCart.removeEventListener(valueEventListener);
//    }

    @Override
    public void onItemClick() {
        gridViewNewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserMerchantUploadProductImage itemSeleced = userMerchantUploadProductImageList.get(position);

                productKey = itemSeleced.getStringProductKey();
                productName = itemSeleced.getStringProductName();
                productBrandName = itemSeleced.getStringBrandname();
                productPrice = itemSeleced.getStringProductPrice();
                productMerchantPhoneKey = itemSeleced.getPhoneKey();
                productGender = itemSeleced.getStringGender();
                productCategory = itemSeleced.getStringProductCategory();
                productDescription = itemSeleced.getStringProductDescription();
                productImage = itemSeleced.getImage();

                BuyProductFragment buyProductFragment = new BuyProductFragment(productKey, productImage, productName, productBrandName, productPrice, productMerchantPhoneKey, productGender, productCategory, productDescription, checkPhone);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, buyProductFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onWishlistClick() {
        gridViewNewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onCartClick() {
        gridViewNewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}


