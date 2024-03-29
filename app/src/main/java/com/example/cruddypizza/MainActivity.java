package com.example.cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    //shared preferences object/language selection
    SharedPreferences prefs;
    static final String LANGUAGE_KEY = "language";
    Language language;

    //views
    Button buttonMakeOrder;
    Button buttonViewOrder;
    Button buttonEnglish;
    Button buttonFrench;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting up views
        buttonMakeOrder = findViewById(R.id.buttonMakeOrder);
        buttonMakeOrder.setOnClickListener(buttonMakeOrderListener); //listener

        buttonViewOrder = findViewById(R.id.buttonViewOrder);
        buttonViewOrder.setOnClickListener(buttonViewOrderListener); //listener

        buttonEnglish = findViewById(R.id.buttonEnglish);
        buttonEnglish.setOnClickListener(buttonEnglishListener); //listener

        buttonFrench = findViewById(R.id.buttonFrench);
        buttonFrench.setOnClickListener(buttonFrenchListener); //listener


        getLanguage(); //getting language with shared preferences
        setLanguage(); //setting the language based on the shared preference

        //DB, checking if sqlite exists
        try{
            String destPath = Environment.getExternalStorageDirectory().getPath() + getPackageName() + "/database/MyDB";
            File f = new File(destPath);
            if (!f.exists()){
                CopyDB(getBaseContext().getAssets().open("mydb"),
                        new FileOutputStream(destPath));
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Log.w("MAINACTIVITY", "File Not Found Exception: " + e.toString());
        }catch (IOException e){
            e.printStackTrace();
            Log.w("MAINACTIVITY", "IO Exception: " + e.toString());
        }
    }//end onCreate method

    //METHODS ======================================================================================
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

    public void getLanguage(){
        prefs = getPreferences(MODE_PRIVATE);

        if (prefs.getInt(LANGUAGE_KEY, 0) == 0){ //english
            language = Language.ENGLISH;
            buttonEnglish.setSelected(true); //setting button to true
        }else{//french
            language = Language.FRENCH;
            buttonFrench.setSelected(true);
        }
    }//end getLanguage method

    private void setLanguage() {
        if (language == Language.ENGLISH){
            buttonEnglish.setSelected(true);
            buttonFrench.setSelected(false);

            buttonMakeOrder.setText(R.string.MakeOrderEN);
            buttonViewOrder.setText(R.string.ViewOrderEN);
            buttonEnglish.setText(R.string.ButtonEnglishEN);
            buttonFrench.setText(R.string.ButtonFrenchEN);
        }else{
            buttonFrench.setSelected(true);
            buttonEnglish.setSelected(false);

            buttonMakeOrder.setText(R.string.MakeOrderFR);
            buttonViewOrder.setText(R.string.ViewOrderFR);
            buttonEnglish.setText(R.string.ButtonEnglishFR);
            buttonFrench.setText(R.string.ButtonFrenchFR);
        }

    }//end setLanguage method

    private void changeLanguage(View view){
        prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (view.getId() == R.id.buttonEnglish){
            //adding to shared prefs object
            editor.putInt(LANGUAGE_KEY, 0); //0 for english
            editor.commit();

            language = Language.ENGLISH;
            setLanguage();
        }else{
            //adding to shared prefs object
            editor.putInt(LANGUAGE_KEY, 1); //1 for french
            editor.commit();

            language = Language.FRENCH;
            setLanguage();
        }
    }

    //ON-CLICK LISTENERS ===========================================================================
    private View.OnClickListener buttonMakeOrderListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, CustomerEntry.class);
            i.putExtra("language", language);
            startActivity(i);
        }
    };

    private View.OnClickListener buttonViewOrderListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, ViewOrders.class);
            i.putExtra("language", language);
            startActivity(i);
        }
    };

    private View.OnClickListener buttonEnglishListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeLanguage(view);
        }
    };

    private View.OnClickListener buttonFrenchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeLanguage(view);
        }
    };
}