package com.example.brandon.challongemobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;


public class MainActivity extends ActionBarActivity {
    private Button viewButton;
    private Button manageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        viewButton = (Button) findViewById(R.id.viewButton);
        manageButton = (Button) findViewById(R.id.manageButton);
        ConnectionManager.init();
    }

    public void onViewTournament(View view)
    {
        Intent intent = new Intent(this,SearchTournaments.class);
        startActivity(intent);
    }

    public void onViewOrganization(View view)
    {
        Intent intent = new Intent(this,SearchOrganizations.class);
        startActivity(intent);
    }

    public void onManageTournaments(View view)
    {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public void onViewAbout(View view)
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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