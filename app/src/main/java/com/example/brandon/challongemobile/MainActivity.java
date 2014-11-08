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
import android.widget.Toast;
import android.widget.CheckBox;

import java.net.Authenticator;
import javax.net.ssl.HttpsURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;


public class MainActivity extends ActionBarActivity
{
    Button loginButton;
    EditText usernameView;
    EditText passwordView;
    CheckBox checkB;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.loginButton);
        usernameView = (EditText) findViewById(R.id.usernameTextField);
        passwordView = (EditText) findViewById(R.id.passwordTextField);
        checkB = (CheckBox) findViewById(R.id.checkBox);


    }

            public void onClick(View v)
            {


                //errorView.setVisibility(View.INVISIBLE);

                /*
                Context context = getApplicationContext();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;
                */

                //Toast toast = Toast.makeText(context, text, duration);

                new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                        {

                            if(checkB.isChecked())
                            {
                                System.out.println("box is checked");

                            }

                            final String username = usernameView.getText().toString();
                            final String password = passwordView.getText().toString();

                            URL url = new URL("https://api.challonge.com/v1/tournaments.xml?state=in_progress");

                            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

                            connection.setRequestProperty("Accept-Encoding","");
                            //connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode("bubblerugs:AJmK8DFMF0EpwVRzTlORtuwyJOcGzViDXrQKG63G".getBytes(),Base64.NO_WRAP)));
                            connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode(new String(username + ":" +password).getBytes(),Base64.NO_WRAP)));

                            connection.connect();
                            if(connection.getResponseCode() == 200)
                            {
                                handleSuccess();
                            }
                            else
                            {
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Incorrect Login Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //System.out.println(connection.getResponseCode() + ": " +connection.getResponseMessage());
                                System.out.println("toasting");
                                //add toast
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



    public void makeToast()
    {
        Toast.makeText(getApplicationContext(), "Incorrect Login Credentials", Toast.LENGTH_SHORT).show();
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
