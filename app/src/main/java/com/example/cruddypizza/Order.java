package com.example.cruddypizza;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private ArrayList<Integer> toppings = new ArrayList<>();
    private Integer size;
    private Integer progress;

    public Order(String pOrderId, String pCustomerInfo, String pToppings, String pSize, String pProgress,Integer numToppings){
        parseCustomerInfo(pCustomerInfo);
        setOrderId(Integer.parseInt(pOrderId));
        setSize(Integer.parseInt(pSize));
        setProgress(Integer.parseInt(pProgress));

        parseToppings(pToppings, numToppings);
    }

    private void parseToppings(String tempToppings, Integer numToppings){
        String[] tempToppingList;
        tempToppingList = tempToppings.split(",", numToppings -1);

        for (int i = 0; i < tempToppingList.length; i++){
            toppings.add(Integer.parseInt(tempToppingList[i]));
        }
    }

    private void parseCustomerInfo(String customerInfo){
        String[] tempCustInfoList;
        tempCustInfoList = customerInfo.split(",", 2);

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
}
