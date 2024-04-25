package com.example.progettoingsw.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.DAO.PopUpModificaSocialDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.view.acquirente.FragmentProfilo;
import com.example.progettoingsw.viewmodel.FragmentProfiloViewModel;

public class PopUpModificaSocial extends DialogPersonalizzato implements View.OnClickListener {
    private AppCompatButton bottoneAnnullaModifica;
    private AppCompatButton bottoneConfermaModifica;
    private AppCompatButton bottoneEliminaSocial;
    private PopupModificaSocialDismissListener popupDismissListener;

    private String nome_vecchio;
    private String link_vecchio;
    private EditText edit_text_nome_social;
    private EditText edit_text_link_social;
    private PopUpModificaSocialDAO popUpModificaSocialDAO;
    private FragmentProfilo fragmentProfilo;
    private String tipoUtente;
    private FragmentProfiloViewModel fragmentProfiloViewModel;


    public PopUpModificaSocial(Context context, FragmentProfilo fragmentProfilo, FragmentProfiloViewModel fragmentProfiloViewModel, String nome, String link,PopupModificaSocialDismissListener popupDismissListener) {
        super(context);
        this.fragmentProfilo = fragmentProfilo;
        this.nome_vecchio = nome;
        this.link_vecchio = link;
        this.fragmentProfiloViewModel=fragmentProfiloViewModel;
        this.popupDismissListener=popupDismissListener;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_modifica_social);
        System.out.println("interno al popUpModificaSocial");

        osservaMessaggioErroreLinkNuovo();
        osservaMessaggioErroreNomeNuovo();
        osservaIsSocialCambiato();



        edit_text_nome_social = findViewById(R.id.edit_text_nome_social);
       // edit_text_nome_social.setText(nome_vecchio);
        edit_text_link_social = findViewById(R.id.edit_text_link_social);
       // edit_text_link_social.setText(link_vecchio);

        //popUpModificaSocialDAO = new PopUpModificaSocialDAO(this,email,tipoUtente);

        bottoneAnnullaModifica = findViewById(R.id.bottoneAnnullaModifica);
        bottoneAnnullaModifica.setOnClickListener(this);
        bottoneConfermaModifica = findViewById(R.id.bottoneConfermaModifica);
        bottoneConfermaModifica.setOnClickListener(this);
        bottoneEliminaSocial = findViewById(R.id.bottoneEliminaSocial);
        bottoneEliminaSocial.setOnClickListener(this);


        osservaNomeSocial();
        osservaLinkSocial();
        fragmentProfiloViewModel.findSocialUtente();


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
            System.out.println("dopo aver premuto conferma e prima dei controlli con nome:"+ nome + " e link: "+link);


                System.out.println("prima della chiamata al backend");
                 fragmentProfiloViewModel.aggiornaSocialViewModel(nome_vecchio,link_vecchio,nome,link);

        }else if(v.getId() == R.id.bottoneEliminaSocial){
            fragmentProfiloViewModel.eliminaSocialViewModel(nome_vecchio,link_vecchio);
            dismissModificaSocialPopup();
        }
    }


    public void setCampi(String nome,String link){
        edit_text_nome_social.setText(nome);
        edit_text_link_social.setText(link);
    }

    public void setNome(String nome){
        edit_text_nome_social.setText(nome);
    }

    public void setLink(String link){
        edit_text_link_social.setText(link);
    }


    public interface PopupModificaSocialDismissListener {
        void onPopupModificaSocialDismissed();
    }

    public void dismissModificaSocialPopup() {
        dismiss();
        if (popupDismissListener != null) {
            popupDismissListener.onPopupModificaSocialDismissed();
        }
    }

    public void setPopupDismissListener( PopupModificaSocialDismissListener listener) {
        this.popupDismissListener = listener;
    }

    public void messaggioErroreNomeNuovo(String messaggio){
        edit_text_nome_social.setError(messaggio);
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
        edit_text_link_social.setError(messaggio);
    }
    public void osservaMessaggioErroreLinkNuovo() {
        fragmentProfiloViewModel.messaggioErroreLinkNuovo.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErroreLinkNuovo()) {
                messaggioErroreLinkNuovo(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }

    public void osservaIsSocialCambiato(){
        fragmentProfiloViewModel.isSocialCambiato.observe(fragmentProfilo, (messaggio) -> {
            if(fragmentProfiloViewModel.getIsSocialCambiato()){
                Log.d("osservaIsSocialCambiato","prima di dismiss");
                //dismiss();
                dismissModificaSocialPopup();
            }else{
                Toast.makeText(getContext(), "Errore nei dati del social", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void osservaNomeSocial() {
        fragmentProfiloViewModel.nomeSocialSelezionato.observe(fragmentProfilo, (nome) -> {
            if (nome != null) {
                //lista social quindi estrarre nomi e link poi fare chiamata a update social names per mostrarli graficamente
                String nomeSocial=nome;
                setNome(nomeSocial);
            }
        });
    }


    public void osservaLinkSocial() {
        fragmentProfiloViewModel.nomeLinkSelezionato.observe(fragmentProfilo, (link) -> {
            if (link != null) {
                //lista social quindi estrarre nomi e link poi fare chiamata a update social names per mostrarli graficamente
                String linkSocial=link;
                setLink(linkSocial);
            }
        });
    }





}
