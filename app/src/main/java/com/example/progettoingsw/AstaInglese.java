
package com.example.progettoingsw;

import static com.example.progettoingsw.MainActivity.redirectActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class AstaInglese extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asta_inglese);

        MaterialButton bottoneConferma = (MaterialButton) findViewById(R.id.bottoneConfermaAstaInglese);
        MaterialButton bottoneBack = (MaterialButton) findViewById(R.id.bottoneBackAstaInglese);


        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                redirectActivity(AstaInglese.this, HomeUtente.class);
            }
        });

       /* bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                redirectActivity(AstaInglese.this, ???.class);
            }
        }); */
    }
}

