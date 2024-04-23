package com.example.progettoingsw.view;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.DAO.RegistrazioneFacoltativaDAO;
import com.example.progettoingsw.DAO.RegistrazioneSocialDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.VenditoreModel;
import com.example.progettoingsw.repository.Repository;
import com.example.progettoingsw.view.acquirente.FragmentCreaAstaInversa;
import com.example.progettoingsw.viewmodel.RegistrazioneViewModel;
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
    private AcquirenteModel acquirenteModel;
    private VenditoreModel venditoreModel;
    String nomeSocial;
    String nomeUtente;
    ArrayList<String> elencoNomeSocialRegistrazione = new ArrayList<String>();
    ArrayList<String> elencoNomeUtenteSocialRegistrazione = new ArrayList<String>();

    MaterialButton bottoneAnnulla ;
    MaterialButton bottoneSocial ;
    MaterialButton bottoneProseguiRegistrazione ;
    EditText testoBio ;
    EditText testoProvenienza ;
    EditText testoSitoWeb ;
    private Repository repository;
    RegistrazioneViewModel registrazioneViewModel;
    private AcquirenteModel acquirente;
    private VenditoreModel venditore;

    String bio ;
    String paese;
    String sitoWeb ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_campi_facoltativi);

      /*  controller = new Controller();
        intent = getIntent();
        email =intent.getStringExtra("email").trim();
        tipoUtente =intent.getStringExtra("tipoUtente");
        nome =intent.getStringExtra("nome");
        cognome =intent.getStringExtra("cognome");
        password =intent.getStringExtra("password");

        Log.d("i valori in entrata facoltativi", " " + nome + cognome + email + password + tipoUtente);*/


        bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnullaRegistrazione);
        bottoneSocial = (MaterialButton) findViewById(R.id.bottoneSocialRegistrazione);
        bottoneProseguiRegistrazione = (MaterialButton) findViewById(R.id.bottoneProseguiRegistrazione);
        testoBio = (EditText) findViewById(R.id.editTextBio);
        testoProvenienza = (EditText) findViewById(R.id.editTextPaeseDiProvenienza);
        testoSitoWeb = (EditText) findViewById(R.id.editTextSitoWeb);
        registrazioneViewModel = new ViewModelProvider(this).get(RegistrazioneViewModel.class);
        bio = testoBio.getText().toString().trim();
        paese = testoProvenienza.getText().toString().trim();
        sitoWeb = testoSitoWeb.getText().toString().trim();

        osservaAcquirenteModelPresente();
        osservaVenditoreModelPresente();
        osservaApriPopUpSocial();
        osservaProseguiInserimento();


        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.redirectActivity(RegistrazioneCampiFacoltativi.this, Registrazione.class);
            }
        });


        bottoneSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrazioneViewModel.apriPopUp();
            }
        });

        bottoneProseguiRegistrazione.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                registrazioneViewModel.checkTipoUtente();

                // Ottieni i valori dai campi di input



/*
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
                */
            }
        });


    }

    public void messaggioErroreBio(String messaggio){testoBio.setError(messaggio);}

    public void messaggioErrorePaese(String messaggio){
        testoProvenienza.setError(messaggio);
    }

    public void messaggioErroreSitoWeb(String messaggio){testoSitoWeb.setError(messaggio);}


    public void osservaMessaggioErroreBio() {
        registrazioneViewModel.messaggioErroreBio.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreBio()) {
                messaggioErroreBio(messaggio);
            }
        });
    }

    public void osservaMessaggioErrorePaese() {
        registrazioneViewModel.messaggioErrorePaese.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErrorePaese()) {
                messaggioErrorePaese(messaggio);
            }
        });
    }

    public void osservaMessaggioErroreSitoWeb() {
        registrazioneViewModel.messaggioErroreSitoWeb.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreSitoWeb()) {
                messaggioErroreSitoWeb(messaggio);
            }
        });
    }

    public void osservaAcquirente(){
        registrazioneViewModel.getAcquirente().observe(this,new Observer<AcquirenteModel>(){
            @Override
            public void onChanged(AcquirenteModel acquirente) {
                // Aggiorna l'interfaccia utente con il modello recuperato
                osservaMessaggioErroreBio();
                osservaMessaggioErrorePaese();
                osservaMessaggioErroreSitoWeb();
                registrazioneViewModel.registrazioneAcquirenteCompleta(bio,paese,sitoWeb,acquirente);
            }
        });
    }

    public void osservaVenditore(){
        registrazioneViewModel.getVenditore().observe(this,new Observer<VenditoreModel>(){
            @Override
            public void onChanged(VenditoreModel venditore) {
                // Aggiorna l'interfaccia utente con il modello recuperato
                osservaMessaggioErroreBio();
                osservaMessaggioErrorePaese();
                osservaMessaggioErroreSitoWeb();
                registrazioneViewModel.registrazioneVenditoreCompleta(bio,paese,sitoWeb,venditore);
            }
        });
    }
    public void osservaAcquirenteModelPresente(){
        registrazioneViewModel.acquirenteModelPresente.observe(this, (messaggio) -> {
            if (registrazioneViewModel.getAcquirenteModelPresente()) {
                registrazioneViewModel.recuperoAcquirente();
                osservaAcquirente();
            }
        });
    }

    public void osservaVenditoreModelPresente(){
        registrazioneViewModel.venditoreModelPresente.observe(this, (messaggio) -> {
            if (registrazioneViewModel.getVenditoreModelPresente()) {
                registrazioneViewModel.recuperoVenditore();
                osservaVenditore();
            }
        });
    }
    public void osservaProseguiInserimento(){
        registrazioneViewModel.proseguiInserimento.observe(this, (messaggio) ->{
            if (registrazioneViewModel.isProseguiInserimento("inserito")) {
                 registrazioneViewModel.controlloSocial();
                Intent intent = new Intent(RegistrazioneCampiFacoltativi.this, RegistrazioneCategorie.class);
                startActivity(intent);
            } else if (registrazioneViewModel.isProseguiInserimento("inserimento fallito")) {

            }

        });
    }
    public void osservaApriPopUpSocial(){
        registrazioneViewModel.apriPopUpSocial.observe(this, (valore) ->{
            if(valore){
                PopUpRegistrazioneSocial popUpRegistrazioneSocial = new PopUpRegistrazioneSocial(this, RegistrazioneCampiFacoltativi.this,registrazioneViewModel);
                popUpRegistrazioneSocial.show();
            }
        });
    }

}