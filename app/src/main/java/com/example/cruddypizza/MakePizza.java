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

    //ingredients
    List<Ingredient> ingredientList = new ArrayList<>();
    String[] ingredientsString;

    //recyclerView
    RecyclerView recylcerViewIngredients; //recycler view
    IngredientsRVAdapter adapter;
    String maxIngredientsMessage;

    //language
    Language language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_pizza);

        //setting up views/textviews
        textViewMakePizzaTitle = findViewById(R.id.textViewMakePizzaTitle);
        textViewPickSize = findViewById(R.id.textViewPickSize);
        textViewPickToppings = findViewById(R.id.textViewPickToppings);

        radioButtonSizeSmall = findViewById(R.id.radioButtonSizeSmall);
        radioButtonSizeMedium = findViewById(R.id.radioButtonSizeMedium);
        radioButtonSizeLarge = findViewById(R.id.radioButtonSizeLarge);

        buttonMakePizzaPlaceOrder = findViewById(R.id.buttonMakePizzaPlaceOrder);
        buttonMakePizzaPlaceOrder.setOnClickListener(buttonMakePizzaPlaceOrderListener);

        buttonMakePizzaCancel = findViewById(R.id.buttonMakePizzaCancel);
        buttonMakePizzaCancel.setOnClickListener(buttonMakePizzaCancelListener);

        recylcerViewIngredients = (RecyclerView) findViewById(R.id.recylcerViewIngredients); //recycler view

        //language setup
        language = (Language) getIntent().getSerializableExtra("language");
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
            ingredientsString = getResources().getStringArray(R.array.ingredients_EN);
            maxIngredientsMessage = getResources().getString(R.string.MaxIngredientsMessageEN);
        }else{
            ingredientsString = getResources().getStringArray(R.array.ingredients_FR);
            maxIngredientsMessage = getResources().getString(R.string.MaxIngredientsMessageFR);
        }
    }

    private void addIngredients(){
        for (int i = 0; i < ingredientsString.length; i++){
            ingredientList.add(new Ingredient(ingredientsString[i]));
        }
    }

    private void setLanguage() {
        if (language == Language.ENGLISH){ //english
            //text views
            textViewMakePizzaTitle.setText(R.string.MakePizzaTitleEN);
            textViewPickSize.setText(R.string.MakePizzaSizeEN);
            textViewPickToppings.setText(R.string.MakePizzaToppingsEN);
            //buttons/ views
            radioButtonSizeSmall.setText(R.string.MakePizzaSizeSmallEN);
            radioButtonSizeMedium.setText(R.string.MakePizzaSizeMediumEN);
            radioButtonSizeLarge.setText(R.string.MakePizzaSizeLargeEN);
            buttonMakePizzaPlaceOrder.setText(R.string.MakePizzaPlaceOrderEN);
            buttonMakePizzaCancel.setText(R.string.buttonCancelEN);
        }else{ //french
            //text views
            textViewMakePizzaTitle.setText(R.string.MakePizzaTitleFR);
            textViewPickSize.setText(R.string.MakePizzaSizeFR);
            textViewPickToppings.setText(R.string.MakePizzaToppingsFR);
            //buttons/ views
            radioButtonSizeSmall.setText(R.string.MakePizzaSizeSmallFR);
            radioButtonSizeMedium.setText(R.string.MakePizzaSizeMediumFR);
            radioButtonSizeLarge.setText(R.string.MakePizzaSizeLargeFR);
            buttonMakePizzaPlaceOrder.setText(R.string.MakePizzaPlaceOrderFR);
            buttonMakePizzaCancel.setText(R.string.buttonCancelFR);
        }
    }//end setLanguage method

    //LISTENERS ====================================================================================
    private View.OnClickListener buttonMakePizzaPlaceOrderListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent i = new Intent(CustomerEntry.this, MakePizza.class);
//            i.putExtra("language", language);
//            startActivity(i);
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