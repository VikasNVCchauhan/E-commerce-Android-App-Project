package com.example.vikas.loginsqlitedata.WishList;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.vikas.loginsqlitedata.CartBag.CartBagMainFragment;
import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.EmptyCartWishListFragment;
import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserWishListAndCartFirebase;
import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.UserBuyAndAddToCartOption.BuyProductFragment;
import com.example.vikas.loginsqlitedata.YourOrders.OrderMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class WishListMainFragment extends Fragment implements RcyclerViewAdapterWishList.onItemClickListener {

    private SQLiteDatabase sqLiteDatabas;
    private Toolbar toolbar;
    private Fragment Selectedfragment;
    private ValueEventListener valueEventListener;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RcyclerViewAdapterWishList rcyclerViewAdapterWishList;
    private AppCompatActivity appCompatActivity;
    private BottomNavigationView bottomNavigationView;
    private String checkPhone;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceWishList, databaseReferenceCart;
    private List<UserWishListAndCartFirebase> userWishListAndCartFirebasesList;
    private String productKey, productImage, productName, productBrandName, productPrice, productMerchantPhoneKey, productGender, productCategory, productDescription;

    @SuppressLint("ValidFragment")
    public WishListMainFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    private String textViewData = "Your Wish List is Empty";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list_main, container, false);


        sqLiteDatabas = getActivity().openOrCreateDatabase("GalaxyCartProduct", android.content.Context.MODE_PRIVATE, null);
        sqLiteDatabas.execSQL("Create table if not exists UserWishList(ProductImage varchar, ProductBrandName varchar, ProductDescription varchar, ProductName varchar, ProductPrice varchar, ProductGender varchar, ProductCategory varchar, ProductMerchantPhoneKey varchar, ProductKey varchar, TAG varchar)");
        sqLiteDatabas.execSQL("Create table if not exists UserCart(ProductImage varchar, ProductBrandName varchar, ProductDescription varchar, ProductName varchar, ProductPrice varchar, ProductGender varchar, ProductCategory varchar, ProductMerchantPhoneKey varchar, ProductKey varchar, TAG varchar)");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceWishList = firebaseDatabase.getReference("UserWishList").child(checkPhone);
        databaseReferenceCart = firebaseDatabase.getReference("UserCart").child(checkPhone);
        appCompatActivity = (AppCompatActivity) view.getContext();

        geIdForAllVariables(view);

        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setTitle("Wish list");
        //  appCompatActivity.getSupportActionBar().setTitle("Wish List");
        progressBar.setVisibility(View.VISIBLE);
        userWishListAndCartFirebasesList = new ArrayList<>();
        bottomNavigationView = appCompatActivity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));


        rcyclerViewAdapterWishList = new RcyclerViewAdapterWishList(getContext(), userWishListAndCartFirebasesList);
        recyclerView.setAdapter(rcyclerViewAdapterWishList);

        rcyclerViewAdapterWishList.setItemClickListener(WishListMainFragment.this);

        getAllDataFromFirebase();
        return view;
    }

    private void getAllDataFromFirebase() {

        valueEventListener = databaseReferenceWishList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userWishListAndCartFirebasesList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserWishListAndCartFirebase userWishListAndCartFirebase = ds.getValue(UserWishListAndCartFirebase.class);
                    userWishListAndCartFirebasesList.add(userWishListAndCartFirebase);
                }
                if (userWishListAndCartFirebasesList.size() == 0) {
                    EmptyCartWishListFragment emptyCartWishListFragment = new EmptyCartWishListFragment(textViewData, checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, emptyCartWishListFragment).addToBackStack(null).commit();
                } else {
                    rcyclerViewAdapterWishList.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(appCompatActivity, "There is some problem please try again latter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void geIdForAllVariables(View view) {
        toolbar = appCompatActivity.findViewById(R.id.action_toolbar);
        recyclerView = view.findViewById(R.id.recycler_view_wish_list);
        progressBar = view.findViewById(R.id.progressBar_wish_list);
    }


    @Override
    public void onItemClick(int possition) {
        UserWishListAndCartFirebase itemSeleced = userWishListAndCartFirebasesList.get(possition);

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
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, buyProductFragment).commit();

    }

    @Override
    public void onDeleteItemClick(int possition) {
        UserWishListAndCartFirebase itemSeleced = userWishListAndCartFirebasesList.get(possition);
        String selectedProductKey = itemSeleced.getStringProductKey();

        sqLiteDatabas.execSQL("Delete from UserWishList where ProductKey='"+selectedProductKey+"'");
        databaseReferenceWishList.child(selectedProductKey).removeValue();
        Toast.makeText(appCompatActivity, "Product Removed Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddCartItemClick(int possition) {

        UserWishListAndCartFirebase itemSeleced = userWishListAndCartFirebasesList.get(possition);
        final String selectedProductKey = itemSeleced.getStringProductKey();
        //----------------- variable declaration ------------------//
        final int[] flag = {0};
        final List<UserWishListAndCartFirebase> listHoldingAllDataForCart;

        final String TAG = "wishlist_added";
        productName = itemSeleced.getStringProductName();
        productBrandName = itemSeleced.getStringBrandname();
        productPrice = itemSeleced.getStringProductPrice();
        productMerchantPhoneKey = itemSeleced.getPhoneKey();
        productGender = itemSeleced.getStringGender();
        productCategory = itemSeleced.getStringProductCategory();
        productDescription = itemSeleced.getStringProductDescription();
        productImage = itemSeleced.getImage();

        listHoldingAllDataForCart = new ArrayList<>();
        final ArrayList<String> productKeyArrayList = new ArrayList<>();

        databaseReferenceCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserWishListAndCartFirebase userWishListAndCartFirebase = ds.getValue(UserWishListAndCartFirebase.class);
                    productKeyArrayList.add(userWishListAndCartFirebase.getStringProductKey());
                    listHoldingAllDataForCart.add(userWishListAndCartFirebase);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(appCompatActivity, "There is some problem please try latter", Toast.LENGTH_SHORT).show();
            }
        });
        CountDownTimer countDownTimer = new CountDownTimer(300, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                for (int i = 0; i < productKeyArrayList.size(); i++) {
                    if (productKeyArrayList.get(i).equals(selectedProductKey)) {
                        flag[0] = 1;
                    }
                }

                if (flag[0] == 0) {

                    sqLiteDatabas.execSQL("Insert into UserCart values('" + productImage + "','" + productBrandName + "','" + productDescription + "','" + productName + "','" + productPrice + "','" + productGender + "','" + productCategory + "','" + productMerchantPhoneKey + "','" + selectedProductKey + "','" + TAG +"')");
                    UserWishListAndCartFirebase userWishListAndCartFirebase = new UserWishListAndCartFirebase(productImage, productBrandName, productDescription, productName, productPrice, productGender, productCategory, productMerchantPhoneKey, selectedProductKey, TAG);
                    databaseReferenceCart.child(selectedProductKey).setValue(userWishListAndCartFirebase);

                    Toast.makeText(appCompatActivity, "Data Added to Cart successfully", Toast.LENGTH_SHORT).show();
                } else if (flag[0] == 1) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.cart);
                    builder.setCancelable(false);
                    builder.setTitle("User Cart Alert");
                    builder.setMessage("This product is already in your Cart.\nDo you want to remove it from your Cart then click 'YES'");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            sqLiteDatabas.execSQL("Delete from UserCart where ProductKey='"+selectedProductKey+"'");
                            databaseReferenceCart.child(selectedProductKey).removeValue();
                            Toast.makeText(appCompatActivity, "Product Removed From Cart", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        };
        countDownTimer.start();

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.oncreate_option_menu_item_for_wish_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_search) {
            Toast.makeText(getContext(), "We are still working on it", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.toolbar_cart) {
            Selectedfragment = new CartBagMainFragment(checkPhone);
        }
        else if (id == R.id.toolbar_order) {
            Selectedfragment = new OrderMainActivity(checkPhone);
        }
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Selectedfragment).commit();
        return true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseReferenceWishList.removeEventListener(valueEventListener);
    }

}
