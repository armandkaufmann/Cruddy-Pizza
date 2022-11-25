package com.example.cruddypizza;

public class Ingredient {
    //member variables
    private String name;
    private int count = 0;

    public Ingredient(String pName){
        setName(pName);
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
}
