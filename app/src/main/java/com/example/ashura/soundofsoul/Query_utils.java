package com.example.ashura.soundofsoul;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.example.ashura.soundofsoul.data.poem_contract;

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
                            }


                            else {
                                     Log.e(LOG_TAG, "Error in connection!! Bad Response "+ jsonResponse);
                            }

                        } catch (IOException e) {

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



    public static List<poem_class> extracttitlefromjson (String jsonresponse){

                    if (TextUtils.isEmpty(jsonresponse)) {
                        return null;
                    }

                    List<poem_class> currenttitle = new ArrayList<poem_class>();
                    try {
                        Log.d(LOG_TAG,"workee");
                        {
                            JSONObject object = new JSONObject(jsonresponse);

                            JSONArray array = object.getJSONArray("titles");
                            for(int i = 0; i < array.length(); i ++){
                                String title = array.getString(i);
                                poem_class titlenow = new poem_class(title);
                                currenttitle.add(titlenow);
                            }

                        }

                    }
                    catch (JSONException e){
                        Log.e(LOG_TAG,"Cant parse the json");
                    }
                    return currenttitle;
    }






    public static poem_class extractpoemjson(String jsonresponse){

                    if (TextUtils.isEmpty(jsonresponse)) {
                        return null;
                    }
                    poem_class poems = new poem_class(null);

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
                            poems = new poem_class(title,author,line);

                            Log.w(LOG_TAG,poems.title_view + "   "+ poems.poem_lines);
                        }

                    }
                    catch (JSONException e){
                        Log.e(LOG_TAG,"Cant parse the json #2");
                    }
                    return poems;
    }

        public static List<poem_class> extractoflinejson(String jsonresponse , SQLiteDatabase db){
            if (TextUtils.isEmpty(jsonresponse)) {
                return null;
            }
            poem_class thispoem = null;
            JSONArray array = null;
            List<poem_class> json = new ArrayList<>();
            try {
                array = new JSONArray(jsonresponse);

            for (int i = 0; i < array.length(); i++) {
                    JSONObject currentpoem = array.getJSONObject(i);
                    String title = currentpoem.getString("title");
                String author = currentpoem.getString("author");
                    JSONArray text = currentpoem.getJSONArray("text");
                    String line = new String();

                    for (int k = 0; k < text.length(); k++) {
                        line = line + "\n" + text.getString(k);
                    }

                    thispoem = new poem_class(title, author  , line);


                    json.add(thispoem);
                    Log.w(LOG_TAG, json.get(i).poem_title+" "+ json.get(i).poem_autor+"    "+ i);

                ContentValues value = new ContentValues();
                value.put(poem_contract.poem_entry.COLUMN_AUTHOR,json.get(i).poem_autor);
                value.put(poem_contract.poem_entry.COLUMN_LINES,json.get(i).poem_lines);
                value.put(poem_contract.poem_entry.COLUMN_TITLE,json.get(i).poem_title);
                value.put(poem_contract.poem_entry.COLUMN_LIKES,0);

                long newRowId = db.insert(poem_contract.poem_entry.TABLE_NAME,null,value);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

}
