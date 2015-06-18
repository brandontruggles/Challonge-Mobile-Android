package com.example.brandon.challongemobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.FileOutputStream;

public class ChallongeHome extends ActionBarActivity
{
    private ProgressBar loadingCircle;
    private Button viewButton;
    private Button createButton;
    private TextView yourAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(ConnectionManager.getUsername().toUpperCase());
        setContentView(R.layout.activity_challonge_home);
        loadingCircle = (ProgressBar)findViewById(R.id.progressBar2);
        loadingCircle.setVisibility(View.INVISIBLE);
        viewButton = (Button)findViewById(R.id.button4);
        createButton = (Button)findViewById(R.id.button5);
        yourAccount = (TextView)findViewById(R.id.textView2);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        hideLoading();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.challonge_home, menu);
        //getSupportActionBar().show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout)
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

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showLoading()
    {
        viewButton.setVisibility(View.INVISIBLE);
        createButton.setVisibility(View.INVISIBLE);
        yourAccount.setVisibility(View.INVISIBLE);
        loadingCircle.setVisibility(View.VISIBLE);
    }
    public void hideLoading()
    {
        viewButton.setVisibility(View.VISIBLE);
        createButton.setVisibility(View.VISIBLE);
        yourAccount.setVisibility(View.VISIBLE);
        loadingCircle.setVisibility(View.INVISIBLE);
    }
    public void onMyTourn(View view)
    {
        showLoading();
        Intent intent = new Intent(this,TournamentViewActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
    public void onCreateTourn(View view)
    {
        showLoading();
        Intent intent = new Intent(this,CreateTournament.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
    public void onSearchTourn(View view)
    {
        showLoading();
        Intent intent = new Intent(this,SearchTournaments.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

}
