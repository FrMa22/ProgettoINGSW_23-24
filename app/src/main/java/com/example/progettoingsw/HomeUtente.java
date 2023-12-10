package com.example.progettoingsw;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeUtente extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ImageButton openDrawerButton;
    private ImageButton preferitiButton;
    private ImageButton profiloButton;
    private EditText ricerca;

    private Menu menu;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_utente);

        preferitiButton = findViewById(R.id.openPreferiti);
//        profiloButton = findViewById(R.id.openProfiloButton);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menu = navigationView.getMenu();

        View headerView = navigationView.getHeaderView(0);

        TextView headerText = headerView.findViewById(R.id.textViewHeader);

        openDrawerButton = findViewById(R.id.openDrawerButton);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        ricerca = findViewById(R.id.edittext_ricerca);
        ricerca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    String inputText = ricerca.getText().toString();

                    if (!inputText.isEmpty()) {

                        Intent intent = new Intent(HomeUtente.this, RicercaAstaActivity.class);
                        intent.putExtra("valoreRicercato", inputText);
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
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
                Controller.redirectActivity(HomeUtente.this, PreferitiActivity.class);
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
                    Controller.redirectActivity(HomeUtente.this, SelezioneCategorie.class);
                }else if(id==R.id.nav_esci){
                    Controller.redirectActivity(HomeUtente.this, MainActivity.class);
                }else if (id == R.id.nav_profilo) {
                    Controller.redirectActivity(HomeUtente.this, ProfiloActivity.class);
                }else if (id==R.id.nav_about_us){
                    Controller.redirectActivity(HomeUtente.this,AboutUs.class);
                }else if (id==R.id.nav_crea_asta){
                    Controller.redirectActivity(HomeUtente.this, CreaLaTuaAstaVenditore.class);
                }else if (id==R.id.nav_le_mie_aste){
                    Controller.redirectActivity(HomeUtente.this, LeMieAste.class);
                }
                return true;
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_home);
        // Imposta il listener per il BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            // Gestisci la selezione dell'elemento del menu

                if(item.getItemId()==R.id.action_categories) {
                    Controller.redirectActivity(HomeUtente.this, SelezioneCategorie.class);
                }
                else if(item.getItemId()==R.id.action_crea_asta) {
                    Controller.redirectActivity(HomeUtente.this, CreaLaTuaAstaAcquirente.class);
                }
                else if(item.getItemId()==R.id.action_search){
                    //da creare
                }
                else if(item.getItemId()==R.id.action_profile) {
                    Controller.redirectActivity(HomeUtente.this, ProfiloActivity.class);
                }



            return false;
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

