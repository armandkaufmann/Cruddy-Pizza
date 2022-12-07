package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MakePizza extends AppCompatActivity {
    //textViews
    TextView textViewMakePizzaTitle;
    TextView textViewPickSize;
    TextView textViewPickToppings;

    //views
    RadioButton radioButtonSizeSmall;
    RadioButton radioButtonSizeMedium;
    RadioButton radioButtonSizeLarge;
    Button buttonMakePizzaPlaceOrder;
    Button buttonMakePizzaCancel;

    //ingredients and sizes
    List<Ingredient> ingredientList = new ArrayList<>();
    String[] ingredientsString;
    String[] sizesString;
    int sizesChoice;
    String sizeChoiceError;
    String toppingsChoiceError;

    //recyclerView
    RecyclerView recylcerViewIngredients; //recycler view
    IngredientsRVAdapter adapter;
    String maxIngredientsMessage;

    //language
    Language language;

    //customer
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_pizza);

        //setting up views/textviews
        textViewMakePizzaTitle = findViewById(R.id.textViewMakePizzaTitle);
        textViewPickSize = findViewById(R.id.textViewOrderPlacedSize);
        textViewPickToppings = findViewById(R.id.textViewOrderPlacedToppings);

        radioButtonSizeSmall = findViewById(R.id.radioButtonSizeSmall);
        radioButtonSizeMedium = findViewById(R.id.radioButtonSizeMedium);
        radioButtonSizeLarge = findViewById(R.id.radioButtonSizeLarge);

        buttonMakePizzaPlaceOrder = findViewById(R.id.buttonMakePizzaPlaceOrder);
        buttonMakePizzaPlaceOrder.setOnClickListener(buttonMakePizzaPlaceOrderListener);

        buttonMakePizzaCancel = findViewById(R.id.buttonMakePizzaCancel);
        buttonMakePizzaCancel.setOnClickListener(buttonMakePizzaCancelListener);

        recylcerViewIngredients = (RecyclerView) findViewById(R.id.recylcerViewIngredients); //recycler view

        //customer info
        customer = (Customer) getIntent().getSerializableExtra("customer");

        //language setup
        language = (Language) getIntent().getSerializableExtra("language");
        loadSizes(); //loading the sizes
        setLanguage();

        //generating ingredients
        loadIngredients();
        addIngredients(); //create ingredients objects and add to array list

        adapter = new IngredientsRVAdapter(ingredientList, maxIngredientsMessage);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recylcerViewIngredients.setLayoutManager(manager);
        recylcerViewIngredients.setAdapter(adapter);
    }

    //METHODS ======================================================================================
    private void loadIngredients() {
        if (language == Language.ENGLISH){
            ingredientsString = getResources().getStringArray(R.array.ingredients_EN); //getting ingredients from string array in english
            maxIngredientsMessage = getResources().getString(R.string.MaxIngredientsMessageEN);
        }else{
            ingredientsString = getResources().getStringArray(R.array.ingredients_FR); //getting ingredients from string array in french
            maxIngredientsMessage = getResources().getString(R.string.MaxIngredientsMessageFR);
        }
    }

    private void loadSizes(){
        if (language == Language.ENGLISH){
            sizesString = getResources().getStringArray(R.array.sizes_EN);
        }else{
            sizesString = getResources().getStringArray(R.array.sizes_FR);
        }
    }

    private void addIngredients(){
        for (int i = 0; i < ingredientsString.length; i++){
            ingredientList.add(new Ingredient(ingredientsString[i], i));
        }
    }

    private void setLanguage() {
        if (language == Language.ENGLISH){ //english
            //text views
            textViewMakePizzaTitle.setText(R.string.MakePizzaTitleEN);
            textViewPickSize.setText(R.string.MakePizzaSizeEN);
            textViewPickToppings.setText(R.string.MakePizzaToppingsEN);
            //buttons/ views
            buttonMakePizzaPlaceOrder.setText(R.string.MakePizzaPlaceOrderEN);
            buttonMakePizzaCancel.setText(R.string.buttonCancelEN);
            //error message
            sizeChoiceError = getResources().getString(R.string.SizeChoiceErrorEN);
            toppingsChoiceError = getResources().getString(R.string.ToppingChoiceErrorEN);
        }else{ //french
            //text views
            textViewMakePizzaTitle.setText(R.string.MakePizzaTitleFR);
            textViewPickSize.setText(R.string.MakePizzaSizeFR);
            textViewPickToppings.setText(R.string.MakePizzaToppingsFR);
            //buttons/ views
            buttonMakePizzaPlaceOrder.setText(R.string.MakePizzaPlaceOrderFR);
            buttonMakePizzaCancel.setText(R.string.buttonCancelFR);
            //error message
            sizeChoiceError = getResources().getString(R.string.SizeChoiceErrorFR);
            toppingsChoiceError = getResources().getString(R.string.ToppingChoiceErrorFR);
        }
        radioButtonSizeSmall.setText(sizesString[0]);
        radioButtonSizeMedium.setText(sizesString[1]);
        radioButtonSizeLarge.setText(sizesString[2]);
    }//end setLanguage method

    private boolean getSizeChoice(){
        boolean hasSelection = false;
        if (radioButtonSizeSmall.isChecked()){
            sizesChoice = 0;
            hasSelection = true;
        }else if (radioButtonSizeMedium.isChecked()){
            sizesChoice = 1;
            hasSelection = true;
        }else if (radioButtonSizeLarge.isChecked()){
            sizesChoice = 2;
            hasSelection = true;
        }
        return hasSelection;
    }

    //LISTENERS ====================================================================================
    private View.OnClickListener buttonMakePizzaPlaceOrderListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!getSizeChoice()){ //if they didn't select a size
                Toast.makeText(getApplicationContext(), sizeChoiceError, Toast.LENGTH_SHORT).show();
            }else if(adapter.ingredientsCount == 0){
                Toast.makeText(getApplicationContext(), toppingsChoiceError, Toast.LENGTH_SHORT).show();
            } else{ //if they selected a size
                Intent i = new Intent(MakePizza.this, activity_order_placed.class);
                i.putExtra("language", language); //language selection enum
                i.putExtra("customer", customer); //customer object
                i.putExtra("ingredientsList", (Serializable) adapter.getIngredients()); //array list of ingredients
                i.putExtra("size", sizesChoice); //size int
                startActivity(i);
            }
        }
    };

    private View.OnClickListener buttonMakePizzaCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MakePizza.this, MainActivity.class);
            startActivity(i);
        }
    };
}