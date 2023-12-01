package com.example.progettoingsw;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class RicercaAstaActivity extends AppCompatActivity {
    private EditText edittext_ricerca;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ImageButton openDrawerButton;
    private Menu menu;
    private ImageButton button_filtro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_asta);

        edittext_ricerca = findViewById(R.id.edittext_ricerca);

        Intent intent = getIntent();
        String valoreRicercato = intent.getStringExtra("valoreRicercato");
        edittext_ricerca.setText(valoreRicercato);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menu = navigationView.getMenu();

        View headerView = navigationView.getHeaderView(0);
        openDrawerButton = findViewById(R.id.openDrawerButton);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        button_filtro = findViewById(R.id.button_filtro);
        button_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.redirectActivity(RicercaAstaActivity.this, PopUpFiltroRicerca.class );
            }
        });

    }
}