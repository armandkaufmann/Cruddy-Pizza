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

import java.util.ArrayList;
import java.util.List;

public class Edit_order extends AppCompatActivity {
    //textviews
    TextView textViewEditOrderTitle;
    TextView textViewEditOrderSize;
    TextView textViewEditOrderToppings;

    //radioButtons
    RadioButton radioButtonEditOrderSizeSmall;
    RadioButton radioButtonEditOrderSizeMedium;
    RadioButton radioButtonEditOrderSizeLarge;

    //buttons
    Button buttonEditOrderSaveChanges;
    Button buttonEditOrderCancel;

    //language
    Language language;

    //order details
    int orderNum; //to hold the current number of the order in the database

    //ingredients and sizes
    List<Ingredient> ingredientList = new ArrayList<>();
    String[] ingredientsString;
    String[] sizesString;
    int sizesChoice;
    String sizeChoiceError;

    //recyclerView
    RecyclerView recylcerViewEditOrderIngredients; //recycler view
    IngredientsRVAdapter adapter;
    String maxIngredientsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        //textviews
        textViewEditOrderTitle = findViewById(R.id.textViewEditOrderTitle);
        textViewEditOrderSize = findViewById(R.id.textViewEditOrderSize);
        textViewEditOrderToppings = findViewById(R.id.textViewEditOrderToppings);

        //radioButtons
        radioButtonEditOrderSizeSmall = findViewById(R.id.radioButtonEditOrderSizeSmall);
        radioButtonEditOrderSizeMedium = findViewById(R.id.radioButtonEditOrderSizeMedium);
        radioButtonEditOrderSizeLarge = findViewById(R.id.radioButtonEditOrderSizeLarge);

        //buttons
        buttonEditOrderSaveChanges = findViewById(R.id.buttonEditOrderSaveChanges);
        buttonEditOrderSaveChanges.setOnClickListener(buttonEditOrderSaveChangesListener);

        buttonEditOrderCancel = findViewById(R.id.buttonEditOrderCancel);
        buttonEditOrderCancel.setOnClickListener(buttonEditOrderCancelListener);

        //recyclerview
        recylcerViewEditOrderIngredients = findViewById(R.id.recylcerViewEditOrderIngredients);

        //getting order ID
        orderNum = (Integer) getIntent().getIntExtra("orderNum", 0); //to hold the current number of the order in the database

        //language setup
        language = (Language) getIntent().getSerializableExtra("language");
        loadSizes(); //loading the sizes
        setLanguage();

        //generating ingredients
        loadIngredients();
        addIngredients(); //create ingredients objects and add to array list

        adapter = new IngredientsRVAdapter(ingredientList, maxIngredientsMessage);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recylcerViewEditOrderIngredients.setLayoutManager(manager);
        recylcerViewEditOrderIngredients.setAdapter(adapter);
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

    private void setLanguage(){
        if (language == Language.ENGLISH){
            //text views
            textViewEditOrderTitle.setText(R.string.editOrderTitleEN);
            textViewEditOrderSize.setText(R.string.MakePizzaSizeTitleEN);
            textViewEditOrderToppings.setText(R.string.MakePizzaToppingsEN);

            //buttons
            buttonEditOrderSaveChanges.setText(R.string.editOrderSaveEN);
            buttonEditOrderCancel.setText(R.string.buttonCancelEN);
        }else{
            //text views
            textViewEditOrderTitle.setText(R.string.editOrderTitleFR);
            textViewEditOrderSize.setText(R.string.MakePizzaSizeTitleFR);
            textViewEditOrderToppings.setText(R.string.MakePizzaToppingsFR);

            //buttons
            buttonEditOrderSaveChanges.setText(R.string.editOrderSaveFR);
            buttonEditOrderCancel.setText(R.string.buttonCancelFR);
        }
        radioButtonEditOrderSizeSmall.setText(sizesString[0]);
        radioButtonEditOrderSizeMedium.setText(sizesString[1]);
        radioButtonEditOrderSizeLarge.setText(sizesString[2]);
    }

    //LISTENERS ====================================================================================
    private View.OnClickListener buttonEditOrderSaveChangesListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Edit_order.this, Order_Details.class);
            Toast.makeText(getApplicationContext(), "Saved changes to order #" + (orderNum + 1), Toast.LENGTH_SHORT).show();
            i.putExtra("language", language);
            i.putExtra("orderNum", orderNum);
            startActivity(i);
        }
    };

    private View.OnClickListener buttonEditOrderCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Edit_order.this, Order_Details.class);
            i.putExtra("language", language);
            i.putExtra("orderNum", orderNum);
            startActivity(i);
        }
    };
}