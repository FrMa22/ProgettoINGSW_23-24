package com.example.progettoingsw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaInversa extends GestoreComuniImplementazioni {
    Controller controller;
    ImageButton bottoneBack;
    MaterialButton bottoneProfiloAcquirente;
    ImageButton bottoneNuovaOfferta;
    String prezzo;

    ImageButton bottonePreferito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inversa);
        bottoneBack = (ImageButton) findViewById(R.id.bottoneBackSchermataAstaInversa);

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(SchermataAstaInversa.this, FragmentHomeUtente.class);
            }
        });

        bottoneProfiloAcquirente = (MaterialButton) findViewById(R.id.bottoneProfiloAcquirenteSchermataAstaInversa);

        bottoneProfiloAcquirente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(SchermataAstaInversa.this, ProfiloAcquirente.class);
            }
        });

        TextView offertaAttuale= (TextView) findViewById(R.id.textViewOffertaAttualeSchermataAstaInversa);
        TextView prezzoAttuale = (TextView) findViewById(R.id. textViewPrezzpAttualeSchermataAstaInversa) ;
        bottoneNuovaOfferta = (ImageButton) findViewById(R.id.bottoneOffertaSchermataAstaInversa);

        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String tipo = "inversa";
                Intent intent = new Intent(SchermataAstaInversa.this, PopUpNuovaOfferta.class);
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


