package com.example.ashura.soundofsoul;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.File;
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

public class authors extends AppCompatActivity {

    public title_adapter madapter;
     ListView list;
    ArrayList<poem_class> orignalarray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poem_activity);


        task thistask = new task();
        thistask.execute();
        final ArrayList<poem_class> authors = new ArrayList<>();
        orignalarray=authors;
        list  = (ListView) findViewById(R.id.list_item);
        madapter = new title_adapter(this,authors);
         list.setAdapter(madapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AUTHORS");


//        list.setOnClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                poem_class currentpoem = madapter.getItem(position);
//                Bundle bundle = new Bundle();
//                bundle.putString("author",currentpoem.poem_title);
//
//                Intent intent = new Intent(authors.this,author_titles.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText=newText.toLowerCase();

                ArrayList<poem_class> authors_array = new ArrayList<poem_class>();
                for(int i=0;i<authors.size();i++){

                    if(authors.get(i).poem_title.toLowerCase().contains(newText)){
                        authors_array.add(authors.get(i));
                    }
                }

                madapter = new title_adapter(authors.this,authors_array);
                list.setAdapter(madapter);

                return false;
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                poem_class current_poem = madapter.getItem(i);


                String title_to_intent=current_poem.poem_title;


                Bundle bundle = new Bundle();
                bundle.putString("author",title_to_intent);




                //This sends the title variable to next file

                Intent intent = new Intent(authors.this,author_titles.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }


    public void onBackPressed(){

        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
        if (searchView.isSearchOpen()) {
            madapter = new title_adapter(authors.this ,orignalarray);
            list.setAdapter(madapter);
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setMenuItem(item);

//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setQueryHint("Type something...");
        return true;
    }



    private class task extends AsyncTask<URL, Void, List<poem_class>> {

        private ProgressDialog progressDialog;

        @Override
        public List<poem_class> doInBackground(URL... urls) {

            InputStream input = getResources().openRawResource(R.raw.authori);

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
                    // String title = root.getString("title");
                    String author = root.getString("author");
                    //  int poem_id = root.getInt("id");
                    thispoem = new poem_class(author,"x","x");
                    json.add(thispoem);

//                        ContentValues value = new ContentValues();
//                        value.put(poem_contract.poem_entry.COLUMN_AUTHOR,author);
//                        value.put(poem_contract.poem_entry.COLUMN_TITLE,title);
//                        value.put(poem_contract.poem_entry.POEM_ID,poem_id);
//                        long newRowId = databasewriter.insert(poem_contract.poem_entry.TABLE_NAME,null,value);


                    Log.w("poem_page", author);
                }


            } catch (JSONException e) {

            }


            return json;


        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(authors.this);
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
