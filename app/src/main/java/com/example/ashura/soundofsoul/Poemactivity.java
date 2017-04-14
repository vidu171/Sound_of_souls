package com.example.ashura.soundofsoul;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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

    private AdView mview;
    String s = new String();
    String Poem_id = new String();
    String Raw_poem_id=new String();
    String author = new String();
    String title = new String();
    boolean like = false;
    public String LOG_TAG = Poemactivity.class.getName();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= getIntent().getExtras();


        title = bundle.getString("title");
         author = bundle.getString("author");
         Raw_poem_id = bundle.getString("poemid");
        Poem_id="a"+Raw_poem_id;
        setContentView(R.layout.poem_layout);

        mview = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mview.loadAd(adRequest);


        task thistask = new task();
        thistask.execute(Poem_id);


        final ImageView likeimage = (ImageView) findViewById(R.id.like);


        likeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!like){

                    like=true;

                    SharedPreferences sharedpref = getSharedPreferences("saved",MODE_PRIVATE);
                    SharedPreferences.Editor editor;
                    editor = sharedpref.edit();

                    String saved_string = sharedpref.getString("_id","");

                    saved_string = saved_string + Raw_poem_id+"#";

                    editor.putString("_id",saved_string);
                    editor.apply();
                    likeimage.setImageDrawable(getResources().getDrawable(R.drawable.like));
                    Toast.makeText(getBaseContext(),"SAVED TO FAVOURITE",Toast.LENGTH_LONG).show();



                }
                else{
                        like = false;
                    SharedPreferences sharedpref = getSharedPreferences("saved",MODE_PRIVATE);
                    SharedPreferences.Editor editor;
                    editor = sharedpref.edit();

                    String saved_string = sharedpref.getString("_id","");

                    saved_string = saved_string.replace("#"+Raw_poem_id+"#","#");


                    editor.putString("_id",saved_string);
                    editor.apply();
                    likeimage.setImageDrawable(getResources().getDrawable(R.drawable.unlike));
                    Toast.makeText(getBaseContext(),"REMOVED FROM FAVOURITE",Toast.LENGTH_LONG).show();


                }

            }
        });

        ImageView share = (ImageView) findViewById(R.id.layout_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, s);
                i.setType("text/plain");
                startActivity(i);
            }
        });

        //txt2.setText(lines);

    }




    public static  String getjson (String json){

        return json;
    }

    public class task extends AsyncTask<String,Void,String>{

        String text=new String();
        @Override
        protected String doInBackground(String... params) {


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

            SharedPreferences sharedpef = getSharedPreferences("saved",MODE_PRIVATE);

            String values = sharedpef.getString("_id","");

            Log.w("poems","values = "+ values);

            String[] values_string = values.split("#");




            for(int i=0;i<values_string.length;i++){


                if(values_string[i].equalsIgnoreCase(Raw_poem_id))
                {
                    like=true;
                    Log.w("poems","yeah here we are!!  "+Raw_poem_id+" " +values_string[i]);
                   // Toast.makeText(getBaseContext(),"Saved",Toast.LENGTH_LONG).show();


                }


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
            ImageView like_img = (ImageView) findViewById(R.id.like);
            if(like){

                like_img.setImageDrawable(getResources().getDrawable(R.drawable.like));

            }
            s=text;

        }
    }

}
