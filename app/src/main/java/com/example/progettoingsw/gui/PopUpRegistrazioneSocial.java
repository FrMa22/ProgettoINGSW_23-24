package com.example.progettoingsw.gui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class PopUpRegistrazioneSocial extends Dialog implements View.OnClickListener {

    private Controller controller;
    private String opzioneSelezionata;
    private String email;
    private String tipoUtente;
    private RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi;
    MaterialButton bottoneChiudiRegistrazioneSocial;
    MaterialButton bottoneConfermaRegistrazioneSocial;
    EditText editTextNomeUtenteSocial;
    EditText editTextNomeSocial;


    public PopUpRegistrazioneSocial(@NonNull Context context, RegistrazioneCampiFacoltativi registrazioneCampiFacoltativi, String email, String tipoUtente) {
        super(context);
        this.email=email;
        this.tipoUtente=tipoUtente;
        this.registrazioneCampiFacoltativi = registrazioneCampiFacoltativi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_registrazione_social);

        controller = new Controller();


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
            editTextNomeUtenteSocial.setError(null);
            editTextNomeSocial.setError(null);
            registrazioneCampiFacoltativi.setProfiloSocialRegistrazione(nomeSocial, nomeUtenteSocial);

            // Chiudi il dialog dopo la conferma
            dismiss();
        }
        }
}
