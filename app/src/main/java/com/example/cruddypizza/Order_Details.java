package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Order_Details extends AppCompatActivity {
    //textviews
    TextView textViewOrderDetailsTitle;
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
    int orderNum; //to hold the current number of the order in the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        //text views
        textViewOrderDetailsTitle = findViewById(R.id.textViewOrderDetailsTitle);

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

        //language
        language = (Language) getIntent().getSerializableExtra("language");
        setLanguage();

        //order details
        orderNum = (Integer) getIntent().getIntExtra("orderNum", 1); //to hold the current number of the order in the database

    }

    //METHODS ======================================================================================
    public void setLanguage(){
        if (language == Language.ENGLISH){
            //text views
            textViewOrderDetailsTitle.setText(R.string.orderEN);
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
        }else{
            textViewOrderDetailsTitle.setText(R.string.orderFR);
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
        }
    }

    //LISTENERS ====================================================================================
    private View.OnClickListener buttonOrderDetailsCompleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Order_Details.this, ViewOrders.class);
            Toast.makeText(getApplicationContext(), "Order #" + (orderNum + 1) + " completed.", Toast.LENGTH_SHORT).show();
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
            i.putExtra("orderNum", orderNum);
            startActivity(i);
        }
    };

    private View.OnClickListener buttonOrderDetailsDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Order_Details.this, ViewOrders.class);
            Toast.makeText(getApplicationContext(), "Order #" + (orderNum + 1) + " deleted.", Toast.LENGTH_SHORT).show();
            i.putExtra("language", language);
            startActivity(i);
        }
    };
}