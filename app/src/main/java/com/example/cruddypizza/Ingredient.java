package com.example.cruddypizza;

import java.io.Serializable;

public class Ingredient implements Serializable {
    //member variables
    private String name;
    private int count = 0;
    private int id;

    public Ingredient(String pName, int pId){
        setName(pName);
        setId(pId);
    }

    //methods
    public void increaseCount(){
        count++;
    }
    public void decreaseCount(){
        count--;
    }

    //setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
