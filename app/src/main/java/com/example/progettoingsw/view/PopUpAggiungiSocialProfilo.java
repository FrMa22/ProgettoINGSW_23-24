package com.example.progettoingsw.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.progettoingsw.DAO.RegistrazioneSocialDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.view.acquirente.FragmentProfilo;
import com.google.android.material.button.MaterialButton;

public class PopUpAggiungiSocialProfilo extends DialogPersonalizzato implements View.OnClickListener {
    private String email;
    private String tipoUtente;
    private FragmentProfilo fragmentProfilo;
    MaterialButton bottoneChiudiRegistrazioneSocial;
    MaterialButton bottoneConfermaRegistrazioneSocial;
    EditText editTextNomeUtenteSocial;
    EditText editTextNomeSocial;
    private ProgressBar progressBarPopUpAggiungiSocialProfilo;

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

        System.out.println("popupAggiungiSocial ha email:"+email);



        // Riferimenti ai widget all'interno del pop-up
        bottoneChiudiRegistrazioneSocial = findViewById(R.id.bottoneChiudiRegistrazioneSocial);
        bottoneConfermaRegistrazioneSocial = findViewById(R.id.bottoneConfermaRegistrazioneSocial);
        editTextNomeUtenteSocial = findViewById(R.id.editTextNomeUtenteSocial);
        editTextNomeSocial = findViewById(R.id.editTextNomeSocial);
        progressBarPopUpAggiungiSocialProfilo = findViewById(R.id.progressBarPopUpAggiungiSocialProfilo);




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

     //   RegistrazioneSocialDAO registrazioneSocialDAO = new RegistrazioneSocialDAO(PopUpAggiungiSocialProfilo.this, email, tipoUtente);
        Log.d("pop", "conferma");
        if (nomeSocial.length() > 50) {
            editTextNomeSocial.setError("Nome social oltre i 50 caratteri");
        }
        if (nomeSocial.isEmpty()) {
            editTextNomeSocial.setError("Nome  social vuoto");
        }
        if (nomeUtenteSocial.length() > 50) {
            editTextNomeUtenteSocial.setError("Nome utente social oltre i 50 caratteri");
        }
        if (nomeUtenteSocial.isEmpty()) {
            editTextNomeUtenteSocial.setError("Nome utente social vuoto");
        }
        if (!nomeSocial.isEmpty() && nomeSocial.length() <= 50 &&
                !nomeUtenteSocial.isEmpty() && nomeUtenteSocial.length() <= 50) {
              //  progressBarPopUpAggiungiSocialProfilo.setVisibility(View.VISIBLE);
            fragmentProfilo.fragmentProfiloViewModel.aggiungiSocialAcquirenteViewModel(nomeSocial,nomeUtenteSocial,email);
                //         registrazioneSocialDAO.openConnection();
         //       registrazioneSocialDAO.inserimentoSingoloSocial(nomeSocial, nomeUtenteSocial);
           //     registrazioneSocialDAO.closeConnection();
            }

        }

    public void handleRegistrazioneSocial(Integer result){
        Log.d("handleRegistrazioneSocial", "valore di result: " + result);
        progressBarPopUpAggiungiSocialProfilo.setVisibility(View.INVISIBLE);
        if(result == 0 ){
            Toast.makeText(getContext(), "Problema con la connessione rilevato", Toast.LENGTH_SHORT).show();
        }else if(result == -1){
            Toast.makeText(getContext(), "Valori già presenti in social", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("handleRegistrazioneSocial", "entrato nell'else");
            // Chiudi il dialog dopo la conferma
            fragmentProfilo.onResume();
            dismiss();
        }

    }
}
