package com.example.progettoingsw.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.viewmodel.FragmentProfiloViewModel;

public class PopUpModificaCampiProfilo extends DialogPersonalizzato implements View.OnClickListener {
    private AppCompatButton bottoneAnnullaModifica;
    private AppCompatButton bottoneConfermaModifica;
    private PopupDismissListener popupDismissListener;

    private EditText edit_text_modifica_nome;
    private EditText edit_text_modifica_cognome;
    private EditText edit_text_modifica_sitoweb;
    private EditText edit_text_modifica_bio;
    private EditText edit_text_modifica_paese;
    private FragmentProfilo fragmentProfilo;

    private FragmentProfiloViewModel fragmentProfiloViewModel;


    public PopUpModificaCampiProfilo(Context context, FragmentProfilo fragmentProfilo, FragmentProfiloViewModel fragmentProfiloViewModel,PopupDismissListener popupDismissListener) {
        super(context);
        this.fragmentProfilo = fragmentProfilo;
        this.fragmentProfiloViewModel=fragmentProfiloViewModel;
        this.popupDismissListener=popupDismissListener;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_modifica_campi_profilo);

        osservaMessaggioErroreNomeNuovo();
        osservaMessaggioErroreCognomeNuovo();
        osservaMessaggioErroreBioNuovo();
        osservaMessaggioErroreSitoNuovo();
        osservaMessaggioErrorePaeseNuovo();
        osservaIsUtenteCambiato();





        edit_text_modifica_nome = findViewById(R.id.edit_text_modifica_nome);
        edit_text_modifica_cognome = findViewById(R.id.edit_text_modifica_cognome);
        edit_text_modifica_sitoweb = findViewById(R.id.edit_text_modifica_sitoweb);
        edit_text_modifica_bio = findViewById(R.id.edit_text_modifica_bio);
        edit_text_modifica_paese = findViewById(R.id.edit_text_modifica_paese);



        bottoneAnnullaModifica = findViewById(R.id.bottoneAnnullaModifica);
        bottoneAnnullaModifica.setOnClickListener(this);
        bottoneConfermaModifica = findViewById(R.id.bottoneConfermaModifica);
        bottoneConfermaModifica.setOnClickListener(this);


        osservaAcquirenteRecuperato();
        osservaVenditoreRecuperato();
        fragmentProfiloViewModel.checkTipoUtente();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAnnullaModifica) {
            fragmentProfiloViewModel.resetErroriModificaCampoProfilo();
            dismiss();
        } else if (v.getId() == R.id.bottoneConfermaModifica) {
            String nome = edit_text_modifica_nome.getText().toString();
            String cognome = edit_text_modifica_cognome.getText().toString();
            String sitoweb = edit_text_modifica_sitoweb.getText().toString();
            String paese = edit_text_modifica_paese.getText().toString();
            String bio = edit_text_modifica_bio.getText().toString();

            fragmentProfiloViewModel.aggiornaViewModel(nome,cognome,bio,sitoweb,paese);
        }
    }

    @Override
    public void onBackPressed(){
        bottoneAnnullaModifica.performClick();
    }



    public void setCampi(String nome,String cognome,String sitoweb,String bio,String paese){
        edit_text_modifica_nome.setText(nome);
        edit_text_modifica_cognome.setText(cognome);
        edit_text_modifica_sitoweb.setText(sitoweb);
        edit_text_modifica_bio.setText(bio);
        edit_text_modifica_paese.setText(paese);
    }



    public interface PopupDismissListener {
        void onPopupDismissed();
    }

    public void dismissPopup() {
        dismiss();
        if (popupDismissListener != null) {
            popupDismissListener.onPopupDismissed();
        }
    }



    public void messaggioErroreNomeNuovo(String messaggio){
        edit_text_modifica_nome.setError(messaggio);
    }
    public void osservaMessaggioErroreNomeNuovo() {
        fragmentProfiloViewModel.messaggioErroreNomeNuovo.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErroreNomeNuovo()) {
                messaggioErroreNomeNuovo(messaggio);
            }
        });
    }


    public void messaggioErroreCognomeNuovo(String messaggio){
        edit_text_modifica_cognome.setError(messaggio);
    }
    public void osservaMessaggioErroreCognomeNuovo() {
        fragmentProfiloViewModel.messaggioErroreCognomeNuovo.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErroreCognomeNuovo()) {
                messaggioErroreCognomeNuovo(messaggio);
            }
        });
    }


    public void messaggioErroreBioNuovo(String messaggio){
        edit_text_modifica_bio.setError(messaggio);
    }
    public void osservaMessaggioErroreBioNuovo() {
        fragmentProfiloViewModel.messaggioErroreBioNuovo.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErroreBioNuovo()) {
                messaggioErroreBioNuovo(messaggio);
            }
        });
    }

    public void messaggioErrorePaeseNuovo(String messaggio){
        edit_text_modifica_paese.setError(messaggio);
    }
    public void osservaMessaggioErrorePaeseNuovo() {
        fragmentProfiloViewModel.messaggioErrorePaeseNuovo.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErrorePaeseNuovo()) {
                messaggioErrorePaeseNuovo(messaggio);
            }
        });
    }

    public void messaggioErroreSitoNuovo(String messaggio){
        edit_text_modifica_sitoweb.setError(messaggio);
    }
    public void osservaMessaggioErroreSitoNuovo() {
        fragmentProfiloViewModel.messaggioErroreSitoNuovo.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErroreSitoNuovo()) {
                messaggioErroreSitoNuovo(messaggio);
            }
        });
    }



    public void osservaIsUtenteCambiato(){
        fragmentProfiloViewModel.isUtenteCambiato.observe(fragmentProfilo, (messaggio) -> {
            if(fragmentProfiloViewModel.getIsUtenteCambiato()){
                fragmentProfiloViewModel.resetErroriModificaCampoProfilo();
                dismissPopup();
            }
        });
    }


    public void osservaAcquirenteRecuperato() {
        fragmentProfiloViewModel.acquirenteRecuperato.observe(fragmentProfilo, (acquirente) -> {
            if (acquirente != null) {
                String nome=acquirente.getNome();
                String cognome=acquirente.getCognome();
                String sitoweb=acquirente.getLink();
                String paese=acquirente.getAreageografica();
                String bio=acquirente.getBio();
                setCampi(nome,cognome,sitoweb,bio,paese);
            }
        });
    }


    public void osservaVenditoreRecuperato() {
        fragmentProfiloViewModel.venditoreRecuperato.observe(fragmentProfilo, (venditore) -> {
            if (venditore != null) {
                String nome=venditore.getNome();
                String cognome=venditore.getCognome();
                String sitoweb=venditore.getLink();
                String paese=venditore.getAreageografica();
                String bio=venditore.getBio();
                setCampi(nome,cognome,sitoweb,bio,paese);
            }
        });
    }


}
