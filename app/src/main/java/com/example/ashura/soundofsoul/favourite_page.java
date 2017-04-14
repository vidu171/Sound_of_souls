package com.example.ashura.soundofsoul;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vidu on 23/3/17.
 */

public class favourite_page extends AppCompatActivity {

    public title_adapter madapter;
    public String bundleauthor = new String();
    ListView list;
    ArrayList<poem_class> orignalarray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poem_activity);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);





        task thistask = new task();
        thistask.execute();
        final ArrayList<poem_class> array = new ArrayList<>();
        orignalarray=array;
        list  = (ListView) findViewById(R.id.list_item);
        madapter = new title_adapter(this,array);
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

                Intent intent = new Intent(favourite_page.this,Poemactivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
//
//    @Override
//    public void onBackPressed() {
//        MaterialSearchView searchView=(MaterialSearchView) findViewById(R.id.search_view);
//
//        if(searchView.isSearchOpen()){
//           // madapter = new title_adapter(author_titles.this,orignalarray);
//           // list.setAdapter(madapter);
//
//        }
//
//        else {
//            super.onBackPressed();
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.action_search,menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
//        searchView.setMenuItem(item);
//
//
//
//        return true;
//    }

    private class task extends AsyncTask<URL, Void, List<poem_class>> {

        private ProgressDialog progressDialog;

        @Override
        public List<poem_class> doInBackground(URL... urls) {

            SharedPreferences pref = getSharedPreferences("saved",MODE_PRIVATE);
            String values = pref.getString("_id","");

            String[] values_array = values.split("#");

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


            ArrayList<poem_class> json = new ArrayList<>();
            ArrayList<poem_class> json2 = new ArrayList<>();
            poem_class thispoem;
            JSONArray array = null;
            try {

                array = new JSONArray(output.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject root = array.getJSONObject(i);
                    String title = root.getString("title");
                    String author = root.getString("author");
                    int poem_id = root.getInt("id");


                        thispoem = new poem_class(title, author, poem_id);
                        json.add(thispoem);



                    Log.w("author_title", author);
                }


            } catch (JSONException e) {

            }

            for(int i =2;i<values_array.length;i++){

                Log.d("hey ",values_array[i]);
                    json2.add(json.get(Integer.valueOf(values_array[i])));



            }


            return json2;


        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(favourite_page.this);
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
