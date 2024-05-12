package com.example.progettoingsw.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.viewmodel.FragmentProfiloViewModel;

public class PopUpControlloPassword extends DialogPersonalizzato implements View.OnClickListener {
    private AppCompatButton bottoneAnnullaControlloPassword;
    private AppCompatButton bottoneConfermaControlloPassword;
    private EditText edit_text_vecchia_password;
    private EditText edit_text_password_nuova;


    private ProgressBar progress_bar_pop_up_controllo_password;

    private FragmentProfilo fragmentProfilo;
    private FragmentProfiloViewModel fragmentProfiloViewModel;

    public  PopUpControlloPassword(Context context, FragmentProfilo fragmentProfilo, FragmentProfiloViewModel fragmentProfiloViewModel) {
        super(context);
       this.fragmentProfiloViewModel=fragmentProfiloViewModel;
        this.fragmentProfilo=fragmentProfilo;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_controllo_password);

        osservaMessaggioErrorePasswordVecchia();
        osservaMessaggioErrorePasswordNuova();
        osservaIsPasswordCambiata();

        progress_bar_pop_up_controllo_password = findViewById(R.id.progress_bar_pop_up_controllo_password);


        edit_text_vecchia_password = findViewById(R.id.edit_text_vecchia_password);
        edit_text_password_nuova = findViewById(R.id.edit_text_password_nuova);

        bottoneAnnullaControlloPassword = findViewById(R.id.bottoneAnnullaControlloPassword);
        bottoneAnnullaControlloPassword.setOnClickListener(this);
        bottoneConfermaControlloPassword = findViewById(R.id.bottoneConfermaControlloPassword);
        bottoneConfermaControlloPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAnnullaControlloPassword) {
            fragmentProfiloViewModel.resetErroriControlloPassword();
            dismiss();
        } else if(v.getId() == R.id.bottoneConfermaControlloPassword){
            confermaPassword();

        }
    }
    @Override
    public void onBackPressed(){
        bottoneAnnullaControlloPassword.performClick();
    }
private void confermaPassword(){
    String password_vecchia = edit_text_vecchia_password.getText().toString().trim();
    String password_nuova = edit_text_password_nuova.getText().toString().trim();
    fragmentProfiloViewModel.aggiornaPasswordViewModel(password_vecchia,password_nuova);
}

    public void dismissPopup() {
        dismiss();
    }


    public void messaggioErrorePasswordVecchia(String messaggio){
        edit_text_vecchia_password.setError(messaggio);
    }
    public void osservaMessaggioErrorePasswordVecchia() {
        fragmentProfiloViewModel.messaggioErrorePasswordVecchia.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErrorePasswordVecchia()) {
                messaggioErrorePasswordVecchia(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }


    public void messaggioErrorePasswordNuova(String messaggio){
        edit_text_password_nuova.setError(messaggio);
    }
    public void osservaMessaggioErrorePasswordNuova() {
        fragmentProfiloViewModel.messaggioErrorePasswordNuova.observe(fragmentProfilo, (messaggio) -> {
            if (fragmentProfiloViewModel.isNuovoMessaggioErrorePasswordNuova()) {
                messaggioErrorePasswordNuova(messaggio);
                //loginViewModel.cancellaMessaggioLogin();
            }
        });
    }

    public void osservaIsPasswordCambiata(){
        fragmentProfiloViewModel.isPasswordCambiata.observe(fragmentProfilo, (messaggio) -> {
            if(fragmentProfiloViewModel.getIsPasswordCambiata()){
                fragmentProfiloViewModel.resetErroriControlloPassword();
                dismissPopup();
            }else{
                Toast.makeText(getContext(), "Errore nelle password!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}