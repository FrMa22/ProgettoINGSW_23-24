package com.example.progettoingsw.gui;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.DAO.LeMieAsteDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.Controller;

import java.util.ArrayList;
import java.util.List;

public class LeMieAste extends GestoreComuniImplementazioni {

    Controller controller;
    private ImageButton preferitiButton;
    private ImageButton profiloButton;
    private AstaAdapter astaAdapter;
    private LeMieAsteDAO lemieAsteDAO;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.le_mie_aste);
        controller = new Controller();
        astaAdapter = new AstaAdapter(this, null);

        email = getIntent().getStringExtra("email");

        //
        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAsteAttive = findViewById(R.id.recyclerViewAsteAttive);
        // Utilizza LinearLayoutManager con orientamento orizzontale per far si che il recycler sia orizzontale, di default è verticale
        LinearLayoutManager linearLayoutManagerAttive = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAsteAttive.setLayoutManager(linearLayoutManagerAttive);

        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, linearLayoutManagerAttive.getOrientation());
        recyclerViewAsteAttive.addItemDecoration(dividerItemDecorationAttive);
        recyclerViewAsteAttive.setAdapter(astaAdapter);


        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAsteNonAttive = findViewById(R.id.recyclerViewAsteNonAttive);
        // Utilizza LinearLayoutManager con orientamento orizzontale per far si che il recycler sia orizzontale, di default è verticale
        LinearLayoutManager linearLayoutManagerNonAttive = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAsteNonAttive.setLayoutManager(linearLayoutManagerNonAttive);

        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationNonAttive = new DividerItemDecoration(this, linearLayoutManagerNonAttive.getOrientation());
        recyclerViewAsteNonAttive.addItemDecoration(dividerItemDecorationNonAttive);
        recyclerViewAsteNonAttive.setAdapter(astaAdapter);







        //

        //Ora inizia codice solo per LeMieAste

        RadioGroup radioGroup = findViewById(R.id.radioGroupLeMieAste);
        RadioButton btnAttivi = findViewById(R.id.bottoneAttive);
        RadioButton btnNonAttivi = findViewById(R.id.bottoneNonAttive);
       // GridLayout gridLayoutAttive = findViewById(R.id.gridLayoutAsteAttive);
       // GridLayout gridLayoutNonAttive = findViewById(R.id.gridLayoutAsteNonAttive);

         lemieAsteDAO = new LeMieAsteDAO(this);
        //di default appena si apre la schermata si è già su aste aperte quindi escono già
        lemieAsteDAO.openConnection();
        lemieAsteDAO.getAsteForEmail(email,"aperta");
        recyclerViewAsteAttive.setVisibility(View.VISIBLE);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup gruppo, int checkedId) {
                if (checkedId == R.id.bottoneAttive) {
                    // Mostra la ScrollView per "Attivi"
                    //gridLayoutAttive.setVisibility(View.VISIBLE);
                    recyclerViewAsteAttive.setVisibility(View.VISIBLE);
                    // Nascondi la ScrollView per "Non Attivi"
                    //gridLayoutNonAttive.setVisibility(View.GONE);
                    recyclerViewAsteNonAttive.setVisibility(View.GONE);

                    lemieAsteDAO.getAsteForEmail(email,"aperta");

                } else if (checkedId == R.id.bottoneNonAttive) {
                    // Mostra la ScrollView per "Non Attivi"
                    //gridLayoutNonAttive.setVisibility(View.VISIBLE);
                    recyclerViewAsteNonAttive.setVisibility(View.VISIBLE);
                    // Nascondi la ScrollView per "Attivi"
                    //gridLayoutAttive.setVisibility(View.GONE);
                    recyclerViewAsteAttive.setVisibility(View.GONE);

                    lemieAsteDAO.getAsteForEmail(email,"chiusa");

                }
            }
        });



    }



    public void updateAste(ArrayList<Object> aste, String cond) {


        //
        if(cond.equals("aperta")) {
            if (aste != null) {
                // Aggiorna l'interfaccia utente con i nomi delle aste attive
                RecyclerView recyclerViewAsteAttive = findViewById(R.id.recyclerViewAsteAttive);
                AstaAdapter astaAdapterAttive = new AstaAdapter(this, aste);
                recyclerViewAsteAttive.setAdapter(astaAdapterAttive);
                recyclerViewAsteAttive.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



                // Aggiungi stampe nel log per verificare che i dati siano correttamente passati

            } else {
                // Nessun nome asta trovato per l'email specificata
            }
        }
        //


        if(cond.equals("chiusa")) {
            if (aste != null) {

                RecyclerView recyclerViewAsteNonAttive = findViewById(R.id.recyclerViewAsteNonAttive);
                AstaAdapter astaAdapterAttive = new AstaAdapter(this, aste);
                recyclerViewAsteNonAttive.setAdapter(astaAdapterAttive);
                recyclerViewAsteNonAttive.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


            } else {
                // Nessun nome asta trovato per l'email specificata
            }
        }

    }//fine metodo

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Chiudi la connessione quando l'Activity viene distrutta
        lemieAsteDAO.closeConnection();
    }

}
