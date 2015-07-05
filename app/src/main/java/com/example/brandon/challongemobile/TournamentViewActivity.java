package com.example.brandon.challongemobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class TournamentViewActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayList<String> tournList;
    private ArrayList<String> urlList;
    private ArrayAdapter<String> arrayadapt;
    private String[] tournArray;
    private JSONArray tournaments;
    private ProgressBar loadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(ConnectionManager.getUsername().toUpperCase());
        listView = (ListView) findViewById(R.id.listView);
        tournList = new ArrayList<String>();
        urlList = new ArrayList<String>();
        loadingCircle = (ProgressBar) findViewById(R.id.progressBar3);

        new Thread(new Runnable() {
            public void run() {
                ConnectionManager.connectToURL("https://api.challonge.com/v1/tournaments.json?state=all");

                final int responseCode = ConnectionManager.getResponseCode();
                final String responseMessage = ConnectionManager.getResponseMessage();

                if (responseCode == 200) {
                    String data = ConnectionManager.readInputStream();
                    try {
                        tournaments = new JSONArray(data);

                        for (int i = 0; i < tournaments.length(); i++) {
                            JSONObject tournament = ((JSONObject) tournaments.get(i)).getJSONObject("tournament");
                            tournList.add(tournament.getString("name"));
                            urlList.add(tournament.getString("url"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    tournArray = new String[tournList.size()];

                    for (int j = 0; j < tournArray.length; j++)
                        tournArray[j] = tournList.get(j);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            startList();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), responseCode + ": " + responseMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loadingCircle.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    public void startList() {
        loadingCircle.setVisibility(View.INVISIBLE);

        arrayadapt = new ArrayAdapter<String>(this, R.layout.challonge_list,R.id.list_content, tournArray);

        listView.setAdapter(arrayadapt);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                runActivity((String) urlList.get(position), (String) tournList.get(position));
            }
        });
    }

    public void deleteTournament(String tournament) {
        final String data = tournament;
        new Thread(new Runnable() {
            public void run() {
                ConnectionManager.connectToURL("https://api.challonge.com/v1/tournaments/" + data + ".json");
                ConnectionManager.requestDelete();
            }
        }).start();
    }

    public void runActivity(String url, String name) {
        listView.setVisibility(View.INVISIBLE);
        loadingCircle.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, ShowBracket.class);
        intent.putExtra("URL", url);
        intent.putExtra("Name", name);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tournament_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to log out of your Challonge account?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    logout();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout()
    {
        try
        {
            FileOutputStream fos = openFileOutput("userCred.txt", Context.MODE_PRIVATE);
            fos.write(("login credentials").getBytes());
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
