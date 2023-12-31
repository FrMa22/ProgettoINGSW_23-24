package com.example.progettoingsw;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaRibasso extends AppCompatActivity {
    Controller controller;
    ImageButton bottoneBack;
    MaterialButton bottoneProfiloVenditore;
    ImageButton bottoneNuovaOfferta;

    ImageButton bottonePreferito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_ribasso);
        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaRibasso);

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Controller.redirectActivity(SchermataAstaRibasso.this, HomeUtente.class);
            }
        });

        bottoneProfiloVenditore = (MaterialButton) findViewById(R.id.bottoneProfiloVenditoreSchermataAstaRibasso);

        bottoneProfiloVenditore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Controller.redirectActivity(SchermataAstaRibasso.this, ProfiloVenditore.class);
            }
        });

        TextView prezzoAttuale= (TextView) findViewById(R.id.textViewPrezzoAttualeSchermataAstaRibasso);
        bottoneNuovaOfferta = (ImageButton) findViewById(R.id.bottoneOffertaSchermataAstaRibasso);

        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog popUpConfermaOffertaDialog = new Dialog(SchermataAstaRibasso.this);
                popUpConfermaOffertaDialog.setContentView(R.layout.pop_up_conferma_offerta);
                popUpConfermaOffertaDialog.show();
                MaterialButton bottoneAnnullaPopuP=(MaterialButton) popUpConfermaOffertaDialog.findViewById(R.id.bottoneAnnullaPopUpAsta);;
                MaterialButton bottoneConfermaPopUP=(MaterialButton) popUpConfermaOffertaDialog.findViewById(R.id.bottoneConfermaPopUpAsta);;
                TextView nuovaOfferta= (TextView) popUpConfermaOffertaDialog.findViewById(R.id.TextViewOffertaAsta);

                bottoneAnnullaPopuP.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popUpConfermaOffertaDialog.dismiss();
                    }
                });

            }
        });
    }
}

