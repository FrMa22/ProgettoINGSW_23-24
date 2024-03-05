package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.progettoingsw.DAO.RegistrazioneFacoltativaDAO;
import com.example.progettoingsw.DAO.RegistrazioneSocialDAO;
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
    String social;
    String link;
    ArrayList<String> elencoLinkSocialRegistrazione = new ArrayList<String>();
    ArrayList<String> elencoSocialRegistrazione = new ArrayList<String>();

    public void setProfiloSocialRegistrazione(String social,String link) {
        this.social = social;
        this.link=link;
        elencoLinkSocialRegistrazione.add(link);
        elencoSocialRegistrazione.add(social);


    }

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
        EditText testoSitoWeb = (EditText) findViewById(R.id.editTextSitoWeb);
        RegistrazioneFacoltativaDAO registrazioneFacoltativaDAO = new RegistrazioneFacoltativaDAO();
        RegistrazioneSocialDAO registrazioneSocialDAO = new RegistrazioneSocialDAO();

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.redirectActivity(CampiFacoltativiRegistrazione.this, Registrazione.class);
            }
        });


        bottoneSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpRegistrazioneSocial PopUpRegistrazioneSocial = new PopUpRegistrazioneSocial(CampiFacoltativiRegistrazione.this,CampiFacoltativiRegistrazione.this,email,tipoUtente);
                PopUpRegistrazioneSocial.show();
            }
        });




        bottoneProseguiRegistrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String bio = testoBio.getText().toString();
                String paese = testoProvenienza.getText().toString();
                String sitoWeb = testoSitoWeb.getText().toString();
                registrazioneFacoltativaDAO.openConnection();
                registrazioneFacoltativaDAO.inserimentoDatiOpzionali(email, tipoUtente, bio,sitoWeb, paese);
                registrazioneFacoltativaDAO.closeConnection();
                registrazioneSocialDAO.openConnection();
                for (int i=0; i<elencoSocialRegistrazione.size(); i++) {
                    String social = elencoSocialRegistrazione.get(i);
                    String link = elencoLinkSocialRegistrazione.get(i);
                    registrazioneSocialDAO.inserimentoSocial(social, link, email, tipoUtente);
                }
                registrazioneSocialDAO.closeConnection();
                controller.redirectActivity(CampiFacoltativiRegistrazione.this, InteressiRegistrazione.class);

            }
        });

    }

    }





