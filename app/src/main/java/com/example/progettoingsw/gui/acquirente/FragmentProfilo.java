package com.example.progettoingsw.gui.acquirente;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.progettoingsw.DAO.Acquirente;
import com.example.progettoingsw.DAO.FragmentProfiloDAO;
import com.example.progettoingsw.gui.PopUpAggiungiSocialProfilo;
import com.example.progettoingsw.gui.PopUpControlloPassword;
import com.example.progettoingsw.gui.PopUpModificaCampiProfilo;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_social;
import com.example.progettoingsw.gui.LeMieAste;
import com.example.progettoingsw.gui.LoginActivity;
import com.example.progettoingsw.gui.PopUpModificaSocial;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.List;
public class FragmentProfilo extends Fragment{
    ImageButton button_log_out;
    MaterialButton button_le_mie_aste;
    ImageButton button_modifica;
    ImageButton bottone_info;
    Button button_cambia_password_profilo;
    ImageButton button_aggiungi_social;

    private GridView gridView;
    private CustomAdapter_gridview_profilo_social adapterSocial;

    private TextView textview_nome;
    private TextView textview_cognome;
    private TextView textview_email;
    private TextView textview_sitoweb;
    private TextView textview_paese;

    private TextView text_view_bio_profilo;
    private ProgressBar progressBarAcquirenteFragmentProfilo;

    // Definisci la variabile di istanza view
    private View view;
    private String email;
    String tipoUtente;
    private RelativeLayout relative_layout_fragment_profilo;

    public FragmentProfilo(String email, String tipoUtente) {
        this.email = email.trim();
        this.tipoUtente = tipoUtente.trim();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Assegna la variabile view nel metodo onCreateView
        view = inflater.inflate(R.layout.fragment_profilo, container, false);
        return view;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(), "Mail " + email + ", tipo: " + tipoUtente, Toast.LENGTH_SHORT).show();


        relative_layout_fragment_profilo = view.findViewById(R.id.relative_layout_fragment_profilo);

        //icona del caricamento
        progressBarAcquirenteFragmentProfilo = view.findViewById(R.id.progressBarAcquirenteFragmentProfilo);
        progressBarAcquirenteFragmentProfilo.setVisibility(View.VISIBLE);

        // Inizializza la GridView
        GridView gridView = view.findViewById(R.id.gridview_social_activity_profilo);

// Imposta un'altezza minima per la GridView
        gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));

// Chiama il metodo per impostare l'altezza in base agli elementi
        setGridViewHeightBasedOnChildren(gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomAdapter_gridview_profilo_social adapter = (CustomAdapter_gridview_profilo_social) gridView.getAdapter();
                List<String> socialNames = adapter.getSocialNames();
                List<String> socialLinks = adapter.getSocialLinks();

                String nome = socialNames.get(position);
                String link = socialLinks.get(position);
                PopUpModificaSocial popUpModificaSocial = new PopUpModificaSocial(getContext(), FragmentProfilo.this, email, tipoUtente, nome, link);
                popUpModificaSocial.show();
            }
        });



        bottone_info = view.findViewById(R.id.bottone_info);
        bottone_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Clickare un social per modificarlo. ", Toast.LENGTH_SHORT).show();
            }
        });


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
                System.out.println("le aste oooh");
                Intent intent = new Intent(getContext(), LeMieAste.class);
                intent.putExtra("email", email);
                intent.putExtra("tipoUtente",tipoUtente);
                startActivity(intent);
            }
        });

        button_modifica = view.findViewById(R.id.button_modifica);
        button_modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpModificaCampiProfilo popUpModificaCampiProfilo = new PopUpModificaCampiProfilo(getContext(), FragmentProfilo.this, email, tipoUtente);
                popUpModificaCampiProfilo.show();
            }
        });

        button_cambia_password_profilo = view.findViewById(R.id.button_cambia_password_profilo);
        button_cambia_password_profilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpControlloPassword popUpControlloPassword = new PopUpControlloPassword(getContext(), email, tipoUtente);
                popUpControlloPassword.show();
            }
        });

        button_aggiungi_social = view.findViewById(R.id.button_aggiungi_social);
        button_aggiungi_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpAggiungiSocialProfilo popUpAggiungiSocialProfilo = new PopUpAggiungiSocialProfilo(FragmentProfilo.this, email,tipoUtente);
                popUpAggiungiSocialProfilo.show();
            }
        });

        textview_nome = view.findViewById(R.id.textview_nome);
        textview_cognome = view.findViewById(R.id.textview_cognome);
        textview_email = view.findViewById(R.id.textview_email);
        textview_sitoweb = view.findViewById(R.id.textview_sitoweb);
        textview_paese = view.findViewById(R.id.textview_paese);
        text_view_bio_profilo = view.findViewById(R.id.text_view_bio_profilo);
        Log.d("Profilo" , "mail " + email +", tipoUtente " + tipoUtente + ".");

        // Inizializza il DAO e recupera i dati dell'acquirente
        FragmentProfiloDAO acquirente_fragment_profilo_DAO = new FragmentProfiloDAO(this, email, tipoUtente);
        acquirente_fragment_profilo_DAO.openConnection();
        acquirente_fragment_profilo_DAO.findUser();
        acquirente_fragment_profilo_DAO.getSocialNamesForEmail();
    }

    @Override //questo metodo serve per fare un refresh dei valori dei campi dopo una possibile modifica fatta da PopUp
    public void onResume() {
        super.onResume();
        progressBarAcquirenteFragmentProfilo.setVisibility(View.VISIBLE);
        setAllClickable(relative_layout_fragment_profilo,false);
        setNavigationView(false);

        Toast.makeText(getContext(), "la mail in ingresso è: " + email, Toast.LENGTH_SHORT).show();

        // Inizializza il DAO e recupera i dati dell'acquirente
        FragmentProfiloDAO acquirente_fragment_profilo_DAO = new FragmentProfiloDAO(this,email, tipoUtente);
        acquirente_fragment_profilo_DAO.openConnection();
        acquirente_fragment_profilo_DAO.findUser();
        acquirente_fragment_profilo_DAO.getSocialNamesForEmail();
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
    private void setGridViewHeightBasedOnChildren(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
            // Se l'adapter è null o non ci sono elementi, imposta l'altezza a 20dp e esci dal metodo
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
            gridView.setLayoutParams(params);
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
                Log.d("FragmentProfilo", "Nome Social: " + socialNames.get(i) + ", Link Social: " + socialLinks.get(i));
            }
        } else {
            // Rimuovi tutti i dati dall'adattatore e aggiorna la GridView
            gridView = view.findViewById(R.id.gridview_social_activity_profilo);
            adapterSocial = new CustomAdapter_gridview_profilo_social(getContext());
            gridView.setAdapter(adapterSocial);

            // Aggiungi stampe nel log per verificare che i dati siano correttamente passati
            Log.d("FragmentProfilo", "Nessun social disponibile");

            // Imposta l'altezza della GridView a 50dp
            gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        }
        setAllClickable(relative_layout_fragment_profilo,true);
        progressBarAcquirenteFragmentProfilo.setVisibility(View.INVISIBLE);
        setNavigationView(true);
    }

    protected void setAllClickable(ViewGroup viewGroup, boolean enabled) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                setAllClickable((ViewGroup) child, enabled);
            }
        }
    }

    //metodo per rendere clickabile o non la bottom navigation view, accede alla bottom di main tramite un metodo di main
    private void setNavigationView(Boolean valore) {
            AcquirenteMainActivity activity = (AcquirenteMainActivity) getActivity();
            if (activity != null) {
                // Abilita la BottomNavigationView
                // Log.d("acquirente", "disabilito");
                activity.enableBottomNavigationView(valore);
            }

    }

}

