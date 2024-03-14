package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.DAO.AstePreferiteDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;

import java.util.ArrayList;

public class PreferitiActivity extends GestoreComuniImplementazioni {
    Controller controller;
    private ImageButton backBottone;
    Intent intent;
    private String email;
    private String tipoUtente;
    private AstaAdapter astaAdapter;
    private AstePreferiteDAO AstePreferiteDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferiti);
        controller = new Controller();
         intent= getIntent();
        email =intent.getStringExtra("email");
        tipoUtente =intent.getStringExtra("tipoUtente");
        backBottone = findViewById(R.id.backButton);
        astaAdapter = new AstaAdapter(this, null);

        //
        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAsteAttive = findViewById(R.id.recyclerAstePreferiti);
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2 ,RecyclerView.VERTICAL,false);
        recyclerViewAsteAttive.setLayoutManager(gridLayoutManagerAttive);
        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, gridLayoutManagerAttive.getOrientation());
        recyclerViewAsteAttive.addItemDecoration(dividerItemDecorationAttive);
        recyclerViewAsteAttive.setAdapter(astaAdapter);

        AstePreferiteDAO = new AstePreferiteDAO(this);
        //di default appena si apre la schermata si è già su aste aperte quindi escono già
        AstePreferiteDAO.openConnection();
        AstePreferiteDAO.getAsteForEmailUtente(email,tipoUtente);
        recyclerViewAsteAttive.setVisibility(View.VISIBLE);


        backBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });


    }

    public void astePreferite(ArrayList<Object> aste) {



            if (aste != null) {
                // Aggiorna l'interfaccia utente con i nomi delle aste attive
                RecyclerView recyclerViewAsteAttive = findViewById(R.id.recyclerAstePreferiti);
                AstaAdapter astaAdapterAttive = new AstaAdapter(this, aste);
                recyclerViewAsteAttive.setAdapter(astaAdapterAttive);
                recyclerViewAsteAttive.setLayoutManager(new GridLayoutManager(this,2, RecyclerView.VERTICAL,false));



                // Aggiungi stampe nel log per verificare che i dati siano correttamente passati

            } else {
                // Nessun nome asta trovato per l'email specificata
            }
        //


    }

}