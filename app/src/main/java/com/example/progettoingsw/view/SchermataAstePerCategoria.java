package com.example.progettoingsw.view;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.progettoingsw.DAO.AstePerCategorieDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.item.AstaIngleseItem;
import com.example.progettoingsw.item.AstaInversaItem;
import com.example.progettoingsw.item.AstaRibassoItem;
import com.example.progettoingsw.viewmodel.SchermataAstePerCategoriaViewModel;

import java.util.ArrayList;

public class SchermataAstePerCategoria extends GestoreComuniImplementazioni {
     TextView textViewTtitoloCategorie;
     Controller controller;
     Intent intent;
     String email;
     String tipoUtente;
      ImageButton backBottone;
     AstaAdapter astaAdapter;
     private ProgressBar progress_bar_schermata_aste_per_categoria;
     private TextView text_view_nessuna_asta_ricercata_per_categoria;
    AstePerCategorieDAO astePerCategorieDAO;
    private SchermataAstePerCategoriaViewModel schermataAstePerCategoriaViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schermata_aste_per_categoria);
        textViewTtitoloCategorie = findViewById(R.id.titoloRicercaCategoria);
        progress_bar_schermata_aste_per_categoria = findViewById(R.id.progress_bar_schermata_aste_per_categoria);
        //progress_bar_schermata_aste_per_categoria.setVisibility(View.VISIBLE);
        text_view_nessuna_asta_ricercata_per_categoria = findViewById(R.id.text_view_nessuna_asta_ricercata_per_categoria);

        backBottone = findViewById(R.id.backButtonCategorieRicerca);
        astaAdapter = new AstaAdapter(this, null);

        schermataAstePerCategoriaViewModel = new ViewModelProvider(this).get(SchermataAstePerCategoriaViewModel.class);
        osservaIsAcquirente();
        osservaNomeCategoriaPerTextview();
        schermataAstePerCategoriaViewModel.checkNomeCategoriaPerTextview();
        schermataAstePerCategoriaViewModel.checkTipoUtente();



        //
        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAstePerCategoria = findViewById(R.id.recycler_view_aste_per_categoria);
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2 ,RecyclerView.VERTICAL,false);
        recyclerViewAstePerCategoria.setLayoutManager(gridLayoutManagerAttive);
        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, gridLayoutManagerAttive.getOrientation());
        recyclerViewAstePerCategoria.addItemDecoration(dividerItemDecorationAttive);
        recyclerViewAstePerCategoria.setAdapter(astaAdapter);
        astaAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aste per categoria" , "click");
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

    }

//    public void asteCategorie(ArrayList<Object> aste) {
//        boolean asteVuote = aste == null || aste.isEmpty();
//        if (!asteVuote)  {
//            astaAdapter.setAste(aste);
//        } else {
//            astaAdapter.setAste(null);
//            // Nessun nome asta trovato per l'email specificata
//        }
//        if(asteVuote){
//            text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.VISIBLE);
//        }else{
//            text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.INVISIBLE);
//        }
//        progress_bar_schermata_aste_per_categoria.setVisibility(View.INVISIBLE);
//    }

    public void osservaIsAcquirente(){
        schermataAstePerCategoriaViewModel.isAcquirente.observe(this, (valore) -> {
            if(valore){
                Log.d("acquirente", "" );
                osservaEntraInSchermataAstaInglese();
                osservaEntraInSchermataAstaRibasso();
                osservaListaAstaIngleseCategoria();
                osservaListaAstaRibassoCategoria();
            }else{
                Log.d("venditore", "" );
                osservaEntraInSchermataAstaInversa();
                osservaListaAstaInversaCategoria();
            }
            schermataAstePerCategoriaViewModel.getAstePerCategoria();
        });
    }
    public void osservaListaAstaIngleseCategoria(){
        schermataAstePerCategoriaViewModel.listaAstaIngleseCategoria.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                osservaListaAstaIngleseCategoriaConvertite();
                schermataAstePerCategoriaViewModel.convertiAsteInglese();
            }
        });
    }
    public void osservaListaAstaIngleseCategoriaConvertite(){
        schermataAstePerCategoriaViewModel.listaAstaIngleseCategoriaConvertite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                Log.d("inglesi", "" + listaAste.size());
                astaAdapter.setAste(listaAste);
                text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void osservaListaAstaRibassoCategoria(){
        schermataAstePerCategoriaViewModel.listaAstaRibassoCategoria.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                osservaListaAstaRibassoCategoriaConvertite();
                schermataAstePerCategoriaViewModel.convertiAsteRibasso();
            }
        });
    }
    public void osservaListaAstaRibassoCategoriaConvertite(){
        schermataAstePerCategoriaViewModel.listaAstaRibassoCategoriaConvertite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                Log.d("ribasso", "" + listaAste.size());
                //fai qualcosa
                astaAdapter.setAste(listaAste);
                text_view_nessuna_asta_ricercata_per_categoria.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void osservaListaAstaInversaCategoria(){
        schermataAstePerCategoriaViewModel.listaAstaInversaCategoria.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                osservaListaAstaInversaCategoriaConvertite();
                schermataAstePerCategoriaViewModel.convertiAsteInversa();
            }
        });
    }
    public void osservaListaAstaInversaCategoriaConvertite(){
        schermataAstePerCategoriaViewModel.listaAstaInversaCategoriaConvertite.observe(this, (listaAste) -> {
            if(listaAste!=null){
                //fai qualcosa
                Log.d("inverse", "" + listaAste.size());
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