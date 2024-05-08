package com.example.progettoingsw.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.viewmodel.PopUpNuovaOffertaViewModel;

public class PopUpNuovaOfferta extends DialogPersonalizzato implements View.OnClickListener {
    private PopUpNuovaOffertaViewModel popUpNuovaOffertaViewModel;
    private PopupDismissListener popupDismissListener;
    Controller controller;
    TextView textviewPrezzoAttuale;
    EditText textviewNuovoPrezzo;
    Button bottoneAnnullaPopUpOfferta;
    Button bottoneConfermaPopUpOfferta;
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
    private FragmentActivity fragmentActivity;

    public PopUpNuovaOfferta(FragmentActivity activity, PopupDismissListener dismissListener) {
        super(activity);
        this.fragmentActivity = activity;
        this.popupDismissListener = dismissListener;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_nuova_offerta);

        textviewPrezzoAttuale = (TextView) findViewById(R.id.TextViewPrezzoOffertaAsta);
        textviewNuovoPrezzo = (EditText) findViewById(R.id.EditTextPrezzoNuovaOffertaAsta);
        bottoneAnnullaPopUpOfferta = findViewById(R.id.bottoneAnnullaPopUpAsta);
        bottoneConfermaPopUpOfferta = findViewById(R.id.bottoneConfermaPopUpAsta);
        textviewPrezzoAttuale.setText(prezzoVecchio);
        prezzoVecchio = textviewPrezzoAttuale.getText().toString();
        linear_layout_prezzo_attuale_popup_nuova_offerta = findViewById(R.id.linear_layout_prezzo_attuale_popup_nuova_offerta);
        linear_layout_rialzo_minimo_popup_nuova_offerta = findViewById(R.id.linear_layout_rialzo_minimo_popup_nuova_offerta);
        TextViewPrezzoRialzoMinimo = findViewById(R.id.TextViewPrezzoRialzoMinimo);



        popUpNuovaOffertaViewModel = new ViewModelProvider(fragmentActivity).get(PopUpNuovaOffertaViewModel.class);

        osservaTipoAstaChecked();
        osservamessaggioErroreOfferta();
        osservaOffertaValida();
        osservaIsPartecipazioneAvvenuta();
        Log.d("PopUpNuovaOfferta onCreate", "osservatori riaperti");
        popUpNuovaOffertaViewModel.checkTipoAsta();
        textviewPrezzoAttuale.setText(String.valueOf(popUpNuovaOffertaViewModel.getPrezzoVecchio()));



//        if(schermataAstaInglese!=null){
//            Integer minimaOffeta = Math.round(Float.parseFloat(rialzoMin) + Float.parseFloat(prezzoVecchio));
//            TextViewPrezzoRialzoMinimo.setText(minimaOffeta.toString());
//        }else{
//            linear_layout_rialzo_minimo_popup_nuova_offerta.setVisibility(View.GONE);
//            view_popup_nuova_offerta.setVisibility(View.GONE);
//        }
        bottoneConfermaPopUpOfferta.setOnClickListener(this);
        bottoneAnnullaPopUpOfferta.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAnnullaPopUpAsta) {
            //dismiss();
            popUpNuovaOffertaViewModel.resetErroriNuovaOfferta(fragmentActivity);
            dismissPopup();
        } else if (v.getId() == R.id.bottoneConfermaPopUpAsta) {
            offerta = textviewNuovoPrezzo.getText().toString();
            Log.d("PopUpNuovaOfferta", "premuto conferma");

            popUpNuovaOffertaViewModel.checkOfferta(offerta);




        }
    }

    public interface PopupDismissListener {
        void onPopupDismissed();
    }
    public void setPopupDismissListener(PopupDismissListener listener) {
        this.popupDismissListener = listener;
    }


    public void setImpostazioniPerAstaInglese(){
        TextViewPrezzoRialzoMinimo.setText(popUpNuovaOffertaViewModel.getMinimaOfferta());
    }
    public void setImpostazioniPerAstainversa(){
        linear_layout_rialzo_minimo_popup_nuova_offerta.setVisibility(View.GONE);
        //view_popup_nuova_offerta.setVisibility(View.GONE);
    }
    public void osservamessaggioErroreOfferta(){
        popUpNuovaOffertaViewModel.messaggioErroreOfferta.observe(fragmentActivity, (messaggio) -> {
            Log.d("osservamessaggioErroreOfferta", ""+messaggio);
            if(popUpNuovaOffertaViewModel.isMessaggioErroreOfferta()){
                Log.d("osservamessaggioErroreOfferta", "entrato nel if con " + messaggio + " e " + popUpNuovaOffertaViewModel.getMessaggioErrore());
                textviewNuovoPrezzo.setError(popUpNuovaOffertaViewModel.getMessaggioErrore());
            }
        });
    }
    public void osservaOffertaValida(){
        popUpNuovaOffertaViewModel.offertaValida.observe( fragmentActivity, (messaggio) -> {
            Log.d("osservaOffertaValida", "avviato osservatore");
            if(popUpNuovaOffertaViewModel.isOffertaValida()){
                Log.d("osservaOffertaValida", "entrato");
                popUpNuovaOffertaViewModel.proseguiPartecipazione(offerta);
            }
        });
    }
    public void osservaTipoAstaChecked(){
        popUpNuovaOffertaViewModel.tipoAstaChecked.observe(fragmentActivity, (messaggio) ->{
            if(popUpNuovaOffertaViewModel.isTipoAstaChecked()){
                if(popUpNuovaOffertaViewModel.isAstaInglese()){
                    setImpostazioniPerAstaInglese();
                }else{
                    setImpostazioniPerAstainversa();
                }
            }
        } );
    }
    public void dismissPopup() {
        dismiss();
        if (popupDismissListener != null) {
            popupDismissListener.onPopupDismissed();
        }
    }
    public void osservaIsPartecipazioneAvvenuta(){
        Log.d("osservaIsPartecipazioneAvvenuta","osservaIsPartecipazioneAvvenuta");
        popUpNuovaOffertaViewModel.isPartecipazioneAvvenuta.observe(fragmentActivity, (messaggio) -> {
            if(popUpNuovaOffertaViewModel.getIsPartecipazioneAvvenuta()){
                String messaggioAcquisto = popUpNuovaOffertaViewModel.getMessaggioPartecipazioneAsta();
                if(messaggioAcquisto!=null && !messaggioAcquisto.isEmpty()){
                    Toast.makeText(getContext(),messaggioAcquisto , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"result null" , Toast.LENGTH_SHORT).show();
                }
                Log.d("osservaIsPartecipazioneAvvenuta","prima di dismiss");
                //dismiss();
                popUpNuovaOffertaViewModel.resetErroriNuovaOfferta(fragmentActivity);
                dismissPopup();
            }else{
                Toast.makeText(getContext(), "Errore nell'offerta!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
