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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.progettoingsw.DAO.SchermataPartecipazioneAsteDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaInversaItem;
import com.example.progettoingsw.model.AstaRibassoItem;

import java.util.ArrayList;

public class SchermataPartecipazioneAste extends AppCompatActivity {
    private ProgressBar progress_bar_schermata_partecipazione_aste;
    private TextView text_view_nessuna_partecipazione;
    private String email;
    private String tipoUtente;
    private RecyclerView recyclerView_schermata_partecipazione_aste;
    private AstaAdapter astaAdapter;
    private ImageButton bottone_back_schermata_partecipazione_aste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_partecipazione_aste);

        email = getIntent().getStringExtra("email");
        tipoUtente = getIntent().getStringExtra("tipoUtente");

        astaAdapter = new AstaAdapter(this,null);
        recyclerView_schermata_partecipazione_aste = findViewById(R.id.recyclerView_schermata_partecipazione_aste);
        bottone_back_schermata_partecipazione_aste = findViewById(R.id.bottone_back_schermata_partecipazione_aste);
        progress_bar_schermata_partecipazione_aste = findViewById(R.id.progress_bar_schermata_partecipazione_aste);
        text_view_nessuna_partecipazione = findViewById(R.id.text_view_nessuna_partecipazione);


        progress_bar_schermata_partecipazione_aste.setVisibility(View.VISIBLE);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerView_schermata_partecipazione_aste.setLayoutManager(gridLayoutManager);
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, gridLayoutManager.getOrientation());
        recyclerView_schermata_partecipazione_aste.addItemDecoration(dividerItemDecorationAttive);
        astaAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerView_schermata_partecipazione_aste.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapter.getItem(position);

                // Esegui le azioni desiderate con l'oggetto Asta
                if (asta instanceof AstaIngleseItem) {
                    int id = ((AstaIngleseItem) asta).getId();
                    Log.d("Asta inglese", "id è " + id);
                    Intent intent = new Intent(SchermataPartecipazioneAste.this, SchermataAstaInglese.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaRibassoItem) {
                    int id = ((AstaRibassoItem) asta).getId();
                    Intent intent = new Intent(SchermataPartecipazioneAste.this, SchermataAstaRibasso.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaInversaItem) {
                    int id = ((AstaInversaItem) asta).getId();
                    Intent intent = new Intent(SchermataPartecipazioneAste.this, SchermataAstaInversa.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

        recyclerView_schermata_partecipazione_aste.setAdapter(astaAdapter);

        SchermataPartecipazioneAsteDAO schermataPartecipazioneAsteDAO = new SchermataPartecipazioneAsteDAO(SchermataPartecipazioneAste.this,email,tipoUtente);
        schermataPartecipazioneAsteDAO.openConnection();
        schermataPartecipazioneAsteDAO.partecipazioneAste();
        schermataPartecipazioneAsteDAO.closeConnection();

        bottone_back_schermata_partecipazione_aste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SchermataPartecipazioneAste" , "Back");
                onBackPressed();
            }
        });

    }

    public void updatePartecipazioni(ArrayList<Object> aste) {
        boolean asteVuote = aste == null || aste.isEmpty();
        Log.d("updatePartecipazioni", "asteVuote: " + asteVuote);
        if (!asteVuote) {
            astaAdapter.setAste(aste);
        } else {
            astaAdapter.setAste(null);
        }
        if(asteVuote){
            Log.d("updateNotifiche", "caso vuoto: ");
            text_view_nessuna_partecipazione.setVisibility(View.VISIBLE);
        }else{
            text_view_nessuna_partecipazione.setVisibility(View.INVISIBLE);
        }
        progress_bar_schermata_partecipazione_aste.setVisibility(View.GONE);

    }

}