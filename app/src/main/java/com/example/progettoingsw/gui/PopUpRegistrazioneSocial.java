package com.example.progettoingsw.gui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.google.android.material.button.MaterialButton;

public class PopUpRegistrazioneSocial extends Dialog implements View.OnClickListener {

    private Controller controller;
    private String opzioneSelezionata;

    public PopUpRegistrazioneSocial(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_registrazione_social);

        controller = new Controller();

        // Riferimenti ai widget all'interno del pop-up
        MaterialButton bottoneChiudiRegistrazioneSocial = findViewById(R.id.bottoneChiudiRegistrazioneSocial);
        MaterialButton bottoneConfermaRegistrazioneSocial = findViewById(R.id.bottoneConfermaRegistrazioneSocial);
        EditText editTextUsernameRegistrazioneSocial = findViewById(R.id.editTextUsernameRegistrazioneSocial);

        Spinner spinnerRegistrazioneSocial = findViewById(R.id.spinner_social_registrazione);
        ArrayAdapter<CharSequence> adapterSpinnerRegistrazioneSocial = ArrayAdapter.createFromResource(getContext(), R.array.elencoSocialRegistrazione, android.R.layout.simple_spinner_item);
        adapterSpinnerRegistrazioneSocial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegistrazioneSocial.setAdapter(adapterSpinnerRegistrazioneSocial);

        spinnerRegistrazioneSocial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opzioneSelezionata = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Non fa nulla
            }
        });

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
        EditText editTextUsernameRegistrazioneSocial = findViewById(R.id.editTextUsernameRegistrazioneSocial);
        String usernameSocialRegistrazione = editTextUsernameRegistrazioneSocial.getText().toString();
        String profiloSocialRegistrazione = "https://www." + opzioneSelezionata + ".com/" + usernameSocialRegistrazione;

        // Aggiungere social al database o qualcosa di back-end

        Toast.makeText(getContext(), "Social aggiunto correttamente! " + profiloSocialRegistrazione, Toast.LENGTH_SHORT).show();

        // Chiudi il dialog dopo la conferma
        dismiss();
    }
}
