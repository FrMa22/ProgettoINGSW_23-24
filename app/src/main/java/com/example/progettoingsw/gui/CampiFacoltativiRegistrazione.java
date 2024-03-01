package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.progettoingsw.DAO.RegistrazioneFacoltativaDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CampiFacoltativiRegistrazione  extends GestoreComuniImplementazioni {

    Controller controller;
    Intent intent;
    String email ;
    String tipoUtente ;
    String opzioneSelezionata;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_campi_facoltativi);
        controller = new Controller();
        intent = getIntent();
        email =intent.getStringExtra("email");
        tipoUtente =intent.getStringExtra("tipoUtente");

        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnullaRegistrazione);
        MaterialButton bottoneSocial = (MaterialButton) findViewById(R.id.bottoneSocialRegistrazione);
        MaterialButton bottoneProseguiRegistrazione = (MaterialButton) findViewById(R.id.bottoneProseguiRegistrazione);
        EditText testoBio = (EditText) findViewById(R.id.editTextBio);
        EditText testoProvenienza = (EditText) findViewById(R.id.editTextPaeseDiProvenienza);
        ArrayList<String> ElencoSocialRegistrazione = new ArrayList<String>();

        RegistrazioneFacoltativaDAO registrazioneFacoltativaDAO = new RegistrazioneFacoltativaDAO();


        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.redirectActivity(CampiFacoltativiRegistrazione.this, Registrazione.class);
            }
        });


        bottoneSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpRegistrazioneSocial PopUpRegistrazioneSocial = new PopUpRegistrazioneSocial(CampiFacoltativiRegistrazione.this,email,tipoUtente);
                PopUpRegistrazioneSocial.show();
            }
        });




        bottoneProseguiRegistrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String bio = testoBio.getText().toString();
                String paese = testoProvenienza.getText().toString();
                registrazioneFacoltativaDAO.openConnection();
                registrazioneFacoltativaDAO.inserimentoDatiOpzionali(email, tipoUtente, bio, paese);
                registrazioneFacoltativaDAO.closeConnection();
                controller.redirectActivity(CampiFacoltativiRegistrazione.this, InteressiRegistrazione.class);

            }
        });

    }

    }





