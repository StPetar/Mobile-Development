package com.example.madcampusmap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class CampusPrittActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    String[] rooms;
    RoomsDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_pritt);

        //Toolbar toolbar = findViewById(R.id.toolbar3);
        //toolbar.setTitle("Campus Prittwitzstrasse");

        database = new RoomsDB();

        listView = findViewById(R.id.listView);

        rooms = database.getRooms();

        final RoomAdapter adapter = new RoomAdapter(Arrays.asList(rooms));
        listView.setAdapter(adapter);

        // Show the capital on the map
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), PictureActivity.class);
                intent.putExtra("room", rooms[position]);
                startActivity(intent);

            }
        });

    }
}
