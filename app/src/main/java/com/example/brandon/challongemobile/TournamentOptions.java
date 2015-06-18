package com.example.brandon.challongemobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class TournamentOptions extends Activity
{
    private ProgressBar loadingCircle;
    private Button bracketButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_options);
        loadingCircle = (ProgressBar) findViewById(R.id.progressBar5);
        bracketButton = (Button) findViewById(R.id.button6);
        loadingCircle.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadingCircle.setVisibility(View.INVISIBLE);
        bracketButton.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tournament_options, menu);
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

    public void bracketClick(View view)
    {
        loadingCircle.setVisibility(View.VISIBLE);
        bracketButton.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this,ShowBracket.class);
        intent.putExtra("Data",(String)getIntent().getExtras().get("Data"));
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}
