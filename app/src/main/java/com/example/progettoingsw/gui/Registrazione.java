package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione);
        MaterialButton bottoneAnnulla = (MaterialButton) findViewById(R.id.bottoneAnnulla);
        MaterialButton bottoneProsegui = (MaterialButton) findViewById(R.id.bottoneProsegui);

        edittext_nome = findViewById(R.id.editTextNomeRegistrazione);
        edittext_cognome = findViewById(R.id.editTextCongnomeRegistrazione);
        edittext_email = findViewById(R.id.editTextEmailRegistrazione);
        edittext_password = findViewById(R.id.editTextPasswordRegistrazione);
        edittext_conferma_password = findViewById(R.id.editTextConfermaPasswordRegistrazione);
        spinner_tipo_utente = findViewById(R.id.spinnerRegistrazione);

        RegistrazioneDAO registrazioneDAO = new RegistrazioneDAO();




        bottoneAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(String.valueOf(LoginActivity.class));
                Controller.redirectActivity(Registrazione.this, LoginActivity.class);

            }
        });

        bottoneProsegui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nome = edittext_nome.getText().toString();
                String cognome = edittext_cognome.getText().toString();
                String email = edittext_email.getText().toString();
                String password = edittext_password.getText().toString();
                String conferma_password = edittext_conferma_password.getText().toString();
                String tipoUtente = spinner_tipo_utente.getSelectedItem().toString();

                if(nome.isEmpty() || email.isEmpty() || cognome.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Inserire tutti i valori", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.equals(conferma_password)){
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
        });
    }
}