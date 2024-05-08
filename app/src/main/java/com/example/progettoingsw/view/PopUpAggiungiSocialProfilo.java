package com.example.progettoingsw.view;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.viewmodel.FragmentProfiloViewModel;
import com.google.android.material.button.MaterialButton;

public class PopUpAggiungiSocialProfilo extends DialogPersonalizzato implements View.OnClickListener {
    private String email;
    private String tipoUtente;
    private FragmentProfilo fragmentProfilo;
    private PopupAggiungiSocialDismissListener popupDismissListener;
    MaterialButton bottoneChiudiRegistrazioneSocial;
    MaterialButton bottoneConfermaRegistrazioneSocial;
    EditText editTextLinkSocial;
    EditText editTextNomeSocial;
    private ProgressBar progressBarPopUpAggiungiSocialProfilo;
    private FragmentProfiloViewModel fragmentProfiloViewModel;


    public PopUpAggiungiSocialProfilo(FragmentProfilo fragmentProfilo, FragmentProfiloViewModel fragmentProfiloViewModel,PopupAggiungiSocialDismissListener popupAggiungiSocialDismissListener) {
        super(fragmentProfilo.getContext()); // Chiama il costruttore della superclasse Dialog
        this.fragmentProfilo = fragmentProfilo;
        this.fragmentProfiloViewModel=fragmentProfiloViewModel;
        this.popupDismissListener=popupAggiungiSocialDismissListener;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_aggiungi_social_profilo);







        // Riferimenti ai widget all'interno del pop-up
        bottoneChiudiRegistrazioneSocial = findViewById(R.id.bottoneChiudiRegistrazioneSocial);
        bottoneConfermaRegistrazioneSocial = findViewById(R.id.bottoneConfermaRegistrazioneSocial);
        editTextLinkSocial = findViewById(R.id.editTextNomeUtenteSocial);
        editTextNomeSocial = findViewById(R.id.editTextNomeSocial);
        progressBarPopUpAggiungiSocialProfilo = findViewById(R.id.progressBarPopUpAggiungiSocialProfilo);


        osservaMessaggioErroreLinkNuovo();
        osservaMessaggioErroreNomeNuovo();
        osservaIsSocialAggiunto();

        bottoneChiudiRegistrazioneSocial.setOnClickListener(this);

        bottoneConfermaRegistrazioneSocial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.bottoneChiudiRegistrazioneSocial) {
            fragmentProfiloViewModel.setApriPopUpAggiungiSocial(false);
            //dismissAggiungiSocialPopup();
            fragmentProfiloViewModel.resetErroriSocialAggiunti();
            dismiss(); // Chiude il dialog
        } else if (viewId == R.id.bottoneConfermaRegistrazioneSocial) {
            confermaRegistrazioneSocialProfilo();
        }
    }

    @Override
    public void onBackPressed(){
            bottoneChiudiRegistrazioneSocial.performClick();
    }
    private void confermaRegistrazioneSocialProfilo() {
        String nomeSocial = editTextNomeSocial.getText().toString().trim();
        String linkSocial = editTextLinkSocial.getText().toString().trim();

        fragmentProfiloViewModel.aggiungiSocialViewModel(nomeSocial,linkSocial);


        }


    public interface PopupAggiungiSocialDismissListener {
        void onPopupAggiungiSocialDismissed();
    }

    public void dismissAggiungiSocialPopup() {
        dismiss();
        if (popupDismissListener != null) {
            popupDismissListener.onPopupAggiungiSocialDismissed();
        }
    }

    public void setPopupDismissListener( PopupAggiungiSocialDismissListener listener) {
        this.popupDismissListener = listener;
    }


    public void messaggioErroreNomeNuovo(String messaggio){
        editTextNomeSocial.setError(messaggio);
    }
    public void osservaMessaggioErroreNomeNuovo() {
        fragmentProfiloViewModel.messaggioErroreNomeNuovo.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErroreNomeNuovo()) {
                messaggioErroreNomeNuovo(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }



    public void messaggioErroreLinkNuovo(String messaggio){
        editTextLinkSocial.setError(messaggio);
    }
    public void osservaMessaggioErroreLinkNuovo() {
        fragmentProfiloViewModel.messaggioErroreLinkNuovo.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErroreLinkNuovo()) {
                messaggioErroreLinkNuovo(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }

    public void osservaIsSocialAggiunto(){
        fragmentProfiloViewModel.isSocialAggiunto.observe(fragmentProfilo, (messaggio) -> {
            if(fragmentProfiloViewModel.getIsSocialAggiunto()){
                //dismiss();
                fragmentProfiloViewModel.resetErroriSocialAggiunti();
                dismissAggiungiSocialPopup();
            }else{
                Toast.makeText(getContext(), "Errore nei dati del social da aggiungere", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
