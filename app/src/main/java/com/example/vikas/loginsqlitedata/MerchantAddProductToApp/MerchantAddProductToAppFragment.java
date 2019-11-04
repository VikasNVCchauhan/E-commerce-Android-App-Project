package com.example.vikas.loginsqlitedata.MerchantAddProductToApp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantAddProductToAppFragment extends Fragment implements View.OnClickListener {


    private String phoneKey;
    private EditText editTextProductBrandname, editTextProductDescription, editTextProductName, editTextProductPrice, editTextGender, editTextProductCategory;
    private Button saveData;
    private AppCompatActivity appCompatActivity;

    private String stringBrandnameArray[], stringBrandname = "", stringProductDescription = "", stringProductName = "", stringProductPrice = "", stringGender = "", stringProductCategory = "", stringGenderArray[], stringProductCategoryArray[];

    public MerchantAddProductToAppFragment() {
        // Required empty public constructor
    }
    public MerchantAddProductToAppFragment(String phoneKey) {
        this.phoneKey=phoneKey;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_add_product_to_app, container, false);


        appCompatActivity = (AppCompatActivity) view.getContext();
        phoneKey=appCompatActivity.getIntent().getStringExtra("PHONE");

        setIdToAllVaribles(view);

        stringBrandnameArray = appCompatActivity.getResources().getStringArray(R.array.AddProductMerchantClothBrandNameSelection);
        stringProductCategoryArray = appCompatActivity.getResources().getStringArray(R.array.AddProductMerchantProductCategorySelection);
        stringGenderArray = appCompatActivity.getResources().getStringArray(R.array.AddProductMerchantGenderSelection);

        editTextProductBrandname.setOnClickListener(this);
        editTextProductCategory.setOnClickListener(this);
        editTextGender.setOnClickListener(this);

        saveData.setOnClickListener(this);

        return view;

    }


    private void setIdToAllVaribles(View view) {

        editTextProductBrandname = view.findViewById(R.id.editText_add_product_fragment_Product_brand_name);
        editTextProductDescription = view.findViewById(R.id.editText_add_product_fragment_Product_description);
        editTextProductName = view.findViewById(R.id.editText_add_product_fragment_Product_name);
        editTextProductPrice = view.findViewById(R.id.editText_add_product_fragment_Product_price);
        editTextGender = view.findViewById(R.id.editText_add_product_fragment_Product_gender);
        editTextProductCategory = view.findViewById(R.id.editText_add_product_fragment_Product_category);

        saveData = view.findViewById(R.id.save_Data);

    }

    @Override
    public void onClick(View v) {
        if (v == editTextProductCategory) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select product Category");
            builder.setIcon(R.mipmap.category);
            builder.setSingleChoiceItems(stringProductCategoryArray, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    stringProductCategory = stringProductCategoryArray[which];
                    editTextProductCategory.setText(stringProductCategory);
                }
            });
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    int possition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    stringProductCategory = stringProductCategoryArray[possition];
                    editTextProductCategory.setText(stringProductCategory);
                }
            });
            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        } else if (v == editTextGender) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select Gender");
            builder.setIcon(R.mipmap.gender);
            builder.setSingleChoiceItems(stringGenderArray, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    stringGender = stringGenderArray[which];
                    editTextGender.setText(stringGender);
                }
            });
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    int possition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    stringGender = stringGenderArray[possition];
                    editTextGender.setText(stringGenderArray[possition]);
                }
            });
            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        } else if (v == editTextProductBrandname) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select the brand");
            builder.setIcon(R.mipmap.brands);
            builder.setSingleChoiceItems(stringBrandnameArray, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    stringBrandname = stringBrandnameArray[which];
                    editTextProductBrandname.setText(stringBrandname);
                }
            });
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    int possition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    editTextProductBrandname.setText(stringBrandnameArray[possition]);
                }
            });
            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else if (v == saveData) {

            stringProductDescription = editTextProductDescription.getText().toString();
            stringProductName = editTextProductName.getText().toString();
            stringProductPrice = editTextProductPrice.getText().toString();

//            if (editTextProductCategory.getText().toString().equals("")) {
//                Toast.makeText(appCompatActivity, "Enter the Product Category", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (editTextProductBrandname.getText().toString().equals("")) {
//                Toast.makeText(appCompatActivity, "Enter the Brand Name of the Product", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (editTextProductName.getText().toString().equals("")) {
//                Toast.makeText(appCompatActivity, "Enter the Product Name", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (editTextGender.getText().toString().equals("")) {
//                Toast.makeText(appCompatActivity, "Enter the Gender", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (editTextProductPrice.getText().toString().equals("")) {
//                Toast.makeText(appCompatActivity, "Enter the Product Price", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (editTextProductPrice.getText().toString().length() < 2 || editTextProductPrice.getText().toString().length() > 6) {
//                Toast.makeText(appCompatActivity, "Please Enter correct price or you have entered very high price", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (editTextProductDescription.getText().toString().equals("")) {
//                Toast.makeText(appCompatActivity, "Enter the Product description", Toast.LENGTH_SHORT).show();
//                return;
//            }
            MerchantAddProductImageFragment addProductImageFragment = new MerchantAddProductImageFragment(stringBrandname, stringProductDescription, stringProductName, stringProductPrice, stringGender, stringProductCategory,phoneKey);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_merchant, addProductImageFragment).commit();
        }
    }
}
