package com.example.progettoingsw.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.viewmodel.LeMieAsteViewModel;

import java.util.ArrayList;

public class LeMieAste extends GestoreComuniImplementazioni {
    private SwitchCompat switch_compat_aste_attive_nonattive;
    private ImageButton bottoneBackLeMieAste;
    private ImageButton preferitiButton;
    private ImageButton profiloButton;
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

        //osservaAcquirenteModelPresente();
        //osservaVenditoreModelPresente();
        //leMieAsteViewModel.checkTipoUtente();
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
        DividerItemDecoration dividerItemDecorationAttive = new DividerItemDecoration(this, gridLayoutManagerAttive.getOrientation());
        recyclerViewAsteAttive.addItemDecoration(dividerItemDecorationAttive);
        astaAdapterAttive.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerViewAsteAttive.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapterAttive.getItem(position);
                System.out.println("entrah, non penso proprio");
                leMieAsteViewModel.gestisciClickRecyclerView(asta);

            }
        });

        recyclerViewAsteAttive.setAdapter(astaAdapterAttive);

        RecyclerView recyclerViewAsteNonAttive = findViewById(R.id.recyclerViewAsteNonAttive);
        GridLayoutManager gridLayoutManagerNonAttive = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewAsteNonAttive.setLayoutManager(gridLayoutManagerNonAttive);
        DividerItemDecoration dividerItemDecorationNonAttive = new DividerItemDecoration(this, gridLayoutManagerNonAttive.getOrientation());
        recyclerViewAsteNonAttive.addItemDecoration(dividerItemDecorationNonAttive);
        astaAdapterNonAttive.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerViewAsteNonAttive.getChildAdapterPosition(v);
                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapterNonAttive.getItem(position);
                System.out.println("entrah, non penso proprio");
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


                //   lemieAsteDAO.getAsteForEmail(email,"aperta");
                   //qui fare viewmodel.setTrovaAperte(true) e trovaChiuse(false)
                   leMieAsteViewModel.setVisualizzaAsteAperte(true);
                   leMieAsteViewModel.setVisualizzaAsteChiuse(false);
                   //
                   text_view_aste_attive_non_attive.setText("ASTE ATTIVE");
                   text_view_aste_attive_non_attive.setTextColor(getResources().getColor(R.color.verde));
               } else {
                   text_view_nessuna_asta_trovata.setVisibility(View.INVISIBLE);
                   recyclerViewAsteNonAttive.setVisibility(View.VISIBLE);
                   recyclerViewAsteAttive.setVisibility(View.GONE);
                //   lemieAsteDAO.getAsteForEmail(email,"chiusa");
                   //qui fare viewmodel.setTrovaAperte(false) e trovaChiuse(true)



                   leMieAsteViewModel.setVisualizzaAsteAperte(false);
                   leMieAsteViewModel.setVisualizzaAsteChiuse(true);
                   //
                   text_view_aste_attive_non_attive.setText("ASTE NON ATTIVE");
                   text_view_aste_attive_non_attive.setTextColor(getResources().getColor(R.color.rosso));
               }
           }
       });
        //di default appena si apre la schermata si è già su aste aperte quindi escono già
        //nel blocco di codice sopra si gestisce uno dei due casi di asta da settare in base al pulsante mentre qui sotto è il caso iniziale che è per le aste aperte

     //   lemieAsteDAO.getAsteForEmail(email,"aperta");
        leMieAsteViewModel.setVisualizzaAsteAperte(true);//di default true
        recyclerViewAsteAttive.setVisibility(View.VISIBLE);




        bottoneBackLeMieAste = findViewById(R.id.bottoneBackLeMieAste);
        bottoneBackLeMieAste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LeMieAste" , "Back");
                onBackPressed();
            }
        });

    }



    //data una lista di oggetti e la condizione "aperta" o "chiusa", le aste vengono messe tra le aste attive o non attive in base al valore di cond

    public void updateAste(ArrayList<Object> aste, String cond) {
        // Controllo se l'ArrayList di aste è vuoto
        boolean asteVuote = aste == null || aste.isEmpty();

        if(cond.equals("aperta")) {
            astaAdapterAttive.clearItems();
            if (!asteVuote) {
                Log.d("updateAste","caso aperte");
                // Se l'ArrayList di aste non è vuoto, mostro le aste nell'adapter
                astaAdapterAttive.setAstecopia(aste);


            } else {
                // Se l'ArrayList di aste è vuoto, nascondo l'adapter e mostro il TextView
                astaAdapterAttive.setAstecopia(null); // Resetta l'adapter


                Toast.makeText(this, "Nessuna asta trovata", Toast.LENGTH_SHORT).show();
            }
        } else if(cond.equals("chiusa")) {
            astaAdapterNonAttive.clearItems();
            if (!asteVuote) {
                // Se l'ArrayList di aste non è vuoto, mostro le aste nell'adapter
                astaAdapterNonAttive.setAstecopia(aste);
            } else {
                // Se l'ArrayList di aste è vuoto, nascondo l'adapter e mostro il TextView
                astaAdapterNonAttive.setAstecopia(null); // Resetta l'adapter
                Toast.makeText(this, "Nessuna asta trovata", Toast.LENGTH_SHORT).show();
            }
        }

        // Imposto la visibilità del TextView in base all'ArrayList di aste
        if (asteVuote) {
            progress_bar_le_mie_aste.setVisibility(View.INVISIBLE);
            text_view_nessuna_asta_trovata.setVisibility(View.VISIBLE);
        } else {
            progress_bar_le_mie_aste.setVisibility(View.INVISIBLE);
            text_view_nessuna_asta_trovata.setVisibility(View.INVISIBLE);
        }
    }




    //levare gli observer per tipo utente e fare solo per chiusa e aperta

//    public void osservaAcquirenteModelPresente(){
//        leMieAsteViewModel.acquirenteModelPresente.observe(this, (messaggio) -> {
//            if (leMieAsteViewModel.getAcquirenteModelPresente()) {
//                Toast.makeText(this, "Entrato come acquirente in le mie aste.", Toast.LENGTH_SHORT).show();
//                //chiamate ai vari observer, tra cui quelli di cambiare schermata
//                osservaCondizioneApertaAcquirente();//appena si entra in le mie aste viene fatto un set aperte/quando viene premuto lo switch su aperte
//                osservaCondizioneChiusaAcquirente();//osserva il caso in qui si debbano cercare aste chiuse
//
//            }
//        });
//    }
//
//    public void osservaVenditoreModelPresente(){
//        leMieAsteViewModel.venditoreModelPresente.observe(this, (messaggio) -> {
//            if (leMieAsteViewModel.getVenditoreModelPresente()) {
//                Toast.makeText(this, "Entrato come venditore in le mie aste.", Toast.LENGTH_SHORT).show();
//                //chiamate ai vari observer, tra cui quelli di cambiare schermata
//                osservaCondizioneApertaVenditore();//appena si entra in le mie aste viene fatto un set aperte/quando viene premuto lo switch su aperte
//                osservaCondizioneChiusaVenditore();//osserva il caso in qui si debbano cercare aste chiuse
//
//            }
//        });
//    }


//fa le ricerche-pre merge
    public void osservaCondizioneAperta(){
        leMieAsteViewModel.visualizzaAsteAperte.observe(this, (messaggio) -> {
            if (leMieAsteViewModel.getVisualizzaAsteAperte()){
                System.out.println("condizione aperta");
                //fa le cose che si farebbero quando la condizione è settata su aperta:recuperare le aste con condizione aperta

                leMieAsteViewModel.trovaAsteAperteViewModel();//prima trova queste aste poi
            }
        });
    }

//    public void osservaCondizioneApertaVenditore(){
//        leMieAsteViewModel.visualizzaAsteAperte.observe(this, (messaggio) -> {
//            if (leMieAsteViewModel.getVisualizzaAsteAperte()){
//                System.out.println("condizione aperta");
//                //fa le cose che si farebbero quando la condizione è settata su aperta:recuperare le aste con condizione aperta
//                osservaAsteAperteRecuperate();
//                osservaEntraInSchermataAstaInglese();
//                osservaEntraInSchermataAstaRibasso();
//                osservaEntraInSchermataAstaInversa();
//                leMieAsteViewModel.trovaAsteAperteVenditoreViewModel();//prima trova queste aste poi
//            }
//        });
//    }


//fare che in ogni trova asta si setta a finale un boleano a true e fare un controllo sui 3 valori e se tutti e 3 true ha fatto le 3 ricerche e poi fa il merge


    //post merge
    public void osservaAsteAperteRecuperate() {
        leMieAsteViewModel.asteAperteRecuperate.observe(this, (lista) -> {
            if (lista != null) {
                System.out.println("in osserva aste aperte recuperate");
                //lista di aste TUTTI I TIPI DI ASTA aperte recuperate(fare 3 recuperi per singolo tipo poi fondere in una mega lista)
                updateAste(lista,"aperta");

            }
        });
    }


    public void osservaAsteChiuseRecuperate() {
        leMieAsteViewModel.asteChiuseRecuperate.observe(this, (lista) -> {
            if (lista != null) {
                System.out.println("in osserva aste chiuse recuperate");
                //lista di aste TUTTI I TIPI DI ASTA chiuse recuperate(fare 3 recuperi per singolo tipo poi fondere in una mega lista)
                updateAste(lista,"chiusa");

            }
        });
    }


    public void osservaCondizioneChiusa(){
        leMieAsteViewModel.visualizzaAsteChiuse.observe(this, (messaggio) -> {
            if (leMieAsteViewModel.getVisualizzaAsteChiuse()){
                System.out.println("condizione chiusa");
                //fa le cose che si farebbero quando la condizione è settata su aperta:recuperare le aste con condizione aperta

                leMieAsteViewModel.trovaAsteChiuseViewModel();//prima trova queste aste poi
            }
        });
    }


//    public void osservaCondizioneChiusaVenditore(){
//        leMieAsteViewModel.visualizzaAsteChiuse.observe(this, (messaggio) -> {
//            if (leMieAsteViewModel.getVisualizzaAsteChiuse()){
//                System.out.println("condizione chiusa");
//                //fa le cose che si farebbero quando la condizione è settata su aperta:recuperare le aste con condizione aperta
//                osservaAsteChiuseRecuperate();
//                osservaEntraInSchermataAstaInglese();
//                osservaEntraInSchermataAstaRibasso();
//                osservaEntraInSchermataAstaInversa();
//                leMieAsteViewModel.trovaAsteChiuseVenditoreViewModel();//prima trova queste aste poi
//            }
//        });
//    }


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
