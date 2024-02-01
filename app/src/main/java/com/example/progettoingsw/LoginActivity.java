package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends GestoreComuniImplementazioni {

    Button bottone;
    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        controller = new Controller();


        TextView registrazione = (TextView) findViewById(R.id.TextViewRegistrati);
        Button bottoneLogin = (Button) findViewById(R.id.bottonelogin);


        registrazione.setOnClickListener(v -> {
            //apre schermata registrazione
            controller.redirectActivity(LoginActivity.this, Registrazione.class);
        });

        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                controller.redirectActivity(LoginActivity.this, PopUpLogin.class);
                PopUpLogin popUpLogin = new PopUpLogin(LoginActivity.this);
                popUpLogin.show();
                /*Dialog dialog = new Dialog(LoginActivity.this);
                // Imposta il layout personalizzato per il popup
                dialog.setContentView(R.layout.pop_up_login);
                // Imposta uno sfondo trasparente con opacità al 50%
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setDimAmount(0.75f); // Imposta il livello di opacità
                // Mostra il dialog
                dialog.show();*/
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