package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.progettoingsw.DAO.RegistrazioneFacoltativaDAO;
import com.example.progettoingsw.DAO.RegistrazioneSocialDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class RegistrazioneCampiFacoltativi extends GestoreComuniImplementazioni {

    Controller controller;
    Intent intent;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String tipoUtente;
    String nomeSocial;
    String nomeUtente;
    ArrayList<String> elencoNomeSocialRegistrazione = new ArrayList<String>();
    ArrayList<String> elencoNomeUtenteSocialRegistrazione = new ArrayList<String>();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_campi_facoltativi);
        controller = new Controller();
        intent = getIntent();
        email =intent.getStringExtra("email");
        tipoUtente =intent.getStringExtra("tipoUtente");
        nome =intent.getStringExtra("nome");
        cognome =intent.getStringExtra("cognome");
        password =intent.getStringExtra("password");

        Log.d("i valori in entrata facoltativi", " " + nome + cognome + email + password + tipoUtente);


        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnullaRegistrazione);
        MaterialButton bottoneSocial = (MaterialButton) findViewById(R.id.bottoneSocialRegistrazione);
        MaterialButton bottoneProseguiRegistrazione = (MaterialButton) findViewById(R.id.bottoneProseguiRegistrazione);
        EditText testoBio = (EditText) findViewById(R.id.editTextBio);
        EditText testoProvenienza = (EditText) findViewById(R.id.editTextPaeseDiProvenienza);
        EditText testoSitoWeb = (EditText) findViewById(R.id.editTextSitoWeb);
        RegistrazioneFacoltativaDAO registrazioneFacoltativaDAO = new RegistrazioneFacoltativaDAO();
        RegistrazioneSocialDAO registrazioneSocialDAO = new RegistrazioneSocialDAO(email, tipoUtente);

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.redirectActivity(RegistrazioneCampiFacoltativi.this, Registrazione.class);
            }
        });


        bottoneSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpRegistrazioneSocial PopUpRegistrazioneSocial = new PopUpRegistrazioneSocial( RegistrazioneCampiFacoltativi.this,RegistrazioneCampiFacoltativi.this,email,tipoUtente);
                PopUpRegistrazioneSocial.show();
            }
        });




        bottoneProseguiRegistrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Ottieni i valori dai campi di input
                String bio = testoBio.getText().toString().trim();
                String paese = testoProvenienza.getText().toString().trim();
                String sitoWeb = testoSitoWeb.getText().toString().trim();

                // Verifica che la lunghezza della bio non superi i 100 caratteri
                if (bio.length() > 100) {
                    testoBio.setError("La bio non può superare i 100 caratteri");
                    return; // Esce dal metodo onClick se la bio supera i 100 caratteri
                }

                // Verifica che la lunghezza del paese non superi i 25 caratteri
                if (paese.length() > 25) {
                    testoProvenienza.setError("Il paese non può superare i 25 caratteri");
                    return; // Esce dal metodo onClick se il paese supera i 25 caratteri
                }

                // Verifica che la lunghezza del sito web non superi i 50 caratteri
                if (sitoWeb.length() > 50) {
                    testoSitoWeb.setError("Il sito web non può superare i 50 caratteri");
                    return; // Esce dal metodo onClick se il sito web supera i 50 caratteri
                }

                // Altrimenti, se tutti i controlli passano, procedi con l'inserimento dei dati nel database
                registrazioneFacoltativaDAO.openConnection();
                registrazioneFacoltativaDAO.inserimentoDatiRegistrazione(nome, cognome, email, password, tipoUtente, bio, sitoWeb, paese);
                registrazioneFacoltativaDAO.closeConnection();

                registrazioneSocialDAO.openConnection();
                registrazioneSocialDAO.inserimentoSocial(elencoNomeSocialRegistrazione, elencoNomeUtenteSocialRegistrazione);
                registrazioneSocialDAO.closeConnection();

                // Avvia l'activity successiva
                Intent intent = new Intent(RegistrazioneCampiFacoltativi.this, RegistrazioneCategorie.class);
                intent.putExtra("email", email);
                intent.putExtra("tipoUtente", tipoUtente);
                startActivity(intent);
            }
        });


    }

    public void setProfiloSocialRegistrazione(String nomeSocial,String nomeUtente) {
        this.nomeSocial = nomeSocial;
        this.nomeUtente = nomeUtente;
        elencoNomeSocialRegistrazione.add(nomeSocial);
        elencoNomeUtenteSocialRegistrazione.add(nomeUtente);
    }

    }





