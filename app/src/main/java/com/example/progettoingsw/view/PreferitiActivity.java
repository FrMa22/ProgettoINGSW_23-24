package com.example.progettoingsw.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.viewmodel.SchermataPreferityViewModel;

public class PreferitiActivity extends GestoreComuniImplementazioni {
    private ImageButton backBottone;
    private TextView text_view_nessuna_asta_preferita_trovata;
    private AstaAdapter astaAdapter;
    private ProgressBar progress_bar_schermata_preferiti;
    private RelativeLayout relative_layout_schermata_preferiti;
    private SchermataPreferityViewModel schermataPreferityViewModel;
    private SwipeRefreshLayout swipe_refresh_layout_preferiti;
    private ScrollView scroll_view_preferiti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferiti);

        schermataPreferityViewModel = new ViewModelProvider(this).get(SchermataPreferityViewModel.class);
        osservaIsAcquirente();

        osservaListaAstaInglesePreferite();
        osservaListaAstaInglesePreferiteConvertite();
        osservaListaAstaRibassoPreferite();
        osservaListaAstaRibassoPreferiteConvertite();
        osservaListaAstaInversaPreferite();
        osservaListaAstaInversaPreferiteConvertite();

        osservaEntraInSchermataAstaInglese();
        osservaEntraInSchermataAstaRibasso();
        osservaEntraInSchermataAstaInversa();


        schermataPreferityViewModel.getTipoUtente();

        backBottone = findViewById(R.id.backButton);
        astaAdapter = new AstaAdapter(this, null);

        progress_bar_schermata_preferiti = findViewById(R.id.progress_bar_schermata_preferiti);
        //progress_bar_schermata_preferiti.setVisibility(View.VISIBLE);
        relative_layout_schermata_preferiti = findViewById(R.id.relative_layout_schermata_preferiti);
        text_view_nessuna_asta_preferita_trovata = findViewById(R.id.text_view_nessuna_asta_preferita_trovata);
        //
        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAstePreferite = findViewById(R.id.recyclerAstePreferiti);
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2 ,RecyclerView.VERTICAL,false);
        recyclerViewAstePreferite.setLayoutManager(gridLayoutManagerAttive);
        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, gridLayoutManagerAttive.getOrientation());
        //recyclerViewAstePreferite.addItemDecoration(dividerItemDecorationAttive);

        astaAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                Log.d("preferiti" , "clicl");
                int position = recyclerViewAstePreferite.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapter.getItem(position);
                schermataPreferityViewModel.gestisciClickRecyclerView(asta);


            }
        });
        recyclerViewAstePreferite.setAdapter(astaAdapter);



        backBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        swipe_refresh_layout_preferiti = findViewById(R.id.swipe_refresh_layout_preferiti);
        scroll_view_preferiti = findViewById(R.id.scroll_view_preferiti);
        scroll_view_preferiti.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scroll_view_preferiti.getScrollY() == 0) {
                    // La ScrollView è scorsa fino alla parte superiore
                    swipe_refresh_layout_preferiti.setEnabled(true); // Abilita lo SwipeRefreshLayout
                } else {
                    // La ScrollView non è alla parte superiore
                    swipe_refresh_layout_preferiti.setEnabled(false); // Disabilita lo SwipeRefreshLayout
                }
            }
        });
        swipe_refresh_layout_preferiti.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                astaAdapter.clearItems();
                schermataPreferityViewModel.getTipoUtente();
                swipe_refresh_layout_preferiti.setRefreshing(false);
            }
        });

    }


    public void osservaListaAstaInglesePreferite(){
        schermataPreferityViewModel.listaAstaInglesePreferite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                schermataPreferityViewModel.convertiAsteInglese();
            }
        });
    }
    public void osservaListaAstaInglesePreferiteConvertite(){
        schermataPreferityViewModel.listaAstaInglesePreferiteConvertite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                astaAdapter.setAste(listaAste);
                text_view_nessuna_asta_preferita_trovata.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void osservaListaAstaRibassoPreferite(){
        schermataPreferityViewModel.listaAstaRibassoPreferite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                schermataPreferityViewModel.convertiAsteRibasso();
            }
        });
    }
    public void osservaListaAstaRibassoPreferiteConvertite(){
        schermataPreferityViewModel.listaAstaRibassoPreferiteConvertite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                astaAdapter.setAste(listaAste);
                text_view_nessuna_asta_preferita_trovata.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void osservaListaAstaInversaPreferite(){
        schermataPreferityViewModel.listaAstaInversaPreferite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                schermataPreferityViewModel.convertiAsteInversa();
            }
        });
    }
    public void osservaListaAstaInversaPreferiteConvertite(){
        schermataPreferityViewModel.listaAstaInversaPreferiteConvertite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                astaAdapter.setAste(listaAste);
                text_view_nessuna_asta_preferita_trovata.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void osservaEntraInSchermataAstaInglese(){
        schermataPreferityViewModel.entraInSchermataAstaInglese.observe(this, (messaggio) -> {
            if (schermataPreferityViewModel.getEntraInSchermataAstaInglese()){
                Intent intent = new Intent(PreferitiActivity.this, SchermataAstaInglese.class);
                startActivity(intent);
            }
        });
    }
    public void osservaEntraInSchermataAstaRibasso(){
        schermataPreferityViewModel.entraInSchermataAstaRibasso.observe(this, (messaggio) -> {
            if (schermataPreferityViewModel.getEntraInSchermataAstaRibasso()){
                Intent intent = new Intent(PreferitiActivity.this, SchermataAstaRibasso.class);
                startActivity(intent);
            }
        });
    }
    public void osservaEntraInSchermataAstaInversa(){
        schermataPreferityViewModel.entraInSchermataAstaInversa.observe(this, (messaggio) -> {
            if (schermataPreferityViewModel.getEntraInSchermataAstaInversa()){
                Intent intent = new Intent(PreferitiActivity.this, SchermataAstaInversa.class);
                startActivity(intent);
            }
        });
    }

    public void osservaIsAcquirente(){
        schermataPreferityViewModel.isAcquirente.observe(this, (valore) -> {
            if(valore){
                schermataPreferityViewModel.getAsteInglesePreferite();
                schermataPreferityViewModel.getAsteRibassoPreferite();
            }else{
                schermataPreferityViewModel.getAsteInversaPreferite();
            }
        });
    }
}