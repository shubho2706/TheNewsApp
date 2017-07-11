package com.example.shubham.thenewsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {


    ImageView imageView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        //textView=(TextView)findViewById(R.id.text);
        //imageView=(ImageView)findViewById(R.id.imageView);


        ArrayList<Bundle> newsList=Constant.databaseController.getNews();

//        LinearLayout.LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        for (Bundle bundle: newsList) {

            String title = bundle.getString("title");
            String publishedAt = bundle.getString("publishedAt");
            String description = bundle.getString("description");
            TextView textView=new TextView(this);
            textView.setText("\n\nTITLE: " + title + "\nPUBLISHED AT : " + publishedAt + "\nDESCRIPTION: " + description);


            byte image[]=bundle.getByteArray("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            ImageView imageView=new ImageView(this);
            imageView.setImageBitmap(bmp);
            imageView.setPadding(20,0,20,0);
            //imageView.setImageResource(R.drawable.image);

            /*imageView.setMaxWidth(2);
            imageView.setPadding(10,10,10,10);*/

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          //  lp.setMargins(20,0,20,0);
            imageView.setLayoutParams(lp);


            linearLayout.addView(textView);
            linearLayout.addView(imageView);

//            textView.append("\n\nTITLE: " + title + "\nPUBLISHED AT : " + publishedAt + "\nDESCRIPTION: " + description);

            // imageView.setImageBitmap(bmp);
        }

    }
}
