package com.example.vikas.loginsqlitedata.LoginSignUp;

public class User {
    private String firstNameDataBase, lastNameDataBase, emailAddressDataBase, phoneDataBase, passwordDataBase;

    public User(String firstNameDataBase, String lastNameDataBase, String emailAddressDataBase, String phoneDataBase, String passwordDataBase) {
        this.firstNameDataBase = firstNameDataBase;
        this.lastNameDataBase = lastNameDataBase;
        this.emailAddressDataBase = emailAddressDataBase;
        this.phoneDataBase = phoneDataBase;
        this.passwordDataBase = passwordDataBase;
    }

    public User() {

    }

    public String getFirstNameDataBase() {
        return firstNameDataBase;
    }

    public String getLastNameDataBase() {
        return lastNameDataBase;
    }

    public String getEmailAddressDataBase() {
        return emailAddressDataBase;
    }

    public String getPhoneDataBase() {
        return phoneDataBase;
    }

    public String getPasswordDataBase() {
        return passwordDataBase;
    }
}
