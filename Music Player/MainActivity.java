package com.unitest.moad.madmusicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // listOfSongs contains all the Song objects (unnecessary if you want it efficient)
    // PERMISSION_REQUEST_CODE is used for the user to give permission to read their external storage
    // mediaPlayer is used to play the songs
    // TextView variables are used to display info about the song in the activity
    private static final int PERMISSION_REQUEST_CODE = 1;
    MediaPlayer mediaPlayer;
    TextView artist;
    TextView song;
    TextView album;

    static int cursorCount;
    static Cursor musicCursor;
    boolean permissions = false;

    // pathOfSong is used to store the current song (path) in case the app is destroyed
    String pathOfSong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cursorCount = 0;

        // initialize the display variables
        artist = findViewById(R.id.textView3);
        song = findViewById(R.id.textView4);
        album = findViewById(R.id.textView5);

        // User gave permission?
        if(!checkPermission()){
            requestPermission();
        }

        while(!permissions){
            //Wait for permission to be accepted
            if(checkPermission())
                permissions = true;
        }
        scanElements();

    }

    // Play Random song button
    public void onRandomClick(View v){

        Random random = new Random();

        int randomID = random.nextInt(cursorCount);

        musicCursor = getCursor(randomID);

        playSong(randomID);

    }

    // Play / Pause song button
    public void onPlayPauseClick(View v){
        // No song selected check
        if (mediaPlayer == null) {
            return;
        }

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }

    }


    // On app startup scan all the elements
    // (this is used to determine how many songs there are so the random song can be selected when pressing the random button)
    public void scanElements(){
        ContentResolver mContentResolver = getApplicationContext().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor mCursor = mContentResolver.query(songUri,  null, null, null, null);

        cursorCount = mCursor.getCount();

    }

    // Returns the cursor over the current song
    public Cursor getCursor(int id){

        ContentResolver mContentResolver = getApplicationContext().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor mCursor = mContentResolver.query(songUri,  null, null, null, null);

        mCursor.moveToPosition(id);
        return mCursor;

    }

    // Returns the song (path)
    public String songSelect(int id){

        ContentResolver mContentResolver = getApplicationContext().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor mCursor = mContentResolver.query(songUri,  null, null, null, null);

        mCursor.moveToPosition(id);
        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        pathOfSong = path;
        return path;

    }

    // Play the song
    public void playSong(int id){

        // Get the location of the song on the phone
        String songPath = songSelect(id);

        // Stop the previous song from playing if one is playing
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Re-initialize the media player
        mediaPlayer = new MediaPlayer();

        try {


            mediaPlayer.setDataSource(songPath);

            // Update the UI
            artist.setText(musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            song.setText(musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            album.setText(musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));

            // Prepare the Media Player for playing
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Loop the song(s)
        mediaPlayer.setLooping(true);

        // Start the Media Player
        mediaPlayer.start();

    }

    // Save values
    public void onPause() {

        super.onPause();

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("ArtistKey", artist.getText().toString());
        editor.putString("SongKey", song.getText().toString());
        editor.putString("AlbumKey", album.getText().toString());
        editor.putString("PathOfSong", pathOfSong);
        editor.putInt("SongPosition", mediaPlayer.getCurrentPosition());
        editor.apply();

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }


    }

    // Obtain saved values
    public void onResume() {

        super.onResume();

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        artist.setText(sharedPreferences.getString("ArtistKey", "Artist"));
        song.setText(sharedPreferences.getString("SongKey", "Song"));
        album.setText(sharedPreferences.getString("AlbumKey", "Album"));
        pathOfSong = sharedPreferences.getString("PathOfSong", null);

        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(pathOfSong);
            mediaPlayer.prepare();
        } catch (Exception e){e.printStackTrace();}
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(sharedPreferences.getInt("SongPosition", 0));
    }

    // Permission check
    private boolean checkPermission() {

        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

}