package com.example.progettoingsw.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.progettoingsw.DAO.AstePerCategorieDAO;
import com.example.progettoingsw.DAO.AstePreferiteDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaInversaItem;
import com.example.progettoingsw.model.AstaRibassoItem;

import java.util.ArrayList;

public class SchermataAstePerCategoria extends AppCompatActivity {
     TextView textViewTtitoloCategorie;
     Controller controller;
     Intent intent;
     String email;
     String tipoUtente;
      ImageButton backBottone;
     AstaAdapter astaAdapter;
     private ProgressBar progress_bar_schermata_aste_per_categoria;
     private TextView text_view_nessuna_asta_ricercata_per_categoria;
    AstePerCategorieDAO astePerCategorieDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_aste_per_categoria);
        intent= getIntent();
        String categoriaSelezionata = intent.getStringExtra("categoria_selezionata");
        textViewTtitoloCategorie = findViewById(R.id.titoloRicercaCategoria);
        textViewTtitoloCategorie.setText(categoriaSelezionata);
        progress_bar_schermata_aste_per_categoria = findViewById(R.id.progress_bar_schermata_aste_per_categoria);
        progress_bar_schermata_aste_per_categoria.setVisibility(View.VISIBLE);
        text_view_nessuna_asta_ricercata_per_categoria = findViewById(R.id.text_view_nessuna_asta_ricercata_per_categoria);

        controller = new Controller();
        email =intent.getStringExtra("email");
        tipoUtente =intent.getStringExtra("tipoUtente");
        backBottone = findViewById(R.id.backButtonCategorieRicerca);
        astaAdapter = new AstaAdapter(this, null);

        //
        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAstePerCategoria = findViewById(R.id.recycler_view_aste_per_categoria);
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2 ,RecyclerView.VERTICAL,false);
        recyclerViewAstePerCategoria.setLayoutManager(gridLayoutManagerAttive);
        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, gridLayoutManagerAttive.getOrientation());
        recyclerViewAstePerCategoria.addItemDecoration(dividerItemDecorationAttive);
        recyclerViewAstePerCategoria.setAdapter(astaAdapter);
        astaAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerViewAstePerCategoria.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapter.getItem(position);

                // Esegui le azioni desiderate con l'oggetto Asta
                if (asta instanceof AstaIngleseItem) {
                    int id = ((AstaIngleseItem) asta).getId();
                    Log.d("Asta inglese", "id è " + id);
                    Intent intent = new Intent(SchermataAstePerCategoria.this, SchermataAstaInglese.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaRibassoItem) {
                    int id = ((AstaRibassoItem) asta).getId();
                    Intent intent = new Intent(SchermataAstePerCategoria.this, SchermataAstaRibasso.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaInversaItem) {
                    int id = ((AstaInversaItem) asta).getId();
                    Intent intent = new Intent(SchermataAstePerCategoria.this, SchermataAstaInversa.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

        astePerCategorieDAO = new AstePerCategorieDAO(this);
        //di default appena si apre la schermata si è già su aste aperte quindi escono già
        astePerCategorieDAO.openConnection();
        astePerCategorieDAO.getAsteForCategoriaUtente(categoriaSelezionata,tipoUtente);
        recyclerViewAstePerCategoria.setVisibility(View.VISIBLE);


        backBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

    }

    public void asteCategorie(ArrayList<Object> aste) {
        boolean asteVuote = aste == null || aste.isEmpty();
        if (!asteVuote)  {
            astaAdapter.setAste(aste);
        } else {
            astaAdapter.setAste(null);
            // Nessun nome asta trovato per l'email specificata
        }
        if(asteVuote){
            text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.VISIBLE);
        }else{
            text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.INVISIBLE);
        }
        progress_bar_schermata_aste_per_categoria.setVisibility(View.INVISIBLE);
    }

}