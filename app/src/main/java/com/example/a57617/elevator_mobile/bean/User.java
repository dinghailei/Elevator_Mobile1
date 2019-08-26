package com.example.a57617.elevator_mobile.bean;

import java.io.Serializable;

public class User implements Serializable {
    private String phone;
    private String password;

    public User(){}

    public User(String phone, String password){
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
