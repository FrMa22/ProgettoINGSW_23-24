package com.example.progettoingsw.gui;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
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
    private SwitchCompat switch_compat_aste_attive_nonattive;
    Controller controller;
    private ImageButton bottoneBackLeMieAste;
    private ImageButton preferitiButton;
    private ImageButton profiloButton;
    private AstaAdapter astaAdapterAttive;
    private AstaAdapter astaAdapterNonAttive;
    private LeMieAsteDAO lemieAsteDAO;
    private String email;
    private String tipoUtente;
    private TextView text_view_aste_attive_non_attive;
    private ProgressBar progress_bar_le_mie_aste;
    private TextView text_view_nessuna_asta_trovata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.le_mie_aste);
        controller = new Controller();
        astaAdapterAttive = new AstaAdapter(this, null);
        astaAdapterNonAttive = new AstaAdapter(this, null);

        email = getIntent().getStringExtra("email");
        tipoUtente = getIntent().getStringExtra("tipoUtente");

        RecyclerView recyclerViewAsteAttive = findViewById(R.id.recyclerViewAsteAttive);
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewAsteAttive.setLayoutManager(gridLayoutManagerAttive);
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

        RecyclerView recyclerViewAsteNonAttive = findViewById(R.id.recyclerViewAsteNonAttive);
        GridLayoutManager gridLayoutManagerNonAttive = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewAsteNonAttive.setLayoutManager(gridLayoutManagerNonAttive);
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

        progress_bar_le_mie_aste = findViewById(R.id.progress_bar_le_mie_aste);
        progress_bar_le_mie_aste.setVisibility(View.VISIBLE);



        //

        //Ora inizia codice solo per LeMieAste

//        RadioGroup radioGroup = findViewById(R.id.radioGroupLeMieAste);
//        RadioButton btnAttivi = findViewById(R.id.bottoneAttive);
//        RadioButton btnNonAttivi = findViewById(R.id.bottoneNonAttive);

        text_view_aste_attive_non_attive = findViewById(R.id.text_view_aste_attive_non_attive);
        switch_compat_aste_attive_nonattive = findViewById(R.id.switch_compat_aste_attive_nonattive);
        text_view_nessuna_asta_trovata = findViewById(R.id.text_view_nessuna_asta_trovata);
        switch_compat_aste_attive_nonattive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   text_view_nessuna_asta_trovata.setVisibility(View.INVISIBLE);
                   recyclerViewAsteAttive.setVisibility(View.VISIBLE);
                   recyclerViewAsteNonAttive.setVisibility(View.GONE);
                   lemieAsteDAO.getAsteForEmail(email,"aperta");
                   text_view_aste_attive_non_attive.setText("ASTE ATTIVE");
                   text_view_aste_attive_non_attive.setTextColor(getResources().getColor(R.color.verde));
               } else {
                   text_view_nessuna_asta_trovata.setVisibility(View.INVISIBLE);
                   recyclerViewAsteNonAttive.setVisibility(View.VISIBLE);
                   recyclerViewAsteAttive.setVisibility(View.GONE);
                   lemieAsteDAO.getAsteForEmail(email,"chiusa");
                   text_view_aste_attive_non_attive.setText("ASTE NON ATTIVE");
                   text_view_aste_attive_non_attive.setTextColor(getResources().getColor(R.color.rosso));
               }
           }
       });
         lemieAsteDAO = new LeMieAsteDAO(this);
        //di default appena si apre la schermata si è già su aste aperte quindi escono già
        lemieAsteDAO.openConnection();
        lemieAsteDAO.getAsteForEmail(email,"aperta");
        recyclerViewAsteAttive.setVisibility(View.VISIBLE);


//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup gruppo, int checkedId) {
//                if (checkedId == R.id.bottoneAttive) {
//                    // Mostra la ScrollView per "Attivi"
//                    //gridLayoutAttive.setVisibility(View.VISIBLE);
//                    recyclerViewAsteAttive.setVisibility(View.VISIBLE);
//                    // Nascondi la ScrollView per "Non Attivi"
//                    //gridLayoutNonAttive.setVisibility(View.GONE);
//                    recyclerViewAsteNonAttive.setVisibility(View.GONE);
//
//                    lemieAsteDAO.getAsteForEmail(email,"aperta");
//
//                } else if (checkedId == R.id.bottoneNonAttive) {
//                    // Mostra la ScrollView per "Non Attivi"
//                    //gridLayoutNonAttive.setVisibility(View.VISIBLE);
//                    recyclerViewAsteNonAttive.setVisibility(View.VISIBLE);
//                    // Nascondi la ScrollView per "Attivi"
//                    //gridLayoutAttive.setVisibility(View.GONE);
//                    recyclerViewAsteAttive.setVisibility(View.GONE);
//
//                    lemieAsteDAO.getAsteForEmail(email,"chiusa");
//
//                }
//            }
//        });

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
        // Controllo se l'ArrayList di aste è vuoto
        boolean asteVuote = aste == null || aste.isEmpty();

        if(cond.equals("aperta")) {
            if (!asteVuote) {
                // Se l'ArrayList di aste non è vuoto, mostro le aste nell'adapter
                astaAdapterAttive.setAste(aste);
            } else {
                // Se l'ArrayList di aste è vuoto, nascondo l'adapter e mostro il TextView
                astaAdapterAttive.setAste(null); // Resetta l'adapter
                Toast.makeText(this, "Nessuna asta trovata", Toast.LENGTH_SHORT).show();
            }
        } else if(cond.equals("chiusa")) {
            if (!asteVuote) {
                // Se l'ArrayList di aste non è vuoto, mostro le aste nell'adapter
                astaAdapterNonAttive.setAste(aste);
            } else {
                // Se l'ArrayList di aste è vuoto, nascondo l'adapter e mostro il TextView
                astaAdapterNonAttive.setAste(null); // Resetta l'adapter
                Toast.makeText(this, "Nessuna asta trovata", Toast.LENGTH_SHORT).show();
            }
        }

        // Imposto la visibilità del TextView in base all'ArrayList di aste
        if (asteVuote) {
            progress_bar_le_mie_aste.setVisibility(View.INVISIBLE);
            text_view_nessuna_asta_trovata.setVisibility(View.VISIBLE);
        } else {
            progress_bar_le_mie_aste.setVisibility(View.INVISIBLE);
            text_view_nessuna_asta_trovata.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Chiudi la connessione quando l'Activity viene distrutta
        lemieAsteDAO.closeConnection();
    }

}
