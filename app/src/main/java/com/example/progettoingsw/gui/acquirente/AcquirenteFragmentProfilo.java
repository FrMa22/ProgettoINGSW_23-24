package com.example.progettoingsw.gui.acquirente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.progettoingsw.DAO.Acquirente;
import com.example.progettoingsw.DAO.AcquirenteFragmentProfiloDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_campi;
import com.example.progettoingsw.gui.LeMieAste;
import com.example.progettoingsw.gui.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

public class AcquirenteFragmentProfilo extends Fragment {
    private Controller controller;
    ImageButton button_log_out;
    MaterialButton button_le_mie_aste;

    private GridView gridView;
    private CustomAdapter_gridview_profilo_campi adapterSocial;

    private TextView textview_campo_nome;
    private TextView textview_nome;
    private String testo_textview_nome;

    private TextView textview_campo_cognome;
    private TextView textview_cognome;
    private String testo_textview_cognome;

    private TextView textview_campo_email;
    private TextView textview_email;
    private String testo_textview_email;

    private TextView textview_campo_sitoweb;
    private TextView textview_sitoweb;
    private String testo_textview_sitoweb;

    private TextView textview_campo_paese;
    private TextView textview_paese;
    private String testo_textview_paese;

    private TextView text_view_bio_profilo;

    public AcquirenteFragmentProfilo() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acquirente_fragment_profilo, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = getArguments().getString("email");
        Toast.makeText(getContext(), "la mail in ingresso è: " + email, Toast.LENGTH_SHORT).show();

        button_log_out = view.findViewById(R.id.button_log_out_profilo);
        button_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.redirectActivity(getContext(), LoginActivity.class);
            }
        });

        button_le_mie_aste = view.findViewById(R.id.button_le_mie_aste);
        button_le_mie_aste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.redirectActivity(getContext(), LeMieAste.class);
            }
        });

        /*gridView = view.findViewById(R.id.gridview_social_activity_profilo);
        adapterSocial = new CustomAdapter_gridview_profilo_campi(getContext());
        gridView.setAdapter(adapterSocial);
        String[] socialArray = getResources().getStringArray(R.array.array_elenco_social_nomi_profilo);
        String[] linksArray = getResources().getStringArray(R.array.array_elenco_social_nomi_profilo);
        ArrayList<String> socialArrayList = new ArrayList<>(Arrays.asList(socialArray));
        ArrayList<String> linksArrayList = new ArrayList<>(Arrays.asList(linksArray));
        adapterSocial.setData(socialArrayList, linksArrayList);

        textview_campo_nome = view.findViewById(R.id.textview_campo_nome);
        testo_textview_campo_nome = textview_campo_nome.getText().toString();
        textview_nome = view.findViewById(R.id.textview_nome);
        testo_textview_nome = textview_nome.getText().toString();*/

        textview_nome = view.findViewById(R.id.textview_nome);
        textview_cognome = view.findViewById(R.id.textview_cognome);
        textview_email = view.findViewById(R.id.textview_email);
        textview_sitoweb = view.findViewById(R.id.textview_sitoweb);
        textview_paese = view.findViewById(R.id.textview_paese);
        text_view_bio_profilo = view.findViewById(R.id.text_view_bio_profilo);

        // Inizializza il DAO e recupera i dati dell'acquirente
        AcquirenteFragmentProfiloDAO acquirente_fragment_profilo_DAO = new AcquirenteFragmentProfiloDAO(this);
        acquirente_fragment_profilo_DAO.openConnection();
        acquirente_fragment_profilo_DAO.findUser(email);
    }
    public void updateEditTexts(Acquirente acquirente) {
        if (acquirente != null) {
            // Esempio: aggiorna l'interfaccia utente con i dati dell'acquirente
            textview_nome.setText(acquirente.getNome());
            textview_cognome.setText(acquirente.getCognome());
            textview_email.setText(acquirente.getEmail());
            textview_sitoweb.setText(acquirente.getSitoWeb());
            textview_paese.setText(acquirente.getPaese());
            text_view_bio_profilo.setText(acquirente.getBio());
        } else {
            // L'acquirente non è stato trovato
        }
    }
}