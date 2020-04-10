package com.example.madcampusmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    static RoomsDB database;
    static List<String> rooms;
    Spinner spinner;
    Spinner spinner2;

    private Button cPritt;
    private Button cEber;
    private Button cAlbert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Select a Campus");

        // Database
        database = new RoomsDB();
        rooms = Arrays.asList(database.getRooms());
        RoomAdapter adapter = new RoomAdapter(rooms);

        cPritt = findViewById(R.id.button);
        cPritt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), CampusPrittActivity.class);
                startActivity(intent);

            }
        });

        cEber = findViewById(R.id.button2);
        cEber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), CampusPrittActivity.class);
                startActivity(intent);

            }
        });

        cAlbert = findViewById(R.id.button3);
        cAlbert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), CampusPrittActivity.class);
                startActivity(intent);

            }
        });
    }
}
