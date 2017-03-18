package com.example.ashura.soundofsoul;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vidu on 17/3/17.
 */

public class author_titles extends AppCompatActivity {

    public title_adapter madapter;
    public String bundleauthor = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poem_activity);
        Bundle bundle = getIntent().getExtras();

        bundleauthor = bundle.getString("author");

        task thistask = new task();
        thistask.execute();
        ArrayList<poem_class> authors = new ArrayList<>();
        ListView list  = (ListView) findViewById(R.id.list_item);
        madapter = new title_adapter(this,authors);
        list.setAdapter(madapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                poem_class current_poem = madapter.getItem(i);


                String title_to_intent=current_poem.poem_title;
                String author_to_intent= current_poem.poem_autor;
                int poemId = current_poem.poem_id;

                Bundle bundle = new Bundle();
                bundle.putString("title",title_to_intent);
                bundle.putString("author",author_to_intent);
                bundle.putString("poemid",String.valueOf(poemId));



                //This sends the title variable to next file

                Intent intent = new Intent(author_titles.this,Poemactivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private class task extends AsyncTask<URL, Void, List<poem_class>> {

        private ProgressDialog progressDialog;

        @Override
        public List<poem_class> doInBackground(URL... urls) {

            InputStream input = getResources().openRawResource(R.raw.title);

            InputStreamReader reader = new InputStreamReader(input, Charset.forName("UTF-8"));

            StringBuilder output = new StringBuilder();
            BufferedReader bufferedreader = new BufferedReader(reader);
            try {
                String line = bufferedreader.readLine();
                while (line != null) {

                    output.append(line);
                    line = bufferedreader.readLine();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   SQLiteDatabase databasewriter = mdbhelper.getWritableDatabase();

            ArrayList<poem_class> json = new ArrayList<>();
            poem_class thispoem;
            JSONArray array = null;
            try {

                array = new JSONArray(output.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject root = array.getJSONObject(i);
                     String title = root.getString("title");
                    String author = root.getString("author");
                      int poem_id = root.getInt("id");

                    if(author.equals(bundleauthor) ) {
                        thispoem = new poem_class(title, author, poem_id);
                        json.add(thispoem);
                    }


                    Log.w("author_title", author);
                }


            } catch (JSONException e) {

            }


            return json;


        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(author_titles.this);
            progressDialog.setMessage("We have over 12k poems to offer");
            progressDialog.setCancelable(false);
            progressDialog.show();


            super.onPreExecute();
        }


        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(List<poem_class> data) {
            madapter.clear();


            if (data != null && !data.isEmpty()) {
                madapter.addAll(data);
            }
            progressDialog.dismiss();


        }


    }
}


