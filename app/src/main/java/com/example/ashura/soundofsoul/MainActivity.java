package com.example.ashura.soundofsoul;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
                                super.onCreate(savedInstanceState);
                                setContentView(R.layout.activity_main);
      //  getLoaderManager().initLoader(0, null, this);


                            createsharedpref();


                                 Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                                setSupportActionBar(toolbar);
                                ImageView t = (ImageView) findViewById(R.id.poem_title_text);

        ImageView quote_image = (ImageView) findViewById(R.id.quote_title_text);
                                t.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//
                                        Intent i = new Intent(MainActivity.this, poem_page.class);
                                                    startActivity(i);
                                    }
                                });
                                quote_image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(MainActivity.this,quotes_page.class);
                                        startActivity(i);
                                    }
                                });





                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                                drawer.setDrawerListener(toggle);
                                toggle.syncState();

                                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                                navigationView.setNavigationItemSelectedListener(this);
    }



public void createsharedpref()
{
    SharedPreferences sharedpref = getSharedPreferences("saved", MODE_PRIVATE);

    if(!sharedpref.contains("initialized")){
        SharedPreferences.Editor ed;
        ed = sharedpref.edit();
        ed.putBoolean("initialized",true);

                ed.putString("_id","##");
        Log.w("pp","not again!");
        ed.apply();
    }


}



    @Override
    public void onBackPressed() {
                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                if (drawer.isDrawerOpen(GravityCompat.START)) {
                                    drawer.closeDrawer(GravityCompat.START);
                                } else {
                                    super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
                            // Inflate the menu; this adds items to the action bar if it is present.
                           // getMenuInflater().inflate(R.menu.main, menu);
                            return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                            // Handle action bar item clicks here. The action bar will
                            // automatically handle clicks on the Home/Up button, so long
                            // as you specify a parent activity in AndroidManifest.xml.
                            int id = item.getItemId();

                            //noinspection SimplifiableIfStatement
                            if (id == R.id.report_problem) {



                                return true;
                            }

                            return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

                    if (id == R.id.rate_app) {
                        // Handle the camera action
                        Intent i = new Intent(MainActivity.this, Poem_activity.class);
        Uri uri = Uri.parse("market://details?id=" + getBaseContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName())));
        }
    }

                   else if (id == R.id.report_problem) {
                    Intent i = new Intent();
                        i.setAction(Intent.ACTION_SEND);
                        i.setType("text/html");
                        i.putExtra(Intent.EXTRA_EMAIL,"illumy.inc@gmail.com");
                        startActivity(Intent.createChooser(i,"Send Email"));


                   }
                     else if (id == R.id.nav_share) {

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sound of Soul");

                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName());
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));

                   }
                   //else if (id == R.id.nav_manage) {
//
//                    } else if (id == R.id.nav_share) {
//
//                    } else if (id == R.id.nav_send) {
//
//                    }

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
    }



    private  boolean haveconnection(){
                    ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this
                            .getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    return networkInfo!= null && networkInfo.isConnected();
    }
}
