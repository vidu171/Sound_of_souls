package com.example.ashura.soundofsoul;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by vidu on 26/2/17.
 */

public class Poemactivity extends AppCompatActivity {


    String Poem_id = new String();
    String author = new String();
    String title = new String();
    public String LOG_TAG = Poemactivity.class.getName();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= getIntent().getExtras();
        title = bundle.getString("title");
         author = bundle.getString("author");
         Poem_id = bundle.getString("poemid");
        Poem_id="a"+Poem_id;
        setContentView(R.layout.poem_layout);

        task thistask = new task();
        thistask.execute();





        //txt2.setText(lines);

    }




    public static  String getjson (String json){

        return json;
    }

    public class task extends AsyncTask<poem_class,Void,String>{

        String text=new String();
        @Override
        protected String doInBackground(poem_class... params) {


            InputStream ins = getResources().openRawResource(
                    getResources().getIdentifier(Poem_id,
                            "raw", getPackageName()));

            InputStreamReader reader = new InputStreamReader(ins, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder output = new StringBuilder();

            try{
                String line  = bufferedReader.readLine();
                while(line!=null){
                    output.append(line);
                    line = bufferedReader.readLine();
                }



            }
             catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject root  = new JSONObject(output.toString());
                 text = root.getString("text");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            // InputStream input = getResources().openRawResource(R.raw.Poem_id);

            return text;
        }

        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(String st) {

            TextView txt = (TextView) findViewById(R.id.title);
            txt.setText(title);
            TextView txt1 = (TextView) findViewById(R.id.authorname);
            txt1.setText(author);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/sans.ttf");
            txt1.setTypeface(font);
            font = Typeface.createFromAsset(getAssets(),"fonts/aron.ttf");
            TextView txt2 = (TextView) findViewById(R.id.lines);
            txt.setTypeface(font);
            txt2.setText(text);


        }
    }

}
