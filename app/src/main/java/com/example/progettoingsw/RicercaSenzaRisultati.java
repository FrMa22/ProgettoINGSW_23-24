package com.example.progettoingsw;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

public class RicercaSenzaRisultati extends AppCompatActivity {


    Controller controller;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ImageButton openDrawerButton;
    private ImageButton preferitiButton;
    private ImageButton profiloButton;
    private MaterialButton creaAstaInversa;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ricerca_senza_risultati);

        controller = new Controller();

        preferitiButton = findViewById(R.id.openPreferitiRicercaSenzaRisultati);
        profiloButton = findViewById(R.id.openProfiloButtonRicercaSenzaRisultati);

        drawerLayout = findViewById(R.id.drawer_layoutRicercaSenzaRisultati);
        navigationView = findViewById(R.id.navigation_viewRicercaSenzaRisultati);
        menu = navigationView.getMenu();

        View headerView = navigationView.getHeaderView(0);

        TextView headerText = headerView.findViewById(R.id.textViewHeader);

        openDrawerButton = findViewById(R.id.openDrawerButtonRicercaSenzaRisultati);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        creaAstaInversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.redirectActivity(RicercaSenzaRisultati.this, CreaLaTuaAstaAcquirente.class);
            }
        });

        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        preferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.redirectActivity(RicercaSenzaRisultati.this, PreferitiActivity.class);
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
                    Controller.redirectActivity(RicercaSenzaRisultati.this, SelezioneCategorie.class);
                }else if(id==R.id.nav_esci){
                    Controller.redirectActivity(RicercaSenzaRisultati.this, MainActivity.class);
                }else if (id == R.id.nav_profilo) {
                    Controller.redirectActivity(RicercaSenzaRisultati.this, ProfiloActivity.class);
                }else if (id==R.id.nav_about_us){
                    Controller.redirectActivity(RicercaSenzaRisultati.this,AboutUs.class);
                }else if (id==R.id.nav_crea_asta){
                    Controller.redirectActivity(RicercaSenzaRisultati.this, CreaLaTuaAstaAcquirente.class);
                }
                return true;
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



