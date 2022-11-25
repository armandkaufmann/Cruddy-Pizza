package com.example.cruddypizza;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pizza implements Serializable {
    //member variables/attributes
    private List<Ingredient> ingredientList;

    //constructor
    public Pizza(List<Ingredient> pIngredientList){
        setIngredientList(pIngredientList);
    }

    //setters and getters
    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
