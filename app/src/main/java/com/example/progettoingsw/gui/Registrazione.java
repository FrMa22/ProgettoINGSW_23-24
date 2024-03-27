package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.example.progettoingsw.DAO.RegistrazioneDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class Registrazione extends GestoreComuniImplementazioni {
    private EditText edittext_nome;
    private EditText edittext_cognome;
    private EditText edittext_email;
    private EditText edittext_password;
    private EditText edittext_conferma_password;
    private Spinner spinner_tipo_utente;
    private SwitchCompat switch_mostra_password_registrazione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione);
        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnulla);
        MaterialButton bottoneProsegui = (MaterialButton) findViewById(R.id.bottoneProsegui);

        switch_mostra_password_registrazione = findViewById(R.id.switch_mostra_password_registrazione);
        switch_mostra_password_registrazione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Se lo switch è selezionato, mostra la password
                if (isChecked) {
                    edittext_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edittext_conferma_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Altrimenti, nascondi la password
                    edittext_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edittext_conferma_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        edittext_nome = findViewById(R.id.editTextNomeRegistrazione);
        edittext_cognome = findViewById(R.id.editTextCongnomeRegistrazione);
        edittext_email = findViewById(R.id.editTextEmailRegistrazione);
        edittext_password = findViewById(R.id.editTextPasswordRegistrazione);
        edittext_conferma_password = findViewById(R.id.editTextConfermaPasswordRegistrazione);
        spinner_tipo_utente = findViewById(R.id.spinnerRegistrazione);




        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(String.valueOf(LoginActivity.class));
                Controller.redirectActivity(Registrazione.this, LoginActivity.class);

            }
        });

        bottoneProsegui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!edittext_email.getText().toString().isEmpty() && !spinner_tipo_utente.getSelectedItem().toString().isEmpty()){
                    String email = edittext_email.getText().toString();
                    String tipoUtente = spinner_tipo_utente.getSelectedItem().toString();
                    Log.d("bottone prosegui", "email: " + email + ", tipo : " + tipoUtente) ;
                    RegistrazioneDAO registrazioneDAO = new RegistrazioneDAO(Registrazione.this,email,tipoUtente);
                    registrazioneDAO.openConnection();
                    registrazioneDAO.checkEmail();
                }else{
                    Log.d("bottone prosegui", "errore " );
                }

//                String nome = edittext_nome.getText().toString();
//                String cognome = edittext_cognome.getText().toString();
//                String email = edittext_email.getText().toString();
//                String password = edittext_password.getText().toString();
//                String conferma_password = edittext_conferma_password.getText().toString();
//                String tipoUtente = spinner_tipo_utente.getSelectedItem().toString();
//
//                if(nome.isEmpty() || email.isEmpty() || cognome.isEmpty() || password.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "Inserire tutti i valori", Toast.LENGTH_SHORT).show();
//                }else{
//                    if(password.equals(conferma_password)){
//                        Intent intent = new Intent(Registrazione.this, RegistrazioneCampiFacoltativi.class);
//                        intent.putExtra("email", email);
//                        intent.putExtra("tipoUtente", tipoUtente);
//                        intent.putExtra("nome", nome);
//                        intent.putExtra("cognome", cognome);
//                        intent.putExtra("password", password);
//                        startActivity(intent);
//                    }
//                }


            }
        });
    }

    public void handleCheckEmail(int result) {
        //caso in cui la mail è gia presente nel db
        Log.d("handleCheckEmail", "result : " + result);
        if (result == 1) {
            Toast.makeText(getApplicationContext(), "L'indirizzo email inserito è gia stato utilizzato. Si prega di utilizzarne un altro.", Toast.LENGTH_LONG).show();
        } else if(result == 0){
            String nome = edittext_nome.getText().toString();
            String cognome = edittext_cognome.getText().toString();
            String email = edittext_email.getText().toString();
            String password = edittext_password.getText().toString();
            String conferma_password = edittext_conferma_password.getText().toString();
            String tipoUtente = spinner_tipo_utente.getSelectedItem().toString();

            if (nome.isEmpty() || email.isEmpty() || cognome.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Inserire tutti i valori", Toast.LENGTH_SHORT).show();
            } else {
                if (password.equals(conferma_password)) {
                    Intent intent = new Intent(Registrazione.this, RegistrazioneCampiFacoltativi.class);
                    intent.putExtra("email", email);
                    intent.putExtra("tipoUtente", tipoUtente);
                    intent.putExtra("nome", nome);
                    intent.putExtra("cognome", cognome);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        }
    }
}