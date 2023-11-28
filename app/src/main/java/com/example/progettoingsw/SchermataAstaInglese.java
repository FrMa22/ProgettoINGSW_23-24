package com.example.progettoingsw;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaInglese extends AppCompatActivity {
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
                controller.redirectActivity(SchermataAstaInglese.this, HomeUtente.class);
            }
        });

        bottoneProfiloVenditore = (MaterialButton) findViewById(R.id.bottoneProfiloVenditoreSchermataAstaInglese);

        bottoneProfiloVenditore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(SchermataAstaInglese.this, ProfiloVenditore.class);
            }
        });

        TextView offertaAttuale= (TextView) findViewById(R.id.textViewOffertaAttualeSchermataAstaInglese);
        bottoneNuovaOfferta = (ImageButton) findViewById(R.id.bottoneOffertaSchermataAstaInglese);

        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog popUpNuovaOffertaDialog = new Dialog(SchermataAstaInglese.this);
                popUpNuovaOffertaDialog.setContentView(R.layout.pop_up_nuova_offerta);
                MaterialButton bottoneAnnullaPopuP=(MaterialButton) popUpNuovaOffertaDialog.findViewById(R.id.bottoneAnnullaPopUpAsta);;
                MaterialButton bottoneConfermaPopUP=(MaterialButton) popUpNuovaOffertaDialog.findViewById(R.id.bottoneConfermaPopUpAsta);;
                EditText nuovaOfferta= (EditText) popUpNuovaOffertaDialog.findViewById(R.id.editTextNuovaOffertaAsta);
                TextView testoErrore = (TextView) popUpNuovaOffertaDialog.findViewById(R.id.textViewErrorePrezzoPopUp);
                nuovaOfferta.setText(offertaAttuale.getText());

                bottoneAnnullaPopuP.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popUpNuovaOffertaDialog.dismiss();
                    }
                });

                bottoneConfermaPopUP.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(nuovaOfferta.getText().toString()) > Integer.parseInt(offertaAttuale.getText().toString())) {
                            offertaAttuale.setText(nuovaOfferta.getText());
                            popUpNuovaOffertaDialog.dismiss();

                        }
                        else {
                            testoErrore.setText("Offerta non valida");
                        }
                    }
                });
                popUpNuovaOffertaDialog.show();
            }
        });
    }
}
