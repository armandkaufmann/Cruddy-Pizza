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

    //methods
    public void parseIngredients(){ //removing ingredients that have a count of 0
        for (int i = 0; i < ingredientList.size(); i++){
            if (ingredientList.get(i).getCount() == 0){
                ingredientList.remove(i); //remove ingredients with a count of 0
            }
        }
    }

    //setters and getters
    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
