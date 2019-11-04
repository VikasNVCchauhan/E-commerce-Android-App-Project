package com.example.vikas.loginsqlitedata.UserProfile;

public class UserFirebaseProfileImage  {
    private String mKey;
    String imagrUrl;

    public UserFirebaseProfileImage() {

    }

    public UserFirebaseProfileImage(String imagrUrl) {
        this.imagrUrl = imagrUrl;
    }

    public String getImagrUrl() {
        return imagrUrl;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getmKey() {
        return mKey;
    }
}
