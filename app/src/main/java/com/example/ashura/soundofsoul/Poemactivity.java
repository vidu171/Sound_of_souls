package com.example.ashura.soundofsoul;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import static java.security.AccessController.getContext;

/**
 * Created by vidu on 26/2/17.
 */

public class Poemactivity extends AppCompatActivity {
    String title = new String();
    public String LOG_TAG = Poemactivity.class.getName();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= getIntent().getExtras();
        title = bundle.getString("title");
        setContentView(R.layout.poem_layout);
        title = "http://poetrydb.org/title/"+title;
        title=title.replace(" ","%20");
        URL url = Query_utils.createurl(title);

        poemAsynctask poemtask = new poemAsynctask();
        poemtask.execute(url);

    }



    private class poemAsynctask extends AsyncTask<URL,Void,view> {

        String json = "";
        view parsedjson;
        private ProgressDialog progressDialog;


        protected view doInBackground(URL... urls){

            Log.w(LOG_TAG,"hhss"+title);

            URL url = Query_utils.createurl(title);

            try{
                json= Query_utils.makehttprequest(Poemactivity.this,url);


            }
            catch (IOException e){

            }
            parsedjson = Query_utils.extractpoemjson(json);
            return parsedjson;
        }


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Poemactivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(view result) {
            TextView txt = (TextView) findViewById(R.id.title);
            txt.setText(parsedjson.poem_title);
            TextView txt1 = (TextView) findViewById(R.id.authorname);
            txt1.setText(parsedjson.poem_autor);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/sans.ttf");
            txt1.setTypeface(font);
            font = Typeface.createFromAsset(getAssets(),"fonts/aron.ttf");
            TextView txt2 = (TextView) findViewById(R.id.lines);
            txt.setTypeface(font);
            txt2.setText(parsedjson.poem_lines);
            //updateui();
            getjson(json);
            progressDialog.dismiss();

        }

    }


    public static  String getjson (String json){

        return json;
    }
}
