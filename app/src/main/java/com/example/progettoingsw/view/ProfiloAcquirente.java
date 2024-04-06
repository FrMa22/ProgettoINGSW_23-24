package com.example.progettoingsw.view;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.progettoingsw.DAO.Acquirente;
import com.example.progettoingsw.DAO.ProfiloAcquirenteDaAstaDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_social;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ProfiloAcquirente extends GestoreComuniImplementazioni {


    ImageButton button_log_out;
    MaterialButton button_le_mie_aste;
    ImageButton button_modifica;
    ImageButton bottone_info;
    Button button_cambia_password_profilo;
    ImageButton button_aggiungi_social;

    private GridView gridView;
    private CustomAdapter_gridview_profilo_social adapterSocial;
    private ImageButton bottoneBackProfiloAcquirente;
    private TextView textview_nome;
    private TextView textview_cognome;
    private TextView textview_email;
    private TextView textview_sitoweb;
    private TextView textview_paese;

    private TextView text_view_bio_profilo;
    private ProgressBar progressBarAcquirenteFragmentProfilo;

    // Definisci la variabile di istanza view

    private String email;
    private RelativeLayout relative_layout_profilo_acquirente;
    private BottomNavigationView acquirente_nav_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilo_acquirente);
        email = getIntent().getStringExtra("email");
        bottoneBackProfiloAcquirente=findViewById(R.id.bottoneBackProfiloAcquirente);
        bottoneBackProfiloAcquirente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        relative_layout_profilo_acquirente = findViewById(R.id.relative_layout_profilo_acquirente);

        //icona del caricamento
          progressBarAcquirenteFragmentProfilo = findViewById(R.id.progressBarProfiloAcquirente);
          progressBarAcquirenteFragmentProfilo.setVisibility(View.VISIBLE);
          setAllClickable(relative_layout_profilo_acquirente,false);

        GridView gridView = findViewById(R.id.gridview_social_activity_profilo);
        gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        setGridViewHeightBasedOnChildren(gridView);

        button_le_mie_aste=findViewById(R.id.button_aste_acquirente);
        button_le_mie_aste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("le aste oooh");
                Intent intent = new Intent(ProfiloAcquirente.this, LeMieAste.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


        textview_nome = findViewById(R.id.textview_nome);
        textview_cognome = findViewById(R.id.textview_cognome);
        textview_email = findViewById(R.id.textview_email);
        textview_sitoweb = findViewById(R.id.textview_sitoweb);
        textview_paese = findViewById(R.id.textview_paese);
        text_view_bio_profilo = findViewById(R.id.text_view_bio_profilo);

        // Inizializza il DAO e recupera i dati dell'acquirente
        ProfiloAcquirenteDaAstaDAO acquirenteAstaDAO = new ProfiloAcquirenteDaAstaDAO(this, email, "acquirente");
        acquirenteAstaDAO.openConnection();
        acquirenteAstaDAO.findUser();
        // venditoreAstaDAO.getSocialNamesForEmail();





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

        setAllClickable(relative_layout_profilo_acquirente,true);
        progressBarAcquirenteFragmentProfilo.setVisibility(View.GONE);
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
            gridView = findViewById(R.id.gridview_social_activity_profilo);
            adapterSocial = new CustomAdapter_gridview_profilo_social(this);
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
            gridView = findViewById(R.id.gridview_social_activity_profilo);
            adapterSocial = new CustomAdapter_gridview_profilo_social(this);
            gridView.setAdapter(adapterSocial);

            // Aggiungi stampe nel log per verificare che i dati siano correttamente passati
            Log.d("FragmentProfilo", "Nessun social disponibile");

            // Imposta l'altezza della GridView a 50dp
            gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        }

        setAllClickable(relative_layout_profilo_acquirente,true);
        progressBarAcquirenteFragmentProfilo.setVisibility(View.GONE);
    }



}
