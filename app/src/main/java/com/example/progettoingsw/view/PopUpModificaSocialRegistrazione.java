package com.example.progettoingsw.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.viewmodel.RegistrazioneViewModel;

public class PopUpModificaSocialRegistrazione extends DialogPersonalizzato implements View.OnClickListener {

    private RegistrazioneViewModel registrazioneViewModel;
    private AppCompatButton bottoneAnnullaModifica;
    private AppCompatButton bottoneConfermaModifica;
    private AppCompatButton bottoneEliminaSocial;
    private String nome_vecchio;
    private String link_vecchio;
    private EditText edit_text_nome_social;
    private EditText edit_text_link_social;
    private RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi;
    private PopupModificaSocialRegistrazioneDismissListener popupDismissListener;

    public PopUpModificaSocialRegistrazione(Context context, RegistrazioneViewModel registrazioneViewModel, RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi, PopupModificaSocialRegistrazioneDismissListener popupDismissListener){
        super(context);
        this.registrazioneViewModel = registrazioneViewModel;
        this.registrazioneCampiFacoltativi = registrazioneCampiFacoltativi;
        this.popupDismissListener = popupDismissListener;
        setCanceledOnTouchOutside(false);
    }
    public interface PopupModificaSocialRegistrazioneDismissListener {
        void onPopupModificaSocialRegistrazioneDismissed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_modifica_social);




        edit_text_nome_social = findViewById(R.id.edit_text_nome_social);
        edit_text_link_social = findViewById(R.id.edit_text_link_social);

        //popUpModificaSocialDAO = new PopUpModificaSocialDAO(this,email,tipoUtente);

        bottoneAnnullaModifica = findViewById(R.id.bottoneAnnullaModifica);
        bottoneAnnullaModifica.setOnClickListener(this);
        bottoneConfermaModifica = findViewById(R.id.bottoneConfermaModifica);
        bottoneConfermaModifica.setOnClickListener(this);
        bottoneEliminaSocial = findViewById(R.id.bottoneEliminaSocial);
        bottoneEliminaSocial.setOnClickListener(this);

        osservaMessaggioErroreLinkNuovo();
        osservaMessaggioErroreNomeSocial();
        osservaIsSocialCambiato();
        osservaNomeSocial();
        osservaLinkSocial();
        registrazioneViewModel.recuperaNomeLinkSocial();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAnnullaModifica) {
            Toast.makeText(getContext(), "Annulla", Toast.LENGTH_SHORT).show();
            dismiss();
        } else if (v.getId() == R.id.bottoneConfermaModifica) {
            Toast.makeText(getContext(), "Conferma", Toast.LENGTH_SHORT).show();
            String nome = edit_text_nome_social.getText().toString();
            String link = edit_text_link_social.getText().toString();

            registrazioneViewModel.aggiornaSocialViewModel(nome_vecchio,link_vecchio,nome,link);
        }else if(v.getId() == R.id.bottoneEliminaSocial){
            registrazioneViewModel.eliminaSocialViewModel(nome_vecchio,link_vecchio);
            dismissModificaSocialPopup();

        }
    }



    public void dismissModificaSocialPopup() {
        if (popupDismissListener != null) {
            Log.d("dismissModificaSocialPopup","chiamo");
            popupDismissListener.onPopupModificaSocialRegistrazioneDismissed();
        }
        dismiss();

    }


    public void messaggioErroreNomeSocial(String messaggio){
        edit_text_nome_social.setError(messaggio);
    }
    public void osservaMessaggioErroreNomeSocial() {
        registrazioneViewModel.messaggioErroreNomeSocial.observe(registrazioneCampiFacoltativi, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErrorNomeSociale()) {
                messaggioErroreNomeSocial(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }


    public void messaggioErroreLinkNuovo(String messaggio){
        edit_text_link_social.setError(messaggio);
    }
    public void osservaMessaggioErroreLinkNuovo() {
        registrazioneViewModel.messaggioErroreLink.observe(registrazioneCampiFacoltativi, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErrorLink()) {
                messaggioErroreLinkNuovo(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }

    public void osservaIsSocialCambiato(){
        registrazioneViewModel.isSocialCambiato.observe(registrazioneCampiFacoltativi, (valore) -> {
            if(valore){
                registrazioneViewModel.resetErroriModificaSocial();
                dismissModificaSocialPopup();
            }else{
                Toast.makeText(getContext(), "Errore nei dati del social", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void osservaNomeSocial() {
        registrazioneViewModel.nomeSocialRecuperato.observe(registrazioneCampiFacoltativi, (nome) -> {
            if (registrazioneViewModel.isNomeSocialRecuperato()) {
                nome_vecchio = nome;
                edit_text_nome_social.setText(nome);
            }
        });
    }


    public void osservaLinkSocial() {
        registrazioneViewModel.linkSocialRecuperato.observe(registrazioneCampiFacoltativi, (link) -> {
            if (registrazioneViewModel.isLinkSocialRecuperato()) {
                link_vecchio = link;
                edit_text_link_social.setText(link);
            }
        });
    }
}
