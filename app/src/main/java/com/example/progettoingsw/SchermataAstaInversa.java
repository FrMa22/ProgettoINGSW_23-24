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

public class SchermataAstaInversa extends AppCompatActivity {
    Controller controller;
    MaterialButton bottoneBack;
    MaterialButton bottoneProfiloAcquirente;
    ImageButton bottoneNuovaOfferta;

    ImageButton bottonePreferito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inversa);
        bottoneBack = (MaterialButton) findViewById(R.id.bottoneBackSchermataAstaInversa);

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(SchermataAstaInversa.this, HomeUtente.class);
            }
        });

        bottoneProfiloAcquirente = (MaterialButton) findViewById(R.id.bottoneProfiloAcquirenteSchermataAstaInversa);

        bottoneProfiloAcquirente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                controller.redirectActivity(SchermataAstaInversa.this, ProfiloAcquirente.class);
            }
        });

        TextView offertaAttuale= (TextView) findViewById(R.id.textViewOffertaAttualeSchermataAstaInversa);
        bottoneNuovaOfferta = (ImageButton) findViewById(R.id.bottoneOffertaSchermataAstaInversa);

        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog popUpNuovaOffertaDialog = new Dialog(SchermataAstaInversa.this);
                popUpNuovaOffertaDialog.setContentView(R.layout.pop_up_nuova_offerta);
                popUpNuovaOffertaDialog.show();
                MaterialButton bottoneAnnullaPopuP=(MaterialButton) popUpNuovaOffertaDialog.findViewById(R.id.bottoneAnnullaPopUpAsta);
                MaterialButton bottoneConfermaPopUP=(MaterialButton) popUpNuovaOffertaDialog.findViewById(R.id.bottoneConfermaPopUpAsta);
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
                        else{
                            testoErrore.setText("Offerta non valida");
                        }
                    }
                });
            }
        });
    }
}


