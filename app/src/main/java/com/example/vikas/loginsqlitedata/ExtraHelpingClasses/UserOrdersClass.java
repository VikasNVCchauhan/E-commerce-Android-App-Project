package com.example.vikas.loginsqlitedata.ExtraHelpingClasses;

public class UserOrdersClass {
    private String stringBrandname,stringPaymentType,image,stringOrderNumber, stringProductKey, stringProductName, stringProductPrice, stringProductOrderDelivery, stringProductExpectedDelivery, phoneKey, orderNumberstringProductMerchantPhoneKey, stringProductDescription, stringProductCategory, stringProductGender, stringProductDiscount, stringProductQuantity, stringProductCount,stringProductSize,stringProductMerchantProductConfirmationStatus;

    public UserOrdersClass(String image,String stringPaymentType,String stringOrderNumber,String stringBrandname, String stringProductKey, String stringProductName, String stringProductPrice, String stringProductOrderDelivery, String stringProductExpectedDelivery, String phoneKey, String orderNumberstringProductMerchantPhoneKey, String stringProductDescription, String stringProductCategory, String stringProductGender, String stringProductDiscount, String stringProductQuantity, String stringProductCount,String stringProductSize,String stringProductMerchantProductConfirmationStatus) {

        this.image = image;
        this.stringOrderNumber=stringOrderNumber;
        this.stringBrandname = stringBrandname;
        this.stringProductKey = stringProductKey;
        this.stringProductName = stringProductName;
        this.stringProductPrice = stringProductPrice;
        this.stringProductOrderDelivery = stringProductOrderDelivery;
        this.stringProductExpectedDelivery = stringProductExpectedDelivery;
        this.phoneKey = phoneKey;
        this.orderNumberstringProductMerchantPhoneKey = orderNumberstringProductMerchantPhoneKey;
        this.stringProductDescription = stringProductDescription;
        this.stringProductCategory = stringProductCategory;
        this.stringProductGender = stringProductGender;
        this.stringProductDiscount = stringProductDiscount;
        this.stringProductQuantity = stringProductQuantity;
        this.stringProductCount = stringProductCount;
        this.stringPaymentType=stringPaymentType;
        this.stringProductSize=stringProductSize;
        this.stringProductMerchantProductConfirmationStatus=stringProductMerchantProductConfirmationStatus;
    }

    public UserOrdersClass() {

    }

    public String getStringPaymentType() {
        return stringPaymentType;
    }

    public String getImage() {
        return image;
    }

    public String getStringBrandname() {
        return stringBrandname;
    }

    public String getStringProductKey() {
        return stringProductKey;
    }

    public String getStringProductName() {
        return stringProductName;
    }

    public String getStringProductPrice() {
        return stringProductPrice;
    }

    public String getStringProductOrderDelivery() {
        return stringProductOrderDelivery;
    }

    public String getStringProductExpectedDelivery() {
        return stringProductExpectedDelivery;
    }

    public String getPhoneKey() {
        return phoneKey;
    }

    public String getStringOrderNumber() {
        return stringOrderNumber;
    }

    public String getOrderNumberstringProductMerchantPhoneKey() { return orderNumberstringProductMerchantPhoneKey; }

    public String getStringProductDescription() {
        return stringProductDescription;
    }

    public String getStringProductCategory() {
        return stringProductCategory;
    }

    public String getStringProductGender() {
        return stringProductGender;
    }

    public String getStringProductDiscount() {
        return stringProductDiscount;
    }

    public String getStringProductQuantity() {
        return stringProductQuantity;
    }

    public String getStringProductCount() {
        return stringProductCount;
    }

    public String getStringProductSize() {
        return stringProductSize;
    }
}
