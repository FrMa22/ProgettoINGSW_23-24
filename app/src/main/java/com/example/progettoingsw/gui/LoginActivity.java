package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettoingsw.DAO.Acquirente;
import com.example.progettoingsw.DAO.LoginDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;
import com.example.progettoingsw.gui.venditore.VenditoreAstaInglese;
import com.example.progettoingsw.gui.venditore.VenditoreAstaRibasso;
import com.example.progettoingsw.gui.venditore.VenditoreMainActivity;

public class LoginActivity extends GestoreComuniImplementazioni {

    Button bottone;
    Controller controller;
    ProgressBar progress_bar_login;
    LinearLayout linear_layout_login;
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
        linear_layout_login = findViewById(R.id.linear_layout_login);

        progress_bar_login = findViewById(R.id.progress_bar_login);

        LoginDAO logindao = new LoginDAO(this);

        registrazione.setOnClickListener(v -> {
            //apre schermata registrazione
            Controller.redirectActivity(LoginActivity.this, Registrazione.class);

        });
        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//              PopUpLogin popUpLogin = new PopUpLogin(LoginActivity.this);
//              popUpLogin.show();
                String mail = editText_mail.getText().toString().trim();  // Rimuovi eventuali spazi all'inizio e alla fine
                String password = editText_password.getText().toString().trim();

                if (!mail.isEmpty() && !password.isEmpty()) {
                    progress_bar_login.setVisibility(View.VISIBLE);
                    setAllClickable(linear_layout_login,false);
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
                Controller.redirectActivity(LoginActivity.this, VenditoreAstaRibasso.class);
            }
        });

    }
    public void handleLoginResult(String tipoUtente) {
        progress_bar_login.setVisibility(View.INVISIBLE);
        setAllClickable(linear_layout_login,true);
        Log.d("handleLoginResult", "valore di result : " + tipoUtente);
        if (tipoUtente != null) {
            if(tipoUtente.equals("acquirente")){
                Toast.makeText(this, "Trovato Acquirente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AcquirenteMainActivity.class);//test del login
                intent.putExtra("email", editText_mail.getText().toString());
                startActivity(intent);
            }else{
                Toast.makeText(this, "Trovato Venditore", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, VenditoreMainActivity.class);//test del login
                intent.putExtra("email", editText_mail.getText().toString());
                startActivity(intent);
            }

            // L'utente è stato trovato
            // Esegui le azioni necessarie per il login
        } else {
            // L'utente non è stato trovato
            // Mostra un messaggio di errore o esegui altre azioni necessarie
            Toast.makeText(this, "Non trovato", Toast.LENGTH_SHORT).show();

        }
    }




}