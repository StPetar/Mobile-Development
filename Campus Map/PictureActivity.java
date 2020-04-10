package com.example.madcampusmap;


import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class PictureActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_activty);


        String room = getIntent().getStringExtra("room");
        getSupportActionBar().setTitle(room);

        ImageView imageLocation = findViewById(R.id.imageView);
        int imageId = getResources().getIdentifier(room,
                "drawable", getPackageName());
        imageLocation.setImageResource(imageId);

    }
}
