package com.demo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.demo.been.DemoBeen;

import java.util.ArrayList;



/**
 * Created by Pooja Dubey on 2/2/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper
{

    private SQLiteDatabase myDataBase;
    private Context myContext;

    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "demo";
    private static final String TABLE_DEMO = "demo_table";


    private static final String DEMO_ID = "demo_id";
    private static final String DEMO_TITILE = "demo_title";
    private static final String DEMO_URI = "demo_uri";



    private static final String CREATE_TABLE_DEMO = "CREATE TABLE "
            + TABLE_DEMO + "(" + DEMO_ID + " TEXT," + DEMO_TITILE
            + " TEXT," + DEMO_URI + " TEXT " + ")";


    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(CREATE_TABLE_DEMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_DEMO);
        onCreate(db);
    }


    public void createDemo(DemoBeen demoBeen) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DEMO_ID, demoBeen.get_id());
        values.put(DEMO_TITILE, demoBeen.get_title());
        values.put(DEMO_URI, demoBeen.get_img_uri());

        // insert row
        long demo = db.insert(TABLE_DEMO, null, values);
        Log.e("insert", "====" + demo);

        db.close();
    }


    public void updateDemo(DemoBeen demoBeen) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DEMO_TITILE, demoBeen.get_title());
        values.put(DEMO_URI, demoBeen.get_img_uri());

        // insert row
        long demo = db.update(TABLE_DEMO, values, DEMO_ID + " = ?", new String[] {demoBeen.get_id()});
        Log.e("update", "====" + demo);
        db.close();
    }


    public ArrayList<DemoBeen> getAllDemo() {
        ArrayList<DemoBeen> demolist = new ArrayList<DemoBeen>();
        String selectQuery = "SELECT  * FROM " + TABLE_DEMO;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DemoBeen sb = new DemoBeen();
                sb.set_id(c.getString(c.getColumnIndex(DEMO_ID)));
                sb.set_title(c.getString(c.getColumnIndex(DEMO_TITILE)));
                sb.set_img_uri(c.getString(c.getColumnIndex(DEMO_URI)));


                // adding to todo list
                demolist.add(sb);
            } while (c.moveToNext());
        }

        return demolist;
    }

    public boolean isALreadHasStockDetails(String demo_id)
    {
        boolean isAdded=false;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_DEMO +" WHERE "+DEMO_ID+" = ? ";
        Cursor cursor=sqLiteDatabase.rawQuery(query, new String[]{demo_id});
        if(cursor.getCount()>0)
        {
            isAdded=true;
            Log.e("==========", "ALready Added");
        }
        else
        {
            isAdded=false;
            Log.e("=========", "ALready Not Added");
        }
        sqLiteDatabase.close();
        return isAdded;
    }

}
