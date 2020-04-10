package com.unitest.moad.thecurrencyconverter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import android.os.Handler;
import android.os.HandlerThread;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   static ExchangeRateDatabase database;
   static List<String> currencies;
   Spinner spinner;
   Spinner spinner2;
   EditText enteredValue;
   TextView output;

   public static final String CHANNEL_1_ID = "Currency Notification";
   private NotificationManagerCompat notificationManager;
   String fromCurrency;
   String toCurrency;
   double userInput;
   double converted;
   static final DecimalFormat decimalFormat = new DecimalFormat ("#,##0.0000");

   ShareActionProvider shareActionProvider;

   HandlerThread handlerThread = new HandlerThread("URLConnection");
   Handler handler;
   UpdateCurrenciesRunnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannel();

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Currency Converter");


        // UI fields
        enteredValue = findViewById(R.id.inputValue);

        output = findViewById(R.id.outputValue);
        double outputValue = 0.00;
        output.setText(Double.toString(outputValue));

        // Database
        database = new ExchangeRateDatabase();
        currencies = Arrays.asList(database.getCurrencies());

        // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies);

        CustomAdapter adapter = new CustomAdapter(currencies);
        

        // Populating the spinners
        spinner = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);

        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        // Handler for updating currencies
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        runnable = new UpdateCurrenciesRunnable(this);



    }

    // Menu method
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        setShareText(null);

        return true;
    }

    // Share button method
    private void setShareText(String text){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        if(text != null){
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        }

        shareActionProvider.setShareIntent(shareIntent);
    }

    // Menu items method
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()) {
            // Currency List
            case R.id.item:

                Intent intent = new Intent(this, CurrencyListActivity.class);
                this.startActivity(intent);
                return true;

            // Update currencies
            case R.id.item2:
                handler.post(runnable);

                Toast toast = Toast.makeText(this, "Currency rates have been refreshed!", Toast.LENGTH_SHORT);
                toast.show();

                Notification notification  = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                        .setSmallIcon(R.mipmap.icon_launcher)
                        .setContentTitle("Currency Converter")
                        .setContentText("Currency rates have been refreshed!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setAutoCancel(true)
                        .build();


                notificationManager.notify(1, notification);
                return true;

            default:
                return true;
        }
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Currency Converter",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    // Convert button
  public void onButtonClick(View v){

      fromCurrency = spinner.getSelectedItem().toString();
      toCurrency = spinner2.getSelectedItem().toString();

      userInput = Double.parseDouble( ((EditText) findViewById(R.id.inputValue)).getText().toString() );

      converted = database.convert(userInput, fromCurrency, toCurrency);

      output.setText(decimalFormat.format(converted));

      // Update the info for the share button
      setShareText(decimalFormat.format(converted));

  }

    // onPause stores the values we want to keep and onResume sets them again
    public void onPause() {

        super.onPause();

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("SourceCurrency", spinner.getSelectedItemPosition());
        editor.putInt("TargetCurrency", spinner2.getSelectedItemPosition());
        editor.putString("EnteredValue", ((EditText) findViewById(R.id.inputValue)).getText().toString());
        editor.apply();

    }

    public void onResume() {

        super.onResume();

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        spinner.setSelection(sharedPreferences.getInt("SourceCurrency", 0));
        spinner2.setSelection(sharedPreferences.getInt("TargetCurrency", 0));
        enteredValue.setText(sharedPreferences.getString("EnteredValue", "0.00"), TextView.BufferType.EDITABLE);
    }

}