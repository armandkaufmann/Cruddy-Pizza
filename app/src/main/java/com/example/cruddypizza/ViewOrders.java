package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewOrders extends AppCompatActivity {
    //textViews
    TextView textViewViewOrdersTitle;

    //recycler view
    RecyclerView recylcerViewOrders;
    OrdersRVAdapter adapter;

    //buttons
    Button buttonViewOrdersBack;

    //language
    Language language;
    String orderString;

    //ingredients, sizes, customer names
    List<String> customerNames = new ArrayList<>(); //add fake data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        //textviews
        textViewViewOrdersTitle = findViewById(R.id.textViewViewOrdersTitle);

        //recycler view
        recylcerViewOrders = findViewById(R.id.recylcerViewOrders);

        //buttons
        buttonViewOrdersBack = findViewById(R.id.buttonViewOrdersBack);
        buttonViewOrdersBack.setOnClickListener(buttonViewOrdersBackListener);

        //setting language
        language = (Language) getIntent().getSerializableExtra("language");
        setLanguage();

        //adding temp data
        addTempData();


        adapter = new OrdersRVAdapter(customerNames, language, orderString);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recylcerViewOrders.setLayoutManager(manager);
        recylcerViewOrders.setAdapter(adapter);
    }

    //methods ======================================================================================
    private void setLanguage(){
        if (language == Language.ENGLISH){
            textViewViewOrdersTitle.setText(R.string.viewOrdersTitleEN);
            buttonViewOrdersBack.setText(R.string.viewOrdersBackEN);
            orderString = getResources().getString(R.string.orderEN);
        }else{
            textViewViewOrdersTitle.setText(R.string.viewOrdersTitleFR);
            buttonViewOrdersBack.setText(R.string.viewOrdersBackFR);
            orderString = getResources().getString(R.string.orderFR);
        }
    }

    private void addTempData(){
        customerNames.add("Armand");
        customerNames.add("Bob");
        customerNames.add("Joe");
        customerNames.add("Michael");
    }

    //Listeners ====================================================================================
    private View.OnClickListener buttonViewOrdersBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ViewOrders.this, MainActivity.class);
            startActivity(i);
        }
    };
}