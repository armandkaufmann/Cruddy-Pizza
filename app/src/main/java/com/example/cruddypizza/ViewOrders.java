package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import android.database.*;

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

    //orders for recycler view
    ArrayList<Order> orders = new ArrayList<>(); //array list of orders
    Integer numToppings;

    //database
    DBAdapter db;


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

        //getting the number of toppings
        numToppings = getResources().getStringArray(R.array.ingredients_EN).length;

        //getting data from DB
        try{
            db = new DBAdapter(this); //initializing db
            //retrieving all rows
            db.open();
            Cursor c = db.getAllOrders();
            if(c.moveToFirst()){
                do{
                    //orderid, String customer,String toppings, Integer size, Integer progress
                    orders.add(new Order(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
                }while(c.moveToNext());
            }
            db.close();
        }catch (SQLiteException e){
            Log.w("VIEWORDERS", "Database Error: " + e.toString());
        }
        catch (Exception e){
            Log.w("VIEWORDERS", "Error: " + e.toString());
        }

        adapter = new OrdersRVAdapter(orders, language, orderString);
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

    //Listeners ====================================================================================
    private View.OnClickListener buttonViewOrdersBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ViewOrders.this, MainActivity.class);
            startActivity(i);
        }
    };
}