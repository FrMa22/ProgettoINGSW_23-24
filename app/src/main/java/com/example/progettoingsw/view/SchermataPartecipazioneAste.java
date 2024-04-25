package com.example.progettoingsw.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.progettoingsw.item.AstaIngleseItem;
import com.example.progettoingsw.item.AstaInversaItem;
import com.example.progettoingsw.item.AstaRibassoItem;
import com.example.progettoingsw.viewmodel.LeMieAsteViewModel;
import com.example.progettoingsw.viewmodel.SchermataPartecipazioneAsteViewModel;

import java.util.ArrayList;

public class SchermataPartecipazioneAste extends AppCompatActivity {
    private ProgressBar progress_bar_schermata_partecipazione_aste;
    private TextView text_view_nessuna_partecipazione;
    private RecyclerView recyclerView_schermata_partecipazione_aste;
    private AstaAdapter astaAdapter;
    private ImageButton bottone_back_schermata_partecipazione_aste;
    private SchermataPartecipazioneAsteViewModel schermataPartecipazioneAsteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_partecipazione_aste);

        astaAdapter = new AstaAdapter(this,null);

        schermataPartecipazioneAsteViewModel=new ViewModelProvider(this).get(SchermataPartecipazioneAsteViewModel.class);

        osservaAsteRecuperate();
        osservaEntraInSchermataAstaInglese();
        osservaEntraInSchermataAstaInversa();
        schermataPartecipazioneAsteViewModel.trovaAsteViewModel();


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
                System.out.println("entrah, non penso proprio");
                schermataPartecipazioneAsteViewModel.gestisciClickRecyclerView(asta);
            }
        });

        recyclerView_schermata_partecipazione_aste.setAdapter(astaAdapter);

//        SchermataPartecipazioneAsteDAO schermataPartecipazioneAsteDAO = new SchermataPartecipazioneAsteDAO(SchermataPartecipazioneAste.this,email,tipoUtente);
//        schermataPartecipazioneAsteDAO.openConnection();
//        schermataPartecipazioneAsteDAO.partecipazioneAste();
//        schermataPartecipazioneAsteDAO.closeConnection();

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
            astaAdapter.setAstecopia(aste);
        } else {
            astaAdapter.setAstecopia(null);
        }
        if(asteVuote){
            text_view_nessuna_partecipazione.setVisibility(View.VISIBLE);
        }else{
            text_view_nessuna_partecipazione.setVisibility(View.INVISIBLE);
        }
        progress_bar_schermata_partecipazione_aste.setVisibility(View.GONE);

    }




    public void osservaEntraInSchermataAstaInglese(){
        schermataPartecipazioneAsteViewModel.entraInSchermataAstaInglese.observe(this, (messaggio) -> {
            if (schermataPartecipazioneAsteViewModel.getEntraInSchermataAstaInglese()){
                Intent intent = new Intent(SchermataPartecipazioneAste.this, SchermataAstaInglese.class);
                startActivity(intent);
            }
        });
    }


    public void osservaEntraInSchermataAstaInversa(){
        schermataPartecipazioneAsteViewModel.entraInSchermataAstaInversa.observe(this, (messaggio) -> {
            if (schermataPartecipazioneAsteViewModel.getEntraInSchermataAstaInversa()){
                Intent intent = new Intent(SchermataPartecipazioneAste.this, SchermataAstaInversa.class);
                startActivity(intent);
            }
        });
    }


    public void osservaAsteRecuperate() {
        schermataPartecipazioneAsteViewModel.asteRecuperate.observe(this, (lista) -> {
            if (lista != null) {
                System.out.println("in osserva aste  recuperate");
                //lista di aste TUTTI I TIPI DI ASTA aperte recuperate(fare 3 recuperi per singolo tipo poi fondere in una mega lista)
                updatePartecipazioni(lista);
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}