package com.example.ashura.soundofsoul;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashura.soundofsoul.data.poem_contract;
import com.example.ashura.soundofsoul.data.poem_provider;
import com.example.ashura.soundofsoul.data.poemdbhelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

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
import java.util.Random;

/**
 * Created by vidu on 26/2/17.
 */

public class Poem_activity extends AppCompatActivity {
     public static final String LOG_TAG = MainActivity.class.getName();
        public static String POEM_TITLE = "http://poetrydb.org/title";

ArrayList<poem_class> orignalarray = new ArrayList<>();
    ArrayList<poem_class> devidedarray = new ArrayList<>();
     ListView poem_Title_list;






    public title_adapter madapter;
             @Override
                 protected void onCreate(Bundle savedInstanceState) {
                             super.onCreate(savedInstanceState);
                              setContentView(R.layout.poem_title_layout);


                 Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                 setSupportActionBar(toolbar);
                 getSupportActionBar().setTitle("POEMS");
                 final MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);



                            TitleAsynctask task = new TitleAsynctask();
                            URL url = Query_utils.createurl(POEM_TITLE);
                            task.execute(url);


                             final ArrayList<poem_class > arr = new ArrayList<poem_class>();
                       poem_Title_list = (ListView) findViewById(R.id.list_item);
                                 //Log.w("main   ", arr.get(2).poem_title);

                            orignalarray = arr;

                 madapter = new title_adapter(this ,arr);
                 poem_Title_list.setAdapter(madapter);




               //  getSupportLoaderManager().initLoader(1, null, this).forceLoad();
                  poem_Title_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                         final poem_class current_poem = madapter.getItem(i);

                                        String title_to_intent=current_poem.poem_title;
                                        String author_to_intent= current_poem.poem_autor;
                                        int poemId = current_poem.poem_id;

                                    Bundle bundle = new Bundle();
                                    bundle.putString("title",title_to_intent);
                                    bundle.putString("author",author_to_intent);
                                    bundle.putString("poemid",String.valueOf(poemId));



                                    //This sends the title variable to next file

                                       Intent intent = new Intent(Poem_activity.this,Poemactivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });

                 searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                     @Override
                     public boolean onQueryTextSubmit(String query) {
                         return false;
                     }

                     @Override
                     public boolean onQueryTextChange(String newText) {
                         newText=newText.toLowerCase();
                         if(newText!=null && !newText.isEmpty()) {
                             ArrayList<poem_class> poem_list = new ArrayList<poem_class>();
                             for(int i =0;i<arr.size();i++){
                                 if(arr.get(i).poem_autor.toLowerCase().contains(newText) || arr.get(i).poem_title.toLowerCase().contains(newText)){

                                     poem_list.add(arr.get(i));
                                  }
                                 madapter = new title_adapter(Poem_activity.this ,poem_list);
                                 poem_Title_list.setAdapter(madapter);


                             }

                         }

                         return false;
                     }
                 });


    }

    public void onBackPressed() {
        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
        if (searchView.isSearchOpen()) {
            madapter = new title_adapter(Poem_activity.this ,orignalarray);
            poem_Title_list.setAdapter(madapter);
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
                                        // Inflate the menu; this adds items to the action bar if it is present.


                                        getMenuInflater().inflate(R.menu.action_search, menu);
                                        MenuItem item = menu.findItem(R.id.action_search);
        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setMenuItem(item);

                                        return true;
    }



    private class TitleAsynctask extends AsyncTask<URL,Void,List<poem_class>> {

      private ProgressDialog progressDialog;
            @Override
            public List<poem_class> doInBackground(URL... urls) {

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
                       // String poem_id = root.getString("id");
                        thispoem = new poem_class(title,author,i);
                        json.add(thispoem);

//                        ContentValues value = new ContentValues();
//                        value.put(poem_contract.poem_entry.COLUMN_AUTHOR,author);
//                        value.put(poem_contract.poem_entry.COLUMN_TITLE,title);
//                        value.put(poem_contract.poem_entry.POEM_ID,poem_id);
//                        long newRowId = databasewriter.insert(poem_contract.poem_entry.TABLE_NAME,null,value);

                        Log.w("poem_page",title + String.valueOf(i));
                    }


                }
                catch (JSONException e){

                }

                return json;
            }

                    @Override
            protected void onPreExecute() {
                        progressDialog = new ProgressDialog(Poem_activity.this);
                        progressDialog.setMessage("We have over 12k poems to offer");
                        progressDialog.setCancelable(false);
                        progressDialog.show();


                        super.onPreExecute();
            }



            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            protected void onPostExecute(List<poem_class> data) {
                        madapter.clear();


                        if (data != null && !data.isEmpty()){
                            madapter.addAll(data);
                        }
                        progressDialog.dismiss();


            }


    }








}
