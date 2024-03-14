package com.example.progettoingsw.gui;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.DAO.LeMieAsteDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaInversaItem;
import com.example.progettoingsw.model.AstaRibassoItem;

import java.util.ArrayList;
import java.util.List;

public class LeMieAste extends GestoreComuniImplementazioni {

    Controller controller;
    private ImageButton bottoneBackLeMieAste;
    private ImageButton preferitiButton;
    private ImageButton profiloButton;
    private AstaAdapter astaAdapterAttive;
    private AstaAdapter astaAdapterNonAttive;
    private LeMieAsteDAO lemieAsteDAO;
    private String email;
    private String tipoUtente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.le_mie_aste);
        controller = new Controller();
        astaAdapterAttive = new AstaAdapter(this, null);
        astaAdapterNonAttive = new AstaAdapter(this, null);

        email = getIntent().getStringExtra("email");
        tipoUtente = getIntent().getStringExtra("tipoUtente");

        //
        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAsteAttive = findViewById(R.id.recyclerViewAsteAttive);
        // Utilizza LinearLayoutManager con orientamento orizzontale per far si che il recycler sia orizzontale, di default è verticale
// Utilizza GridLayoutManager con due colonne
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewAsteAttive.setLayoutManager(gridLayoutManagerAttive);
//
//        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, gridLayoutManagerAttive.getOrientation());
        recyclerViewAsteAttive.addItemDecoration(dividerItemDecorationAttive);
        astaAdapterAttive.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerViewAsteAttive.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapterAttive.getItem(position);

                // Esegui le azioni desiderate con l'oggetto Asta
                if (asta instanceof AstaIngleseItem) {
                    int id = ((AstaIngleseItem) asta).getId();
                    Log.d("Asta inglese", "id è " + id);
                    Intent intent = new Intent(LeMieAste.this, SchermataAstaInglese.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaRibassoItem) {
                    int id = ((AstaRibassoItem) asta).getId();
                    Intent intent = new Intent(LeMieAste.this, SchermataAstaRibasso.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaInversaItem) {
                    int id = ((AstaInversaItem) asta).getId();
                    Intent intent = new Intent(LeMieAste.this, SchermataAstaInversa.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

        recyclerViewAsteAttive.setAdapter(astaAdapterAttive);

        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAsteNonAttive = findViewById(R.id.recyclerViewAsteNonAttive);
        // Utilizza LinearLayoutManager con orientamento orizzontale per far si che il recycler sia orizzontale, di default è verticale
        GridLayoutManager gridLayoutManagerNonAttive = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewAsteNonAttive.setLayoutManager(gridLayoutManagerNonAttive);
//        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationNonAttive = new DividerItemDecoration(this, gridLayoutManagerNonAttive.getOrientation());
        recyclerViewAsteNonAttive.addItemDecoration(dividerItemDecorationNonAttive);
        astaAdapterNonAttive.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerViewAsteNonAttive.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapterNonAttive.getItem(position);

                // Esegui le azioni desiderate con l'oggetto Asta
                if (asta instanceof AstaIngleseItem) {
                    int id = ((AstaIngleseItem) asta).getId();
                    Log.d("Asta inglese", "id è " + id);
                    Intent intent = new Intent(LeMieAste.this, SchermataAstaInglese.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaRibassoItem) {
                    int id = ((AstaRibassoItem) asta).getId();
                    Intent intent = new Intent(LeMieAste.this, SchermataAstaRibasso.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if (asta instanceof AstaInversaItem) {
                    int id = ((AstaInversaItem) asta).getId();
                    Intent intent = new Intent(LeMieAste.this, SchermataAstaInversa.class);//test del login
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

        recyclerViewAsteNonAttive.setAdapter(astaAdapterNonAttive);





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

        bottoneBackLeMieAste = findViewById(R.id.bottoneBackLeMieAste);
        bottoneBackLeMieAste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LeMieAste" , "Back");
                onBackPressed();
            }
        });

    }



    public void updateAste(ArrayList<Object> aste, String cond) {


        //
        if(cond.equals("aperta")) {
            if (aste != null) {
//          Questo crea un altro recycler ogni volta sovrascrivendo il vecchio, eliminando il onClickListener di prima perciò non va fatto
//                RecyclerView recyclerViewAsteAttive = findViewById(R.id.recyclerViewAsteAttive);
//                AstaAdapter astaAdapterAttive = new AstaAdapter(this, aste);
//                recyclerViewAsteAttive.setAdapter(astaAdapterAttive);
//                recyclerViewAsteAttive.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
                astaAdapterAttive.setAste(aste);


                // Aggiungi stampe nel log per verificare che i dati siano correttamente passati

            } else {
                // Nessun nome asta trovato per l'email specificata
            }
        }
        //


        if(cond.equals("chiusa")) {
            if (aste != null) {
//          Questo crea un altro recycler ogni volta sovrascrivendo il vecchio, eliminando il onClickListener di prima, perciò non va fatto
//                RecyclerView recyclerViewAsteNonAttive = findViewById(R.id.recyclerViewAsteNonAttive);
//                AstaAdapter astaAdapterAttive = new AstaAdapter(this, aste);
//                recyclerViewAsteNonAttive.setAdapter(astaAdapterAttive);
//                recyclerViewAsteNonAttive.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
                astaAdapterNonAttive.setAste(aste);

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
