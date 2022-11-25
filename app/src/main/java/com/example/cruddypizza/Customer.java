package com.example.cruddypizza;

import java.io.Serializable;

public class Customer implements Serializable {
    private String name = "";
    private String address = "";
    private String number = "";

    //constructor
    public Customer(){//default constructor

    }

    public Customer(String pName, String pAddress, String pNumber){
        setName(pName);
        setAddress(pAddress);
        setNumber(pNumber);
    }

    //setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
