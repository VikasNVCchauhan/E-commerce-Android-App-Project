package com.example.vikas.loginsqlitedata.UserProfile;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.example.vikas.loginsqlitedata.HomeScreen.HomeFragment;

import com.example.vikas.loginsqlitedata.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class OnClickOnEditImageInMainPrifilefragmentFragment extends Fragment implements View.OnClickListener {


    private SQLiteDatabase sqLiteDatabase;
    private ProgressDialog progressDialog;
    private String checkPhone, ProfileImageURLForSqlite, verifyphoneNumber = "", imageURL = "";
    private AppCompatActivity appCompatActivity;
    private Button chooseimageFromGallery, uploadImageToFirebaseDatabase;
    private CircleImageView circleImageView;
    private ImageView imageView;
    private Uri filepathFromGallery;
    private static final int MY_CAMERA_PERMISSION_CODE = 3;      //want to ask runtime permission but code is not still completed
    private static final int PIC_IMAGE_REQUEST = 1;
    private static final int CAPTURE_IMAGE_REQUEST = 2;

    private StorageTask mStorageTask;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    @SuppressLint("ValidFragment")
    public OnClickOnEditImageInMainPrifilefragmentFragment(String checkPhone) {
        this.checkPhone = checkPhone;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_click_on_edit_image_in_main_prifilefragment, container, false);

        appCompatActivity = (AppCompatActivity) view.getContext();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("UserProFilePicture");

        mStorageReference = FirebaseStorage.getInstance().getReference("UserProFilePicture");

        sqLiteDatabase = getActivity().openOrCreateDatabase("galaxyCart", MODE_PRIVATE, null); //This for data privacy insted of following type creationof data
        sqLiteDatabase.execSQL("Create table if not exists UserProfilePicture(imageURL varchar,Phone varchar)");

        getIdForAllVariables(view);

        viewValueOfSQliteDatabase();
        if (!verifyphoneNumber.equals("")) {
            Picasso.with(getContext()).load(imageURL).into(circleImageView);
        }

        chooseimageFromGallery.setOnClickListener(this);
        uploadImageToFirebaseDatabase.setOnClickListener(this);
        imageView.setOnClickListener(this);
        return view;
    }

    private void getIdForAllVariables(View view) {
        chooseimageFromGallery = view.findViewById(R.id.choose_image_butoon);
        uploadImageToFirebaseDatabase = view.findViewById(R.id.upload_image_butoon);

        imageView = view.findViewById(R.id.clickImageUserProfile);
        circleImageView = view.findViewById(R.id.circuler_image_view_edit_image);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if (v == chooseimageFromGallery) {
            getImageFromGallery();
        } else if (v == uploadImageToFirebaseDatabase) {
            if (filepathFromGallery != null) {

                if (mStorageTask != null && mStorageTask.isInProgress()) {
                    Toast.makeText(appCompatActivity, "Uploading is in progress...", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Image uploading in progress...");
                    progressDialog.show();
                    uploadImagesToFirebasedatabase();
                }
            } else {
                Toast.makeText(appCompatActivity, "No file selected", Toast.LENGTH_SHORT).show();
            }
        } else if (v == imageView) {
//            if(appCompatActivity.checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
//            {
//                requestPermissions(new String[]{android.Manifest.permission.CAMERA},MY_CAMERA_PERMISSION_CODE);
//            }
//            else
//            {
            takeAPhotoFromeCamera();
//            }
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==MY_CAMERA_PERMISSION_CODE)
//        {
//            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
//            {
//                takeAPhotoFromeCamera();
//            }
//            else
//            {
//                Toast.makeText(appCompatActivity, "camera permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void takeAPhotoFromeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST);
    }

    private void uploadImagesToFirebasedatabase() {

        final StorageReference uFirebaseStorage = mStorageReference.child(checkPhone);
        mStorageTask = uFirebaseStorage.putFile(filepathFromGallery).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final UserFirebaseProfileImage userFirebaseProfileImage = new UserFirebaseProfileImage(taskSnapshot.getDownloadUrl().toString());

                DatabaseReference uDatabaseReference = mDatabaseReference.child(checkPhone);
                uDatabaseReference.setValue(userFirebaseProfileImage);

                final ArrayList<String> userPhonekeyFromFireBase = new ArrayList<>();
                final ArrayList<String> profileImageURL = new ArrayList<>();

                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            userPhonekeyFromFireBase.add(ds.getKey());
                            UserFirebaseProfileImage userFirebaseProfileImage1 = ds.getValue(UserFirebaseProfileImage.class);
                            profileImageURL.add(userFirebaseProfileImage.getImagrUrl());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                        Toast.makeText(appCompatActivity, "Image FetchingFailed " + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
                    }
                });

                CountDownTimer countDownTimer = new CountDownTimer(1000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        for (int i = 0; i < userPhonekeyFromFireBase.size(); i++) {
                            if (checkPhone.equals(userPhonekeyFromFireBase.get(i))) {
                                ProfileImageURLForSqlite = profileImageURL.get(i).toString();

                                viewValueOfSQliteDatabase();

                                if (!verifyphoneNumber.equals("")) {

                                    if (verifyphoneNumber.equals(checkPhone)) {
                                        upDatedataInSqlitedatabase(ProfileImageURLForSqlite, checkPhone);

                                    } else {
                                        insertDataIntoSqlitedataBase(ProfileImageURLForSqlite, checkPhone);
                                    }
                                } else {
                                    insertDataIntoSqlitedataBase(ProfileImageURLForSqlite, checkPhone);
                                }
                            }
                        }
                        HomeFragment homeFragment = new HomeFragment();
                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).addToBackStack(null).commit();
                        progressDialog.dismiss();
                        Toast.makeText(appCompatActivity, "ImageUploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                };
                countDownTimer.start();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(appCompatActivity, "Image Uploading failed..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PIC_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filepathFromGallery = data.getData();

            Cursor returnCursor = appCompatActivity.getContentResolver().query(filepathFromGallery, null, null, null, null);
            /*
             * Get the column indexes of the data in the Cursor,
             * move to the first row in the Cursor, get the data,
             * and display it.
             */
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            int size = (int) returnCursor.getLong(sizeIndex);
            int sizeInKB = size / 1000;

            if (sizeInKB < 500) {
                Picasso.with(getContext()).load(filepathFromGallery).into(circleImageView);
            } else {
                getImageFromGallery();
                for (int i = 0; i < 3; i++) {
                    Toast.makeText(appCompatActivity, "Please select a small size image ", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == CAPTURE_IMAGE_REQUEST) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                circleImageView.setImageBitmap(bitmap);
                filepathFromGallery = getImageUri(appCompatActivity.getApplicationContext(), bitmap);
            }
        }
    }

    private Uri getImageUri(Context applicationContext, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private void viewValueOfSQliteDatabase() {
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from UserProfilePicture where Phone='" + checkPhone + "'", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            imageURL = cursor.getString(0);
            verifyphoneNumber = cursor.getString(1);
        }
    }

    private void insertDataIntoSqlitedataBase(String filePath, String phonenumber) {
        String filephoto = filePath;
        String Phonenumber = phonenumber;

        sqLiteDatabase.execSQL("insert into UserProfilePicture values('" + filephoto + "','" + Phonenumber + "')");
    }

    private void upDatedataInSqlitedatabase(String filePath, String phonenumber) {
        String filephoto = filePath;
        String Phonenumber = phonenumber;
        sqLiteDatabase.execSQL("update UserProfilePicture set imageURL='" + filephoto + "',Phone='" + Phonenumber + "'");
    }
}
