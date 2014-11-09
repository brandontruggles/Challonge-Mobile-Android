package com.example.brandon.challongemobile;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Authenticator;
import javax.net.ssl.HttpsURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Scanner;


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

        System.out.println("on create");

        String content;
        BufferedReader in;
        try {
            File file = new File(getFilesDir(),"userCred.txt");
            Scanner scan = new Scanner(file);
            if(file.exists())
            {
                //System.out.println("file exists");
                URL url = new URL("https://api.challonge.com/v1/tournaments.xml?state=in_progress");

                HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

                connection.setRequestProperty("Accept-Encoding","");
                //connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode("bubblerugs:AJmK8DFMF0EpwVRzTlORtuwyJOcGzViDXrQKG63G".getBytes(),Base64.NO_WRAP)));
                connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode(new String(scan.nextLine() + ":" +scan.nextLine()).getBytes(),Base64.NO_WRAP)));

                handleSuccess("","");
            }
        }
        catch (Exception e)
        {
            System.out.println("File Not Found");
        }


    }

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

                            URL url = new URL("https://api.challonge.com/v1/tournaments.json?state=all");

                            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

                            connection.setRequestProperty("Accept-Encoding","");
                            connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode("bubblerugs:AJmK8DFMF0EpwVRzTlORtuwyJOcGzViDXrQKG63G".getBytes(),Base64.NO_WRAP)));
                            //connection.setRequestProperty("Authorization","Basic " + new String(Base64.encode(new String(username + ":" +password).getBytes(),Base64.NO_WRAP)));

                            connection.connect();

                            if(connection.getResponseCode() == 200)
                            {
                                handleSuccess(username,password);
                            }
                            else
                            {
                                MainActivity.this.runOnUiThread(new Runnable()
                            {
                                public void run()
                                {
                                    Toast.makeText(getApplicationContext(), "Incorrect Login Credentials", Toast.LENGTH_LONG).show();
                                    // System.out.println("Error response code: " + connection.getResponseCode());
                                }
                            });
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
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

    private void handleSuccess(String username, String password)
    {
        System.out.println("Success");

        if(checkB.isChecked())
        {

            try {
                FileOutputStream fos = openFileOutput("userCred.txt",Context.MODE_PRIVATE);
                //fos.write((username+"\n"+password).getBytes());
                fos.write(("bubblerugs"+"\n"+"AJmK8DFMF0EpwVRzTlORtuwyJOcGzViDXrQKG63G").getBytes());

                fos.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }

        Intent intent = new Intent(this,ChallongeHome.class);
        intent.putExtra("Username",username);
        intent.putExtra("Password",password);
        startActivity(intent);
        finish();
    }

}
