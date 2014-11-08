package com.example.brandon.challongemobile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Authenticator;
import javax.net.ssl.HttpsURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText usernameView = (EditText) findViewById(R.id.usernameTextField);
        final EditText passwordView = (EditText) findViewById(R.id.passwordTextField);
        final TextView errorView = (TextView) findViewById(R.id.loginErrorLabel);
        errorView.setVisibility(View.INVISIBLE);


        loginButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                        {

                            final String username = usernameView.getText().toString();
                            final String password = passwordView.getText().toString();

                            URL url = new URL("https://api.challonge.com/v1/tournaments.xml?state=in_progress");

                            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

                            connection.setRequestProperty("Accept-Encoding","");
                            connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode("bubblerugs:AJmK8DFMF0EpwVRzTlORtuwyJOcGzViDXrQKG63G".getBytes(),Base64.NO_WRAP)));
                            //connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode(new String(username + ":" +password).getBytes(),Base64.NO_WRAP)));

                            connection.connect();
                            if(connection.getResponseCode() == 200)
                            {
                                handleSuccess();
                            }
                            else
                            {
                                System.out.println(connection.getResponseCode() + ": " +connection.getResponseMessage());
                               // if(connection.getResponseCode() == )
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void handleSuccess()
    {
        Intent intent = new Intent(this,ChallongeHome.class);
        startActivity(intent);
        finish();
    }

}
