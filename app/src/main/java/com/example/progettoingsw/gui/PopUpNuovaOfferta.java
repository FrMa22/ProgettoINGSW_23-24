package com.example.progettoingsw.gui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class PopUpNuovaOfferta extends Dialog implements View.OnClickListener {
    Controller controller;
    TextView textviewPrezzoAttuale;
    EditText textviewNuovoPrezzo;
    MaterialButton textviewAnnullaPopUpOfferta;
    MaterialButton textviewConfermaPopUpOfferta;
    String textViewPrezzo;
    String tipo ;
    String offerta;
    String prezzoVecchio;
    String emailOfferente;
    String tipoAsta;
    int id_asta;
    private AstaIngleseDAO astaIngleseDAO;
    private AstaInversaDAO astaInversaDAO;
    public PopUpNuovaOfferta(Context context, String emailOfferente, int id_asta,String tipoAsta, String prezzoVecchio) {
        super(context);
        this.emailOfferente = emailOfferente;
        this.id_asta = id_asta;
        this.tipoAsta = tipoAsta;
        this.prezzoVecchio = prezzoVecchio;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_nuova_offerta);

        controller = new Controller();



        textviewPrezzoAttuale = (TextView) findViewById(R.id.TextViewPrezzoOffertaAsta);
        textviewNuovoPrezzo = (EditText) findViewById(R.id.EditTextPrezzoNuovaOffertaAsta);
        textviewAnnullaPopUpOfferta = (MaterialButton) findViewById(R.id.bottoneAnnullaPopUpAsta);
        textviewConfermaPopUpOfferta = (MaterialButton) findViewById(R.id.bottoneConfermaPopUpAsta);
        Log.d("PopupNuovaOfferta", "prezzo vecchio : " + prezzoVecchio);
        textviewPrezzoAttuale.setText(prezzoVecchio);
        prezzoVecchio = textviewPrezzoAttuale.getText().toString();

        textviewConfermaPopUpOfferta.setOnClickListener(this);
        textviewAnnullaPopUpOfferta.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAnnullaPopUpAsta) {
            dismiss();
        } else if (v.getId() == R.id.bottoneConfermaPopUpAsta) {
            offerta = textviewNuovoPrezzo.getText().toString();
            if(offerta.isEmpty()){
                Toast.makeText(getContext(), "Si prega di inserire un offerta!", Toast.LENGTH_SHORT).show();
            }else{
                    Float offertaAttuale = Float.parseFloat(offerta);
                    Float offertaVecchia = Float.parseFloat(prezzoVecchio);
                    if(tipoAsta.equals("inglese")){
                        if(offertaAttuale<=offertaVecchia) {
                            Toast.makeText(getContext(), "Attenzione! L'offerta deve superare il prezzo attuale dell'asta.", Toast.LENGTH_SHORT).show();
                        }else{
                            astaIngleseDAO = new AstaIngleseDAO();
                            astaIngleseDAO.openConnection();
                            astaIngleseDAO.partecipaAstaInglese(id_asta,emailOfferente,offertaAttuale);
                            astaIngleseDAO.closeConnection();
                            Toast.makeText(getContext(), "Partecipazione aggiunta con successo!", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }else if(tipoAsta.equals("inversa")){
                        if(offertaAttuale>=offertaVecchia) {
                            Toast.makeText(getContext(), "Attenzione! L'offerta deve essere inferiore al prezzo attuale dell'asta.", Toast.LENGTH_SHORT).show();
                        }else{
                            astaInversaDAO = new AstaInversaDAO();
                            astaInversaDAO.openConnection();
                            astaInversaDAO.partecipaAstaInversa(id_asta,emailOfferente,offertaAttuale);
                            astaInversaDAO.closeConnection();
                            Toast.makeText(getContext(), "Partecipazione aggiunta con successo!", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }

            }
        }
    }
}
