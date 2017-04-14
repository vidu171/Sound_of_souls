package com.example.ashura.soundofsoul;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashura.soundofsoul.data.poem_contract;
import com.example.ashura.soundofsoul.data.poemdbhelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

//import static com.example.ashura.soundofsoul.R.raw.i;

/**
 * Created by vidu on 16/3/17.
 */

public class poem_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poem_page);
//        task thistask = new task();
//        thistask.execute();

        ImageView by_title = (ImageView) findViewById(R.id.textView2);
        by_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(poem_page.this,Poem_activity.class);
                startActivity(intent);
            }
        });
        ImageView fav = (ImageView) findViewById(R.id.textView3);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(poem_page.this,favourite_page.class);
                startActivity(i);
            }
        });
        ImageView by_author = (ImageView) findViewById(R.id.textView);
        by_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(poem_page.this,authors.class);
                startActivity(intent);
            }
        });









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.poem_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.report_problem){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.setType("text/html");
            i.putExtra(Intent.EXTRA_EMAIL,"illumy.inc@gmail.com");
            startActivity(Intent.createChooser(i,"Send Email"));

        }
        else if(id==R.id.menu_nav_share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sound of Soul");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName());
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        }


        return super.onOptionsItemSelected(item);
    }

    public class task extends AsyncTask<Void,Void,Void>{

        ProgressDialog progressDialog ;

        @Override
        protected Void doInBackground(Void... params) {

            poemdbhelper  mdbhelper = new poemdbhelper(poem_page.this);

         //   SQLiteDatabase database = mdbhelper.getReadableDatabase();




                InputStream input  = getResources().openRawResource(R.raw.title);

                InputStreamReader reader = new InputStreamReader(input, Charset.forName("UTF-8"));

                StringBuilder output = new StringBuilder();
                BufferedReader bufferedreader = new BufferedReader(reader);
                try {
                    String line = bufferedreader.readLine();
                    while (line!=null){

                        output.append(line);
                        line=bufferedreader.readLine();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
         //   SQLiteDatabase databasewriter = mdbhelper.getWritableDatabase();

                ArrayList<poem_class> json = new ArrayList<>();
                poem_class thispoem ;
                JSONArray array = null;
                try {

                    array = new JSONArray(output.toString());
                    for(int i =0;i<array.length();i++){
                        JSONObject root  = array.getJSONObject(i);
                        String title = root.getString("title");
                        String author = root.getString("author");
                        int poem_id = root.getInt("id");
                        thispoem = new poem_class(title,author,poem_id);
                        json.add(thispoem);

//                        ContentValues value = new ContentValues();
//                        value.put(poem_contract.poem_entry.COLUMN_AUTHOR,author);
//                        value.put(poem_contract.poem_entry.COLUMN_TITLE,title);
//                        value.put(poem_contract.poem_entry.POEM_ID,poem_id);
//                        long newRowId = databasewriter.insert(poem_contract.poem_entry.TABLE_NAME,null,value);


                        Log.w("poem_page",title);
                    }


                }
                catch (JSONException e){

                }


                Log.w("heya" , "DONE");








            return null;
        }
        @Override
        protected void onPreExecute() {
            progressDialog  = new ProgressDialog(poem_page.this);
            progressDialog.setMessage("We have over 12k poems to offer");
            progressDialog.setCancelable(false);
            progressDialog.show();


            super.onPreExecute();
        }



//        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//        @Override
//        protected void onPostExecute(List<poem_class> data) {
//            // madapter.clear();
//
//
////            if (data != null && !data.isEmpty()){
////                //   madapter.addAll(data);
////            }
//            progressDialog.dismiss();
//
//
//        }
    }


}
