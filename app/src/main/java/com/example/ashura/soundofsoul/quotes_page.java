package com.example.ashura.soundofsoul;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by vidu on 21/3/17.
 */

public class quotes_page extends AppCompatActivity {
    private AdView mview;
    quote_Adapter madapter;
    Random rand = new Random();
    int r = rand.nextInt(4400);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.quotes);


        Log.w("quotes","quote page opened");

        mview = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mview.loadAd(adRequest);

        task thistask = new task();
        thistask.execute();
        ListView listView = (ListView) findViewById(R.id.list);


        ArrayList<quotes_class> quotes = new ArrayList<>();

         madapter = new quote_Adapter(this,quotes);
        listView.setAdapter(madapter);


    }

    public class task extends AsyncTask<Void,Void,List<quotes_class>>{
        ArrayList<quotes_class> final_array = new ArrayList<>();
        ArrayList<quotes_class> array_list = new ArrayList<>();
        @Override
        protected ArrayList<quotes_class> doInBackground(Void... params) {



            Log.w("quotes","Async working");
            InputStream input = getResources().openRawResource(R.raw.quotes);
            InputStreamReader reader = new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder output = new StringBuilder();
            try {

                String line = bufferedReader.readLine();
                while(line!=null){
                    output.append(line);
                    line = bufferedReader.readLine();
                }

            }catch (IOException e){
                e.printStackTrace();
            }
           try {
               JSONArray quote_array = new JSONArray(output.toString());
               quotes_class thisquote;



               for(int i =r;i<r+1000;i++){

                   JSONObject root = quote_array.getJSONObject(i);
                   String text = root.getString("quoteText");
                   String writer = root.getString("quoteAuthor");


                   thisquote = new quotes_class(text,writer);
                    array_list.add(thisquote);
               }






           }
           catch (JSONException e){


           }


            return array_list;
        }

        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(List<quotes_class> data) {
            madapter.clear();


            if (data != null && !data.isEmpty()){
                madapter.addAll(data);
            }



        }
    }

}
