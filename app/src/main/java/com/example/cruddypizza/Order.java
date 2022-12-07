package com.example.cruddypizza;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int orderId;
    //raw data
    private String rawCustDetails;
    private String rawToppings;

    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private ArrayList<Integer> toppings = new ArrayList<>();
    private Integer size;
    private Integer progress;
    private String date;

    public Order(String pOrderId, String pCustomerInfo, String pToppings, String pSize, String pProgress, String pDate){
        parseCustomerInfo(pCustomerInfo);
        setOrderId(Integer.parseInt(pOrderId));
        setSize(Integer.parseInt(pSize));
        setProgress(Integer.parseInt(pProgress));
        setDate(pDate);

        setRawCustDetails(pCustomerInfo);
        setRawToppings(pToppings);

        parseToppings(pToppings);
    }

    public void parseToppings(String tempToppings){
        toppings = new ArrayList<>(); //resetting in case
        String[] tempToppingList;
        tempToppingList = tempToppings.split(",");

        for (int i = 0; i < tempToppingList.length; i++){
            toppings.add(Integer.parseInt(tempToppingList[i]));
        }
    }

    public void parseCustomerInfo(String customerInfo){
        String[] tempCustInfoList;
        tempCustInfoList = customerInfo.split(",");
        setCustomerName(tempCustInfoList[0]);
        setCustomerAddress(tempCustInfoList[1]);
        setCustomerPhone(tempCustInfoList[2]);
    }

    //setters and getters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public ArrayList<Integer> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<Integer> toppings) {
        this.toppings = toppings;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getRawCustDetails() {
        return rawCustDetails;
    }

    public void setRawCustDetails(String rawCustDetails) {
        this.rawCustDetails = rawCustDetails;
    }

    public String getRawToppings() {
        return rawToppings;
    }

    public void setRawToppings(String rawToppings) {
        this.rawToppings = rawToppings;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
