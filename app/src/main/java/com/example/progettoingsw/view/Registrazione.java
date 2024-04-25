package com.example.progettoingsw.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.DAO.RegistrazioneDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.viewmodel.RegistrazioneViewModel;
import com.google.android.material.button.MaterialButton;

public class Registrazione extends GestoreComuniImplementazioni {
    private EditText edittext_nome;
    private EditText edittext_cognome;
    private EditText edittext_email;
    private EditText edittext_password;
    private EditText edittext_conferma_password;
    private Spinner spinner_tipo_utente;
    private SwitchCompat switch_mostra_password_registrazione;
    private RegistrazioneViewModel registrazioneViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registrazioneViewModel = new ViewModelProvider(this).get(RegistrazioneViewModel.class);

        setContentView(R.layout.registrazione);
        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnulla);
        MaterialButton bottoneProsegui = (MaterialButton) findViewById(R.id.bottoneProsegui);

        switch_mostra_password_registrazione = findViewById(R.id.switch_mostra_password_registrazione);
        switch_mostra_password_registrazione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Se lo switch è selezionato, mostra la password
                int cursorPositionPassword = edittext_password.getSelectionStart();
                int cursorPositionPasswordConferma = edittext_conferma_password.getSelectionStart();
                if (isChecked) {
                    edittext_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edittext_conferma_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Altrimenti, nascondi la password
                    edittext_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edittext_conferma_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                edittext_password.setSelection(cursorPositionPassword);
                edittext_conferma_password.setSelection(cursorPositionPasswordConferma);
            }
        });

        edittext_nome = findViewById(R.id.editTextNomeRegistrazione);
        edittext_cognome = findViewById(R.id.editTextCongnomeRegistrazione);
        edittext_email = findViewById(R.id.editTextEmailRegistrazione);
        edittext_password = findViewById(R.id.editTextPasswordRegistrazione);
        edittext_conferma_password = findViewById(R.id.editTextConfermaPasswordRegistrazione);
        spinner_tipo_utente = findViewById(R.id.spinnerRegistrazione);

        osservaMessaggioErroreEmail();
        osservaMessaggioErrorePassword();
        osservaMessaggioErroreNome();
        osservaMessaggioErroreCognome();
        osservaMessaggioErroreConfermaPassword();
        osservaProseguiRegistrazione();

        osservaValoriPresentiAcquirente();
        osservaValoriPresentiVenditore();

        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(String.valueOf(LoginActivity.class));
                Controller.redirectActivity(Registrazione.this, LoginActivity.class);

            }
        });


        bottoneProsegui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Ottieni i valori dai campi di input
                String email = edittext_email.getText().toString().trim();
                String tipoUtente = spinner_tipo_utente.getSelectedItem().toString().trim();
                Log.d("onClickbottoneprosegui", "valore spinner : " + tipoUtente);
                String nome = edittext_nome.getText().toString().trim();
                String cognome = edittext_cognome.getText().toString().trim();
                String password = edittext_password.getText().toString().trim();
                String conferma_password = edittext_conferma_password.getText().toString().trim();


                System.out.println("premuto bottone");



                if(tipoUtente.equals("acquirente")){

                    registrazioneViewModel.registrazioneAcquirente(email,password,conferma_password,nome,cognome);
                } else if (tipoUtente.equals("venditore")) {

                    registrazioneViewModel.registrazioneVenditore(email,password,conferma_password,nome,cognome);
                }

            }
        });


    }


    @Override
    public void onResume(){
        super.onResume();
        registrazioneViewModel.checkValoriPresenti();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Distrugge il ViewModel quando l'Activity viene distrutta
        registrazioneViewModel.resetAllVariables();
    }

    public void messaggioErroreMail(String messaggio){edittext_email.setError(messaggio);}

    public void messaggioErrorePassword(String messaggio){
        edittext_password.setError(messaggio);
    }

    public void messaggioErroreConfermaPassword(String messaggio){edittext_conferma_password.setError(messaggio);}
    public void messaggioErroreNome(String messaggio){edittext_nome.setError(messaggio);}
    public void messaggioErroreCognome(String messaggio){edittext_cognome.setError(messaggio);}



    public void osservaMessaggioErroreEmail() {
        registrazioneViewModel.messaggioErroreEmail.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreEmail()) {
                messaggioErroreMail(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }
    public void osservaMessaggioErrorePassword() {
        registrazioneViewModel.messaggioErrorePassword.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErrorePassword()) {
                messaggioErrorePassword(messaggio);
            }
        });
    }

    public void osservaMessaggioErroreConfermaPassword() {
        registrazioneViewModel.messaggioErroreConfermaPassword.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreConfermaPassword()) {
                messaggioErroreConfermaPassword(messaggio);
            }
        });
    }

    public void osservaMessaggioErroreNome() {
        registrazioneViewModel.messaggioErroreNome.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreNome()) {
                messaggioErroreNome(messaggio);
            }
        });
    }

    public void osservaMessaggioErroreCognome() {
        registrazioneViewModel.messaggioErroreCognome.observe(this, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErroreCognome()) {
                messaggioErroreCognome(messaggio);
            }
        });
    }

    public void osservaProseguiRegistrazione(){
        registrazioneViewModel.proseguiRegistrazione.observe(this, (messaggio) ->{
            if (registrazioneViewModel.isProseguiRegistrazione("nuovo acquirente")) {
                Intent intent = new Intent(Registrazione.this, RegistrazioneCampiFacoltativi.class);
                startActivity(intent);
                Toast.makeText(this, "Passaggio ai campi facoltativi", Toast.LENGTH_SHORT).show();
            } else if (registrazioneViewModel.isProseguiRegistrazione("nuovo venditore")) {
                Intent intent = new Intent(Registrazione.this, RegistrazioneCampiFacoltativi.class);
                startActivity(intent);
                Toast.makeText(this, "Passaggio ai campi facoltativi", Toast.LENGTH_SHORT).show();
            }

        });
    }
    public void osservaValoriPresentiAcquirente(){
        registrazioneViewModel.valoriPresentiAcquirente.observe(this, (utente)->{
            if(registrazioneViewModel.isValoriPresentiAcquirente()){
                edittext_nome.setText(utente.getNome());
                edittext_cognome.setText(utente.getCognome());
                edittext_email.setText(utente.getIndirizzo_email());
                edittext_password.setText(utente.getPassword());
                edittext_conferma_password.setText(utente.getPassword());
                spinner_tipo_utente.setSelection(0);
            }
        });
    }
    public void osservaValoriPresentiVenditore(){
        registrazioneViewModel.valoriPresentiVenditore.observe(this, (utente) ->{
            if(registrazioneViewModel.isValoriPresentiVenditore()){
                edittext_nome.setText(utente.getNome());
                edittext_cognome.setText(utente.getCognome());
                edittext_email.setText(utente.getIndirizzo_email());
                edittext_password.setText(utente.getPassword());
                edittext_conferma_password.setText(utente.getPassword());
                spinner_tipo_utente.setSelection(1);
            }
        });
    }

}