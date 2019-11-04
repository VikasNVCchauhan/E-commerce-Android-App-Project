package com.example.vikas.loginsqlitedata.MenProducts;


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

import com.example.vikas.loginsqlitedata.CartBag.CartBagMainFragment;
import com.example.vikas.loginsqlitedata.ExtraHelpingClasses.UserWishListAndCartFirebase;
import com.example.vikas.loginsqlitedata.MerchantAddProductToApp.UserMerchantUploadProductImage;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.UserBuyAndAddToCartOption.BuyProductFragment;
import com.example.vikas.loginsqlitedata.WishList.WishListMainFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenProducsFragment extends Fragment implements View.OnClickListener, RecyclerViewAdapterClass.onItemClickListener {

    private SQLiteDatabase sqLiteDatabas;
    private Fragment Selectedfragment;
    private BottomNavigationView bottomNavigationView;
    private ValueEventListener valueEventListener;
    private AppCompatActivity appCompatActivity;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayout shortBy, filter, switching;
    private FirebaseStorage mFirebaseStorage;
    private FirebaseDatabase mFirebaseDatabase;
    private StorageReference mStorageReference, storageReference;
    private DatabaseReference mDatabaseReference, databaseReference, databaseReferenceForUserWishList, databaseReferenceForUserCart;

    private List<UserWishListAndCartFirebase> listHoldingAllDataForWishList;
    private List<UserMerchantUploadProductImage> userMerchantUploadProductImageList;

    private RecyclerViewAdapterClass recyclerViewAdapterClass;
    private String checkPhone;
    private String productKey, productImage, productName, productBrandName, productPrice, productMerchantPhoneKey, productGender, productCategory, productDescription;

    @SuppressLint("ValidFragment")
    public MenProducsFragment(String checkPhone) {
        this.checkPhone = checkPhone;
    }

    public MenProducsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_men_producs, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();
        bottomNavigationView = appCompatActivity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);

        setIdToAllVariables(view);

        progressBar.setVisibility(View.VISIBLE);
        userMerchantUploadProductImageList = new ArrayList<>();
        listHoldingAllDataForWishList = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));

        recyclerViewAdapterClass = new RecyclerViewAdapterClass(getContext(), userMerchantUploadProductImageList, listHoldingAllDataForWishList);
        recyclerView.setAdapter(recyclerViewAdapterClass);

        recyclerViewAdapterClass.setOnItemClickListener(MenProducsFragment.this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();


        sqLiteDatabas = getActivity().openOrCreateDatabase("GalaxyCartProduct", android.content.Context.MODE_PRIVATE, null);

        sqLiteDatabas.execSQL("Create table if not exists UserWishList(ProductImage varchar,ProductBrandName varchar,ProductDescription varchar,ProductName varchar,ProductPrice varchar,ProductGender varchar,ProductCategory varchar,ProductMerchantPhoneKey varchar,ProductKey varchar,TAG varchar)");
        sqLiteDatabas.execSQL("Create table if not exists UserCart( ProductImage varchar, ProductBrandName varchar, ProductDescription varchar, ProductName varchar, ProductPrice varchar, ProductGender varchar, ProductCategory varchar, ProductMerchantPhoneKey varchar, ProductKey varchar, TAG varchar)");

        mDatabaseReference = mFirebaseDatabase.getReference("MerchantAccout");
        databaseReference = mDatabaseReference.child("MerchantProducts");
        databaseReferenceForUserWishList = FirebaseDatabase.getInstance().getReference("UserWishList").child(checkPhone);

        databaseReferenceForUserCart = FirebaseDatabase.getInstance().getReference("UserCart").child(checkPhone);
        getAllDataFromFirebase();

        shortBy.setOnClickListener(this);
        filter.setOnClickListener(this);
        switching.setOnClickListener(this);

        return view;
    }

    public void getAllDataFromFirebase() {
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userMerchantUploadProductImageList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    UserMerchantUploadProductImage userMerchantUploadProductImage = ds.getValue(UserMerchantUploadProductImage.class);
                    userMerchantUploadProductImageList.add(userMerchantUploadProductImage);
                }
                recyclerViewAdapterClass.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Data not Found", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReferenceForUserWishList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listHoldingAllDataForWishList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserWishListAndCartFirebase userWishListAndCartFirebase = ds.getValue(UserWishListAndCartFirebase.class);
                    listHoldingAllDataForWishList.add(userWishListAndCartFirebase);
                }
                recyclerViewAdapterClass.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(appCompatActivity, "There is some problem please try latter", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setIdToAllVariables(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_ment_products);
        progressBar = view.findViewById(R.id.progressBarInmenProducts);

        shortBy = (LinearLayout) view.findViewById(R.id.sort_layout);
        filter = (LinearLayout) view.findViewById(R.id.filter_layout);
        switching = (LinearLayout) view.findViewById(R.id.switch_layout);
    }

    @Override
    public void onClick(View v) {
        if (v == shortBy) {

            Toast.makeText(getContext(), "We are still working on it ", Toast.LENGTH_SHORT).show();
        } else if (v == filter) {
            Toast.makeText(getContext(), "We are still working on it ", Toast.LENGTH_SHORT).show();
        } else if (v == switching) {
            Toast.makeText(getContext(), "We are still working on it ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(int position) {


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

    @Override
    public void onItemWishList(int position) {

        UserMerchantUploadProductImage itemSeleced = userMerchantUploadProductImageList.get(position);
        final String selectedKey = itemSeleced.getStringProductKey();


        //----------------- variable declaration ------------------//
        final int[] flag = {0};
        final List<UserWishListAndCartFirebase> listHoldingAllDataForWishList;

        final String TAG = "wishlist_added";
        productName = itemSeleced.getStringProductName();
        productBrandName = itemSeleced.getStringBrandname();
        productPrice = itemSeleced.getStringProductPrice();
        productMerchantPhoneKey = itemSeleced.getPhoneKey();
        productGender = itemSeleced.getStringGender();
        productCategory = itemSeleced.getStringProductCategory();
        productDescription = itemSeleced.getStringProductDescription();
        productImage = itemSeleced.getImage();

        listHoldingAllDataForWishList = new ArrayList<>();
        final ArrayList<String> productKeyArrayList = new ArrayList<>();

        databaseReferenceForUserWishList.addValueEventListener(new ValueEventListener() {
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
                    if (productKeyArrayList.get(i).equals(selectedKey)) {
                        flag[0] = 1;
                    }
                }

                if (flag[0] == 0) {

                    sqLiteDatabas.execSQL("Insert into UserWishList values('" + productImage + "','" + productBrandName + "','" + productDescription + "','" + productName + "','" + productPrice + "','" + productGender + "','" + productCategory + "','" + productMerchantPhoneKey + "','" + selectedKey + "','" + TAG + "')");
                    UserWishListAndCartFirebase userWishListAndCartFirebase = new UserWishListAndCartFirebase(productImage, productBrandName, productDescription, productName, productPrice, productGender, productCategory, productMerchantPhoneKey, selectedKey, TAG);
                    databaseReferenceForUserWishList.child(selectedKey).setValue(userWishListAndCartFirebase);

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

                            sqLiteDatabas.execSQL("Delete from UserWishList where ProductKey='" + selectedKey + "'");
                            databaseReferenceForUserWishList.child(selectedKey).removeValue();
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

    @Override
    public void onItemCart(int position) {

        UserMerchantUploadProductImage itemSeleced = userMerchantUploadProductImageList.get(position);
        final String selectedKey = itemSeleced.getStringProductKey();
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

        databaseReferenceForUserCart.addValueEventListener(new ValueEventListener() {
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
                    if (productKeyArrayList.get(i).equals(selectedKey)) {
                        flag[0] = 1;
                    }
                }

                if (flag[0] == 0) {

                    sqLiteDatabas.execSQL("Insert into UserCart values('" + productImage + "','" + productBrandName + "','" + productDescription + "','" + productName + "','" + productPrice + "','" + productGender + "','" + productCategory + "','" + productMerchantPhoneKey + "','" + selectedKey + "','" + TAG +"')");
                    UserWishListAndCartFirebase userWishListAndCartFirebase = new UserWishListAndCartFirebase(productImage, productBrandName, productDescription, productName, productPrice, productGender, productCategory, productMerchantPhoneKey, selectedKey, TAG);
                    databaseReferenceForUserCart.child(selectedKey).setValue(userWishListAndCartFirebase);

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

                            sqLiteDatabas.execSQL("Delete from UserCart where ProductKey='" + selectedKey + "'");
                            databaseReferenceForUserCart.child(selectedKey).removeValue();
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
    public void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }

//
//    public static void popFragments(@NonNull FragmentManager fragmentManager) {
//        while (fragmentManager.getBackStackEntryCount() > 0) {
//            fragmentManager.popBackStackImmediate();
//        }
//    }
}

//---------------------create an interface----------------------//
//---------------------create an instance of  interface in fragment and pass it on to the adapter----------------------//
//---------------------let the adapter pass this interface on to the view holder----------------------//
//---------------------let the view holder define an onClickListener that passes the event on to the interface---------------------//