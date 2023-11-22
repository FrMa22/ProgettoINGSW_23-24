package com.example.progettoingsw;


import android.os.Bundle;
import android.view.View;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;


public class AstaRibasso extends AppCompatActivity{

    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asta_ribasso);

        controller = new Controller();

        MaterialButton bottoneConferma = (MaterialButton) findViewById(R.id.bottoneConfermaAstaRibasso);
        MaterialButton bottoneBack = (MaterialButton) findViewById(R.id.bottoneBackAstaRibasso);


        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(AstaRibasso.this, HomeUtente.class);
            }
        });

       /* bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                redirectActivity(AstaInglese.this, ???.class);
            }
        }); */
    }
}

