package com.example.shivani.miete;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivani.miete.utils.PrefsHelper;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    Toolbar toolbar;
    CategoryFragment categoryFragment;
    FeedbackFragment feedbackFragment;
    YourOrdersFragment yourOrdersFragment;
    YourProfileFragment yourProfileFragment;
    WesternFragment westernFragment;
    EthnicFragment ethnicFragment;
    FootFragment footFragment;
    SportsFragment sportsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        categoryFragment = new CategoryFragment();
        feedbackFragment = new FeedbackFragment();
        yourOrdersFragment = new YourOrdersFragment();
        yourProfileFragment = new YourProfileFragment();
        westernFragment = new WesternFragment();
        ethnicFragment = new EthnicFragment();
        footFragment = new FootFragment();
        sportsFragment = new SportsFragment();



       /* navHeadMiete = (TextView) findViewById(R.id.nav_head_miete);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/alex.ttf");
        navHeadMiete.setTypeface(custom_font);*/

        String token =(String) PrefsHelper.getPrefsHelper(NavigationActivity.this).getPref(PrefsHelper.PREF_TOKEN,"Cool");
        Log.e("tok ",token);
        if(token.equals("Cool"))
        {
            hideItem(R.id.logout);
            showItem(R.id.login);
        }
        else
        {

            showItem(R.id.logout);
            hideItem(R.id.login);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, categoryFragment)
                .commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token =(String) PrefsHelper.getPrefsHelper(NavigationActivity.this).getPref(PrefsHelper.PREF_TOKEN,"Cool");
                Log.e("tok ",token);
                if(token.equals("Cool"))
                {
                    Toast.makeText(NavigationActivity.this,"Login to continue",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(NavigationActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent= new Intent(NavigationActivity.this,Upload.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void hideItem(int id)
    {
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(id).setVisible(false);
    }
    private void showItem(int id)
    {
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        Menu nav_menu= navigationView.getMenu();
        nav_menu.findItem(id).setVisible(true);
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
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.aboutUs) {
            Intent intent = new Intent(NavigationActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.western) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag_container, westernFragment).addToBackStack("categoryFragment")
                    .commit();

        } else if (id == R.id.ethnic) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag_container, ethnicFragment).addToBackStack("categoryFragment")
                    .commit();

        } else if (id == R.id.foot) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag_container, footFragment).addToBackStack("categoryFragment")
                    .commit();

        } else if (id == R.id.sports) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag_container, sportsFragment).addToBackStack("categoryFragment")
                    .commit();

        } else if (id == R.id.feedback) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag_container,feedbackFragment ).addToBackStack("categoryFragment")
                    .commit();
        }else if (id == R.id.login) {
            Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
            startActivity(intent);
        }else if(id == R.id.logout){
            PrefsHelper.getPrefsHelper(NavigationActivity.this).delete(PrefsHelper.PREF_TOKEN);
            Toast.makeText(NavigationActivity.this,"You are Successfully Logged Out",Toast.LENGTH_SHORT).show();
            hideItem(R.id.logout);
            showItem(R.id.login);
        }
        else if (id == R.id.home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag_container,categoryFragment ).addToBackStack("categoryFragment")
                    .commit();
        }else if (id == R.id.yourorders) {
            String token =(String) PrefsHelper.getPrefsHelper(NavigationActivity.this).getPref(PrefsHelper.PREF_TOKEN,"Cool");
            Log.e("tok ",token);
            if(token.equals("Cool"))
            {   Toast.makeText(NavigationActivity.this,"Login to continue",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else{getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag_container,yourOrdersFragment ).addToBackStack("categoryFragment")
                    .commit();}
        }else if (id == R.id.yourprofile) {
            String token =(String) PrefsHelper.getPrefsHelper(NavigationActivity.this).getPref(PrefsHelper.PREF_TOKEN,"Cool");
            Log.e("tok ",token);
            if(token.equals("Cool"))
            {   Toast.makeText(NavigationActivity.this,"Login to continue",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_container, yourProfileFragment).addToBackStack("categoryFragment")
                        .commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
