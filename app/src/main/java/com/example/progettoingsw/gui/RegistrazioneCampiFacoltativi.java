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
                String bio = testoBio.getText().toString();
                String paese = testoProvenienza.getText().toString();
                String sitoWeb = testoSitoWeb.getText().toString();
                registrazioneFacoltativaDAO.openConnection();
                registrazioneFacoltativaDAO.inserimentoDatiRegistrazione(nome, cognome, email,password, tipoUtente, bio,sitoWeb, paese);
                registrazioneFacoltativaDAO.closeConnection();

                registrazioneSocialDAO.openConnection();
                registrazioneSocialDAO.inserimentoSocial(elencoNomeSocialRegistrazione,elencoNomeUtenteSocialRegistrazione );
                registrazioneSocialDAO.closeConnection();

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





