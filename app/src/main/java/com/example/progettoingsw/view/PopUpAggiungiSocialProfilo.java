package com.example.progettoingsw.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.view.acquirente.FragmentProfilo;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_aggiungi_social_profilo);

        osservaMessaggioErroreLinkNuovo();
        osservaMessaggioErroreNomeNuovo();
        osservaIsSocialAggiunto();






        // Riferimenti ai widget all'interno del pop-up
        bottoneChiudiRegistrazioneSocial = findViewById(R.id.bottoneChiudiRegistrazioneSocial);
        bottoneConfermaRegistrazioneSocial = findViewById(R.id.bottoneConfermaRegistrazioneSocial);
        editTextLinkSocial = findViewById(R.id.editTextNomeUtenteSocial);
        editTextNomeSocial = findViewById(R.id.editTextNomeSocial);
        progressBarPopUpAggiungiSocialProfilo = findViewById(R.id.progressBarPopUpAggiungiSocialProfilo);




        bottoneChiudiRegistrazioneSocial.setOnClickListener(this);

        bottoneConfermaRegistrazioneSocial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.bottoneChiudiRegistrazioneSocial) {
            fragmentProfiloViewModel.setApriPopUpAggiungiSocial(false);
            //dismissAggiungiSocialPopup();
            dismiss(); // Chiude il dialog
        } else if (viewId == R.id.bottoneConfermaRegistrazioneSocial) {
            confermaRegistrazioneSocialProfilo();
        }
    }


    private void confermaRegistrazioneSocialProfilo() {
        String nomeSocial = editTextNomeSocial.getText().toString().trim();
        String linkSocial = editTextLinkSocial.getText().toString().trim();

     //   RegistrazioneSocialDAO registrazioneSocialDAO = new RegistrazioneSocialDAO(PopUpAggiungiSocialProfilo.this, email, tipoUtente);
//        Log.d("pop", "conferma");
//        if (nomeSocial.length() > 50) {
//            editTextNomeSocial.setError("Nome social oltre i 50 caratteri");
//        }
//        if (nomeSocial.isEmpty()) {
//            editTextNomeSocial.setError("Nome  social vuoto");
//        }
//        if (nomeUtenteSocial.length() > 50) {
//            editTextNomeUtenteSocial.setError("Nome utente social oltre i 50 caratteri");
//        }
//        if (nomeUtenteSocial.isEmpty()) {
//            editTextNomeUtenteSocial.setError("Nome utente social vuoto");
//        }
       // if (!nomeSocial.isEmpty() && nomeSocial.length() <= 50 &&
           //     !nomeUtenteSocial.isEmpty() && nomeUtenteSocial.length() <= 50) {
              //  progressBarPopUpAggiungiSocialProfilo.setVisibility(View.VISIBLE);
            fragmentProfiloViewModel.aggiungiSocialViewModel(nomeSocial,linkSocial);
         //   dismiss();
                //         registrazioneSocialDAO.openConnection();
         //       registrazioneSocialDAO.inserimentoSingoloSocial(nomeSocial, nomeUtenteSocial);
           //     registrazioneSocialDAO.closeConnection();
         //   }

        }

//    public void handleRegistrazioneSocial(Integer result){
//        Log.d("handleRegistrazioneSocial", "valore di result: " + result);
//        progressBarPopUpAggiungiSocialProfilo.setVisibility(View.INVISIBLE);
//        if(result == 0 ){
//            Toast.makeText(getContext(), "Problema con la connessione rilevato", Toast.LENGTH_SHORT).show();
//        }else if(result == -1){
//            Toast.makeText(getContext(), "Valori già presenti in social", Toast.LENGTH_SHORT).show();
//        }else{
//            Log.d("handleRegistrazioneSocial", "entrato nell'else");
//            // Chiudi il dialog dopo la conferma
//            fragmentProfilo.onResume();
//            dismiss();
//        }
//
//    }

  //  public void dismissPopup() {
      //  dismiss();
    //}

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
                Log.d("osservaIsSocialAggiunto","prima di dismiss");
                //dismiss();
                dismissAggiungiSocialPopup();
            }else{
                Toast.makeText(getContext(), "Errore nei dati del social da aggiungere", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
