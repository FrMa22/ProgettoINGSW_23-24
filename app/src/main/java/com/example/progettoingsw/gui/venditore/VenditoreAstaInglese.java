package com.example.progettoingsw.gui.venditore;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.DAO.LoginDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class VenditoreAstaInglese extends GestoreComuniImplementazioni {
    MaterialButton bottoneConferma;
    ImageButton bottoneBack;
    ImageButton bottone_info;

    EditText baseAsta;
    EditText intervalloAsta;
    EditText rialzoAsta;

    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venditore_asta_inglese);
         controller = new Controller();
        AstaIngleseDAO astaIngleseDao = new AstaIngleseDAO();

        baseAsta=findViewById(R.id.editTextBaseAstaAstaInglese);
        intervalloAsta=findViewById(R.id.editTextIntervalloTempoAstaInglese);
        rialzoAsta=findViewById(R.id.editTextSogliaRialzoAstaInglese);

        String nomeProdotto=getIntent().getStringExtra("nomeProd");
        String descrizioneProdotto=getIntent().getStringExtra("descProd");


        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaInglese);
        bottoneBack =  findViewById(R.id.bottoneBackAstaInglese);
        bottone_info = findViewById(R.id.button_info_asta_inglese);

        bottone_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showPopup();
            }
        });

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        bottoneConferma.setOnClickListener(v -> {
            //
            //Toast.makeText(AstaInglese.this,"Connessione esistente",Toast.LENGTH_SHORT).show();

            String base = baseAsta.getText().toString();
            String intervallo = intervalloAsta.getText().toString();
            String rialzo=rialzoAsta.getText().toString();

            // Chiamata al metodo per creare l'asta nel database
            astaIngleseDao.openConnection();
            astaIngleseDao.creaAstaInglese(base,intervallo,rialzo,nomeProdotto,descrizioneProdotto);
            astaIngleseDao.closeConnection();
            //Dopo aver creato l'asta,verrà creato anche il prodotto legato all'asta

        });

    }




    private void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cos'è un asta all'inglese?"); // Puoi impostare un titolo per il popup
        builder.setMessage(" Un’asta all’inglese è caratterizzata da una base d’asta iniziale pubblica, specificata dal venditore al momento della " +
                "creazione dell’asta, da un intervallo di tempo fisso per presentare nuove offerte (di default 1 " +
                "ora), e da una soglia di rialzo (di default 10€).\n Gli acquirenti possono presentare un’offerta per " +
                "il prezzo corrente.\n Quando viene presentata un’offerta, il timer per la presentazione di nuove " +
                "offerte viene resettato. Quando il timer raggiunge lo zero senza che siano presentate nuove " +
                "offerte, l’ultima offerta si aggiudica il bene/servizio in vendita, e il venditore e gli acquirenti che " +
                "hanno partecipato all’asta visualizzano una notifica. "); // Il testo da mostrare nel popup

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Azione da eseguire quando si preme il pulsante OK
                dialog.dismiss(); // Chiudi il popup
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
