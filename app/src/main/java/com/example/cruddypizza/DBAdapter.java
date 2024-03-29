package com.example.cruddypizza;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.Log;
public class DBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_CUSTOMER = "customer";
    public static final String KEY_TOPPINGS = "toppings";
    public static final String KEY_SIZE = "size";
    public static final String KEY_PROGRESS = "progress";
    public static final String KEY_DATETIME = "datetime";
    public static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "CruddyPizzaDB";
    private static final String DATABASE_TABLE = "customerOrders";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE =
            "create table customerOrders(_id integer primary key autoincrement,"
                    + "customer text not null,toppings text not null, size integer not null, progress integer not null, datetime text not null);";
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db)
        {
            try{
                db.execSQL(DATABASE_CREATE);
            } catch (SQLiteException e){
                Log.w(TAG, "Database Error: " + e.toString());
            } catch(Exception e){
                Log.w(TAG, "Error: " + e.toString());
            }

        }//end method onCreate

        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
        {
            Log.w(TAG,"Upgrade database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS customerOrders");
            onCreate(db);
        }//end method onUpgrade
    }

    //open the database
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //close the database
    public void close()
    {
        DBHelper.close();
    }

    //insert a contact into the database
    public long insertOrder(String customer,String toppings, Integer size, Integer progress, String datetime)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CUSTOMER, customer);
        initialValues.put(KEY_TOPPINGS, toppings);
        initialValues.put(KEY_SIZE, size);
        initialValues.put(KEY_PROGRESS, progress);
        initialValues.put(KEY_DATETIME, datetime);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //delete a particular contact
    public boolean deleteOrder(long rowId)
    {
        return db.delete(DATABASE_TABLE,KEY_ROWID + "=" + rowId,null) >0;
    }

    //retrieve all the contacts
    public Cursor getAllOrders()
    {
        return db.query(DATABASE_TABLE,new String[]{KEY_ROWID,KEY_CUSTOMER,
                KEY_TOPPINGS, KEY_SIZE, KEY_PROGRESS, KEY_DATETIME},null,null,null,null,KEY_PROGRESS + " ASC, " + KEY_ROWID + " DESC;");
    }

    //retrieve a single contact
    public Cursor getOrder(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                KEY_CUSTOMER, KEY_TOPPINGS, KEY_SIZE,KEY_PROGRESS, KEY_DATETIME},KEY_ROWID + "=" + rowId,null,null,null,null,null);
        if(mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //updates a contact
    public boolean updateOrder(long rowId, String customer,String toppings, Integer size, Integer progress)
    {
        ContentValues cval = new ContentValues();
        cval.put(KEY_CUSTOMER, customer);
        cval.put(KEY_TOPPINGS, toppings);
        cval.put(KEY_SIZE, size);
        cval.put(KEY_PROGRESS, progress);
        return db.update(DATABASE_TABLE, cval, KEY_ROWID + "=" + rowId,null) >0;
    }
}//end class DBAdapter
