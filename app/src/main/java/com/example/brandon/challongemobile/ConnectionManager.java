package com.example.brandon.challongemobile;

import android.util.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class ConnectionManager
{
    private static String username;
    private static String password;
    private static String responseMessage;
    private static String input;
    private static int responseCode;
    private static HttpsURLConnection connection;
    public static void init()
    {
        //initializes variables
        username = "";
        password = "";
        responseMessage = "No Connection Attempted";
        input = "";
        responseCode = -1;
    }
    public static void login()
    {
        //"logs in" the user by attempting to connect to a random url on Challonge, check the response code to know if successful
        connectToURL("https://api.challonge.com/v1/tournaments.json?state=all");
    }
    public static void connectToURL(String urlText)
    {
        //disconnects any connections currently running and creates a new one to the specified url using HTTP GET, this method or the login() method must be called before any others
        try
        {
            if(connection!=null)
                connection.disconnect();
            URL url = new URL(urlText);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Encoding", "");
            connection.setRequestProperty("Authorization", "Basic " + new String(Base64.encode((username + ":" + password).getBytes(), Base64.NO_WRAP)));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void postToServer(String message)
    {
        //Changes the connection request property to HTTP POST (Sends information to the server such as tournament info, player info, etc)
        writeToServer(message,"POST");
    }
    public static void putToServer(String message)
    {
        //Changes the connection request property to HTTP PUT (Updates already-existing variables on the server)
        writeToServer(message,"PUT");
    }

    public static void requestDelete()
    {
        //Changes the connection request property to HTTP DELETE (Used to delete information from the server)
        try
        {
            connection.setRequestMethod("DELETE");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void writeToServer(String message,String method)
    {
        //Writes data to the server using the specified HTTP request method
        try
        {
            connection.setRequestMethod(method);
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(os);
            writer.write(message);
            writer.flush();
            writer.close();
            os.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void setUsername(String name)
    {
        //Change username the user typed in
        username = name;
    }
    public static void setPassword(String word)
    {
        //Change password the user typed in
        password = word;
    }
    public static String getUsername()
    {
        //Get the username the user typed in
        return username;
    }
    public static String getPassword()
    {
        //Get the password the user typed in
        return password;
    }
    public static String readInputStream()
    {
        //Reads the detailed response from the server (may include useful data such as JSON after a HTTP GET request issued by the connectToURL() method)
        try
        {
            BufferedReader b = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp;
            while ((temp = b.readLine()) != null)
                input += temp;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return input;
    }
    public static String getPreviousInput()
    {
        //Get the previous input read from the server without reading from it again
        return input;
    }
    public static String getResponseMessage()
    {
        //Get the basic HTTP response message returned by the server (helps us figure out problems connecting to the server)
        try
        {
            if (connection != null)
                responseMessage = connection.getResponseMessage();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return responseMessage;
    }
    public static int getResponseCode()
    {
        //Get the basic HTTP response code returned by the server (used to determine if we successfully connected or not)
        try
        {
            if (connection != null)
                responseCode = connection.getResponseCode();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return responseCode;
    }
}
