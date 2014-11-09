package com.example.brandon.challongemobile;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

                    writer.write("tournament[name]=test15&tournament[url]=challongemobiletest15");

                    writer.flush();
                    writer.close();
                    os.close();

                    System.out.println(connection.getResponseCode());
                    System.out.println(connection.getResponseMessage());

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_tournament, menu);
        return true;
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
