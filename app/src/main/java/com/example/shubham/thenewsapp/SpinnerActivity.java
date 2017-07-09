package com.example.shubham.thenewsapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpinnerActivity extends AppCompatActivity {

    Context context=this;
   // Constant.databaseController=new DataBaseController(getApplicationContext());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        Constant.databaseController=new DatabaseController(/* new NewsActivity().*/getApplicationContext());
        GetNews getNews=new GetNews();
        //// TODO: buildUrl = All the urls of the APIs
        getNews.execute("https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey=cd5d4db1ea284edbb026179b8c7f7677");
    }


    protected class GetNews extends AsyncTask<String,Integer,Boolean> {
        @Override
        protected void onPostExecute(Boolean b) {
            //super.onPostExecute(s);
           // textView.setText(s);

            if(b)
                Toast.makeText(context,"News Updated",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"Failed to Updated News",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(context,NewsActivity.class);
            startActivity(intent);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(String...strings) {

            HttpURLConnection httpURLConnection=null;
            BufferedReader bufferedReader=null;
            try {
                URL url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream=httpURLConnection.getInputStream();

                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

                String line;
                StringBuffer stringBuffer=new StringBuffer();
                while((line=bufferedReader.readLine())!=null){

                    stringBuffer.append(line);
                }

                String finalJSON=stringBuffer.toString();
                JSONObject jsonObject=new JSONObject(finalJSON);
                JSONArray jsonArray=jsonObject.getJSONArray("articles");
               // StringBuffer finalNews=new StringBuffer();

                for(int i=0;i<jsonArray.length();i++){



                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    //TODO: Update the database

                    ContentValues currentNews=new ContentValues();
                    currentNews.put(Constant.TITLE,""+jsonObject1.get("title"));
                    currentNews.put(Constant.DESCRIPTION,""+jsonObject1.get("description"));
                    currentNews.put(Constant.PUBLISHED_AT,""+jsonObject1.get("publishedAt"));

                    Constant.databaseController.putNews(currentNews);
                   /* finalNews.append("\n\nTITLE: ").append(jsonObject1.get("title"))
                            .append("\nDESCRIPTION: ").append(jsonObject1.get("description"))
                            .append("\nPUBLISHED AT :").append(jsonObject1.get("publishedAt"));*/
                }

                return true;



            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if(httpURLConnection!=null)
                    httpURLConnection.disconnect();
                if(bufferedReader!=null)
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
            return null;

        }
    }

}
