package com.example.progettoingsw.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.gestori_gui.AstaAdapter;
import com.example.progettoingsw.viewmodel.LeMieAsteViewModel;

import java.util.ArrayList;

public class LeMieAste extends GestoreComuniImplementazioni {
    private SwitchCompat switch_compat_aste_attive_nonattive;
    private ImageButton bottoneBackLeMieAste;
    private AstaAdapter astaAdapterAttive;
    private AstaAdapter astaAdapterNonAttive;


    private TextView text_view_aste_attive_non_attive;
    private ProgressBar progress_bar_le_mie_aste;
    private TextView text_view_nessuna_asta_trovata;

    private LeMieAsteViewModel leMieAsteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.le_mie_aste);
        astaAdapterAttive = new AstaAdapter(this, null);
        astaAdapterNonAttive = new AstaAdapter(this, null);

        leMieAsteViewModel = new ViewModelProvider(this).get(LeMieAsteViewModel.class);

        osservaCondizioneAperta();
        osservaCondizioneChiusa();

        osservaAsteAperteRecuperate();
        osservaEntraInSchermataAstaInglese();
        osservaEntraInSchermataAstaRibasso();
        osservaEntraInSchermataAstaInversa();

        osservaAsteChiuseRecuperate();

        RecyclerView recyclerViewAsteAttive = findViewById(R.id.recyclerViewAsteAttive);
        GridLayoutManager gridLayoutManagerAttive = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewAsteAttive.setLayoutManager(gridLayoutManagerAttive);
        astaAdapterAttive.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerViewAsteAttive.getChildAdapterPosition(v);

                Object asta = astaAdapterAttive.getItem(position);
                leMieAsteViewModel.gestisciClickRecyclerView(asta);

            }
        });

        recyclerViewAsteAttive.setAdapter(astaAdapterAttive);

        RecyclerView recyclerViewAsteNonAttive = findViewById(R.id.recyclerViewAsteNonAttive);
        GridLayoutManager gridLayoutManagerNonAttive = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewAsteNonAttive.setLayoutManager(gridLayoutManagerNonAttive);
        astaAdapterNonAttive.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerViewAsteNonAttive.getChildAdapterPosition(v);
                Object asta = astaAdapterNonAttive.getItem(position);
                leMieAsteViewModel.gestisciClickRecyclerView(asta);
            }
        });

        recyclerViewAsteNonAttive.setAdapter(astaAdapterNonAttive);

        progress_bar_le_mie_aste = findViewById(R.id.progress_bar_le_mie_aste);
        progress_bar_le_mie_aste.setVisibility(View.VISIBLE);


        text_view_aste_attive_non_attive = findViewById(R.id.text_view_aste_attive_non_attive);
        switch_compat_aste_attive_nonattive = findViewById(R.id.switch_compat_aste_attive_nonattive);
        text_view_nessuna_asta_trovata = findViewById(R.id.text_view_nessuna_asta_trovata);
        switch_compat_aste_attive_nonattive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   text_view_nessuna_asta_trovata.setVisibility(View.INVISIBLE);
                   recyclerViewAsteAttive.setVisibility(View.VISIBLE);
                   recyclerViewAsteNonAttive.setVisibility(View.GONE);

                   leMieAsteViewModel.setVisualizzaAsteAperte(true);
                   leMieAsteViewModel.setVisualizzaAsteChiuse(false);
                   text_view_aste_attive_non_attive.setText("ASTE ATTIVE");
                   text_view_aste_attive_non_attive.setTextColor(getResources().getColor(R.color.colore_secondario));
               } else {
                   text_view_nessuna_asta_trovata.setVisibility(View.INVISIBLE);
                   recyclerViewAsteNonAttive.setVisibility(View.VISIBLE);
                   recyclerViewAsteAttive.setVisibility(View.GONE);

                   leMieAsteViewModel.setVisualizzaAsteAperte(false);
                   leMieAsteViewModel.setVisualizzaAsteChiuse(true);
                   //
                   text_view_aste_attive_non_attive.setText("ASTE NON ATTIVE");
                   text_view_aste_attive_non_attive.setTextColor(getResources().getColor(R.color.colore_secondario));
               }
           }
       });
        leMieAsteViewModel.setVisualizzaAsteAperte(true);
        recyclerViewAsteAttive.setVisibility(View.VISIBLE);




        bottoneBackLeMieAste = findViewById(R.id.bottoneBackLeMieAste);
        bottoneBackLeMieAste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    public void updateAste(ArrayList<Object> aste, String cond) {
        boolean asteVuote = aste == null || aste.isEmpty();

        if(cond.equals("aperta")) {
            astaAdapterAttive.clearItems();
            if (!asteVuote) {
                astaAdapterAttive.setAstecopia(aste);
            } else {
                astaAdapterAttive.setAstecopia(null);
            }
        } else if(cond.equals("chiusa")) {
            astaAdapterNonAttive.clearItems();
            if (!asteVuote) {
                astaAdapterNonAttive.setAstecopia(aste);
            } else {
                astaAdapterNonAttive.setAstecopia(null);
            }
        }

        if (asteVuote) {
            progress_bar_le_mie_aste.setVisibility(View.INVISIBLE);
            text_view_nessuna_asta_trovata.setVisibility(View.VISIBLE);
        } else {
            progress_bar_le_mie_aste.setVisibility(View.INVISIBLE);
            text_view_nessuna_asta_trovata.setVisibility(View.INVISIBLE);
        }
    }



    public void osservaCondizioneAperta(){
        leMieAsteViewModel.visualizzaAsteAperte.observe(this, (messaggio) -> {
            if (leMieAsteViewModel.getVisualizzaAsteAperte()){
                leMieAsteViewModel.trovaAsteAperteViewModel();
            }
        });
    }



    public void osservaAsteAperteRecuperate() {
        leMieAsteViewModel.asteAperteRecuperate.observe(this, (lista) -> {
            if (lista != null) {
                updateAste(lista,"aperta");

            }
        });
    }


    public void osservaAsteChiuseRecuperate() {
        leMieAsteViewModel.asteChiuseRecuperate.observe(this, (lista) -> {
            if (lista != null) {
                updateAste(lista,"chiusa");

            }
        });
    }


    public void osservaCondizioneChiusa(){
        leMieAsteViewModel.visualizzaAsteChiuse.observe(this, (messaggio) -> {
            if (leMieAsteViewModel.getVisualizzaAsteChiuse()){
                leMieAsteViewModel.trovaAsteChiuseViewModel();
            }
        });
    }



    public void osservaEntraInSchermataAstaInglese(){
        leMieAsteViewModel.entraInSchermataAstaInglese.observe(this, (messaggio) -> {
            if (leMieAsteViewModel.getEntraInSchermataAstaInglese()){
                Intent intent = new Intent(LeMieAste.this, SchermataAstaInglese.class);
                startActivity(intent);
            }
        });
    }
    public void osservaEntraInSchermataAstaRibasso(){
        leMieAsteViewModel.entraInSchermataAstaRibasso.observe(this, (messaggio) -> {
            if (leMieAsteViewModel.getEntraInSchermataAstaRibasso()){
                Intent intent = new Intent(LeMieAste.this, SchermataAstaRibasso.class);
                startActivity(intent);
            }
        });
    }
    public void osservaEntraInSchermataAstaInversa(){
        leMieAsteViewModel.entraInSchermataAstaInversa.observe(this, (messaggio) -> {
            if (leMieAsteViewModel.getEntraInSchermataAstaInversa()){
                Intent intent = new Intent(LeMieAste.this, SchermataAstaInversa.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
