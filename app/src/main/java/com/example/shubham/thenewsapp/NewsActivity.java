package com.example.shubham.thenewsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {


    TextView textView;

  /*  private static NewsActivity instance;

    public NewsActivity() {
        instance = this;
    }

    public  NewsActivity getInstance() {
        return instance;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        textView=(TextView)findViewById(R.id.text);
        textView.setText(Constant.databaseController.getNews());
    }
}
