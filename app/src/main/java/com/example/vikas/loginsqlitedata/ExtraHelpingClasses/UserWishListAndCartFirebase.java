package com.example.vikas.loginsqlitedata.ExtraHelpingClasses;

public class UserWishListAndCartFirebase {

    private String image;
    private String imageKey;
    private String stringBrandname,stringProductKey, stringProductDescription, stringProductName, stringProductPrice, stringGender, stringProductCategory,phoneKey,TAG;

    public UserWishListAndCartFirebase() {

    }
    public UserWishListAndCartFirebase(String image,String stringBrandname,String stringProductDescription,String stringProductName,String stringProductPrice,String stringGender,String stringProductCategory,String phoneKey,String stringProductKey,String TAG) {
        this.image = image;
        this.stringBrandname=stringBrandname;
        this.stringProductDescription=stringProductDescription;
        this.stringProductName=stringProductName;
        this.stringProductPrice=stringProductPrice;
        this.stringGender=stringGender;
        this.stringProductCategory=stringProductCategory;
        this.phoneKey=phoneKey;
        this.stringProductKey=stringProductKey;
        this.TAG=TAG;
    }

    public String getTAG() {
        return TAG;
    }

    public String getImage() {
        return image;
    }

    public String getStringProductKey() {
        return stringProductKey;
    }

    public String getStringBrandname() {
        return stringBrandname;
    }

    public String getStringProductDescription() {
        return stringProductDescription;
    }

    public String getStringProductName() {
        return stringProductName;
    }

    public String getStringProductPrice() {
        return stringProductPrice;
    }

    public String getStringGender() {
        return stringGender;
    }

    public String getStringProductCategory() {
        return stringProductCategory;
    }

    public String getPhoneKey() {
        return phoneKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImageKey() {
        return imageKey;
    }
}
