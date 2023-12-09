package com.example.progettoingsw;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class PopUpNuovaOfferta extends AppCompatActivity {
    Controller controller;
    TextView prezzoAttuale;
    EditText nuovoPrezzo;
    MaterialButton annullaPopUpOfferta;
    MaterialButton confermaPopUpOfferta;
    String textViewPrezzo;
    String tipo ;
    TextView errore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_nuova_offerta);

        controller = new Controller();


        DisplayMetrics metricaDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metricaDisplay);
        int width = metricaDisplay.widthPixels;
        int height = metricaDisplay.heightPixels;
        int offsetY = (int) (height * 0.15);
        getWindow().setLayout((int) (width * .763), (int) (height * .4475));
        getWindow().setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        getWindow().getAttributes().y = offsetY;

        prezzoAttuale= (TextView) findViewById(R.id.TextViewPrezzoOffertaAsta);
        nuovoPrezzo= (EditText) findViewById(R.id.EditTextPrezzoNuovaOffertaAsta);
        annullaPopUpOfferta= (MaterialButton) findViewById(R.id.bottoneAnnullaPopUpAsta);
        confermaPopUpOfferta= (MaterialButton) findViewById(R.id.bottoneConfermaPopUpAsta);
        errore= (TextView)findViewById(R.id.textViewErrorePrezzoPopUp) ;

        Intent intent = getIntent();
        if (intent != null) {
            textViewPrezzo = intent.getStringExtra("textViewPrezzo");
           prezzoAttuale.setText(textViewPrezzo);
        }
        tipo = intent.getStringExtra("tipoPopUp");
        confermaPopUpOfferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( tipo.equals("inversa")){
                    if ((Integer.parseInt(textViewPrezzo))>(Integer.parseInt(nuovoPrezzo.getText().toString()))){
                        Intent intent = new Intent(PopUpNuovaOfferta.this, SchermataAstaInversa.class);
                        intent.putExtra("editTextPrezzo", nuovoPrezzo.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        errore.setText("offerta non valida");
                    }
                } else if (tipo.equals("inglese")) {
                    if ((Integer.parseInt(textViewPrezzo))<(Integer.parseInt(nuovoPrezzo.getText().toString()))){
                        Intent intent = new Intent(PopUpNuovaOfferta.this, SchermataAstaInglese.class);
                        intent.putExtra("editTextPrezzo", nuovoPrezzo.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        errore.setText("offerta non valida");
                    }
                }
            }
        });
     annullaPopUpOfferta.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (tipo.equals("inversa")) {
                 controller.redirectActivity(PopUpNuovaOfferta.this, SchermataAstaInversa.class);
             } else if (tipo.equals("inglese")) {
                 controller.redirectActivity(PopUpNuovaOfferta.this, SchermataAstaInglese.class);
             }
         }
     });
    }
}
