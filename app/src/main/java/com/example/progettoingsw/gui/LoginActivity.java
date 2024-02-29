package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettoingsw.DAO.Acquirente;
import com.example.progettoingsw.DAO.LoginDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;
import com.example.progettoingsw.gui.venditore.VenditoreAstaInglese;

public class LoginActivity extends GestoreComuniImplementazioni {

    Button bottone;
    Controller controller;
    EditText editText_mail;
    EditText editText_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registrazione = (TextView) findViewById(R.id.TextViewRegistrati);
        Button bottoneLogin = (Button) findViewById(R.id.bottonelogin);
        editText_mail = findViewById(R.id.editTextEmail);
        editText_password = findViewById(R.id.editTextPassword);

        LoginDAO logindao = new LoginDAO(this);

        registrazione.setOnClickListener(v -> {
            //apre schermata registrazione
            Controller.redirectActivity(LoginActivity.this, Registrazione.class);

        });
        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                PopUpLogin popUpLogin = new PopUpLogin(LoginActivity.this);
//                popUpLogin.show();
                String mail = editText_mail.getText().toString();
                String password = editText_password.getText().toString();

                if (!mail.isEmpty() && !password.isEmpty()) {
                    // Chiamata al metodo per cercare nel database
                    logindao.openConnection();
                    logindao.findUser(mail, password);
                    Log.d("result set" , "fatta finduser");

                } else {
                    // Gestione del caso in cui uno o entrambi i campi sono vuoti
                    Toast.makeText(LoginActivity.this, "Inserisci sia l'email che la password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        bottone = findViewById(R.id.button);
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.redirectActivity(LoginActivity.this, VenditoreAstaInglese.class);
            }
        });

    }
    public void handleLoginResult(boolean result) {
        Log.d("handleLoginResult", " result è : " + result);
        if (!result) {
            Log.d("caso false", " result è : " + result);
            Toast.makeText(this, "Non trovato", Toast.LENGTH_SHORT).show();
            // L'utente è stato trovato
            // Esegui le azioni necessarie per il login
        } else {
            Log.d("caso true", " result è : " + result);
            // L'utente non è stato trovato
            // Mostra un messaggio di errore o esegui altre azioni necessarie
             Toast.makeText(this, "Trovato", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);
                        intent.putExtra("email", editText_mail.getText().toString());
                        startActivity(intent);
        }
    }


}