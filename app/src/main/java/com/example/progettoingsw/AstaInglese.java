
package com.example.progettoingsw;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class AstaInglese extends AppCompatActivity {
    MaterialButton bottoneConferma;
    ImageButton bottoneBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asta_inglese);


        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaInglese);
        bottoneBack =  findViewById(R.id.bottoneBackAstaInglese);


        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Controller.redirectActivity(AstaInglese.this, FragmentHomeUtente.class);
            }
        });

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

