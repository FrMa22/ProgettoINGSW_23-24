package com.example.progettoingsw.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.progettoingsw.DAO.PopUpModificaCampiProfiloDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.view.acquirente.FragmentProfilo;
import com.example.progettoingsw.viewmodel.FragmentProfiloViewModel;

public class PopUpModificaCampiProfilo extends DialogPersonalizzato implements View.OnClickListener {
    private AppCompatButton bottoneAnnullaModifica;
    private AppCompatButton bottoneConfermaModifica;

    private EditText edit_text_modifica_nome;
    private EditText edit_text_modifica_cognome;
    private EditText edit_text_modifica_sitoweb;
    private EditText edit_text_modifica_bio;
    private EditText edit_text_modifica_paese;
    private PopUpModificaCampiProfiloDAO popUpModificaCampiProfiloDAO;
    private FragmentProfilo fragmentProfilo;

    private FragmentProfiloViewModel fragmentProfiloViewModel;


    public PopUpModificaCampiProfilo(Context context, FragmentProfilo fragmentProfilo, FragmentProfiloViewModel fragmentProfiloViewModel) {
        super(context);
        this.fragmentProfilo = fragmentProfilo;
        this.fragmentProfiloViewModel=fragmentProfiloViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_modifica_campi_profilo);

        edit_text_modifica_nome = findViewById(R.id.edit_text_modifica_nome);
        edit_text_modifica_cognome = findViewById(R.id.edit_text_modifica_cognome);
        edit_text_modifica_sitoweb = findViewById(R.id.edit_text_modifica_sitoweb);
        edit_text_modifica_bio = findViewById(R.id.edit_text_modifica_bio);
        edit_text_modifica_paese = findViewById(R.id.edit_text_modifica_paese);

//        popUpModificaCampiProfiloDAO = new PopUpModificaCampiProfiloDAO(this, email, tipoUtente);
//        popUpModificaCampiProfiloDAO.openConnection();
//        popUpModificaCampiProfiloDAO.getFields();

        bottoneAnnullaModifica = findViewById(R.id.bottoneAnnullaModifica);
        bottoneAnnullaModifica.setOnClickListener(this);
        bottoneConfermaModifica = findViewById(R.id.bottoneConfermaModifica);
        bottoneConfermaModifica.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottoneAnnullaModifica) {
            Toast.makeText(getContext(), "Annulla", Toast.LENGTH_SHORT).show();
            dismiss();
        } else if (v.getId() == R.id.bottoneConfermaModifica) {
            Toast.makeText(getContext(), "Conferma", Toast.LENGTH_SHORT).show();
            String nome = edit_text_modifica_nome.getText().toString();
            String cognome = edit_text_modifica_cognome.getText().toString();
            String sitoweb = edit_text_modifica_sitoweb.getText().toString();
            String paese = edit_text_modifica_paese.getText().toString();
            String bio = edit_text_modifica_bio.getText().toString();

            if (!nome.isEmpty() && !cognome.isEmpty() && nome.length()<=50 && cognome.length()<=50 && sitoweb.length()<=50 && paese.length()<=25 && bio.length()<=100) {
                Log.d("bottone", " i valori di nome e cognome sono: " + nome + cognome);
//                popUpModificaCampiProfiloDAO.updateFields(nome, cognome, sitoweb, paese, bio);
                //chiamata al backend
                fragmentProfiloViewModel.aggiornaViewModel(nome,cognome,bio,sitoweb,paese);
                fragmentProfilo.onResume();
                dismiss();
            }else{
                if(nome.isEmpty() && cognome.isEmpty()){
                    Toast.makeText(getContext(), "I valori di Nome e Cognome non possono essere vuoti!", Toast.LENGTH_SHORT).show();
                }else if(nome.isEmpty()){
                    Toast.makeText(getContext(), "Il valore di Nome non può essere vuoto!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Il valore di Cognome non può essere vuoto!", Toast.LENGTH_SHORT).show();
                }
                if (nome.length() > 50) {
                    edit_text_modifica_nome.setError("Il nome non può superare i 50 caratteri");
                    return;
                }
                if (cognome.length() > 50) {
                    edit_text_modifica_cognome.setError("Il cognome non può superare i 50 caratteri");
                    return;
                }
                if (bio.length() > 100) {
                    edit_text_modifica_bio.setError("La bio non può superare i 100 caratteri");
                    return;
                }

                if (paese.length() > 25) {
                    edit_text_modifica_paese.setError("Il paese non può superare i 25 caratteri");
                    return;
                }
                if (sitoweb.length() > 50) {
                    edit_text_modifica_sitoweb.setError("Il sito web non può superare i 50 caratteri");
                    return;
                }
            }
        }
    }

    public void setFields(Object[] object) {
        if (object != null && object.length >= 5) {
            String nome = (String) object[0];
            String cognome = (String) object[1];
            String sitoWeb = (String) object[2];
            String paese = (String) object[3];
            String bio = (String) object[4];

            edit_text_modifica_nome.setText(nome);
            edit_text_modifica_cognome.setText(cognome);
            edit_text_modifica_sitoweb.setText(sitoWeb);
            edit_text_modifica_bio.setText(bio);
            edit_text_modifica_paese.setText(paese);
        }
    }

}
