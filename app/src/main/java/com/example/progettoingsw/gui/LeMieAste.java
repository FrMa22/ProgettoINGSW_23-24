package com.example.progettoingsw.gui;

import static java.security.AccessController.getContext;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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

import com.example.progettoingsw.DAO.LeMieAsteDAO;
import com.example.progettoingsw.DAO.VenditoreFragmentProfiloDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_social;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

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

        String email=getIntent().getStringExtra("email");
        // Inizializza il DAO e recupera i dati del venditore
        LeMieAsteDAO lemieAsteDAO = new LeMieAsteDAO(this);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup gruppo, int checkedId) {
                if (checkedId == R.id.bottoneAttive) {
                    // Mostra la ScrollView per "Attivi"
                    gridLayoutAttive.setVisibility(View.VISIBLE);
                    // Nascondi la ScrollView per "Non Attivi"
                    gridLayoutNonAttive.setVisibility(View.GONE);
                    lemieAsteDAO.openConnection();
                    lemieAsteDAO.getAsteForEmail(email,"aperta");
                    lemieAsteDAO.closeConnection();
                } else if (checkedId == R.id.bottoneNonAttive) {
                    // Mostra la ScrollView per "Non Attivi"
                    gridLayoutNonAttive.setVisibility(View.VISIBLE);
                    // Nascondi la ScrollView per "Attivi"
                    gridLayoutAttive.setVisibility(View.GONE);
                    lemieAsteDAO.openConnection();
                    lemieAsteDAO.getAsteForEmail(email,"chiusa");
                    lemieAsteDAO.closeConnection();
                }
            }
        });



    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

    /*
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();

    }
*/
    /*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);

    }
*/

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }
*/

    public void updateAsteNames(List<String> asteNames,String cond) {


        //
        if(cond.equals("aperta")) {
            if (asteNames != null) {
                // Aggiorna l'interfaccia utente con i nomi delle aste attive
                GridLayout gridLayoutAttive = findViewById(R.id.gridLayoutAsteAttive);

                // Rimuovi tutte le viste presenti nel GridLayout
                gridLayoutAttive.removeAllViews();

                for (String nomeAsta : asteNames) {
                    // Crea una nuova TextView per ogni nome asta
                    TextView textView = new TextView(this); // Utilizza 'this' come contesto
                    textView.setText(nomeAsta);
                    textView.setTextSize(16);
                    // Aggiungi la TextView al GridLayout
                    gridLayoutAttive.addView(textView);
                }

                // Aggiungi stampe nel log per verificare che i dati siano correttamente passati
                for (String nomeAsta : asteNames) {
                    Log.d("LeMieAste", "Nome Asta: " + nomeAsta);
                }
            } else {
                // Nessun nome asta trovato per l'email specificata
            }
        }
        //


        if(cond.equals("chiusa")) {
            if (asteNames != null) {
                // Aggiorna l'interfaccia utente con i nomi delle aste attive
                GridLayout gridLayoutNonAttive = findViewById(R.id.gridLayoutAsteNonAttive);

                // Rimuovi tutte le viste presenti nel GridLayout
                gridLayoutNonAttive.removeAllViews();

                for (String nomeAsta : asteNames) {
                    // Crea una nuova TextView per ogni nome asta
                    TextView textView = new TextView(this); // Utilizza 'this' come contesto
                    textView.setText(nomeAsta);
                    textView.setTextSize(16);
                    // Aggiungi la TextView al GridLayout
                    gridLayoutNonAttive.addView(textView);
                }

                // Aggiungi stampe nel log per verificare che i dati siano correttamente passati
                for (String nomeAsta : asteNames) {
                    Log.d("LeMieAste", "Nome Asta: " + nomeAsta);
                }
            } else {
                // Nessun nome asta trovato per l'email specificata
            }
        }

    }//fine metodo


}
