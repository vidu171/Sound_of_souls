package com.example.ashura.soundofsoul;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by vidu on 26/2/17.
 */

public class Poem_activity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();
    public static String POEM_TITLE = //"http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=3&limit=10";

            "http://poetrydb.org/title";
    public static String title_to_intent = new String();

    public title_adapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poem_activity);
//        if(!haveconnection()){
//
//            AlertDialog alertDialog = new AlertDialog.Builder(Poem_activity.this).create(); //Use context
//            alertDialog.setTitle("Warning");
//            alertDialog.setMessage("You are currently in a battle");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
//        }

        TitleAsynctask task = new TitleAsynctask();
        URL url = Query_utils.createurl(POEM_TITLE);
        task.execute(url);


        ArrayList<view > arr = new ArrayList<view>();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view1);
//        navigationView.setNavigationItemSelectedListener(Poem_activity.this);

       ArrayList<view> arran = arr;
         //arran = utils.arrydivider(arr);


        final ListView poem_Title_list = (ListView) findViewById(R.id.list_item);
        madapter = new title_adapter(this ,arran);
        poem_Title_list.setAdapter(madapter);

        poem_Title_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view title = madapter.getItem(i);
                title_to_intent=title.getTitle_view();
                Bundle bundle = new Bundle();
                bundle.putString("title",title_to_intent);

                Intent intent = new Intent(Poem_activity.this,Poemactivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    //@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            //Intent i = new Intent(MainActivity.this, Poem_activity.class);
          //  startActivity(i);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private class TitleAsynctask extends AsyncTask<URL,Void,List<view>> {


        private ProgressDialog progressDialog;
        @Override
        public List<view> doInBackground(URL... urls){

            URL url = Query_utils.createurl(POEM_TITLE);
            String json = "";
            Log.d(LOG_TAG,"this worked");
            {
                try {
                    if(haveconnection()) {
                        json = Query_utils.makehttprequest(Poem_activity.this, url);
                        Log.d(LOG_TAG, "make Httprequest works");
                    }
                    else {
                                    Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.");
//            AlertDialog alertDialog = new AlertDialog.Builder(Poem_activity.this).create(); //Use context
//            alertDialog.setTitle("Warning");
//            alertDialog.setMessage("You are currently in a battle");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
                    }
                } catch (IOException e) {

                }
            }
            List<view> title_view = Query_utils.extracttitlefromjson(json);
            Random rand = new Random();
            ArrayList<view> arran = new ArrayList<>();
            int  n = rand.nextInt(2000);

            for(int i =0 ; i<10;i++){
                arran.add(title_view.get(n+i));
            }
            return arran;

        }
                @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Poem_activity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();


            super.onPreExecute();
        }
        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(List<view> data) {
            madapter.clear();

//            if(!haveconnection()){
//
//                AlertDialog alertDialog = new AlertDialog.Builder(Poem_activity.this).create(); //Use context
//                alertDialog.setTitle("Warning");
//                alertDialog.setMessage("You are currently in a battle");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
//            }

            if (data != null && !data.isEmpty()){
                madapter.addAll(data);
            }
            progressDialog.dismiss();

        }


    }
    public  static ArrayList<view> deivedArray ;
    private  boolean haveconnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) Poem_activity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!= null && networkInfo.isConnected();


    }

    public static ArrayList<view> arrydivider (ArrayList<view> completearray ){


        return deivedArray;

    }
}
