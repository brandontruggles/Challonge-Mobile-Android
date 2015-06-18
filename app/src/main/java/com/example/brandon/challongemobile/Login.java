package com.example.brandon.challongemobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.CheckBox;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;


public class Login extends ActionBarActivity
{
    private Button loginButton;
    private EditText usernameView;
    private EditText passwordView;
    private CheckBox checkB;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        usernameView = (EditText) findViewById(R.id.usernameTextField);
        passwordView = (EditText) findViewById(R.id.passwordTextField);
        checkB = (CheckBox) findViewById(R.id.checkBox);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        ConnectionManager.init();

        try
        {
            File file = new File(getFilesDir(),"userCred.txt");
            Scanner scan = new Scanner(file);
            if(file.exists())
            {
                ConnectionManager.setUsername(scan.nextLine());

                if(!ConnectionManager.getUsername().equals("login credentials"))
                {
                    ConnectionManager.setPassword(scan.nextLine());
                    new Thread(new Runnable()
                    {
                        public void run()
                        {
                            loginButton.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.VISIBLE);

                            ConnectionManager.login();

                            final int responseCode = ConnectionManager.getResponseCode();
                            final String responseMessage = ConnectionManager.getResponseMessage();
                            if (responseCode == 200)
                                handleSuccess();
                            else
                            {
                                runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        Toast.makeText(getApplicationContext(), responseCode + ": " + responseMessage, Toast.LENGTH_LONG).show();
                                        loginButton.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onClick(View v)
    {
        ConnectionManager.setUsername("bubblerugs");//usernameView.getText().toString();"bubblerugs";"dfu3";
        ConnectionManager.setPassword("AJmK8DFMF0EpwVRzTlORtuwyJOcGzViDXrQKG63G");//passwordView.getText().toString();"AJmK8DFMF0EpwVRzTlORtuwyJOcGzViDXrQKG63G";"KULR1goMHWqp0UOcIbXljRAet7pLgXDQma0IxKO1";
        loginButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        usernameView.setEnabled(false);
        passwordView.setEnabled(false);
        checkB.setEnabled(false);

        new Thread(new Runnable()
        {
            public void run()
            {
                ConnectionManager.login();

                final int responseCode = ConnectionManager.getResponseCode();
                final String responseMessage = ConnectionManager.getResponseMessage();

                if(responseCode == 200)
                    handleSuccess();
                else
                {
                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            Toast.makeText(getApplicationContext(), responseCode + ": " + responseMessage, Toast.LENGTH_LONG).show();
                            loginButton.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            usernameView.setEnabled(true);
                            passwordView.setEnabled(true);
                            checkB.setEnabled(true);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private void handleSuccess()
    {
        if(checkB.isChecked())
        {
            try
            {
                FileOutputStream fos = openFileOutput("userCred.txt",Context.MODE_PRIVATE);
                fos.write((ConnectionManager.getUsername()+"\n"+ConnectionManager.getPassword()).getBytes());
                fos.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
        Intent intent = new Intent(this,ChallongeHome.class);
        startActivity(intent);
        finish();
    }

}