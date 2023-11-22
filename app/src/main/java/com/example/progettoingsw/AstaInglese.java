
package com.example.progettoingsw;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class AstaInglese extends AppCompatActivity {

    Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asta_inglese);

        controller = new Controller();

        MaterialButton bottoneConferma = (MaterialButton) findViewById(R.id.bottoneConfermaAstaInglese);
        MaterialButton bottoneBack = (MaterialButton) findViewById(R.id.bottoneBackAstaInglese);


        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(AstaInglese.this, HomeUtente.class);
            }
        });

       /* bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                redirectActivity(AstaInglese.this, ???.class);
            }
        }); */
    }
}

