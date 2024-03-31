package com.example.progettoingsw.gui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private SchermataAstaInglese schermataAstaInglese;
    private SchermataAstaInversa schermataAstaInversa;
    private LinearLayout linear_layout_prezzo_attuale_popup_nuova_offerta;
    private LinearLayout linear_layout_rialzo_minimo_popup_nuova_offerta;
    private View view_popup_nuova_offerta;
    private String rialzoMin;
    private TextView TextViewPrezzoRialzoMinimo;

    public PopUpNuovaOfferta(Context context, String emailOfferente, int id_asta, String prezzoVecchio, String rialzoMin, SchermataAstaInglese schermataAstaInglese) {
        super(context);
        this.emailOfferente = emailOfferente;
        this.id_asta = id_asta;
        this.prezzoVecchio = prezzoVecchio;
        this.rialzoMin = rialzoMin;
        this.schermataAstaInglese = schermataAstaInglese;
    }
    public PopUpNuovaOfferta(Context context, String emailOfferente, int id_asta, String prezzoVecchio, SchermataAstaInversa schermataAstaInversa) {
        super(context);
        this.emailOfferente = emailOfferente;
        this.id_asta = id_asta;
        this.prezzoVecchio = prezzoVecchio;
        this.schermataAstaInversa = schermataAstaInversa;
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
        linear_layout_prezzo_attuale_popup_nuova_offerta = findViewById(R.id.linear_layout_prezzo_attuale_popup_nuova_offerta);
        linear_layout_rialzo_minimo_popup_nuova_offerta = findViewById(R.id.linear_layout_rialzo_minimo_popup_nuova_offerta);
        view_popup_nuova_offerta = findViewById(R.id.view_popup_nuova_offerta);
        TextViewPrezzoRialzoMinimo = findViewById(R.id.TextViewPrezzoRialzoMinimo);

        if(schermataAstaInglese!=null){
            Integer minimaOffeta = Math.round(Float.parseFloat(rialzoMin) + Float.parseFloat(prezzoVecchio));
            TextViewPrezzoRialzoMinimo.setText(minimaOffeta.toString());
        }else{
            linear_layout_rialzo_minimo_popup_nuova_offerta.setVisibility(View.GONE);
            view_popup_nuova_offerta.setVisibility(View.GONE);
        }
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
                    //caso in cui popup è per un'asta inglese
                    if(schermataAstaInglese != null){
                        Float minimaOffeta = (Float.parseFloat(rialzoMin) + Float.parseFloat(prezzoVecchio));
                        if(offertaAttuale<=offertaVecchia) {
                            Toast.makeText(getContext(), "Attenzione! L'offerta deve superare il prezzo attuale dell'asta.", Toast.LENGTH_SHORT).show();
                        } else if(offertaAttuale < minimaOffeta){
                            Toast.makeText(getContext(), "Attenzione! L'offerta deve almeno eguagliare il rialzo minimo.", Toast.LENGTH_SHORT).show();
                        }else{
                            astaIngleseDAO = new AstaIngleseDAO();
                            astaIngleseDAO.openConnection();
                            astaIngleseDAO.partecipaAstaInglese(id_asta,emailOfferente,offertaAttuale);
                            astaIngleseDAO.closeConnection();
                            Toast.makeText(getContext(), "Partecipazione aggiunta con successo!", Toast.LENGTH_SHORT).show();
                            schermataAstaInglese.handlePopUp();
                            dismiss();
                        }
                    }else if(schermataAstaInversa != null){
                        if(offertaAttuale>=offertaVecchia) {
                            Toast.makeText(getContext(), "Attenzione! L'offerta deve essere inferiore al prezzo attuale dell'asta.", Toast.LENGTH_SHORT).show();
                        }else{
                            astaInversaDAO = new AstaInversaDAO();
                            astaInversaDAO.openConnection();
                            astaInversaDAO.partecipaAstaInversa(id_asta,emailOfferente,offertaAttuale);
                            astaInversaDAO.closeConnection();
                            Toast.makeText(getContext(), "Partecipazione aggiunta con successo!", Toast.LENGTH_SHORT).show();
                            schermataAstaInversa.setPrezzo(Math.round(offertaAttuale));
                            dismiss();
                        }
                    }

            }
        }
    }
}
