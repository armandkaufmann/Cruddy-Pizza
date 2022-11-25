package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class activity_order_placed extends AppCompatActivity {
    //textViews titles
    TextView textViewOrderPlacedTitle;
    TextView textViewOrderPlacedSize;
    TextView textViewOrderPlacedToppings;
    TextView textViewOrderCustomerInfoTitle;
    TextView textViewOrderPlacedCustName;
    TextView textViewORderPlacedCustAddress;
    TextView textViewORderPlacedCustPhone;

    //textviews to insert data
    TextView textViewOrderPlacedSizeSelection;
    TextView textViewOrderPlacedIngredientsInfo;
    TextView textViewOrderPlaceCustNameInfo;
    TextView textViewOrderPlaceCustAddressInfo;
    TextView textViewOrderPlaceCustPhoneInfo;

    //buttons
    Button buttonOrderPlacedHome;

    //data from bundle
    Language language;
    Customer customer;
    List<Ingredient> ingredientList;
    int size;

    //string resources
    String[] ingredientsString;
    String[] sizesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        //textView titles
        textViewOrderPlacedTitle = findViewById(R.id.textViewOrderPlacedTitle);
        textViewOrderPlacedSize = findViewById(R.id.textViewOrderPlacedSize);
        textViewOrderPlacedToppings = findViewById(R.id.textViewOrderPlacedToppings);
        textViewOrderCustomerInfoTitle = findViewById(R.id.textViewOrderCustomerInfoTitle);
        textViewOrderPlacedCustName = findViewById(R.id.textViewOrderPlacedCustName);
        textViewORderPlacedCustAddress = findViewById(R.id.textViewORderPlacedCustAddress);
        textViewORderPlacedCustPhone = findViewById(R.id.textViewORderPlacedCustPhone);

        //textview data
        textViewOrderPlacedSizeSelection = findViewById(R.id.textViewOrderPlacedSizeSelection);
        textViewOrderPlacedIngredientsInfo = findViewById(R.id.textViewOrderPlacedIngredientsInfo);
        textViewOrderPlaceCustNameInfo = findViewById(R.id.textViewOrderPlaceCustNameInfo);
        textViewOrderPlaceCustAddressInfo = findViewById(R.id.textViewOrderPlaceCustAddressInfo);
        textViewOrderPlaceCustPhoneInfo = findViewById(R.id.textViewOrderPlaceCustPhoneInfo);

        //buttons
        buttonOrderPlacedHome = findViewById(R.id.buttonOrderPlacedHome);

        //getting data from bundles
        language = (Language) getIntent().getSerializableExtra("language");
        customer = (Customer) getIntent().getSerializableExtra("customer");
        ingredientList = (ArrayList<Ingredient>) getIntent().getSerializableExtra("ingredientsList");
        size = (Integer) getIntent().getIntExtra("size", 1);

        //loading string array
        loadIngredients();
        loadSizes();

        //setting the language
        setLanguage();

    }

    //methods
    private void loadIngredients() {
        if (language == Language.ENGLISH){
            ingredientsString = getResources().getStringArray(R.array.ingredients_EN); //getting ingredients from string array in english
        }else{
            ingredientsString = getResources().getStringArray(R.array.ingredients_FR); //getting ingredients from string array in french
        }
    }

    private void loadSizes(){
        if (language == Language.ENGLISH){
            sizesString = getResources().getStringArray(R.array.sizes_EN);
        }else{
            sizesString = getResources().getStringArray(R.array.sizes_FR);
        }
    }

    private void setLanguage() {
        if (language == Language.ENGLISH){ //english
            //textView titles
            textViewOrderPlacedTitle.setText(R.string.orderPlacedTitleEN);
            textViewOrderPlacedSize.setText(R.string.orderPlacedSizeEN);
            textViewOrderPlacedToppings.setText(R.string.orderPlacedToppingsEN);
            textViewOrderCustomerInfoTitle.setText(R.string.orderPlacedCustomerInfoEN);
            textViewOrderPlacedCustName.setText(R.string.CustNameEN);
            textViewORderPlacedCustAddress.setText(R.string.CustAddressEN);
            textViewORderPlacedCustPhone.setText(R.string.CustPhoneEN);

            //buttons
            buttonOrderPlacedHome.setText(R.string.orderPlacedCustomerHomeEN);
        }else{ //french
            //textView titles
            textViewOrderPlacedTitle.setText(R.string.orderPlacedTitleFR);
            textViewOrderPlacedSize.setText(R.string.orderPlacedSizeFR);
            textViewOrderPlacedToppings.setText(R.string.orderPlacedToppingsFR);
            textViewOrderCustomerInfoTitle.setText(R.string.orderPlacedCustomerInfoFR);
            textViewOrderPlacedCustName.setText(R.string.CustNameFR);
            textViewORderPlacedCustAddress.setText(R.string.CustAddressFR);
            textViewORderPlacedCustPhone.setText(R.string.CustPhoneFR);

            //buttons
            buttonOrderPlacedHome.setText(R.string.orderPlacedCustomerHomeFR);
        }
    }//end setLanguage method
}