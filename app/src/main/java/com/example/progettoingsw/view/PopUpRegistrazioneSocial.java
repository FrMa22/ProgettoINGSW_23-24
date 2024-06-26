package com.example.progettoingsw.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;


import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.viewmodel.RegistrazioneViewModel;
import com.google.android.material.button.MaterialButton;

public class PopUpRegistrazioneSocial extends DialogPersonalizzato implements View.OnClickListener {

    private Context mContext;
    String nomeSocial;
    String link;
    private RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi;
    MaterialButton bottoneChiudiRegistrazioneSocial;
    MaterialButton bottoneConfermaRegistrazioneSocial;
    EditText editTextLink;
    EditText editTextNomeSocial;
    RegistrazioneViewModel registrazioneViewModel;
    private PopupRegistrazioneSocialDismissListener popupRegistrazioneSocialDismissListener;

    public PopUpRegistrazioneSocial(Context context, RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi, RegistrazioneViewModel registrazioneViewModel, PopupRegistrazioneSocialDismissListener popupRegistrazioneSocialDismissListener) {
        super(context);
        mContext = context;
        this.registrazioneCampiFacoltativi = registrazioneCampiFacoltativi;
        this.registrazioneViewModel = registrazioneViewModel;
        this. popupRegistrazioneSocialDismissListener = popupRegistrazioneSocialDismissListener;
        setCanceledOnTouchOutside(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_registrazione_social);


        bottoneChiudiRegistrazioneSocial = findViewById(R.id.bottoneChiudiRegistrazioneSocial);
        bottoneConfermaRegistrazioneSocial = findViewById(R.id.bottoneConfermaRegistrazioneSocial);
        editTextLink = findViewById(R.id.editTextNomeUtenteSocial);
        editTextNomeSocial = findViewById(R.id.editTextNomeSocial);


        osservaMessaggioErroreNomeSocial();
        osservaMessaggioErroreLink();
        osservaProseguiInserimentoSocial();



        bottoneChiudiRegistrazioneSocial.setOnClickListener(this);

        bottoneConfermaRegistrazioneSocial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.bottoneChiudiRegistrazioneSocial) {
            registrazioneViewModel.resetErrori();
            dismiss();
        } else if (viewId == R.id.bottoneConfermaRegistrazioneSocial) {
            nomeSocial = editTextNomeSocial.getText().toString().trim();
            link = editTextLink.getText().toString().trim();
            registrazioneViewModel.checkSocial(nomeSocial,link);
        }
    }
    public interface PopupRegistrazioneSocialDismissListener {
        void onPopupRegistrazioneSocialDismissed();
    }

    public void messaggioErroreNomeSocial(String messaggio){
        editTextNomeSocial.setError(messaggio);
    }

    public void messaggioErroreLink(String messaggio){
        editTextLink.setError(messaggio);}

    public void osservaMessaggioErroreNomeSocial() {
        registrazioneViewModel.messaggioErroreNomeSocial.observe(registrazioneCampiFacoltativi, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErrorNomeSociale()) {
                messaggioErroreNomeSocial(messaggio);
            }
        });
    }

    public void osservaMessaggioErroreLink() {
        registrazioneViewModel.messaggioErroreLink.observe(registrazioneCampiFacoltativi, (messaggio) -> {
            if (registrazioneViewModel.isNuovoMessaggioErrorLink()) {
                messaggioErroreLink(messaggio);
            }
        });
    }
    public void osservaProseguiInserimentoSocial(){
        registrazioneViewModel.proseguiInserimentoSocial.observe(registrazioneCampiFacoltativi, (messaggio) ->{
            if (registrazioneViewModel.isProseguiInserimentoSocial("inserito")) {
                registrazioneViewModel.resetErrori();
                dismissModificaSocialPopup();
            }
        });
    }
    public void dismissModificaSocialPopup() {
        if (popupRegistrazioneSocialDismissListener != null) {
            popupRegistrazioneSocialDismissListener.onPopupRegistrazioneSocialDismissed();
        }
        dismiss();

    }
}
