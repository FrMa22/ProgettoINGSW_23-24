package com.example.progettoingsw.view;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.gestori_gui.AstaAdapter;
import com.example.progettoingsw.viewmodel.SchermataAstePerCategoriaViewModel;


public class SchermataAstePerCategoria extends GestoreComuniImplementazioni {
     TextView textViewTtitoloCategorie;
      ImageButton backBottone;
     AstaAdapter astaAdapter;
     private ProgressBar progress_bar_schermata_aste_per_categoria;
     private TextView text_view_nessuna_asta_ricercata_per_categoria;
    private SchermataAstePerCategoriaViewModel schermataAstePerCategoriaViewModel;
    private SwipeRefreshLayout swipe_refresh_layout_aste_per_categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_aste_per_categoria);
        textViewTtitoloCategorie = findViewById(R.id.titoloRicercaCategoria);
        progress_bar_schermata_aste_per_categoria = findViewById(R.id.progress_bar_schermata_aste_per_categoria);
        text_view_nessuna_asta_ricercata_per_categoria = findViewById(R.id.text_view_nessuna_asta_ricercata_per_categoria);
        text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.VISIBLE);

        backBottone = findViewById(R.id.backButtonCategorieRicerca);
        astaAdapter = new AstaAdapter(this, null);

        schermataAstePerCategoriaViewModel = new ViewModelProvider(this).get(SchermataAstePerCategoriaViewModel.class);
        osservaIsAcquirente();
        osservaNomeCategoriaPerTextview();

        osservaEntraInSchermataAstaInglese();
        osservaEntraInSchermataAstaRibasso();
        osservaEntraInSchermataAstaInversa();

        osservaListaAstaIngleseCategoria();
        osservaListaAstaRibassoCategoria();
        osservaListaAstaInversaCategoria();

        osservaListaAstaRibassoCategoriaConvertite();
        osservaListaAstaIngleseCategoriaConvertite();
        osservaListaAstaInversaCategoriaConvertite();

        schermataAstePerCategoriaViewModel.checkNomeCategoriaPerTextview();
        schermataAstePerCategoriaViewModel.checkTipoUtente();


        RecyclerView recyclerViewAstePerCategoria = findViewById(R.id.recycler_view_aste_per_categoria);
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2 ,RecyclerView.VERTICAL,false);
        recyclerViewAstePerCategoria.setLayoutManager(gridLayoutManagerAttive);
        recyclerViewAstePerCategoria.setAdapter(astaAdapter);
        astaAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerViewAstePerCategoria.getChildAdapterPosition(v);
                Object asta = astaAdapter.getItem(position);
                schermataAstePerCategoriaViewModel.gestisciClickRecyclerView(asta);

            }
        });


        backBottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        swipe_refresh_layout_aste_per_categoria = findViewById(R.id.swipe_refresh_layout_aste_per_categoria);
        recyclerViewAstePerCategoria.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (recyclerViewAstePerCategoria.getScrollY() == 0) {
                    // La ScrollView è scorsa fino alla parte superiore
                    swipe_refresh_layout_aste_per_categoria.setEnabled(true); // Abilita lo SwipeRefreshLayout
                } else {
                    // La ScrollView non è alla parte superiore
                    swipe_refresh_layout_aste_per_categoria.setEnabled(false); // Disabilita lo SwipeRefreshLayout
                }
            }
        });
        swipe_refresh_layout_aste_per_categoria.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                astaAdapter.clearItems();
                schermataAstePerCategoriaViewModel.checkTipoUtente();
                swipe_refresh_layout_aste_per_categoria.setRefreshing(false);
            }
        });

    }


    public void osservaIsAcquirente(){
        schermataAstePerCategoriaViewModel.isAcquirente.observe(this, (valore) -> {
            schermataAstePerCategoriaViewModel.getAstePerCategoria();
        });
    }
    public void osservaListaAstaIngleseCategoria(){
        schermataAstePerCategoriaViewModel.listaAstaIngleseCategoria.observe(this, (listaAste) -> {
            if(listaAste!=null){
                schermataAstePerCategoriaViewModel.convertiAsteInglese();
            }
        });
    }
    public void osservaListaAstaIngleseCategoriaConvertite(){
        schermataAstePerCategoriaViewModel.listaAstaIngleseCategoriaConvertite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                astaAdapter.setAste(listaAste);
                text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void osservaListaAstaRibassoCategoria(){
        schermataAstePerCategoriaViewModel.listaAstaRibassoCategoria.observe(this, (listaAste) -> {
            if(listaAste!=null){
                schermataAstePerCategoriaViewModel.convertiAsteRibasso();
            }
        });
    }
    public void osservaListaAstaRibassoCategoriaConvertite(){
        schermataAstePerCategoriaViewModel.listaAstaRibassoCategoriaConvertite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                astaAdapter.setAste(listaAste);
                text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void osservaListaAstaInversaCategoria(){
        schermataAstePerCategoriaViewModel.listaAstaInversaCategoria.observe(this, (listaAste) -> {
            if(listaAste!=null){
                schermataAstePerCategoriaViewModel.convertiAsteInversa();
            }
        });
    }
    public void osservaListaAstaInversaCategoriaConvertite(){
        schermataAstePerCategoriaViewModel.listaAstaInversaCategoriaConvertite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                astaAdapter.setAste(listaAste);
                text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void osservaNomeCategoriaPerTextview(){
        schermataAstePerCategoriaViewModel.nomeCategoriaPerTextview.observe(this, (valore) ->{
            if(schermataAstePerCategoriaViewModel.isNomeCategoriaPerTextView()){
                textViewTtitoloCategorie.setText(valore);
            }
        });
    }
    public void osservaEntraInSchermataAstaInglese(){
        schermataAstePerCategoriaViewModel.entraInSchermataAstaInglese.observe(this, (messaggio) -> {
            if (schermataAstePerCategoriaViewModel.getEntraInSchermataAstaInglese()){
                Intent intent = new Intent(SchermataAstePerCategoria.this, SchermataAstaInglese.class);
                startActivity(intent);
            }
        });
    }
    public void osservaEntraInSchermataAstaRibasso(){
        schermataAstePerCategoriaViewModel.entraInSchermataAstaRibasso.observe(this, (messaggio) -> {
            if (schermataAstePerCategoriaViewModel.getEntraInSchermataAstaRibasso()){
                Intent intent = new Intent(SchermataAstePerCategoria.this, SchermataAstaRibasso.class);
                startActivity(intent);
            }
        });
    }
    public void osservaEntraInSchermataAstaInversa(){
        schermataAstePerCategoriaViewModel.entraInSchermataAstaInversa.observe(this, (messaggio) -> {
            if (schermataAstePerCategoriaViewModel.getEntraInSchermataAstaInversa()){
                Intent intent = new Intent(SchermataAstePerCategoria.this, SchermataAstaInversa.class);
                startActivity(intent);
            }
        });
    }
}