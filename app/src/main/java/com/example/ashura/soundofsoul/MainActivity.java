package com.example.ashura.soundofsoul;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

                                 Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                                setSupportActionBar(toolbar);
                                TextView t = (TextView) findViewById(R.id.poem_title_text);
                                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/main.ttf");
                                t.setTypeface(font);

                                t.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                                if(!haveconnection()){
//
//                                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Use context
//                                    alertDialog.setTitle("Can't process");
//                                    alertDialog.setMessage("You are currently offline");
//                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int which) {
//
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                    alertDialog.show();
//                                }
//                                        else {
//                                                    Intent i = new Intent(MainActivity.this, Poem_activity.class);
//                                                    startActivity(i);
//                                                }
                                        Intent i = new Intent(MainActivity.this, poem_page.class);
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
                    // Handle navigation view item clicks here.
                    int id = item.getItemId();

                    if (id == R.id.nav_camera) {
                        // Handle the camera action
                        Intent i = new Intent(MainActivity.this, Poem_activity.class);
                        startActivity(i);

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



    private  boolean haveconnection(){
                    ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this
                            .getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    return networkInfo!= null && networkInfo.isConnected();
    }
}
