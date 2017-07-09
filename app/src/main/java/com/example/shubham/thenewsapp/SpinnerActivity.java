package com.example.shubham.thenewsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
    }
    protected class GetNews extends AsyncTask<String,Integer,StringBuffer> {
        @Override
        protected void onPostExecute(StringBuffer s) {
            //super.onPostExecute(s);
            textView.setText(s);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected StringBuffer doInBackground(String...strings) {

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
                StringBuffer finalNews=new StringBuffer();

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    finalNews.append("\n\nTITLE: ").append(jsonObject1.get("title"))
                            .append("\nDESCRIPTION: ").append(jsonObject1.get("description"))
                            .append("\nPUBLISHED AT :").append(jsonObject1.get("publishedAt"));
                }

                return finalNews;



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
