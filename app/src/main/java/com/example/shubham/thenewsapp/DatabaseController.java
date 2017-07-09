package com.example.shubham.thenewsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Shubham on 01-Jul-17.
 */

public class DatabaseController  {

    MyHelper myHelper;

    DatabaseController(Context context){
        myHelper=new MyHelper(context);
    }

























    private static class MyHelper extends SQLiteOpenHelper{

        private static String CREATE_TABLE;
        private static String DROP_TABLE;

        public MyHelper(Context context) {
            super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
            Log.d("DB Constructor","Database Created");
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            for(String tableName :Constant.TABLE_NAME){

                CREATE_TABLE="CREATE TABLE "+tableName +"("
                +Constant.UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Constant.PUBLISHED_AT+" VARCHAR(255), "
                +Constant.TITLE+ " VARCHAR(255), "
                +Constant.IMAGE+" BLOB, "
                +Constant.DESCRIPTION+" TEXT";

                db.execSQL(CREATE_TABLE);
                Log.d("onCreate(): ","Table created");
            }
    }
        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {

           DROP_TABLE = "DROP TABLE IF EXISTS " + Constant.TABLE_NAME[0];
            //TODO : write the upgrade code
        }
    }
}
