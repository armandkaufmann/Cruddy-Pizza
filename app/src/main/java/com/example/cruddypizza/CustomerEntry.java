package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomerEntry extends AppCompatActivity {
    //views
    //edit texts
    EditText editTextTextCustName;
    EditText editTextTextCustAddress;
    EditText editTextTextCustPhone;

    //labels
    TextView textViewCustInfoTitle;
    TextView textViewName;
    TextView textViewAddress;
    TextView textViewPhone;


    Button buttonStartOrder;
    Button buttonCustCancel;

    //language
    Language language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_entry);

        //hooking up views from xml
        //edit text
        editTextTextCustName = findViewById(R.id.editTextTextCustName);
        editTextTextCustAddress = findViewById(R.id.editTextTextCustAddress);
        editTextTextCustPhone = findViewById(R.id.editTextTextCustPhone);

        //labels
        textViewCustInfoTitle = findViewById(R.id.textViewMakePizzaTitle);
        textViewName = findViewById(R.id.textViewName);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewPhone = findViewById(R.id.textViewPhone);

        buttonStartOrder = findViewById(R.id.buttonStartOrder);
        buttonStartOrder.setOnClickListener(buttonStartOrderListener);

        buttonCustCancel = findViewById(R.id.buttonCustCancel);
        buttonCustCancel.setOnClickListener(buttonCustCancelListener);

        //getting data from intent
        language = (Language) getIntent().getSerializableExtra("language");

        //setting language
        setLanguage();

    }

    //METHODS ======================================================================================
    private void setLanguage() {
        if (language == Language.ENGLISH){
            //text views
            textViewCustInfoTitle.setText(R.string.CustInfoTitleEN);
            textViewName.setText(R.string.CustNameEN);
            textViewAddress.setText(R.string.CustAddressEN);
            textViewPhone.setText(R.string.CustPhoneEN);
            //buttons
            buttonStartOrder.setText(R.string.buttonMakePizzaEN);
            buttonCustCancel.setText(R.string.buttonCancelEN);
        }else{
            textViewCustInfoTitle.setText(R.string.CustInfoTitleFR);
            textViewName.setText(R.string.CustNameFR);
            textViewAddress.setText(R.string.CustAddressFR);
            textViewPhone.setText(R.string.CustPhoneFR);
            //buttons
            buttonStartOrder.setText(R.string.buttonMakePizzaFR);
            buttonCustCancel.setText(R.string.buttonCancelFR);
        }

    }//end setLanguage method

    //LISTENERS ====================================================================================
    private View.OnClickListener buttonStartOrderListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent i = new Intent(MainActivity.this, CustomerEntry.class);
//            i.putExtra("language", language);
//            startActivity(i);
        }
    };

    private View.OnClickListener buttonCustCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(CustomerEntry.this, MainActivity.class);
            startActivity(i);
        }
    };


}