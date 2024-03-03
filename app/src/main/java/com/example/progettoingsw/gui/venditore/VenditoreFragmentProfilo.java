package com.example.progettoingsw.gui.venditore;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.progettoingsw.DAO.Acquirente;
import com.example.progettoingsw.DAO.AcquirenteFragmentProfiloDAO;
import com.example.progettoingsw.DAO.Venditore;
import com.example.progettoingsw.DAO.VenditoreFragmentProfiloDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_campi;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_social;
import com.example.progettoingsw.gui.LeMieAste;
import com.example.progettoingsw.gui.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
public class VenditoreFragmentProfilo extends Fragment {
    private Controller controller;
    ImageButton button_log_out;
    MaterialButton button_le_mie_aste;

    private GridView gridView;
    private CustomAdapter_gridview_profilo_social adapterSocial;

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

    // Definisci la variabile di istanza view
    private View view;

    public VenditoreFragmentProfilo() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Assegna la variabile view nel metodo onCreateView
        view = inflater.inflate(R.layout.venditore_fragment_profilo, container, false);
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

        textview_nome = view.findViewById(R.id.textview_nome);
        textview_cognome = view.findViewById(R.id.textview_cognome);
        textview_email = view.findViewById(R.id.textview_email);
        textview_sitoweb = view.findViewById(R.id.textview_sitoweb);
        textview_paese = view.findViewById(R.id.textview_paese);
        text_view_bio_profilo = view.findViewById(R.id.text_view_bio_profilo);

        // Inizializza il DAO e recupera i dati del venditore
        VenditoreFragmentProfiloDAO venditore_fragment_profilo_DAO = new VenditoreFragmentProfiloDAO(this);
        venditore_fragment_profilo_DAO.openConnection();
        venditore_fragment_profilo_DAO.findUser(email);
        venditore_fragment_profilo_DAO.getSocialNamesForEmail(email);
    }

    public void updateEditTexts(Venditore venditore) {
        if (venditore != null) {
            // Esempio: aggiorna l'interfaccia utente con i dati del venditore
            textview_nome.setText(venditore.getNome());
            textview_cognome.setText(venditore.getCognome());
            textview_email.setText(venditore.getEmail());
            textview_sitoweb.setText(venditore.getSitoWeb());
            textview_paese.setText(venditore.getPaese());
            text_view_bio_profilo.setText(venditore.getBio());
        } else {
            // Il venditore non è stato trovato
        }
    }
    private void setGridViewHeightBasedOnChildren(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // Se l'adapter è null, esci dal metodo
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int columns = gridView.getNumColumns();
        int rows = items / columns;
        if (items % columns != 0) {
            rows++;
        }

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        int itemHeight = listItem.getMeasuredHeight();

        for (int i = 0; i < rows; i++) {
            totalHeight += itemHeight;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
    public void updateSocialNames(List<String> socialNames, List<String> socialLinks) {
        if (socialNames != null && !socialNames.isEmpty()) {
            // Aggiorna l'interfaccia utente con i nomi dei social
            gridView = view.findViewById(R.id.gridview_social_activity_profilo);
            adapterSocial = new CustomAdapter_gridview_profilo_social(getContext());
            gridView.setAdapter(adapterSocial);

            // Aggiungi i nomi dei social alla tua adapter
            adapterSocial.setData(socialNames, socialLinks);
            setGridViewHeightBasedOnChildren(gridView);
            // Aggiungi stampe nel log per verificare che i dati siano correttamente passati
            for (int i = 0; i < socialNames.size(); i++) {
                Log.d("VenditoreFragmentProfilo", "Nome Social: " + socialNames.get(i) + ", Link Social: " + socialLinks.get(i));
            }
        } else {
            // Nessun nome sociale trovato per l'email specificata
        }
    }







}