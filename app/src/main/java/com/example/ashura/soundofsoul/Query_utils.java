package com.example.ashura.soundofsoul;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vidu on 26/2/17.
 */

public class Query_utils {
    private static final String LOG_TAG =MainActivity.class.getName();





    public static String makehttprequest(Context context,URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputfromStraeam(inputStream);
            } else {

               //AlertDialog connection_dialog = new AlertDialog.Builder(context).create();

//                AlertDialog alertDialog = new AlertDialog.Builder(context).create(); //Use context
//                alertDialog.setTitle("Warning");
//                alertDialog.setMessage("You are currently in a battle");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();


                Log.e(LOG_TAG, "Error in connection!! Bad Response "+ jsonResponse);
            }

        } catch (IOException e) {
//            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
//            AlertDialog alertDialog = new AlertDialog.Builder(context).create(); //Use context
//            alertDialog.setTitle("Warning");
//            alertDialog.setMessage("You are currently in a battle");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
        } finally {
            if (urlConnection == null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        Log.d(LOG_TAG," " + jsonResponse);

        return jsonResponse;
    }

    public static String readInputfromStraeam (InputStream inputStream) throws IOException{

        StringBuilder output = new StringBuilder();

        if(inputStream!=null){
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String Line = bufferedReader.readLine();
            while (Line!=null){

                output.append(Line);
                Line =bufferedReader.readLine();
            }
            Log.e(LOG_TAG,output.toString());
        }
        return output.toString();
    }

    public static URL createurl ( String url){

        URL url1 = null;
        try {
            url1 = new URL(url);
            Log.w(LOG_TAG,"created");
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url1;

    }
    public static List<view> extracttitlefromjson (String jsonresponse){

        if (TextUtils.isEmpty(jsonresponse)) {
            return null;
        }

        List<view> currenttitle = new ArrayList<view>();
        try {
            Log.d(LOG_TAG,"workee");
            {

                JSONObject object = new JSONObject(jsonresponse);

                JSONArray array = object.getJSONArray("titles");
                for(int i = 0; i < array.length(); i ++){
                    String title = array.getString(i);
                    view titlenow = new view(title);
                    currenttitle.add(titlenow);

                }


            }

        }
        catch (JSONException e){
            Log.e(LOG_TAG,"Cant parse the json");
        }
        return currenttitle;

    }

    public static view extractpoemjson(String jsonresponse){

        if (TextUtils.isEmpty(jsonresponse)) {
            return null;
        }

        view poems = new view(null);

        try {
            Log.d(LOG_TAG,"workee");
            {


                JSONArray array = new JSONArray(jsonresponse);
                JSONObject lines = array.getJSONObject(0);
                String title = lines.getString("title");
                String author = lines.getString("author");
                JSONArray poem_lines = lines.getJSONArray("lines");
                String line = new String();

                for(int i = 0; i < poem_lines.length(); i ++){
                    line = line+"\n"+ poem_lines.getString(i);
                }


                Log.w(LOG_TAG,line);
                poems = new view(title,author,line);

                Log.w(LOG_TAG,poems.title_view + "   "+ poems.poem_lines);


            }

        }
        catch (JSONException e){
            Log.e(LOG_TAG,"Cant parse the json #2");
        }
        return poems;
    }



}
