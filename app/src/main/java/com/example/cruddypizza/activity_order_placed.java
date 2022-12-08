package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.database.*;

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
    String noToppingsMsg;

    //database connection
    DBAdapter db;

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
        buttonOrderPlacedHome.setOnClickListener(buttonOrderPlacedHomeListener);

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

        setCustomerOrder(); //display customer order information

        //customer info
        String customerInfo = customer.getName() + "," + customer.getAddress() + "," + customer.getNumber();

        //toppings
        String toppingsInfo = "";
        for (int i = 0; i < ingredientList.size(); i++){
            if (i != ingredientList.size() - 1){
                toppingsInfo += ingredientList.get(i).getCount() + ",";
            }else{
                toppingsInfo += ingredientList.get(i).getCount();
            }
        }

        //getting the current date and time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String date = dateFormat.format(calendar.getTime());

        //inserting into DB
        //inserting => String customer,String toppings, Integer size, Integer progress
        try{
            db = new DBAdapter(this);

            db.open();
            long id = db.insertOrder(customerInfo, toppingsInfo, size, 0, date);
            db.close();
        }catch (SQLiteException e){
            Log.w("ACTIVITY_ORDER_PLACED", "Database Error: " + e.toString());
        }
        catch (Exception e){
            Log.w("ACTIVITY_ORDER_PLACED", "Error: " + e.toString());
        }

    }

    //METHODS ======================================================================================
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
            noToppingsMsg = getResources().getString(R.string.orderPlacedNoToppingsEN);
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
            noToppingsMsg = getResources().getString(R.string.orderPlacedNoToppingsFR);
            //buttons
            buttonOrderPlacedHome.setText(R.string.orderPlacedCustomerHomeFR);
        }
    }//end setLanguage method

    private void setCustomerOrder(){
        textViewOrderPlacedSizeSelection.setText(sizesString[size]); //setting the size of the pizza

        //displaying all toppings chosen
        String toppingsOrder = "";
        boolean orderPlaced = false; //to figure out if there were any toppings at all
        //outputting the toppings
        for (int i = 0; i < ingredientList.size(); i++){
            if (ingredientList.get(i).getCount() != 0){
                toppingsOrder += ingredientsString[ingredientList.get(i).getId()] + " x " + ingredientList.get(i).getCount() + "\n";
                orderPlaced = true;
            }
        }
        if (orderPlaced){
            textViewOrderPlacedIngredientsInfo.setText(toppingsOrder);
        }else{
            textViewOrderPlacedIngredientsInfo.setText(noToppingsMsg);
        }


        //displaying customer info
        textViewOrderPlaceCustNameInfo.setText(customer.getName());
        textViewOrderPlaceCustAddressInfo.setText(customer.getAddress());
        textViewOrderPlaceCustPhoneInfo.setText(customer.getNumber());
    }

    //LISTENERS ====================================================================================
    private View.OnClickListener buttonOrderPlacedHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(activity_order_placed.this, MainActivity.class);
            startActivity(i);
        }
    };
}