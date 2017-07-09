package com.example.shubham.thenewsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {


    TextView textView;
    ImageView imageView;

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
        imageView=(ImageView)findViewById(R.id.imageView);


        Bundle bundle=Constant.databaseController.getNews();
        String text=bundle.getString("text");
        textView.setText(text);
        byte image[]=bundle.getByteArray("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        imageView.setImageBitmap(bmp);

    }
}
