package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaInglese extends GestoreComuniImplementazioni {
    Controller controller;
    ImageButton bottoneBack;
    MaterialButton bottoneNuovaOfferta;
    ImageButton bottonePreferito;
    private int id;
    private String email;
    private String tipoUtente;
    TextView textViewNomeProdotto;
    ImageView imageViewProdotto;
    TextView textViewDescrizione;
    TextView textViewScadenza;
    TextView textViewPrezzo;
    TextView textViewOffertaAttuale;
    TextView textViewIntervalloOfferte;
    TextView textViewVenditore;
    private AstaIngleseDAO astaIngleseDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inglese);
        astaIngleseDAO = new AstaIngleseDAO(this);

        // Inizializzazione dei TextView, ImageView e altri elementi
        textViewNomeProdotto = findViewById(R.id.textViewNomeProdottoSchermataAstaInglese);
        imageViewProdotto = findViewById(R.id.ImageViewSchermataAstaInglese);
        textViewDescrizione = findViewById(R.id.textViewDescrizioneSchermataAstaInglese);
        textViewPrezzo = findViewById(R.id.textViewPrezzoAttualeSchermataAstaInglese);
        textViewOffertaAttuale = findViewById(R.id.textViewOffertaAttualeSchermataAstaInglese);
        textViewIntervalloOfferte = findViewById(R.id.textViewIntervalloOfferte);
        textViewVenditore = findViewById(R.id.textViewVenditoreSchermataAstaInglese);

        id = getIntent().getIntExtra("id",0);
        email = getIntent().getStringExtra("email");
        tipoUtente = getIntent().getStringExtra("tipoUtente");
        Toast.makeText(this, "l'id è " + id + ", la mail è " + email + ", il tipoutente è " + tipoUtente, Toast.LENGTH_SHORT).show();

        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaInglese);
        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });



        bottoneNuovaOfferta =  findViewById(R.id.bottoneOffertaSchermataAstaInglese);
        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopUpNuovaOfferta popUpNuovaOfferta = new PopUpNuovaOfferta(SchermataAstaInglese.this,email,id,"inglese", textViewPrezzo.getText().toString());
                popUpNuovaOfferta.show();
            }
        });
        String valoreDaModificare = getIntent().getStringExtra("editTextPrezzo");
        if (valoreDaModificare != null) {
            textViewPrezzo.setText(valoreDaModificare);
        }

        astaIngleseDAO.openConnection();
        astaIngleseDAO.getAstaIngleseByID(id);
        astaIngleseDAO.closeConnection();


    textViewVenditore.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailVenditore = textViewVenditore.getText().toString();
            Intent intent=new Intent(SchermataAstaInglese.this, ProfiloVenditore.class);
            intent.putExtra("email",emailVenditore);
            startActivity(intent);
        }
    });


    }

    public void setAstaData(AstaIngleseItem astaIngleseItem) {
        if (astaIngleseItem != null) {
            // Imposta i dati recuperati sui TextView e ImageView della tua activity
            textViewNomeProdotto.setText(astaIngleseItem.getNome());
            textViewDescrizione.setText(astaIngleseItem.getDescrizione());
            textViewPrezzo.setText(astaIngleseItem.getPrezzoAttuale());
            textViewVenditore.setText(astaIngleseItem.getEmailVenditore());
            textViewIntervalloOfferte.setText(astaIngleseItem.getIntervalloTempoOfferte());
            // Imposta l'immagine solo se non è nulla
            if (astaIngleseItem.getImmagine() != null) {
                imageViewProdotto.setImageBitmap(astaIngleseItem.getImmagine());
            }
            // Altri dati da impostare...
        } else {
            // Gestisci il caso in cui non ci siano dati recuperati
            Log.d("Errore", "Impossibile recuperare i dati dell'asta");
        }
    }
}
