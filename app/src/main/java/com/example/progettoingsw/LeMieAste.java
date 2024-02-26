package com.example.progettoingsw;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.navigation.NavigationView;

public class LeMieAste extends GestoreComuniImplementazioni {

    Controller controller;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageButton preferitiButton;
    private ImageButton profiloButton;


    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.le_mie_aste);
        controller = new Controller();



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
