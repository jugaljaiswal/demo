package com.demo.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pooja Dubey on 1/26/2016.
 */
public class Singleton
{


    public static String getUrlApi()
    {

        return "http://private-21c80-care24androidtest.apiary-mock.com/androidtest/getData";
    }




    @SuppressLint("NewApi")
    public static String readStream(String urlX) {

        InputStream inputStream=null;
        try
        {
            URL url = new URL(urlX);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            inputStream=con.getInputStream();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));)
        {
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static boolean checkInternetConenction(Context context)
    {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            return true;
        }
        else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
        {

            return false;
        }
        return false;
    }
}
