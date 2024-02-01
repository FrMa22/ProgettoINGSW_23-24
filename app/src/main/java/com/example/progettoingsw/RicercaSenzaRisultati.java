package com.example.progettoingsw;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.navigation.NavigationView;

public class RicercaSenzaRisultati extends GestoreComuniImplementazioni {


    Controller controller;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageButton preferitiButton;
    private ImageButton profiloButton;
    private Button creaAstaInversa;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ricerca_senza_risultati);
        controller = new Controller();

        preferitiButton = findViewById(R.id.openPreferitiRicercaSenzaRisultati);
        profiloButton = findViewById(R.id.openProfiloButtonRicercaSenzaRisultati);


            creaAstaInversa = findViewById(R.id.bottoneCreaAstaInversa);
            creaAstaInversa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Controller.redirectActivity(RicercaSenzaRisultati.this, FragmentCreaLaTuaAstaAcquirente.class);
                }
            });



        try {
            preferitiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Controller.redirectActivity(RicercaSenzaRisultati.this, PreferitiActivity.class);
                }
            });
        } catch (NullPointerException e) {
            Log.e("Tag", "Preferiti", e);
        }



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



