package com.example.progettoingsw.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.progettoingsw.DAO.AstePerCategorieDAO;
import com.example.progettoingsw.DAO.AstePreferiteDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.Controller;

import java.util.ArrayList;

public class SchermataAstePerCategoria extends AppCompatActivity {
     TextView textViewTtitoloCategorie;
     Controller controller;
     Intent intent;
     String email;
     String tipoUtente;
      ImageButton backBottone;
     AstaAdapter astaAdapter;
    AstePerCategorieDAO astePerCategorieDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_aste_per_categoria);
        intent= getIntent();
        String categoriaSelezionata = intent.getStringExtra("categoria_selezionata");
        textViewTtitoloCategorie = findViewById(R.id.titoloRicercaCategoria);
        textViewTtitoloCategorie.setText(categoriaSelezionata);

        controller = new Controller();
        email =intent.getStringExtra("email");
        tipoUtente =intent.getStringExtra("tipoUtente");
        backBottone = findViewById(R.id.backButtonCategorieRicerca);
        astaAdapter = new AstaAdapter(this, null);

        //
        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAstePreferite = findViewById(R.id.recycler_view_aste_per_categoria);
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2 ,RecyclerView.VERTICAL,false);
        recyclerViewAstePreferite.setLayoutManager(gridLayoutManagerAttive);
        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, gridLayoutManagerAttive.getOrientation());
        recyclerViewAstePreferite.addItemDecoration(dividerItemDecorationAttive);

        astePerCategorieDAO = new AstePerCategorieDAO(this);
        //di default appena si apre la schermata si è già su aste aperte quindi escono già
        astePerCategorieDAO.openConnection();
        astePerCategorieDAO.getAsteForCategoriaUtente(categoriaSelezionata,tipoUtente);
        recyclerViewAstePreferite.setVisibility(View.VISIBLE);


        backBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

    }

    public void asteCategorie(ArrayList<Object> aste) {



        if (aste != null) {
            // Questo sovrascrive -> no
//                RecyclerView recyclerViewAsteAttive = findViewById(R.id.recyclerAstePreferiti);
//                AstaAdapter astaAdapterAttive = new AstaAdapter(this, aste);
//                recyclerViewAsteAttive.setAdapter(astaAdapterAttive);
//                recyclerViewAsteAttive.setLayoutManager(new GridLayoutManager(this,2, RecyclerView.VERTICAL,false));
            astaAdapter.setAste(aste);


            // Aggiungi stampe nel log per verificare che i dati siano correttamente passati

        } else {
            // Nessun nome asta trovato per l'email specificata
        }
        //


    }



}