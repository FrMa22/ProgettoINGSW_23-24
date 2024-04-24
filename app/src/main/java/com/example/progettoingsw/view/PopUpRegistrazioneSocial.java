package com.example.progettoingsw.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.lifecycle.LifecycleOwner;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.viewmodel.RegistrazioneViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class PopUpRegistrazioneSocial extends DialogPersonalizzato implements View.OnClickListener {

    private Controller controller;
    private String opzioneSelezionata;
    private String email;
    private String tipoUtente;
    private Context mContext;
    String nomeSocial;
    String link;
    private RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi;
    MaterialButton bottoneChiudiRegistrazioneSocial;
    MaterialButton bottoneConfermaRegistrazioneSocial;
    EditText editTextLink;
    EditText editTextNomeSocial;
    RegistrazioneViewModel registrazioneViewModel;
    private ArrayList<String> elencoNomeSocialRegistrazioneStrings;
    ArrayList<String> elencoNomeUtenteSocialRegistrazione;

    public PopUpRegistrazioneSocial(Context context, RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi, RegistrazioneViewModel registrazioneViewModel) {
        super(context);
        mContext = context;
        this.registrazioneCampiFacoltativi = registrazioneCampiFacoltativi;
        this.registrazioneViewModel = registrazioneViewModel;
        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Esegui le azioni desiderate quando il popup viene chiuso
                // Ad esempio, esegui un'azione o mostra un messaggio
                Log.d("PopUp", "Popup chiuso premendo all'esterno");
                // Aggiungi qui le azioni desiderate
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_registrazione_social);


        // Riferimenti ai widget all'interno del pop-up
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
            dismiss(); // Chiude il dialog
        } else if (viewId == R.id.bottoneConfermaRegistrazioneSocial) {
            nomeSocial = editTextNomeSocial.getText().toString().trim();
            link = editTextLink.getText().toString().trim();
            registrazioneViewModel.checkSocial(nomeSocial,link);
        }
    }


  /*  private void confermaRegistrazioneSocial() {
        String nomeSocial = editTextNomeSocial.getText().toString().trim();
        String nomeUtenteSocial = editTextNomeUtenteSocial.getText().toString().trim();



        if(nomeSocial.length() > 50){editTextNomeSocial.setError("Nome social oltre i 50 caratteri");}
        if(nomeSocial.isEmpty()){editTextNomeSocial.setError("Nome  social vuoto");}
        if(nomeUtenteSocial.length() > 50){editTextNomeUtenteSocial.setError("Nome utente social oltre i 50 caratteri");}
        if(nomeUtenteSocial.isEmpty()){editTextNomeUtenteSocial.setError("Nome utente social vuoto");}
        if (!nomeSocial.isEmpty() && nomeSocial.length() <= 50 &&
                !nomeUtenteSocial.isEmpty() && nomeUtenteSocial.length() <= 50) {
            //aggiungere qui il controllo negli array se i valori sono gia presenti con stessa posizione
            int indiceNomeSocialTrovato = elencoNomeSocialRegistrazioneStrings.indexOf(nomeSocial);
            int indiceNomeUtenteTrovato = elencoNomeUtenteSocialRegistrazione.indexOf(nomeUtenteSocial);
            if(indiceNomeSocialTrovato != -1 && indiceNomeUtenteTrovato==indiceNomeSocialTrovato){
                Toast.makeText(getContext(), "I valori per social e nome utente sono già stati inseriti", Toast.LENGTH_SHORT).show();
            }else{
                editTextNomeUtenteSocial.setError(null);
                editTextNomeSocial.setError(null);
                registrazioneCampiFacoltativi.setProfiloSocialRegistrazione(nomeSocial, nomeUtenteSocial);

                // Chiudi il dialog dopo la conferma
                dismiss();
            }
        }
        }*/

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
                dismiss();
            }
        });
    }

}
