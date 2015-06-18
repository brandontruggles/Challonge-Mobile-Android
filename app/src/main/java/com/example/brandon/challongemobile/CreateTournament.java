package com.example.brandon.challongemobile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CreateTournament extends ActionBarActivity
{
    private ProgressBar loadingCircle;
    private TextView title;
    private EditText nameField;
    private EditText urlField;
    private RadioButton radio;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(ConnectionManager.getUsername().toUpperCase());
        setContentView(R.layout.activity_create_tournament);
        loadingCircle = (ProgressBar)findViewById(R.id.progressBar4);
        title = (TextView) findViewById(R.id.textView);
        loadingCircle.setVisibility(View.INVISIBLE);
        nameField = (EditText) findViewById(R.id.editText3);
        urlField = (EditText) findViewById(R.id.editText4);
        radio = (RadioButton) findViewById(R.id.radioButton);
        radio2 = (RadioButton) findViewById(R.id.radioButton2);
        radio3 = (RadioButton) findViewById(R.id.radioButton3);
        radio4 = (RadioButton) findViewById(R.id.radioButton4);
        createButton = (Button) findViewById(R.id.button3);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    public void buttPress(View view)
    {
        showLoading();

        ConnectionManager.connectToURL("https://challonge.com/api/tournaments.json");

        String name = nameField.getText().toString();

        String urlText = urlField.getText().toString();

        String type = "";

        if (radio.isChecked())
            type = radio.getText().toString().toLowerCase();
        if (radio2.isChecked())
            type = radio2.getText().toString().toLowerCase();
        if (radio3.isChecked())
            type = radio3.getText().toString().toLowerCase();
        if (radio4.isChecked())
            type = radio4.getText().toString().toLowerCase();

        final String messageToWrite = "tournament[name]=" + name + "&tournament[tournament_type]=" + type + "&tournament[url]=" + urlText;

        new Thread(new Runnable()
        {
            public void run()
            {
                ConnectionManager.postToServer(messageToWrite);

                final int responseCode = ConnectionManager.getResponseCode();
                final String responseMessage = ConnectionManager.getResponseMessage();

                if(responseCode == 200)
                    goToHome();
                else
                {
                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            hideLoading();
                            if(responseCode == 422)
                                Toast.makeText(getApplicationContext(), "Either the URL or name you entered was already taken, or the URL you entered contained an invalid character.", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), responseCode + ": " + responseMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_tournament, menu);
        return true;
    }

    public void showLoading()
    {
        loadingCircle.setVisibility(View.VISIBLE);
        title.setVisibility(View.INVISIBLE);
        nameField.setVisibility(View.INVISIBLE);
        urlField.setVisibility(View.INVISIBLE);
        radio.setVisibility(View.INVISIBLE);
        radio2.setVisibility(View.INVISIBLE);
        radio3.setVisibility(View.INVISIBLE);
        radio4.setVisibility(View.INVISIBLE);
        createButton.setVisibility(View.INVISIBLE);
    }

    public void hideLoading()
    {
        loadingCircle.setVisibility(View.INVISIBLE);
        title.setVisibility(View.VISIBLE);
        nameField.setVisibility(View.VISIBLE);
        urlField.setVisibility(View.VISIBLE);
        radio.setVisibility(View.VISIBLE);
        radio2.setVisibility(View.VISIBLE);
        radio3.setVisibility(View.VISIBLE);
        radio4.setVisibility(View.VISIBLE);
        createButton.setVisibility(View.VISIBLE);
    }

    public void goToHome()
    {
        finish();
        overridePendingTransition(0,0);
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                Toast.makeText(getApplicationContext(), "Tournament successfully created!", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
