package com.example.vikas.loginsqlitedata.MerchantAddProductToApp;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vikas.loginsqlitedata.MerchantHome.MerchantHomeMainContainTabsFragment;
import com.example.vikas.loginsqlitedata.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MerchantAddProductImageFragment extends Fragment implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private AppCompatActivity appCompatActivity;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference, mDataBaseReferenceMerchantLoginSignUp, DataBaseReferenceMerchantLoginSignUp;
    private StorageReference mStorageReference, storageReference, uStorageReference;
    private final static int IMAGE_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST = 2;
    private StorageTask mStorageTask;
    private Uri filePath;
    private ImageView clickImage;
    private File filename;

    private Button chooseImageButton, uploadImageButton;
    private CircleImageView circleImageView;

    private String stringBrandname, stringProductDescription, stringProductName, stringProductPrice, stringGender, stringProductCategory, phoneKey, productKey;

    @SuppressLint("ValidFragment")
    public MerchantAddProductImageFragment(String stringBrandname, String stringProductDescription, String stringProductName, String stringProductPrice, String stringGender, String stringProductCategory, String phoneKey) {
        // Required empty public constructor
        this.stringBrandname = stringBrandname;
        this.stringProductDescription = stringProductDescription;
        this.stringProductName = stringProductName;
        this.stringProductPrice = stringProductPrice;
        this.stringGender = stringGender;
        this.stringProductCategory = stringProductCategory;
        this.phoneKey = phoneKey;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_add_product_image, container, false);
        getIdForAllvariables(view);


        appCompatActivity = (AppCompatActivity) view.getContext();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("MerchantAccout");
        mDataBaseReferenceMerchantLoginSignUp = mDatabaseReference.child("MerchantAccoutLoginSignUp");
        DataBaseReferenceMerchantLoginSignUp = mDataBaseReferenceMerchantLoginSignUp.child(phoneKey);


        mStorageReference = FirebaseStorage.getInstance().getReference("MerchantAccout");
        storageReference = mStorageReference.child("MerchantProducts");

        chooseImageButton.setOnClickListener(this);
        uploadImageButton.setOnClickListener(this);
        clickImage.setOnClickListener(this);

        return view;
    }

    private void getIdForAllvariables(View view) {

        chooseImageButton = view.findViewById(R.id.choose_merchant_product_image_butoon);
        uploadImageButton = view.findViewById(R.id.upload_merchant_product_image_butoon);
        circleImageView = view.findViewById(R.id.circuler__merchant_product_image_view_edit_image);
        clickImage = view.findViewById(R.id.clickImage);
    }

    @Override
    public void onClick(View v) {
        if (v == chooseImageButton) {

            chooseImageFromGallery();

        } else if (v == uploadImageButton) {
            if (filePath != null) {
                if (mStorageTask != null && mStorageTask.isInProgress()) {
                    Toast.makeText(appCompatActivity, "Data Uploading is In Progress", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Data uploading in progress...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    uploadImageToFireBase();
                }
            } else {
                Toast.makeText(appCompatActivity, "Please select an Image", Toast.LENGTH_SHORT).show();
            }
        } else if (v == clickImage) {

            clickImageFromCamera();
        }
    }

    private void uploadImageToFireBase() {
        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String stringdate = dateFormat.format(Calendar.getInstance().getTime());
        long intData = 2 * Long.parseLong(stringdate, 10);
        productKey = phoneKey + String.valueOf(intData);
        uStorageReference = storageReference.child(productKey);
        mStorageTask = uStorageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                UserMerchantUploadProductImage userMerchantUploadProductImage = new UserMerchantUploadProductImage(taskSnapshot.getDownloadUrl().toString(), stringBrandname, stringProductDescription, stringProductName, stringProductPrice, stringGender, stringProductCategory, phoneKey, productKey);
                DatabaseReference uDatabaseReferenceProducttable = mDatabaseReference.child("MerchantProducts");
                DatabaseReference uDatabaseReference = uDatabaseReferenceProducttable.child(productKey);
                DatabaseReference uDataBaseReferenceMerchantLoginSignUp = DataBaseReferenceMerchantLoginSignUp.child("MerchantProductKey");
                uDataBaseReferenceMerchantLoginSignUp.setValue(productKey);

                uDatabaseReference.setValue(userMerchantUploadProductImage);

                progressDialog.dismiss();

                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_merchant, new MerchantAddProductToAppFragment()).addToBackStack(null).commit();

                Toast.makeText(appCompatActivity, "Data Uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                MerchantHomeMainContainTabsFragment merchantHomeMainContainTabsFragment = new MerchantHomeMainContainTabsFragment(phoneKey);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_merchant, merchantHomeMainContainTabsFragment).addToBackStack(null).commit();
                Toast.makeText(appCompatActivity, "Data Upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickImageFromCamera() {
//        File filename = new File(Environment.getExternalStorageDirectory().getPath() + "/GalaxyCart/testfile.jpg");
//        filePath = Uri.fromFile(new File(String.valueOf(filename)));

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, filePath);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    private void chooseImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            Cursor returnCursor = appCompatActivity.getContentResolver().query(filePath, null, null, null, null);

            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            int size = (int) returnCursor.getLong(sizeIndex);    ///it will return size in bytes
            int sizeInKB = size / 1000;

            if (sizeInKB < 500) {
                Picasso.with(getContext()).load(filePath).into(circleImageView);
            } else {
                chooseImageFromGallery();
                for (int i = 0; i < 3; i++) {
                    Toast.makeText(appCompatActivity, "Please select a small size image ", Toast.LENGTH_SHORT).show();
                }
            }
            Picasso.with(getContext()).load(filePath).into(circleImageView);
        }
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == CAMERA_REQUEST) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                filePath = getPathOfImage(getContext(), photo);
                circleImageView.setImageBitmap(photo);

            }
        }
    }

    private Uri getPathOfImage(Context context, Bitmap photo) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(appCompatActivity.getContentResolver(), photo, "title", null);
        return Uri.parse(path);
    }

}
