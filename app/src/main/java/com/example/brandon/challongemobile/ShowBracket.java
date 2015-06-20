package com.example.brandon.challongemobile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.TextView;
import java.io.FileOutputStream;

public class ShowBracket extends ActionBarActivity
{
    private WebView webview;
    private TextView title;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bracket);
        dialog = ProgressDialog.show(this,"","Loading...",true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(ConnectionManager.getUsername().toUpperCase());
        title = (TextView)findViewById(R.id.textView4);
        title.setText((String)getIntent().getExtras().get("Name"));
        webview = (WebView)findViewById(R.id.webView);
        webview.setBackgroundColor(373737);
        webview.setWebChromeClient(new WebChromeClient());
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.hide();
            }
        });
        webview.loadUrl("http://challonge.com/" + getIntent().getExtras().get("URL") + "/module?show_final_results=1&show_standings=1");
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_bracket, menu);
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
        if(id == R.id.action_refresh)
        {
            webview.reload();
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
