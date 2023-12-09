package com.example.progettoingsw;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_campi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfiloActivity extends AppCompatActivity {
    private Controller controller;
    boolean modificaCampi = false;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ImageButton openDrawerButton;
    private GridView gridView;
    private CustomAdapter_gridview_profilo_campi adapterSocial;

    private ImageButton bottone_modifica_nome;
    private TextView textview_campo_nome;
    private TextView textview_nome;
    private String testo_textview_campo_nome;
    private String testo_textview_nome;

    private ImageButton bottone_modifica_cognome;
    private TextView textview_campo_cognome;
    private TextView textview_cognome;
    private String testo_textview_campo_cognome;
    private String testo_textview_cognome;

    private ImageButton bottone_modifica_email;
    private TextView textview_campo_email;
    private TextView textview_email;
    private String testo_textview_campo_email;
    private String testo_textview_email;

    private ImageButton bottone_modifica_sitoweb;
    private TextView textview_campo_sitoweb;
    private TextView textview_sitoweb;
    private String testo_textview_campo_sitoweb;
    private String testo_textview_sitoweb;

    private ImageButton bottone_modifica_paese;
    private TextView textview_campo_paese;
    private TextView textview_paese;
    private String testo_textview_campo_paese;
    private String testo_textview_paese;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        controller = new Controller();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView headerText = headerView.findViewById(R.id.textViewHeader);
        headerText.setText("Profilo");

        openDrawerButton = findViewById(R.id.openDrawerButton);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

                if (id == R.id.nav_home) {
                    controller.redirectActivity(ProfiloActivity.this, HomeUtente.class);
                } else if (id == R.id.nav_profilo) {
                    if (drawerLayout.isOpen()) {
                        drawerLayout.closeDrawer(navigationView);
                    }
                } else if (id == R.id.nav_cliccacategorie) {
                    controller.redirectActivity(ProfiloActivity.this, SelezioneCategorie.class);
                } else if (id == R.id.nav_about_us) {
                    controller.redirectActivity(ProfiloActivity.this, AboutUs.class);
                } else if (id == R.id.nav_esci) {
                    controller.redirectActivity(ProfiloActivity.this, MainActivity.class);
                }

                return true;
            }
        });


        gridView = findViewById(R.id.gridview_social_activity_profilo);
        adapterSocial = new CustomAdapter_gridview_profilo_campi(this);
        gridView.setAdapter(adapterSocial);
        String[] socialArray = getResources().getStringArray(R.array.array_elenco_social_nomi_profilo);
        String[] linksArray = getResources().getStringArray(R.array.array_elenco_social_nomi_profilo);
        ArrayList<String> socialArrayList = new ArrayList<>(Arrays.asList(socialArray));
        ArrayList<String> linksArrayList = new ArrayList<>(Arrays.asList(linksArray));
        adapterSocial.setData(socialArrayList, linksArrayList);

        textview_campo_nome = findViewById(R.id.textview_campo_nome);
        testo_textview_campo_nome = textview_campo_nome.getText().toString();
        textview_nome = findViewById(R.id.textview_nome);
        testo_textview_nome = textview_nome.getText().toString();



        bottone_modifica_nome = findViewById(R.id.button_modifica_nome);
        bottone_modifica_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfiloActivity.this, PopUpModificaCampoProfilo.class);
                intent.putExtra("testoTextViewCampoTitolo", testo_textview_campo_nome);
                intent.putExtra("testoTextViewNomeValore", testo_textview_nome);
                startActivity(intent);
            }
        });

        textview_campo_cognome = findViewById(R.id.textview_campo_cognome);
        testo_textview_campo_cognome = textview_campo_cognome.getText().toString();
        textview_cognome = findViewById(R.id.textview_cognome);
        testo_textview_cognome = textview_cognome.getText().toString();


        bottone_modifica_cognome = findViewById(R.id.button_modifica_cognome);
        bottone_modifica_cognome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfiloActivity.this, PopUpModificaCampoProfilo.class);
                intent.putExtra("testoTextViewCampoTitolo", testo_textview_campo_cognome);
                intent.putExtra("testoTextViewNomeValore", testo_textview_cognome);
                startActivity(intent);
            }
        });


        textview_campo_email = findViewById(R.id.textview_campo_email);
        testo_textview_campo_email = textview_campo_email.getText().toString();
        textview_email = findViewById(R.id.textview_email);
        testo_textview_email = textview_email.getText().toString();


        /*bottone_modifica_email = findViewById(R.id.button_modifica_email);
        bottone_modifica_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfiloActivity.this, PopUpModificaCampoProfilo.class);
                intent.putExtra("testoTextViewCampoTitolo", testo_textview_campo_email);
                intent.putExtra("testoTextViewNomeValore", testo_textview_email);
                startActivity(intent);
            }
        });*/

        textview_campo_sitoweb = findViewById(R.id.textview_campo_sitoweb);
        testo_textview_campo_sitoweb = textview_campo_sitoweb.getText().toString();
        textview_sitoweb = findViewById(R.id.textview_sitoweb);
        testo_textview_sitoweb = textview_sitoweb.getText().toString();


        bottone_modifica_sitoweb = findViewById(R.id.button_modifica_sitoweb);
        bottone_modifica_sitoweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfiloActivity.this, PopUpModificaCampoProfilo.class);
                intent.putExtra("testoTextViewCampoTitolo", testo_textview_campo_sitoweb);
                intent.putExtra("testoTextViewNomeValore", testo_textview_sitoweb);
                startActivity(intent);
            }
        });

        textview_campo_paese = findViewById(R.id.textview_campo_paese);
        testo_textview_campo_paese = textview_campo_paese.getText().toString();
        textview_paese = findViewById(R.id.textview_paese);
        testo_textview_paese = textview_paese.getText().toString();


        bottone_modifica_paese = findViewById(R.id.button_modifica_paese);
        bottone_modifica_paese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfiloActivity.this, PopUpModificaCampoProfilo.class);
                intent.putExtra("testoTextViewCampoTitolo", testo_textview_campo_paese);
                intent.putExtra("testoTextViewNomeValore", testo_textview_paese);
                startActivity(intent);
            }
        });



        String valoreDaModificare = getIntent().getStringExtra("valoreDaModificare");
        String valoreModificato = getIntent().getStringExtra("valoreModificato");

        if (valoreDaModificare != null) {
            switch (valoreDaModificare) {
                case "Nome":
                    textview_nome.setText(valoreModificato);
                    break;
                case "Cognome":
                    textview_cognome.setText(valoreModificato);
                    break;
                /*case "Email":
                    textview_email.setText(valoreModificato);
                    break;*/
                case "Sito web":
                    textview_sitoweb.setText(valoreModificato);
                    break;
                case "Paese":
                    textview_paese.setText(valoreModificato);
                    break;
            }
        }



        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_profile);
        // Imposta il listener per il BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            // Gestisci la selezione dell'elemento del menu

            if(item.getItemId()==R.id.action_categories) {
                Controller.redirectActivity(ProfiloActivity.this, SelezioneCategorie.class);
                return true;
            }
            else if(item.getItemId()==R.id.action_crea_asta) {
                Controller.redirectActivity(ProfiloActivity.this, CreaLaTuaAstaAcquirente.class);
                return true;
            }
            else if(item.getItemId()==R.id.action_search){
                //da creare
                return true;
            }
            else if(item.getItemId()==R.id.action_home) {
                Controller.redirectActivity(ProfiloActivity.this, HomeUtente.class);
                return true;
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

}
