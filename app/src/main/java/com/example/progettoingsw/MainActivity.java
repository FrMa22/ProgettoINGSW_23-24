package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    Button bottone;
    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new Controller();


        TextView registrazione = (TextView) findViewById(R.id.TextViewRegistrati);
        MaterialButton bottoneLogin = (MaterialButton) findViewById(R.id.bottonelogin);


        registrazione.setOnClickListener(v -> {
            //apre schermata registrazione
            controller.redirectActivity(MainActivity.this, Registrazione.class);
        });

        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(MainActivity.this, PopUpLogin.class);
                /*Dialog dialog = new Dialog(MainActivity.this);
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
                Controller.redirectActivity(MainActivity.this, RicercaSenzaRisultati.class);
            }
        });

    }

}