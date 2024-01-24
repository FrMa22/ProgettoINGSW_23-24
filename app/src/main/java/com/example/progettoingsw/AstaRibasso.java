package com.example.progettoingsw;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;


public class AstaRibasso extends AppCompatActivity{
    MaterialButton bottoneConferma;
    ImageButton bottoneBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asta_ribasso);


        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaRibasso);
        bottoneBack =  findViewById(R.id.bottoneBackAstaRibasso);


        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Controller.redirectActivity(AstaRibasso.this, FragmentHomeUtente.class);
            }
        });

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

