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

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_social;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.viewmodel.SchermataUtenteViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ProfiloAcquirente extends GestoreComuniImplementazioni {


    MaterialButton button_le_mie_aste;

    private CustomAdapter_gridview_profilo_social adapterSocial;
    private ImageButton bottoneBackProfiloAcquirente;
    private TextView textview_nome;
    private TextView textview_cognome;
    private TextView textview_email;
    private TextView textview_sitoweb;
    private TextView textview_paese;
    private TextView text_view_nessun_social_profilo_acquirente;
    private TextView text_view_bio_profilo;

    private GridView gridView;
    // Definisci la variabile di istanza view

    private RelativeLayout relative_layout_profilo_acquirente;
    private SchermataUtenteViewModel schermataUtenteViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilo_acquirente);

        schermataUtenteViewModel = new ViewModelProvider(this).get(SchermataUtenteViewModel.class);


        bottoneBackProfiloAcquirente=findViewById(R.id.bottoneBackProfiloAcquirente);
        bottoneBackProfiloAcquirente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        relative_layout_profilo_acquirente = findViewById(R.id.relative_layout_profilo_acquirente);



        gridView = findViewById(R.id.gridview_social_activity_profilo);
        gridView.setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        setGridViewHeightBasedOnChildren(gridView);

        button_le_mie_aste=findViewById(R.id.button_aste_acquirente);
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
        text_view_nessun_social_profilo_acquirente = findViewById(R.id.text_view_nessun_social_profilo_acquirente);


        osservaAcquirenteRecuperato();
        osservaSocialAcquirente();
        osservaApriLeMieAste();
        osservaSocialAssenti();

        schermataUtenteViewModel.getUtenteData();
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
            gridView.setVisibility(View.VISIBLE);
            text_view_nessun_social_profilo_acquirente.setVisibility(View.GONE);
        } else {
            Log.d("updateSocialNames", "no social");
            text_view_nessun_social_profilo_acquirente.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
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

    public void osservaAcquirenteRecuperato(){
        schermataUtenteViewModel.acquirenteRecuperato.observe(this, (acquirente) ->{
            if(schermataUtenteViewModel.isAcquirenteRecuperato()){
                updateDatiUtente(acquirente.getNome(),acquirente.getCognome(),acquirente.getIndirizzo_email(),acquirente.getLink(),acquirente.getAreageografica(),acquirente.getBio());
            }
        });
    }
    public void osservaSocialAcquirente(){
        schermataUtenteViewModel.socialAcquirente.observe(this, (lista) ->{
            if(schermataUtenteViewModel.isSocialAcquirente()){
                if (lista != null  && !lista.isEmpty()) {
                    System.out.println("in osserva social acquirente recuperati");
                    //lista social quindi estrarre nomi e link poi fare chiamata a update social names per mostrarli graficamente
                    List<String> links = new ArrayList<>();
                    List<String> nomi=new ArrayList<>();
                    for (SocialAcquirenteModel social : lista) {
                        links.add(social.getLink());
                        nomi.add(social.getNome());
                    }
                    updateSocialNames(nomi,links);
                }
            }
        });
    }
    public void osservaSocialAssenti() {
        schermataUtenteViewModel.socialAssenti.observe(this, (valore) -> {
            if (valore) {
                Log.d("osservaSocialAssenti", "entrato");
                gridView.setVisibility(View.GONE);
                text_view_nessun_social_profilo_acquirente.setVisibility(View.VISIBLE);
            }
        });
    }
    public void osservaApriLeMieAste(){
        schermataUtenteViewModel.apriLeMieAste.observe(this, (messaggio) -> {
            if (schermataUtenteViewModel.getApriLeMieAste()){
                //fa le cose che si farebbero premendo il pulsante apri le mie aste
                Intent intent = new Intent(ProfiloAcquirente.this, LeMieAste.class);
                startActivity(intent);
            }
        });
    }


}
