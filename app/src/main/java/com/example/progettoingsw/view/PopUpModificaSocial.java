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

public class PopUpModificaSocial extends DialogPersonalizzato implements View.OnClickListener {
    private AppCompatButton bottoneAnnullaModifica;
    private AppCompatButton bottoneConfermaModifica;
    private AppCompatButton bottoneEliminaSocial;
    private String email;
    private String nome_vecchio;
    private String link_vecchio;
    private EditText edit_text_nome_social;
    private EditText edit_text_link_social;
    private PopUpModificaSocialDAO popUpModificaSocialDAO;
    private FragmentProfilo fragmentProfilo;
    private String tipoUtente;


    public PopUpModificaSocial(Context context, FragmentProfilo fragmentProfilo, String email, String tipoUtente, String nome, String link) {
        super(context);
        this.fragmentProfilo = fragmentProfilo;
        this.email = email;
        this.tipoUtente = tipoUtente;
        this.nome_vecchio = nome;
        this.link_vecchio = link;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_modifica_social);

        edit_text_nome_social = findViewById(R.id.edit_text_nome_social);
        edit_text_nome_social.setText(nome_vecchio);
        edit_text_link_social = findViewById(R.id.edit_text_link_social);
        edit_text_link_social.setText(link_vecchio);

        popUpModificaSocialDAO = new PopUpModificaSocialDAO(this,email,tipoUtente);

        bottoneAnnullaModifica = findViewById(R.id.bottoneAnnullaModifica);
        bottoneAnnullaModifica.setOnClickListener(this);
        bottoneConfermaModifica = findViewById(R.id.bottoneConfermaModifica);
        bottoneConfermaModifica.setOnClickListener(this);
        bottoneEliminaSocial = findViewById(R.id.bottoneEliminaSocial);
        bottoneEliminaSocial.setOnClickListener(this);
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

            if (!nome.isEmpty() && !link.isEmpty()) {
                Log.d("bottone", " i valori di nome e link sono: " + nome + link);
                popUpModificaSocialDAO.openConnection();
                popUpModificaSocialDAO.updateSocial(nome_vecchio, link_vecchio, nome, link);
                fragmentProfilo.onResume();
                dismiss();
            }else{
                if(nome.isEmpty() && link.isEmpty() && nome.length()<=50 && link.length()<=50){
                    Toast.makeText(getContext(), "I valori di Nome e link non possono essere vuoti!", Toast.LENGTH_SHORT).show();
                }else if(nome.isEmpty()){
                    Toast.makeText(getContext(), "Il valore di Nome non può essere vuoto!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Il valore di link non può essere vuoto!", Toast.LENGTH_SHORT).show();
                }
                if (nome.length() > 50) {
                    edit_text_nome_social.setError("Il nome non può superare i 50 caratteri");
                    return;
                }
                if (link.length() > 50) {
                    edit_text_link_social.setError("Il link non può superare i 50 caratteri");
                    return;
                }

            }
        }else if(v.getId() == R.id.bottoneEliminaSocial){
            popUpModificaSocialDAO.openConnection();
            popUpModificaSocialDAO.deleteSocial(nome_vecchio,link_vecchio);
            fragmentProfilo.onResume();
            dismiss();
        }
    }


}
