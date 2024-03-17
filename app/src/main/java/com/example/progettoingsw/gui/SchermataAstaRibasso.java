package com.example.progettoingsw.gui;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.DAO.AstaRibassoDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaRibassoItem;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaRibasso extends GestoreComuniImplementazioni {
    Controller controller;
    ImageButton bottoneBack;
    MaterialButton bottoneNuovaOfferta;
    ImageButton bottonePreferito;
    private int id;
    private String email;
    private String tipoUtente;
    TextView textViewNomeProdotto;
    ImageView imageViewProdotto;
    TextView textViewDescrizione;
    TextView textViewProssimoDecremento;
    TextView textViewOffertaAttuale;
    TextView textViewVenditore;
    private AstaRibassoDAO astaRibassoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_ribasso);
        astaRibassoDAO = new AstaRibassoDAO(this);

        id = getIntent().getIntExtra("id",0);
        email = getIntent().getStringExtra("email");
        tipoUtente = getIntent().getStringExtra("tipoUtente");
        Toast.makeText(this, "l'id è " + id + ", la mail è " + email + ", il tipoutente è " + tipoUtente, Toast.LENGTH_SHORT).show();

        textViewNomeProdotto = findViewById(R.id.textViewNomeProdottoSchermataAstaRibasso);
        imageViewProdotto = findViewById(R.id.ImageViewSchermataAstaRibasso);
        textViewDescrizione = findViewById(R.id.textViewDescrizioneSchermataAstaRibasso);
        textViewProssimoDecremento = findViewById(R.id.textViewProssimoDecrementoAstaRibasso);
        textViewOffertaAttuale = findViewById(R.id.textViewOffertaAttualeSchermataAstaRibasso);
        textViewVenditore = findViewById(R.id.textViewVenditoreSchermataAstaRibasso);

        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaRibasso);
        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });


        bottoneNuovaOfferta =  findViewById(R.id.bottoneOffertaSchermataAstaRibasso);

        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog popUpConfermaOffertaDialog = new Dialog(SchermataAstaRibasso.this);
                popUpConfermaOffertaDialog.setContentView(R.layout.pop_up_conferma_offerta);
                popUpConfermaOffertaDialog.show();
                MaterialButton bottoneAnnullaPopuP=(MaterialButton) popUpConfermaOffertaDialog.findViewById(R.id.bottoneAnnullaPopUpAsta);;
                MaterialButton bottoneConfermaPopUP=(MaterialButton) popUpConfermaOffertaDialog.findViewById(R.id.bottoneConfermaPopUpAsta);;
                TextView offertaAttuale= (TextView) popUpConfermaOffertaDialog.findViewById(R.id.TextViewOffertaAsta);
                offertaAttuale.setText(textViewOffertaAttuale.getText().toString());

                bottoneAnnullaPopuP.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popUpConfermaOffertaDialog.dismiss();
                    }
                });
                bottoneConfermaPopUP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        astaRibassoDAO.openConnection();
                        astaRibassoDAO.acquistaAsta(id,email,Float.parseFloat(textViewOffertaAttuale.getText().toString()));
                        astaRibassoDAO.closeConnection();
                    }
                });

            }
        });

        astaRibassoDAO.openConnection();
        astaRibassoDAO.getAstaRibassoByID(id);
        astaRibassoDAO.closeConnection();


        textViewVenditore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVenditore = textViewVenditore.getText().toString();
                Intent intent=new Intent(SchermataAstaRibasso.this, ProfiloVenditore.class);
                intent.putExtra("email",emailVenditore);
                startActivity(intent);
            }
        });

    }
    public void setAstaData(AstaRibassoItem astaRibassoItem) {
        if (astaRibassoItem != null) {
            // Imposta i dati recuperati sui TextView e ImageView della tua activity
            textViewNomeProdotto.setText(astaRibassoItem.getNome());
            textViewDescrizione.setText(astaRibassoItem.getDescrizione());
            textViewOffertaAttuale.setText(String.valueOf(astaRibassoItem.getPrezzoAttuale()));
            textViewProssimoDecremento.setText("Da implementare");
            textViewVenditore.setText(astaRibassoItem.getEmailVenditore());
            // Imposta l'immagine solo se non è nulla
            if (astaRibassoItem.getImmagine() != null) {
                imageViewProdotto.setImageBitmap(astaRibassoItem.getImmagine());
            }
            // Altri dati da impostare...
        } else {
            // Gestisci il caso in cui non ci siano dati recuperati
            Log.d("Errore", "Impossibile recuperare i dati dell'asta");
        }
    }
}

