package com.example.progettoingsw;

import static com.example.progettoingsw.MainActivity.redirectActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;


public class AstaRibasso extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asta_ribasso);

        MaterialButton bottoneConferma = (MaterialButton) findViewById(R.id.bottoneConfermaAstaRibasso);
        MaterialButton bottoneBack = (MaterialButton) findViewById(R.id.bottoneBackAstaRibasso);


        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                redirectActivity(AstaRibasso.this, HomeUtente.class);
            }
        });

       /* bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                redirectActivity(AstaInglese.this, ???.class);
            }
        }); */
    }
}

