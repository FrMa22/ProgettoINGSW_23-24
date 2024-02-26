package com.example.progettoingsw.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaInglese extends GestoreComuniImplementazioni {
    Controller controller;
    MaterialButton bottoneBack;
    MaterialButton bottoneProfiloVenditore;
    ImageButton bottoneNuovaOfferta;


    ImageButton bottonePreferito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inglese);
        bottoneBack = (MaterialButton) findViewById(R.id.bottoneBackSchermataAstaInglese);
        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(SchermataAstaInglese.this, AcquirenteFragmentHome.class);
            }
        });

        bottoneProfiloVenditore = (MaterialButton) findViewById(R.id.bottoneProfiloVenditoreSchermataAstaInglese);

        bottoneProfiloVenditore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(SchermataAstaInglese.this, ProfiloVenditore.class);
            }
        });

        TextView offertaAttuale= (TextView) findViewById(R.id.textViewOffertaAttualeSchermataAstaInglese);
        TextView prezzoAttuale = (TextView) findViewById(R.id.textViewPrezzoAttualeSchermataAstaInglese ) ;
        bottoneNuovaOfferta = (ImageButton) findViewById(R.id.bottoneOffertaSchermataAstaInglese);
        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String tipo = "inglese";
                Intent intent = new Intent(SchermataAstaInglese.this, PopUpNuovaOfferta.class);
                intent.putExtra("textViewPrezzo", prezzoAttuale.getText().toString());
                intent.putExtra("tipoPopUp", tipo);
                startActivity(intent);
            }
        });
        String valoreDaModificare = getIntent().getStringExtra("editTextPrezzo");
        if (valoreDaModificare != null) {
            prezzoAttuale.setText(valoreDaModificare);
        }
    }
}
