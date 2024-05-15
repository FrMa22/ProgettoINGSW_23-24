package com.example.progettoingsw.view;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.viewmodel.PopUpNuovaOffertaViewModel;

public class PopUpNuovaOfferta extends DialogPersonalizzato implements View.OnClickListener {
    private PopUpNuovaOffertaViewModel popUpNuovaOffertaViewModel;
    private PopupDismissListener popupDismissListener;
    TextView textviewPrezzoAttuale;
    EditText textviewNuovoPrezzo;
    Button bottoneAnnullaPopUpOfferta;
    Button bottoneConfermaPopUpOfferta;
    String offerta;
    String prezzoVecchio;
    private LinearLayout linear_layout_prezzo_attuale_popup_nuova_offerta;
    private LinearLayout linear_layout_rialzo_minimo_popup_nuova_offerta;
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
        popUpNuovaOffertaViewModel.checkTipoAsta();
        textviewPrezzoAttuale.setText(String.valueOf(popUpNuovaOffertaViewModel.getPrezzoVecchio()));



        bottoneConfermaPopUpOfferta.setOnClickListener(this);
        bottoneAnnullaPopUpOfferta.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAnnullaPopUpAsta) {
            popUpNuovaOffertaViewModel.resetErroriNuovaOfferta(fragmentActivity);
            dismissPopup();
        } else if (v.getId() == R.id.bottoneConfermaPopUpAsta) {
            offerta = textviewNuovoPrezzo.getText().toString();

            popUpNuovaOffertaViewModel.checkOfferta(offerta);




        }
    }

    public interface PopupDismissListener {
        void onPopupDismissed();
    }


    public void setImpostazioniPerAstaInglese(){
        TextViewPrezzoRialzoMinimo.setText(popUpNuovaOffertaViewModel.getMinimaOfferta());
    }
    public void setImpostazioniPerAstainversa(){
        linear_layout_rialzo_minimo_popup_nuova_offerta.setVisibility(View.GONE);
    }
    public void osservamessaggioErroreOfferta(){
        popUpNuovaOffertaViewModel.messaggioErroreOfferta.observe(fragmentActivity, (messaggio) -> {
            if(popUpNuovaOffertaViewModel.isMessaggioErroreOfferta()){
                textviewNuovoPrezzo.setError(popUpNuovaOffertaViewModel.getMessaggioErrore());
            }
        });
    }
    public void osservaOffertaValida(){
        popUpNuovaOffertaViewModel.offertaValida.observe( fragmentActivity, (messaggio) -> {
            if(popUpNuovaOffertaViewModel.isOffertaValida()){
                popUpNuovaOffertaViewModel.proseguiPartecipazione(offerta);
            }
        });
    }
    public void osservaTipoAstaChecked(){
        popUpNuovaOffertaViewModel.tipoAstaChecked.observe(fragmentActivity, (messaggio) ->{
            if(popUpNuovaOffertaViewModel.isTipoAstaChecked()){
                if(popUpNuovaOffertaViewModel.isAstaInglese()){
                    setImpostazioniPerAstaInglese();
                }else if(popUpNuovaOffertaViewModel.isAstaInversa()){
                    setImpostazioniPerAstainversa();
                }
            }
        } );
    }
    public void dismissPopup() {
        onBackPressed();
        if (popupDismissListener != null) {
            popupDismissListener.onPopupDismissed();
        }
    }
    public void osservaIsPartecipazioneAvvenuta(){
        popUpNuovaOffertaViewModel.messaggioPartecipazioneAsta.observe(fragmentActivity, (messaggio) -> {
            if(popUpNuovaOffertaViewModel.getIsPartecipazioneAvvenuta()){
                Toast.makeText(getContext(),messaggio , Toast.LENGTH_SHORT).show();
                popUpNuovaOffertaViewModel.resetErroriNuovaOfferta(fragmentActivity);
                dismissPopup();
            }
        });
    }
}
