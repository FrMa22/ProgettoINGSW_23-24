package com.example.progettoingsw.gui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.progettoingsw.DAO.RegistrazioneSocialDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.gui.acquirente.FragmentProfilo;
import com.google.android.material.button.MaterialButton;

public class PopUpAggiungiSocialProfilo extends Dialog implements View.OnClickListener {
    private String email;
    private String tipoUtente;
    private FragmentProfilo fragmentProfilo;
    MaterialButton bottoneChiudiRegistrazioneSocial;
    MaterialButton bottoneConfermaRegistrazioneSocial;
    EditText editTextNomeUtenteSocial;
    EditText editTextNomeSocial;


    public PopUpAggiungiSocialProfilo(FragmentProfilo fragmentProfilo, String email, String tipoUtente) {
        super(fragmentProfilo.getContext()); // Chiama il costruttore della superclasse Dialog
        this.email=email;
        this.tipoUtente=tipoUtente;
        this.fragmentProfilo = fragmentProfilo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_aggiungi_social_profilo);



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
            confermaRegistrazioneSocialProfilo();
        }
    }


    private void confermaRegistrazioneSocialProfilo() {
        String nomeSocial = editTextNomeSocial.getText().toString().trim();
        String nomeUtenteSocial = editTextNomeUtenteSocial.getText().toString().trim();

        RegistrazioneSocialDAO registrazioneSocialDAO = new RegistrazioneSocialDAO(email,tipoUtente);
        Log.d("pop" , "conferma");
        registrazioneSocialDAO.openConnection();
        registrazioneSocialDAO.inserimentoSingoloSocial(nomeSocial,nomeUtenteSocial);
        registrazioneSocialDAO.closeConnection();
        // Chiudi il dialog dopo la conferma
        fragmentProfilo.onResume();
        dismiss();
    }
}
