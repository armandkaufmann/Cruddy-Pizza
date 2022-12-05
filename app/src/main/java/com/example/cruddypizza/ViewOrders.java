package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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

    //ingredients, sizes, customer names
    List<String> customerNames = new ArrayList<>(); //add fake data
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
            String destPath = Environment.getExternalStorageDirectory().getPath() + getPackageName() + "/database/MyDB";
            File f = new File(destPath);
            if (!f.exists()){
                CopyDB(getBaseContext().getAssets().open("mydb"),
                        new FileOutputStream(destPath));
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        db = new DBAdapter(this);

        //retrieving all rows
        db.open();
        Cursor c = db.getAllOrders();
        if(c.moveToFirst()){
            do{
                //orderid, String customer,String toppings, Integer size, Integer progress
                orders.add(new Order(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), numToppings));
            }while(c.moveToNext());
        }
        db.close();


        //maybe put this in c.movetofirst() ??
        adapter = new OrdersRVAdapter(customerNames, language, orderString);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recylcerViewOrders.setLayoutManager(manager);
        recylcerViewOrders.setAdapter(adapter);
    }

    //methods ======================================================================================
    public void CopyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException{
        //copy 1k bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer,0,length);
        }
        inputStream.close();
        outputStream.close();
    }//end method CopyDB

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