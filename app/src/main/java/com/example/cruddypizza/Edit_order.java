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
    Order orderDetails; //to hold the current number of the order in the database

    //ingredients and sizes
    List<Ingredient> ingredientList = new ArrayList<>();
    String[] ingredientsString;
    String[] sizesString;
    int sizesChoice;
    String sizeChoiceError;
    String toppingsChoiceError;

    //recyclerView
    RecyclerView recylcerViewEditOrderIngredients; //recycler view
    IngredientsRVAdapter adapter;
    String maxIngredientsMessage;

    //database
    DBAdapter db = new DBAdapter(this);

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
        orderDetails = (Order) getIntent().getSerializableExtra("orderDetails"); //to hold the current number of the order in the database

        //setting the data from the order details
        if (orderDetails.getSize() == 0){
            radioButtonEditOrderSizeSmall.setChecked(true);
        }else if (orderDetails.getSize() == 1){
            radioButtonEditOrderSizeMedium.setChecked(true);
        }else{
            radioButtonEditOrderSizeLarge.setChecked(true);
        }

        //language setup
        language = (Language) getIntent().getSerializableExtra("language");
        loadSizes(); //loading the sizes
        setLanguage();

        //generating ingredients
        loadIngredients();
        addIngredients(); //create ingredients objects and add to array list

        adapter = new IngredientsRVAdapter(ingredientList, maxIngredientsMessage, orderDetails);
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

            //error
            toppingsChoiceError = getResources().getString(R.string.ToppingChoiceErrorEN);
        }else{
            //text views
            textViewEditOrderTitle.setText(R.string.editOrderTitleFR);
            textViewEditOrderSize.setText(R.string.MakePizzaSizeTitleFR);
            textViewEditOrderToppings.setText(R.string.MakePizzaToppingsFR);

            //buttons
            buttonEditOrderSaveChanges.setText(R.string.editOrderSaveFR);
            buttonEditOrderCancel.setText(R.string.buttonCancelFR);

            //error
            toppingsChoiceError = getResources().getString(R.string.ToppingChoiceErrorFR);
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

            //validating toppings
            if (adapter.ingredientsCount == 0){
                Toast.makeText(getApplicationContext(), toppingsChoiceError, Toast.LENGTH_SHORT).show();
            }else{
                //getting ingredients
                List<Ingredient> toppingsOrder = adapter.getIngredients();
                String toppingsString = "";

                for (int j = 0; j < toppingsOrder.size(); j++){
                    if (j != toppingsOrder.size() - 1){
                        toppingsString += toppingsOrder.get(j).getCount() + ",";
                    }else{
                        toppingsString += toppingsOrder.get(j).getCount();
                    }
                }

                //getting size selection
                int sizeSelection;
                if (radioButtonEditOrderSizeSmall.isChecked()){
                    sizeSelection = 0;
                }else if (radioButtonEditOrderSizeMedium.isChecked()){
                    sizeSelection = 1;
                }else{
                    sizeSelection = 2;
                }

                //updating order details object
                orderDetails.parseToppings(toppingsString);
                orderDetails.setSize(sizeSelection);

                //updating in DB
                db.open();
                if (db.updateOrder(orderDetails.getOrderId(), orderDetails.getRawCustDetails(), toppingsString, sizeSelection, orderDetails.getProgress())){
                    Toast.makeText(getApplicationContext(), "Saved changes to Order #" + (orderDetails.getOrderId()), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Could not save changes to Order #" + (orderDetails.getOrderId()), Toast.LENGTH_SHORT).show();
                }
                db.close();

                //starting new activity
                i.putExtra("language", language);
                i.putExtra("orderDetails", orderDetails);
                startActivity(i);
            }
        }
    };

    private View.OnClickListener buttonEditOrderCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Edit_order.this, Order_Details.class);
            i.putExtra("language", language);
            i.putExtra("orderDetails", orderDetails);
            startActivity(i);
        }
    };
}