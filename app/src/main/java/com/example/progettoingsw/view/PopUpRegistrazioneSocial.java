package com.example.progettoingsw.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class PopUpRegistrazioneSocial extends DialogPersonalizzato implements View.OnClickListener {

    private Controller controller;
    private String opzioneSelezionata;
    private String email;
    private String tipoUtente;
    private RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi;
    MaterialButton bottoneChiudiRegistrazioneSocial;
    MaterialButton bottoneConfermaRegistrazioneSocial;
    EditText editTextNomeUtenteSocial;
    EditText editTextNomeSocial;
    private ArrayList<String> elencoNomeSocialRegistrazioneStrings;
    ArrayList<String> elencoNomeUtenteSocialRegistrazione;

    public PopUpRegistrazioneSocial(@NonNull Context context, RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi, String email, String tipoUtente, ArrayList<String> elencoNomeSocialRegistrazioneStrings, ArrayList<String> elencoNomeUtenteSocialRegistrazione) {
        super(context);
        this.email=email;
        this.tipoUtente=tipoUtente;
        this.registrazioneCampiFacoltativi = registrazioneCampiFacoltativi;
        this.elencoNomeSocialRegistrazioneStrings = elencoNomeSocialRegistrazioneStrings;
        this.elencoNomeUtenteSocialRegistrazione = elencoNomeUtenteSocialRegistrazione;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_registrazione_social);

        controller = new Controller();

        // Stampa dei valori negli arraylist nel log
        Log.d("PopUpRegistrazioneSocial", "Valori in elencoNomeSocialRegistrazioneStrings: " + elencoNomeSocialRegistrazioneStrings.toString());
        Log.d("PopUpRegistrazioneSocial", "Valori in elencoNomeUtenteSocialRegistrazione: " + elencoNomeUtenteSocialRegistrazione.toString());


        // Riferimenti ai widget all'interno del pop-up
        bottoneChiudiRegistrazioneSocial = findViewById(R.id.bottoneChiudiRegistrazioneSocial);
        bottoneConfermaRegistrazioneSocial = findViewById(R.id.bottoneConfermaRegistrazioneSocial);
        editTextNomeUtenteSocial = findViewById(R.id.editTextNomeUtenteSocial);
        editTextNomeSocial = findViewById(R.id.editTextNomeSocial);





        bottoneChiudiRegistrazioneSocial.setOnClickListener(this);

        bottoneConfermaRegistrazioneSocial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.bottoneChiudiRegistrazioneSocial) {
            dismiss(); // Chiude il dialog
        } else if (viewId == R.id.bottoneConfermaRegistrazioneSocial) {
            confermaRegistrazioneSocial();
        }
    }


    private void confermaRegistrazioneSocial() {
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
        }
}
