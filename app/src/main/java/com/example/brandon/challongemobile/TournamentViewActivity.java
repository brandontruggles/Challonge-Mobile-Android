package com.example.brandon.challongemobile;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.*;

import javax.net.ssl.HttpsURLConnection;


public class TournamentViewActivity extends ActionBarActivity
{

    ListView listView;
    ArrayList<String>  tournList;
    ArrayAdapter<String> arrayadapt;
    String[] tournArray;
    JSONArray tournaments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_view);

        listView = (ListView) findViewById(R.id.listView);
        tournList = new ArrayList<String>();



        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {

                    //final String username = getIntent().getExtras().getString("Username");
                    //final String password = getIntent().getExtras().getString("Password");

                    URL url = new URL("https://api.challonge.com/v1/tournaments.json?state=all");

                    HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

                    connection.setRequestProperty("Accept-Encoding","");
                    connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode("bubblerugs:AJmK8DFMF0EpwVRzTlORtuwyJOcGzViDXrQKG63G".getBytes(), Base64.NO_WRAP)));
                    //connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode(new String(username + ":" +password).getBytes(),Base64.NO_WRAP)));

                    connection.connect();

                    if(connection.getResponseCode() == 200)
                    {
                        BufferedReader b = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String temp = "";
                        String data = "";
                        while((temp = b.readLine())!=null)
                            data+=temp;
                        tournaments = new JSONArray(data);

                        for(int i = 0;i<tournaments.length();i++)
                        {
                            JSONObject tournament = ((JSONObject)tournaments.get(i)).getJSONObject("tournament");
                            System.out.println("cheese");
                            tournList.add(tournament.getString("name"));

                        }

                        tournArray= new String[tournList.size()];


                        for(int j=0; j<tournArray.length; j++)
                        {
                            tournArray[j]=tournList.get(j);
                        }

                        System.out.println(tournList.size());
                        System.out.println(tournArray.length);

                        TournamentViewActivity.this.runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                startList();

                            }
                        });


                    }
                    else
                    {
                        System.out.println(connection.getResponseCode() + ":" + connection.getResponseMessage());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



            }
        }).start();
           /*
        while(tournaments.length() == tournList.size())
        {
            arrayadapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tournArray);

            //System.out.println(tournArray[0]+"\n"+tournArray[1]+"\n"+tournArray[2]);

            listView.setAdapter(arrayadapt);
            break;
        }
        */
    }

    public void startList()
    {
        System.out.println("in startList");

        arrayadapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tournArray);

        //System.out.println(tournArray[0]+"\n"+tournArray[1]+"\n"+tournArray[2]);

        listView.setAdapter(arrayadapt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_tournament_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
