package com.example.vikas.loginsqlitedata.UserBuyAndAddToCartOption;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.CartBag.CartBagMainFragment;
import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserWishListAndCartFirebase;
import com.example.vikas.loginsqlitedata.PayMentMethods.PaymentMain;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.UserProfile.OnClickOnEditAddressInMainProfileFragment;
import com.example.vikas.loginsqlitedata.WishList.WishListMainFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class BuyProductFragment extends Fragment implements View.OnClickListener {

    private final ArrayList<String> productKeyArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private final String TAG = "wishlist_added";
    private String areaPinCode;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUserCart, databaseReferenceUserWishList;
    private List<UserWishListAndCartFirebase> userWishListAndCartFirebasesCartList;
    private Button buyProduct, addToCartProduct;
    private Fragment Selectedfragment = null;
    private BottomNavigationView bottomNavigationView;
    private AppCompatActivity appCompatActivity;
    private SQLiteDatabase sqLiteDatabase;
    private Toolbar toolbar;
    private LinearLayout wish_listLayout, shareLayout;
    private ImageView imageView;
    private TextView productPrice, productName;
    private TextView similarProductViewMoreTextView, userReviewAndRatingsViewMoreTextView, paymentMethodViewMoreTextView, usserAddressViewMoreTextView, productDetailViewMoreTextView;

    private String stringBrandname, stringProductDescription, stringProductName, stringProductPrice, stringProductImage, stringGender, stringProductCategory, productMerchantPhoneKey, checkPhone, productKey;

    public BuyProductFragment(String productKey, String stringProductImage, String stringProductName, String stringBrandname, String stringProductPrice, String productMerchantPhoneKey, String stringGender, String stringProductCategory, String stringProductDescription, String checkPhone) {

        this.stringBrandname = stringBrandname;
        this.stringProductDescription = stringProductDescription;
        this.stringProductPrice = stringProductPrice;
        this.stringProductName = stringProductName;
        this.stringProductImage = stringProductImage;
        this.stringGender = stringGender;
        this.stringProductCategory = stringProductCategory;
        this.productMerchantPhoneKey = productMerchantPhoneKey;
        this.productKey = productKey;
        this.checkPhone = checkPhone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_view_for_all_fragments_after_user_click_on_card_view_layout, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();
        getidForAllvariables(view);
        setaDataToDestination();
        userWishListAndCartFirebasesCartList = new ArrayList<>();
        bottomNavigationView.setVisibility(View.INVISIBLE);

        toolbar.setTitle("Buy Product");
        productPrice.setText(stringProductPrice);
        productName.setText(stringProductName);

        sqLiteDatabase = appCompatActivity.openOrCreateDatabase("galaxyCart", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("Create table if not exists UserAddressDataSQL (UserHouseNumber varchar,LandMark varchar,StateName varchar,CityName varchar,AreaPinCodeNumber varchar,PhoneNumber varchar,PhoneNumberKey varchar);");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUserCart = firebaseDatabase.getReference("UserCart").child(checkPhone);
        databaseReferenceUserWishList = firebaseDatabase.getReference("UserWishList").child(checkPhone);

        buyProduct.setOnClickListener(this);
        addToCartProduct.setOnClickListener(this);
        similarProductViewMoreTextView.setOnClickListener(this);
        userReviewAndRatingsViewMoreTextView.setOnClickListener(this);
        paymentMethodViewMoreTextView.setOnClickListener(this);
        usserAddressViewMoreTextView.setOnClickListener(this);
        productDetailViewMoreTextView.setOnClickListener(this);
        wish_listLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);

        return view;
    }

    private void setaDataToDestination() {

        productPrice.setText(stringProductPrice);
        productName.setText(stringProductName);
        Picasso.with(getContext())
                .load(stringProductImage)
                .placeholder(R.drawable.place_holder)
                .centerCrop()
                .fit()
                .into(imageView);

    }

    private void getidForAllvariables(View view) {

        bottomNavigationView = appCompatActivity.findViewById(R.id.bottom_navigation);
        toolbar = appCompatActivity.findViewById(R.id.action_toolbar);
        imageView = view.findViewById(R.id.image_view_after_clicking_on_card_view);
        productPrice = view.findViewById(R.id.text_view_price_of_product_in_buy_layout);
        productName = view.findViewById(R.id.product_description_of_after_clicking_on_card_view);

        buyProduct = view.findViewById(R.id.button_buy_now);
        addToCartProduct = view.findViewById(R.id.button_add_to_cart);

        similarProductViewMoreTextView = view.findViewById(R.id.similer_products_view_more);
        userReviewAndRatingsViewMoreTextView = view.findViewById(R.id.ratings_and_review_view_more);
        paymentMethodViewMoreTextView = view.findViewById(R.id.read_more);
        usserAddressViewMoreTextView = view.findViewById(R.id.change_address_view_more);
        productDetailViewMoreTextView = view.findViewById(R.id.product_detail_view_more);

        wish_listLayout = view.findViewById(R.id.layout_wish_list_buy_product_fragment);
        shareLayout = view.findViewById(R.id.layout_share_buy_product_fragment);
    }

    //----------------Below onCreate is essential for showing onCreateOptionsMenu in specific fragment----------------//
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_search_wish_list_cart_icons, menu);
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
        } else if (id == R.id.toolbar_cart) {
            Selectedfragment = new CartBagMainFragment(checkPhone);
        }
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Selectedfragment).commit();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == buyProduct) {
            productBuyMethodO();
        } else if (v == addToCartProduct) {

            addToCartProductMethod();

        } else if (v == similarProductViewMoreTextView) {
            Toast.makeText(appCompatActivity, "We are still working on it", Toast.LENGTH_SHORT).show();
        } else if (v == userReviewAndRatingsViewMoreTextView) {
            Toast.makeText(appCompatActivity, "We are still working on it", Toast.LENGTH_SHORT).show();
        } else if (v == paymentMethodViewMoreTextView) {
            Toast.makeText(appCompatActivity, "We are still working on it", Toast.LENGTH_SHORT).show();
        } else if (v == usserAddressViewMoreTextView) {

        } else if (v == productDetailViewMoreTextView) {
            Toast.makeText(appCompatActivity, "We are still working on it", Toast.LENGTH_SHORT).show();
        } else if (v == wish_listLayout) {
            addToWiishListProductmethod();
        } else if (v == shareLayout) {

        }
    }

    private void productBuyMethodO() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Checking Delivery Address...");
        progressDialog.show();
        getSQLitedataForAddress();
        if (areaPinCode.equals("YES")) {

            progressDialog.dismiss();
            Intent intent = new Intent(getContext(), PaymentMain.class);

            intent.putExtra("ProductPayablePrice", stringProductPrice);
            intent.putExtra("ProductBrandName", stringBrandname);
            intent.putExtra("ProductDescription", stringProductDescription);
            intent.putExtra("ProductImage", stringProductImage);
            intent.putExtra("ProductCategory", stringProductCategory);
            intent.putExtra("Discount", "20");
            intent.putExtra("Quantity", "1");
            intent.putExtra("TotalProduct", "2");
            intent.putExtra("ProductName", stringProductName);
            intent.putExtra("ProductGender", stringGender);
            intent.putExtra("ProductMerchantPhoneKey", productMerchantPhoneKey);
            intent.putExtra("productKey", productKey);
            intent.putExtra("checkPhone", checkPhone);

            startActivity(intent);
        } else {
            progressDialog.dismiss();
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OnClickOnEditAddressInMainProfileFragment(1)).addToBackStack(null).commit();
        }
    }

    private void getSQLitedataForAddress() {
        Cursor crUserAddressdata = sqLiteDatabase.rawQuery("Select * from UserAddressDataSQL where PhoneNumberKey='" + checkPhone + "'", null);
        if (crUserAddressdata.getCount() != 0) {
            crUserAddressdata.moveToFirst();
            areaPinCode = crUserAddressdata.getString(7);
            System.out.println(areaPinCode);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            builder.setMessage("Please Enter the delivery address ");
            builder.setTitle("Delivery Address");
            builder.setIcon(R.drawable.address_menu);
            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OnClickOnEditAddressInMainProfileFragment(1)).addToBackStack(null).commit();
                    progressDialog.dismiss();
                }
            });
            builder.show();
        }
    }

    private void addToWiishListProductmethod() {

        final int[] flag = {0};
        final List<UserWishListAndCartFirebase> listHoldingAllDataForWishList;

        final String TAG = "wishlist_added";

        listHoldingAllDataForWishList = new ArrayList<>();
        final ArrayList<String> productKeyArrayList = new ArrayList<>();

        databaseReferenceUserWishList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserWishListAndCartFirebase userWishListAndCartFirebase = ds.getValue(UserWishListAndCartFirebase.class);
                    productKeyArrayList.add(userWishListAndCartFirebase.getStringProductKey());
                    listHoldingAllDataForWishList.add(userWishListAndCartFirebase);
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
                    if (productKeyArrayList.get(i).equals(productKey)) {
                        flag[0] = 1;
                    }
                }

                if (flag[0] == 0) {
                    UserWishListAndCartFirebase userWishListAndCartFirebase = new UserWishListAndCartFirebase(stringProductImage, stringBrandname, stringProductDescription, stringProductName, stringProductPrice, stringGender, stringProductCategory, productMerchantPhoneKey, productKey, TAG);
                    databaseReferenceUserWishList.child(productKey).setValue(userWishListAndCartFirebase);

                    Toast.makeText(appCompatActivity, "Data Added to wishList successfully", Toast.LENGTH_SHORT).show();
                } else if (flag[0] == 1) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.wishlist_added);
                    builder.setTitle("WishList Alert");
                    builder.setCancelable(false);
                    builder.setMessage("This product is already in your wish list.\nDo you want to remove it from your wish list then click 'YES'");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseReferenceUserWishList.child(productKey).removeValue();
                            Toast.makeText(appCompatActivity, "Product Removed From Wish List", Toast.LENGTH_SHORT).show();
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

    private void addToCartProductMethod() {

        final int[] flag = {0};
        databaseReferenceUserCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserWishListAndCartFirebase userWishListAndCartFirebase = ds.getValue(UserWishListAndCartFirebase.class);
                    userWishListAndCartFirebasesCartList.add(userWishListAndCartFirebase);
                    productKeyArrayList.add(userWishListAndCartFirebase.getStringProductKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(appCompatActivity, "Something went wrong Please try again latter", Toast.LENGTH_SHORT).show();
            }
        });

        CountDownTimer countDownTimer = new CountDownTimer(400, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                for (int i = 0; i < userWishListAndCartFirebasesCartList.size(); i++) {
                    if (productKeyArrayList.get(i).equals(productKey)) {
                        flag[0] = 1;
                    }
                }

                if (flag[0] == 0) {

                    UserWishListAndCartFirebase userWishListAndCartFirebase = new UserWishListAndCartFirebase(stringProductImage, stringBrandname, stringProductDescription, stringProductName, stringProductPrice, stringGender, stringProductCategory, productMerchantPhoneKey, productKey, TAG);
                    databaseReferenceUserCart.child(productKey).setValue(userWishListAndCartFirebase);

                    Toast.makeText(appCompatActivity, "Product Successfully add to cart", Toast.LENGTH_SHORT).show();

                    CartBagMainFragment cartBagMainFragment = new CartBagMainFragment(checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cartBagMainFragment).addToBackStack(null).commit();

                } else if (flag[0] == 1) {
                    Toast.makeText(appCompatActivity, "Product is already in cart", Toast.LENGTH_SHORT).show();
                    CartBagMainFragment cartBagMainFragment = new CartBagMainFragment(checkPhone);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cartBagMainFragment).addToBackStack(null).commit();

                }
            }
        };
        countDownTimer.start();
    }
}