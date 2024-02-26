package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.progettoingsw.DAO.LoginDAO;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends GestoreComuniImplementazioni {

    Button bottone;
    Controller controller;
    EditText editText_mail;
    EditText editText_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        controller = new Controller();
        TextView registrazione = (TextView) findViewById(R.id.TextViewRegistrati);
        Button bottoneLogin = (Button) findViewById(R.id.bottonelogin);
        editText_mail = findViewById(R.id.editTextEmail);
        editText_password = findViewById(R.id.editTextPassword);

        LoginDAO logindao = new LoginDAO();

        registrazione.setOnClickListener(v -> {
            //apre schermata registrazione
            controller.redirectActivity(LoginActivity.this, Registrazione.class);

        });
        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                controller.redirectActivity(LoginActivity.this, PopUpLogin.class);
//                PopUpLogin popUpLogin = new PopUpLogin(LoginActivity.this);
//                popUpLogin.show();
                String mail = editText_mail.getText().toString();
                String password = editText_password.getText().toString();

                // Chiamata al metodo per cercare nel database
                logindao.openConnection();
                logindao.findUser(mail, password);
            }
        });

        bottone = findViewById(R.id.button);
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.redirectActivity(LoginActivity.this, AstaInglese.class);
            }
        });

    }

}