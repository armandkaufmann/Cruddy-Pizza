package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Order_Details extends AppCompatActivity {
    //textviews
    TextView textViewOrderDetailsTitle;
    TextView textViewOrderDateTime;
    TextView textViewOrderDetailsSize;
    TextView textViewOrderDetailsSizeSelection;
    TextView textViewOrderDetailsToppings;
    TextView textViewOrderDetailsIngredientsInfo;
    TextView textViewOrderDetailsCustInfo;
    TextView textViewOrderDetailsCustName;
    TextView textViewOrderDetailsCustNameInfo;
    TextView textViewOrderDetailsCustAddress;
    TextView textViewOrderDetailsCustAddressInfo;
    TextView textViewOrderDetailsCustPhone;
    TextView textViewOrderDetailsCustPhoneInfo;

    //buttons
    Button buttonOrderDetailsComplete;
    Button buttonOrderDetailsBack;
    Button buttonOrderDetailsEdit;
    Button buttonOrderDetailsDelete;

    //language
    Language language;

    //order details
    Order orderDetails; //to hold the current number of the order in the database

    //database
    DBAdapter db = new DBAdapter(this);

    //string arrays ingredients, sizes
    String[] ingredientsString;
    String[] sizesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        //text views
        textViewOrderDetailsTitle = findViewById(R.id.textViewOrderDetailsTitle);
        textViewOrderDateTime = findViewById(R.id.textViewOrderDateTime);

        textViewOrderDetailsSize = findViewById(R.id.textViewOrderDetailsSize);
        textViewOrderDetailsSizeSelection = findViewById(R.id.textViewOrderDetailsSizeSelection);

        textViewOrderDetailsToppings = findViewById(R.id.textViewOrderDetailsToppings);
        textViewOrderDetailsIngredientsInfo = findViewById(R.id.textViewOrderDetailsIngredientsInfo);

        textViewOrderDetailsCustInfo = findViewById(R.id.textViewOrderDetailsCustInfo);
        textViewOrderDetailsCustName = findViewById(R.id.textViewOrderDetailsCustName);
        textViewOrderDetailsCustNameInfo = findViewById(R.id.textViewOrderDetailsCustNameInfo);

        textViewOrderDetailsCustAddress = findViewById(R.id.textViewOrderDetailsCustAddress);
        textViewOrderDetailsCustAddressInfo = findViewById(R.id.textViewOrderDetailsCustAddressInfo);

        textViewOrderDetailsCustPhone = findViewById(R.id.textViewOrderDetailsCustPhone);
        textViewOrderDetailsCustPhoneInfo = findViewById(R.id.textViewOrderDetailsCustPhoneInfo);

        //Buttons
        buttonOrderDetailsComplete = findViewById(R.id.buttonOrderDetailsComplete);
        buttonOrderDetailsComplete.setOnClickListener(buttonOrderDetailsCompleteListener);

        buttonOrderDetailsBack = findViewById(R.id.buttonOrderDetailsBack);
        buttonOrderDetailsBack.setOnClickListener(buttonOrderDetailsBackListener);

        buttonOrderDetailsEdit = findViewById(R.id.buttonOrderDetailsEdit);
        buttonOrderDetailsEdit.setOnClickListener(buttonOrderDetailsEditListener);

        buttonOrderDetailsDelete = findViewById(R.id.buttonOrderDetailsDelete);
        buttonOrderDetailsDelete.setOnClickListener(buttonOrderDetailsDeleteListener);

        //order details
        orderDetails = (Order) getIntent().getSerializableExtra("orderDetails");

        //setting date
        textViewOrderDateTime.setText(orderDetails.getDate());

        //language
        language = (Language) getIntent().getSerializableExtra("language");
        setLanguage();

        //disabling complete button if already complete
        if (orderDetails.getProgress() == 1){
            buttonOrderDetailsComplete.setEnabled(false);
            buttonOrderDetailsEdit.setEnabled(false);
        }

        //setting the text based on the customer's order
        textViewOrderDetailsSizeSelection.setText(sizesString[orderDetails.getSize()]); //size selection

        String orderToppings = "";
        ArrayList<Integer> toppingSelection = orderDetails.getToppings();
        for (int i = 0; i < toppingSelection.size(); i++){ //iterating through the toppings selection
            if (toppingSelection.get(i) != 0){
                orderToppings += ingredientsString[i] + " x " + toppingSelection.get(i) + "\n";
            }
        }

        textViewOrderDetailsIngredientsInfo.setText(orderToppings); //setting the toppings selection
        textViewOrderDetailsCustNameInfo.setText(orderDetails.getCustomerName());
        textViewOrderDetailsCustAddressInfo.setText(orderDetails.getCustomerAddress());
        textViewOrderDetailsCustPhoneInfo.setText(orderDetails.getCustomerPhone());
    }

    //METHODS ======================================================================================
    public void setLanguage(){
        if (language == Language.ENGLISH){
            //text views
            textViewOrderDetailsTitle.setText(getResources().getString(R.string.orderEN) + orderDetails.getOrderId());
            textViewOrderDetailsSize.setText(R.string.orderPlacedSizeEN);
            textViewOrderDetailsToppings.setText(R.string.orderPlacedToppingsEN);
            textViewOrderDetailsCustInfo.setText(R.string.orderPlacedCustomerInfoEN);
            textViewOrderDetailsCustName.setText(R.string.CustNameEN);
            textViewOrderDetailsCustAddress.setText(R.string.CustAddressEN);
            textViewOrderDetailsCustPhone.setText(R.string.CustPhoneEN);

            //buttons
            buttonOrderDetailsComplete.setText(R.string.orderDetailsCompleteEN);
            buttonOrderDetailsBack.setText(R.string.viewOrdersBackEN);
            buttonOrderDetailsEdit.setText(R.string.orderDetailsEditEN);
            buttonOrderDetailsDelete.setText(R.string.orderDetailsDeleteEN);

            //ingredients array
            ingredientsString = getResources().getStringArray(R.array.ingredients_EN); //getting ingredients from string array in english

            //sizes array
            sizesString = getResources().getStringArray(R.array.sizes_EN);
        }else{
            textViewOrderDetailsTitle.setText(getResources().getString(R.string.orderFR) + orderDetails.getOrderId());
            textViewOrderDetailsSize.setText(R.string.orderPlacedSizeFR);
            textViewOrderDetailsToppings.setText(R.string.orderPlacedToppingsFR);
            textViewOrderDetailsCustInfo.setText(R.string.orderPlacedCustomerInfoFR);
            textViewOrderDetailsCustName.setText(R.string.CustNameFR);
            textViewOrderDetailsCustAddress.setText(R.string.CustAddressFR);
            textViewOrderDetailsCustPhone.setText(R.string.CustPhoneFR);

            //buttons
            buttonOrderDetailsComplete.setText(R.string.orderDetailsCompleteFR);
            buttonOrderDetailsBack.setText(R.string.viewOrdersBackFR);
            buttonOrderDetailsEdit.setText(R.string.orderDetailsEditFR);
            buttonOrderDetailsDelete.setText(R.string.orderDetailsDeleteFR);

            //ingredients array
            ingredientsString = getResources().getStringArray(R.array.ingredients_FR); //getting ingredients from string array in french

            //sizes array
            sizesString = getResources().getStringArray(R.array.sizes_FR);
        }
    }

    //LISTENERS ====================================================================================
    private View.OnClickListener buttonOrderDetailsCompleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Order_Details.this, ViewOrders.class);

            //long rowId, String customer,String toppings, Integer size, Integer progress

            db.open();
            if (db.updateOrder(orderDetails.getOrderId(), orderDetails.getRawCustDetails(), orderDetails.getRawToppings(), orderDetails.getSize(), 1)){
                Toast.makeText(getApplicationContext(), "Order #" + (orderDetails.getOrderId()) + " completed.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Could not complete Order #" + orderDetails.getOrderId(), Toast.LENGTH_SHORT).show();
            }


            i.putExtra("language", language);
            startActivity(i);
        }
    };

    private View.OnClickListener buttonOrderDetailsBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Order_Details.this, ViewOrders.class);
            i.putExtra("language", language);
            startActivity(i);
        }
    };

    private View.OnClickListener buttonOrderDetailsEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Order_Details.this, Edit_order.class);
            i.putExtra("language", language);
            i.putExtra("orderDetails", orderDetails);
            startActivity(i);
        }
    };

    private View.OnClickListener buttonOrderDetailsDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Order_Details.this, ViewOrders.class);

            //deleting from DB
            db.open();
            if (db.deleteOrder(orderDetails.getOrderId())){
                Toast.makeText(getApplicationContext(), "Order #" + (orderDetails.getOrderId()) + " deleted.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Failed to delete Order #" + orderDetails.getOrderId(), Toast.LENGTH_SHORT).show();
            }
            db.close();

            i.putExtra("language", language);
            startActivity(i);
        }
    };
}