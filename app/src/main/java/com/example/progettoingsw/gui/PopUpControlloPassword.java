package com.example.progettoingsw.gui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.DAO.PopUpControlloPasswordDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;

public class PopUpControlloPassword extends Dialog implements View.OnClickListener {
    private AppCompatButton bottoneAnnullaControlloPassword;
    private AppCompatButton bottoneConfermaControlloPassword;
    private EditText edit_text_vecchia_password;
    private EditText edit_text_password_nuova;
    private String email;
    private PopUpControlloPasswordDAO popUpControlloPasswordDAO;
    private String tipoUtente;
    private ProgressBar progress_bar_pop_up_controllo_password;


    public  PopUpControlloPassword(Context context, String email, String tipoUtente) {
        super(context);
        this.email = email;
        this.tipoUtente = tipoUtente;
        this.popUpControlloPasswordDAO = new PopUpControlloPasswordDAO(this,email,tipoUtente);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_controllo_password);

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
            dismiss();
        } else if(v.getId() == R.id.bottoneConfermaControlloPassword){
            progress_bar_pop_up_controllo_password.setVisibility(View.VISIBLE);
            String password_vecchia = edit_text_vecchia_password.getText().toString().trim();
            String password_nuova = edit_text_password_nuova.getText().toString().trim();
            popUpControlloPasswordDAO.openConnection();
            popUpControlloPasswordDAO.checkPassword(password_vecchia);
        }
    }


    public void handleResultPassword(Boolean result){
        if(result){
            //sostituzione in db
            Log.d("handleResultPassword", "result è positivo: password corretta");
            popUpControlloPasswordDAO.updatePassword( edit_text_password_nuova.getText().toString().trim());
        }else{
            Toast.makeText(getContext(), "Password non corretta, riprovare.", Toast.LENGTH_SHORT).show();
            progress_bar_pop_up_controllo_password.setVisibility(View.INVISIBLE);
            Log.d("handleResultPassword", "result è negativo: password non corretta");
        }

    }
    public void dismissPopup() {
        dismiss();
    }

}