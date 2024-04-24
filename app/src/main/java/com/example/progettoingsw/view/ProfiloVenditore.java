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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.DAO.ProfiloVenditoreDaAstaDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_social;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.viewmodel.SchermataUtenteViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ProfiloVenditore extends GestoreComuniImplementazioni {

    private ImageButton bottoneBackProfiloVenditore;
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

    // Definisci la variabile di istanza view

    private String email;
    private RelativeLayout relative_layout_profilo_venditore;
    private BottomNavigationView acquirente_nav_view;
    private SchermataUtenteViewModel schermataUtenteViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilo_venditore);

        schermataUtenteViewModel = new ViewModelProvider(this).get(SchermataUtenteViewModel.class);

        osservaVenditoreRecuperato();
        osservaSocialVenditore();
        osservaApriLeMieAste();
        schermataUtenteViewModel.getUtenteData();

        bottoneBackProfiloVenditore = findViewById(R.id.bottoneBackProfiloVenditore);
        bottoneBackProfiloVenditore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        relative_layout_profilo_venditore = findViewById(R.id.relative_layout_profilo_venditore);


        // Inizializza la GridView
        GridView gridView = findViewById(R.id.gridview_social_activity_profilo);

        gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));

        setGridViewHeightBasedOnChildren(gridView);

        button_le_mie_aste=findViewById(R.id.button_aste_venditore);

        button_le_mie_aste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("le aste oooh");
                schermataUtenteViewModel.setApriLeMieAste(true);

            }
        });


        textview_nome = findViewById(R.id.textview_nome);
        textview_cognome = findViewById(R.id.textview_cognome);
        textview_email = findViewById(R.id.textview_email);
        textview_sitoweb = findViewById(R.id.textview_sitoweb);
        textview_paese = findViewById(R.id.textview_paese);
        text_view_bio_profilo = findViewById(R.id.text_view_bio_profilo);






    }



    public void updateDatiUtente(String nome, String cognome, String email, String link, String paese, String bio){
        textview_nome.setText(nome);
        textview_cognome.setText(cognome);
        textview_email.setText(email);
        textview_sitoweb.setText(link);
        textview_paese.setText(paese);
        text_view_bio_profilo.setText(bio);
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

    }
    public void osservaVenditoreRecuperato(){
        schermataUtenteViewModel.venditoreRecuperato.observe(this, (venditore) ->{
            if(schermataUtenteViewModel.isVenditoreRecuperato()){
                updateDatiUtente(venditore.getNome(),venditore.getCognome(),venditore.getIndirizzo_email(),venditore.getLink(),venditore.getAreageografica(),venditore.getBio());
            }
        });
    }
    public void osservaSocialVenditore(){
        schermataUtenteViewModel.socialVenditore.observe(this, (lista) ->{
            if(schermataUtenteViewModel.isSocialVenditore()){
                if (lista != null  && !lista.isEmpty()) {
                    System.out.println("in osserva social acquirente recuperati");
                    //lista social quindi estrarre nomi e link poi fare chiamata a update social names per mostrarli graficamente
                    List<String> links = new ArrayList<>();
                    List<String> nomi=new ArrayList<>();
                    for (SocialVenditoreModel social : lista) {
                        links.add(social.getLink());
                        nomi.add(social.getNome());
                    }
                    updateSocialNames(nomi,links);
                }
            }
        });
    }
    public void osservaApriLeMieAste(){
        schermataUtenteViewModel.apriLeMieAste.observe(this, (messaggio) -> {
            if (schermataUtenteViewModel.getApriLeMieAste()){
                //fa le cose che si farebbero premendo il pulsante apri le mie aste
                Intent intent = new Intent(ProfiloVenditore.this, LeMieAste.class);
                startActivity(intent);
            }
        });
    }
    }
