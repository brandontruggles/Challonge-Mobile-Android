package com.example.brandon.challongemobile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class CreateTournament extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);
    }


    public void buttPress(View view)
    {
        new Thread(new Runnable() {
            public void run() {

                try {
                    URL url = new URL("https://challonge.com/api/tournaments.json");

                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                    connection.setRequestProperty("Accept-Encoding", "");
                    connection.setRequestProperty("Authorization", "Basic " + new String(Base64.encode("bubblerugs:AJmK8DFMF0EpwVRzTlORtuwyJOcGzViDXrQKG63G".getBytes(), Base64.NO_WRAP)));

                    connection.setRequestMethod("POST");

                    OutputStream os = connection.getOutputStream();

                    OutputStreamWriter writer = new OutputStreamWriter(os);

                    EditText nameField = (EditText) findViewById(R.id.editText3);
                    String name = nameField.getText().toString();

                    EditText urlField = (EditText) findViewById(R.id.editText4);
                    String urlText = urlField.getText().toString();

                    String type = "";
                    RadioButton radio = (RadioButton) findViewById(R.id.radioButton);
                    RadioButton radio2 = (RadioButton) findViewById(R.id.radioButton2);
                    RadioButton radio3 = (RadioButton) findViewById(R.id.radioButton3);
                    RadioButton radio4 = (RadioButton) findViewById(R.id.radioButton4);

                    if (radio.isChecked())
                        type = radio.getText().toString().toLowerCase();
                    if (radio2.isChecked())
                        type = radio2.getText().toString().toLowerCase();
                    if (radio3.isChecked())
                        type = radio3.getText().toString().toLowerCase();
                    if (radio4.isChecked())
                        type = radio4.getText().toString().toLowerCase();

                    System.out.println(type);

                    writer.write("tournament[name]=" + name + "&tournament[tournament_type]=" + type + "&tournament[url]=" + urlText);

                    writer.flush();
                    writer.close();
                    os.close();

                    if(connection.getResponseCode() == 200)
                    {
                        CreateTournament.this.runOnUiThread(new Runnable()
                            {
                                public void run()
                                {
                                    goToHome();
                                }
                            });


                    }
                    System.out.println(connection.getResponseCode());
                    System.out.println(connection.getResponseMessage());

                   // BufferedReader b = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                   // String s = "";
                   // while ((s = b.readLine()) != null)
                     //   System.out.println(s);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_create_tournament, menu);
        return true;
    }

    public void goToHome()
    {
        Intent intent = new Intent(this,ChallongeHome.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
