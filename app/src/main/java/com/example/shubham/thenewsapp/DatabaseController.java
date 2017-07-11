package com.example.shubham.thenewsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * Created by Shubham on 01-Jul-17.
 */

public class DatabaseController  {

    MyHelper myHelper;

    DatabaseController(Context context){
        myHelper=new MyHelper(context);
    }
    public void putNews(ContentValues currentNews){

        SQLiteDatabase sqLiteDatabase=myHelper.getWritableDatabase();
        //TODO: check whether a specific news is already in the database and if yes of course don't reput in the database
        // TODO: replace the TABLE_NAME. This is done for a single API only.
        sqLiteDatabase.insert(Constant.TABLE_NAME[0],null,currentNews);
    }
    public ArrayList<Bundle> getNews(){

        ArrayList<Bundle> newsList=new ArrayList<>();
        Bundle newsFromTableBundle;
     //   StringBuffer newsFromTable=new StringBuffer();
        SQLiteDatabase sqLiteDatabase=myHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(Constant.TABLE_NAME[0],null,null,null,null,null,null,null);
        while(cursor.moveToNext()){

            String title=cursor.getString(cursor.getColumnIndex(Constant.TITLE));
            String description=cursor.getString(cursor.getColumnIndex(Constant.DESCRIPTION));
            String publishedAt=cursor.getString(cursor.getColumnIndex(Constant.PUBLISHED_AT));
            byte image[]=cursor.getBlob(cursor.getColumnIndex(Constant.IMAGE));


/*            newsFromTable.append("\n\nTITLE: ").append(title)
                    .append("\nPublished At: ").append(publishedAt)
                    .append("\nDescription: ").append(description);*/
//            newsFromTable.putString("title",title);
//            newsFromTable.putString("description",description);
//            newsFromTable.putString("publishedAt",publishedAt);


            newsFromTableBundle=new Bundle();
            newsFromTableBundle.putString("title",title);
            newsFromTableBundle.putString("publishedAt",publishedAt);
            newsFromTableBundle.putString("description",description);
            //newsFromTableBundle.putString("text",newsFromTable.toString());
            newsFromTableBundle.putByteArray("image",image);

            newsList.add(newsFromTableBundle);

        }
        //return newsFromTable.toString();
        return  newsList;


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
                +Constant.DESCRIPTION+" VARCHAR(255));";

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
