package com.example.vikas.loginsqlitedata.UserProfile;

import android.widget.Toast;

public class UserFireBaseAddress {
    private String houseNumberOrNameDataBase, landMarkDataBase, stateDataBase, cityDataBase, areaPinCodeDataBase, phoneDataBaase,checking_AVELABILITY;

    public UserFireBaseAddress(String houseNumberOrNameDataBase, String landMarkDataBase, String stateDataBase, String cityDataBase, String areaPinCodeDataBase, String phoneDataBaase,String checking_AVELABILITY) {
        this.houseNumberOrNameDataBase = houseNumberOrNameDataBase;
        this.landMarkDataBase = landMarkDataBase;
        this.stateDataBase = stateDataBase;
        this.cityDataBase = cityDataBase;
        this.areaPinCodeDataBase = areaPinCodeDataBase;
        this.phoneDataBaase = phoneDataBaase;
        this.checking_AVELABILITY=checking_AVELABILITY;
    }

    public UserFireBaseAddress() {
    }

    public String getCHECKING_AVELABILITY() {
        return checking_AVELABILITY;
    }

    public String getHouseNumberOrNameDataBase() {
        return houseNumberOrNameDataBase;
    }

    public String getLandMarkDataBase() {
        return landMarkDataBase;
    }

    public String getStateDataBase() {
        return stateDataBase;
    }

    public String getCityDataBase() {
        return cityDataBase;
    }

    public String getAreaPinCodeDataBase() {
        return areaPinCodeDataBase;
    }

    public String getPhoneDataBaase() {
        return phoneDataBaase;
    }
}
