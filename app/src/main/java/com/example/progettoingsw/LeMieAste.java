package com.example.progettoingsw;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

public class LeMieAste extends AppCompatActivity{

    Controller controller;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ImageButton openDrawerButton;
    private ImageButton preferitiButton;
    private ImageButton profiloButton;


    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.le_mie_aste);

        controller = new Controller();

        preferitiButton = findViewById(R.id.openPreferiti);
        profiloButton = findViewById(R.id.openProfiloButton);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menu = navigationView.getMenu();

        View headerView = navigationView.getHeaderView(0);

        TextView headerText = headerView.findViewById(R.id.textViewHeader);

        openDrawerButton = findViewById(R.id.openDrawerButton);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        preferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.redirectActivity(LeMieAste.this, PreferitiActivity.class);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_home) {
                    if (drawerLayout.isDrawerOpen(navigationView)) {
                        drawerLayout.closeDrawer(navigationView);
                    }
                } else if(id==R.id.nav_cliccacategorie){
                    Controller.redirectActivity(LeMieAste.this, SelezioneCategorie.class);
                }else if(id==R.id.nav_esci){
                    Controller.redirectActivity(LeMieAste.this, MainActivity.class);
                }else if (id == R.id.nav_profilo) {
                    Controller.redirectActivity(LeMieAste.this, ProfiloActivity.class);
                }else if (id==R.id.nav_about_us){
                    Controller.redirectActivity(LeMieAste.this,AboutUs.class);
                }else if (id==R.id.nav_crea_asta){
                    Controller.redirectActivity(LeMieAste.this, CreaLaTuaAstaVenditore.class);
                }
                return true;
            }
        });





        //Ora inizia codice solo per LeMieAste

        RadioGroup radioGroup = findViewById(R.id.radioGroupLeMieAste);
        RadioButton btnAttivi = findViewById(R.id.bottoneAttive);
        RadioButton btnNonAttivi = findViewById(R.id.bottoneNonAttive);
        GridLayout gridLayoutAttive = findViewById(R.id.gridLayoutAsteAttive);
        GridLayout gridLayoutNonAttive = findViewById(R.id.gridLayoutAsteNonAttive);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup gruppo, int checkedId) {
                if (checkedId == R.id.bottoneAttive) {
                    // Mostra la ScrollView per "Attivi"
                    gridLayoutAttive.setVisibility(View.VISIBLE);
                    // Nascondi la ScrollView per "Non Attivi"
                    gridLayoutNonAttive.setVisibility(View.GONE);
                } else if (checkedId == R.id.bottoneNonAttive) {
                    // Mostra la ScrollView per "Non Attivi"
                    gridLayoutNonAttive.setVisibility(View.VISIBLE);
                    // Nascondi la ScrollView per "Attivi"
                    gridLayoutAttive.setVisibility(View.GONE);
                }
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }




}
