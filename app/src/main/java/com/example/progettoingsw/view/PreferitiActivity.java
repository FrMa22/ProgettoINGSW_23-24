package com.example.progettoingsw.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.DAO.AstePreferiteDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.view.acquirente.AcquirenteMainActivity;
import com.example.progettoingsw.item.AstaIngleseItem;
import com.example.progettoingsw.item.AstaInversaItem;
import com.example.progettoingsw.item.AstaRibassoItem;

import java.util.ArrayList;

public class PreferitiActivity extends GestoreComuniImplementazioni {
    Controller controller;
    private ImageButton backBottone;
    private TextView text_view_nessuna_asta_preferita_trovata;
    Intent intent;
    private String email;
    private String tipoUtente;
    private AstaAdapter astaAdapter;
    private AstePreferiteDAO astePreferiteDAO;
    private ProgressBar progress_bar_schermata_preferiti;
    private RelativeLayout relative_layout_schermata_preferiti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferiti);
        controller = new Controller();
         intent= getIntent();
        email =intent.getStringExtra("email").trim();
        tipoUtente =intent.getStringExtra("tipoUtente");
        Log.d("preferitiActivity", "email : " + email + ", tipoutente: " + tipoUtente);
        backBottone = findViewById(R.id.backButton);
        astaAdapter = new AstaAdapter(this, null);

        progress_bar_schermata_preferiti = findViewById(R.id.progress_bar_schermata_preferiti);
        progress_bar_schermata_preferiti.setVisibility(View.VISIBLE);
        relative_layout_schermata_preferiti = findViewById(R.id.relative_layout_schermata_preferiti);
        text_view_nessuna_asta_preferita_trovata = findViewById(R.id.text_view_nessuna_asta_preferita_trovata);
        //
        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAstePreferite = findViewById(R.id.recyclerAstePreferiti);
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2 ,RecyclerView.VERTICAL,false);
        recyclerViewAstePreferite.setLayoutManager(gridLayoutManagerAttive);
        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, gridLayoutManagerAttive.getOrientation());
        recyclerViewAstePreferite.addItemDecoration(dividerItemDecorationAttive);

        astaAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                Log.d("preferiti" , "clicl");
                int position = recyclerViewAstePreferite.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapter.getItem(position);

                // Esegui le azioni desiderate con l'oggetto Asta
                if (asta instanceof AstaIngleseItem) {
                    int id = ((AstaIngleseItem) asta).getId();
                    Log.d("Asta inglese", "id è " + id);
                    Intent intent = new Intent(PreferitiActivity.this, SchermataAstaInglese.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaRibassoItem) {
                    int id = ((AstaRibassoItem) asta).getId();
                    Intent intent = new Intent(PreferitiActivity.this, SchermataAstaRibasso.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaInversaItem) {
                    int id = ((AstaInversaItem) asta).getId();
                    Intent intent = new Intent(PreferitiActivity.this, SchermataAstaInversa.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });
        recyclerViewAstePreferite.setAdapter(astaAdapter);

        astePreferiteDAO = new AstePreferiteDAO(this);
        //di default appena si apre la schermata si è già su aste aperte quindi escono già
        setAllClickable(relative_layout_schermata_preferiti,false);
        astePreferiteDAO.openConnection();
        astePreferiteDAO.getAsteForEmailUtente(email,tipoUtente);
        recyclerViewAstePreferite.setVisibility(View.VISIBLE);


        backBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
                Intent intent = new Intent(PreferitiActivity.this, AcquirenteMainActivity.class);//test del login
                intent.putExtra("email", email);
                intent.putExtra("tipoUtente", tipoUtente);
                startActivity(intent);

            }
        });


    }

    public void astePreferite(ArrayList<Object> aste) {
        boolean asteVuote = aste == null || aste.isEmpty();
            if (!asteVuote)  {
                astaAdapter.setAste(aste);
            } else {
                astaAdapter.setAste(null);
                // Nessun nome asta trovato per l'email specificata
            }
            if(asteVuote){
                text_view_nessuna_asta_preferita_trovata.setVisibility(View.VISIBLE);
            }else{
                text_view_nessuna_asta_preferita_trovata.setVisibility(View.INVISIBLE);
            }
            progress_bar_schermata_preferiti.setVisibility(View.INVISIBLE);
            setAllClickable(relative_layout_schermata_preferiti, true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        progress_bar_schermata_preferiti.setVisibility(View.VISIBLE);
        setAllClickable(relative_layout_schermata_preferiti, false);
        astePreferiteDAO.openConnection();
        astePreferiteDAO.getAsteForEmailUtente(email, tipoUtente);
    }

}