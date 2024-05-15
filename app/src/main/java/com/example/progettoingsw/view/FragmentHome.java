package com.example.progettoingsw.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.progettoingsw.R;
import com.example.progettoingsw.gestori_gui.AstaAdapter;
import com.example.progettoingsw.viewmodel.HomeViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

public class FragmentHome extends Fragment {
    private RelativeLayout relative_layout_home_acquirente;
    private AstaAdapter astaAdapterConsigliate;
    private AstaAdapter astaAdapterInScadenza;
    private AstaAdapter astaAdapterNuove;
    private TextView iconaNotifiche;
    private ProgressBar progressBarAcquirenteFragmentHome;
    private RecyclerView recyclerViewAsteConsigliate;
    ImageButton button_notifiche;
    private TextView text_view_nessuna_asta_in_scadenza;
    private TextView text_view_nessuna_asta_nuova;
    private TextView text_view_aste_consigliate_home;
    private TextView text_view_nessuna_asta_in_categorie;
    private HomeViewModel homeViewModel;
    private ScrollView scrollView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public FragmentHome() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        relative_layout_home_acquirente = view.findViewById(R.id.relative_layout_home_acquirente);
        progressBarAcquirenteFragmentHome = view.findViewById(R.id.progressBarAcquirenteFragmentHome);
        text_view_nessuna_asta_in_scadenza = view.findViewById(R.id.text_view_nessuna_asta_in_scadenza);
        text_view_aste_consigliate_home = view.findViewById(R.id.text_view_aste_consigliate_home);
        text_view_nessuna_asta_in_categorie = view.findViewById(R.id.text_view_nessuna_asta_in_categorie);
        text_view_nessuna_asta_nuova = view.findViewById(R.id.text_view_nessuna_asta_nuova);

          astaAdapterConsigliate = new AstaAdapter(getContext(), null);

          recyclerViewAsteConsigliate = view.findViewById(R.id.recycler_view_aste_consigliate);
          LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
          recyclerViewAsteConsigliate.setLayoutManager(linearLayoutManager);


          astaAdapterConsigliate.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerViewAsteConsigliate.getChildAdapterPosition(v);
                Object asta = astaAdapterConsigliate.getItem(position);
                homeViewModel.gestisciClickRecyclerView(asta);

            }
        });
        recyclerViewAsteConsigliate.setAdapter(astaAdapterConsigliate);

        astaAdapterInScadenza = new AstaAdapter(getContext(), null);
        RecyclerView recyclerViewAsteInScadenza = view.findViewById(R.id.recycler_view_aste_in_scadenza);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAsteInScadenza.setLayoutManager(linearLayoutManager2);


        recyclerViewAsteInScadenza.setAdapter(astaAdapterInScadenza);
        astaAdapterInScadenza.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerViewAsteInScadenza.getChildAdapterPosition(v);

                Object asta = astaAdapterInScadenza.getItem(position);
                homeViewModel.gestisciClickRecyclerView(asta);

            }
        });

        astaAdapterNuove = new AstaAdapter(getContext(), null);
        RecyclerView recyclerViewAsteNuove = view.findViewById(R.id.recycler_view_aste_nuove);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAsteNuove.setLayoutManager(linearLayoutManager3);


        recyclerViewAsteNuove.setAdapter(astaAdapterNuove);
        astaAdapterNuove.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerViewAsteNuove.getChildAdapterPosition(v);

                Object asta = astaAdapterNuove.getItem(position);
                homeViewModel.gestisciClickRecyclerView(asta);

            }
        });
        button_notifiche= view.findViewById(R.id.openNotifiche);
        button_notifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SchermataNotifiche.class);

                startActivity(intent);

            }
        });
        iconaNotifiche = view.findViewById(R.id.iconaNotifiche);


        ImageButton preferitiButton = view.findViewById(R.id.openPreferiti);
        preferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PreferitiActivity.class);
                startActivity(intent);
            }
        });


        osservaAcquirenteModelPresente();
        osservaVenditoreModelPresente();
        osservaNumeroNotifiche();

        osservaaste_allingleseCategoriaNomeRecuperate();
        osservaaste_allingleseInScadenzaRecuperate();
        osservaaste_allingleseNuoveRecuperate();
        osservaaste_allingleseNuovePresenti();
        osservaaste_allingleseCategoriaNomePresenti();
        osservaaste_allingleseInScadenzaPresenti();

        osservaaste_alribassoCategoriaNomeRecuperate();
        osservaAste_alribassoNuoveRecuperate();
        osservaAste_alribassoNuovePresenti();
        osservaaste_alribassoCategoriaNomePresenti();

        osservaEntraInSchermataAstaInglese();
        osservaEntraInSchermataAstaRibasso();
        osservaEntraInSchermataAstaInversa();

        osservaaste_inversaCategoriaNomeRecuperate();
        osservaaste_inversaNuoveRecuperate();
        osservaaste_inversaInScadenzaRecuperate();
        osservaaste_inversaInScadenzaPresenti();
        osservaaste_inversaNuovePresenti();
        osservaaste_inversaCategoriaNomePresenti();

        osservaCategorieVuote();


        scrollView = view.findViewById(R.id.scroll_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true); // Abilita lo SwipeRefreshLayout
                } else {
                    swipeRefreshLayout.setEnabled(false); // Disabilita lo SwipeRefreshLayout
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Chiamata al metodo per aggiornare i dati
                astaAdapterConsigliate.clearItems();
                astaAdapterNuove.clearItems();
                astaAdapterInScadenza.clearItems();
                homeViewModel.checkTipoUtente();
                homeViewModel.checkNumeroNotifiche();
                // Termina l'animazione di aggiornamento
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }






    @Override
    public void onPause() {
        super.onPause();
        //stop dei timer che fanno avanzare la scadenza delle aste inglesi
        astaAdapterInScadenza.stopAllTimers();
        astaAdapterConsigliate.stopAllTimers();
        astaAdapterNuove.stopAllTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Fragment Home");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "FragmentHome");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);

        astaAdapterConsigliate.clearItems();
        astaAdapterInScadenza.clearItems();
        astaAdapterNuove.clearItems();
        homeViewModel.checkTipoUtente();
    }

    public void osservaaste_allingleseCategoriaNomePresenti() {
        homeViewModel.aste_allingleseCategoriaNomePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (messaggio){
                homeViewModel.recuperaAste_allingleseCategorieNome();
            }
            checkRecylerVuoti();
        });
    }
    public void osservaaste_allingleseCategoriaNomeRecuperate() {
        homeViewModel.aste_allingleseCategoriaNomeRecuperate.observe(getViewLifecycleOwner(), (listaAste) -> {
            if (listaAste!=null) {
                astaAdapterConsigliate.setAste(listaAste);
            }
        });
    }
    public void osservaaste_allingleseInScadenzaPresenti() {
        homeViewModel.aste_allingleseInScadenzaPresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (messaggio) {
                homeViewModel.recuperaAste_allingleseInScadenza();
            }
            checkRecylerVuoti();
        });
    }
    public void osservaaste_allingleseInScadenzaRecuperate() {
        homeViewModel.aste_allingleseInScadenzaRecuperate.observe(getViewLifecycleOwner(), (lista) -> {
            if (lista != null) {
                astaAdapterInScadenza.setAste(lista);
            }
        });
    }

    public void osservaaste_allingleseNuovePresenti() {
        homeViewModel.aste_allingleseNuovePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (messaggio) {
                homeViewModel.recuperaAste_allingleseNuove();
            }
            checkRecylerVuoti();
        });
    }
    public void osservaaste_allingleseNuoveRecuperate() {
        homeViewModel.aste_allingleseNuoveRecuperate.observe(getViewLifecycleOwner(), (lista) -> {
            if (lista != null) {
                astaAdapterNuove.setAste(lista);
            }
        });
    }
    public void osservaaste_alribassoCategoriaNomePresenti() {
        homeViewModel.aste_alribassoCategoriaNomePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (messaggio) {
                homeViewModel.recuperaAste_alribassoCategorieNome();
            }
            checkRecylerVuoti();
        });
    }
    public void osservaaste_alribassoCategoriaNomeRecuperate() {
        homeViewModel.aste_alribassoCategoriaNomeRecuperate.observe(getViewLifecycleOwner(), (lista) -> {
            if (lista != null) {
                astaAdapterConsigliate.setAste(lista);
            }
        });
    }
    public void osservaAste_alribassoNuovePresenti() {
        homeViewModel.aste_alribassoNuovePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (messaggio) {
                homeViewModel.recuperaAste_alribassoNuove();
            }
            checkRecylerVuoti();
        });
    }
    public void osservaAste_alribassoNuoveRecuperate() {
        homeViewModel.aste_alribassoNuoveRecuperate.observe(getViewLifecycleOwner(), (lista) -> {
            if (lista != null) {
                astaAdapterNuove.setAste(lista);
            }
        });
    }
    public void osservaEntraInSchermataAstaInglese(){
        homeViewModel.entraInSchermataAstaInglese.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getEntraInSchermataAstaInglese()){
                Intent intent = new Intent(getContext(), SchermataAstaInglese.class);
                startActivity(intent);
            }
        });
    }
    public void osservaEntraInSchermataAstaRibasso(){
        homeViewModel.entraInSchermataAstaRibasso.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getEntraInSchermataAstaRibasso()){
                Intent intent = new Intent(getContext(), SchermataAstaRibasso.class);
                startActivity(intent);
            }
        });
    }
    public void osservaAcquirenteModelPresente(){
        homeViewModel.acquirenteModelPresente.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getAcquirenteModelPresente()) {
                homeViewModel.trovaEImpostaAste();
            }
        });
    }
    public void osservaaste_inversaInScadenzaPresenti() {
        homeViewModel.aste_inversaInScadenzaPresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if(messaggio){
                homeViewModel.recuperaAste_inversaInScadenza();
            }
            checkRecylerVuoti();
        });
    }
    public void osservaaste_inversaInScadenzaRecuperate() {
        homeViewModel.aste_inversaInScadenzaRecuperate.observe(getViewLifecycleOwner(), (lista) -> {
            if (lista != null) {
                astaAdapterInScadenza.setAste(lista);
            }
        });
    }
    public void osservaaste_inversaNuovePresenti() {
        homeViewModel.aste_inversaNuovePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (messaggio) {
                homeViewModel.recuperaAste_inversaNuove();
            }
            checkRecylerVuoti();
        });
    }
    public void osservaaste_inversaNuoveRecuperate() {
        homeViewModel.aste_inversaNuoveRecuperate.observe(getViewLifecycleOwner(), (lista) -> {
            if (lista != null) {
                astaAdapterNuove.setAste(lista);
            }
        });
    }
    public void osservaaste_inversaCategoriaNomePresenti() {
        homeViewModel.aste_inversaCategoriaNomePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (messaggio) {
                homeViewModel.recuperaAste_inversaCategorieNome();
            }
            checkRecylerVuoti();
        });
    }
    public void osservaaste_inversaCategoriaNomeRecuperate() {
        homeViewModel.aste_inversaCategoriaNomeRecuperate.observe(getViewLifecycleOwner(), (lista) -> {
            if (lista != null) {
                astaAdapterConsigliate.setAste(lista);
            }
        });
    }
    public void osservaEntraInSchermataAstaInversa(){
        homeViewModel.entraInSchermataAstaInversa.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getEntraInSchermataAstaInversa()){
                Intent intent = new Intent(getContext(), SchermataAstaInversa.class);
                startActivity(intent);
            }
        });
    }
    public void osservaVenditoreModelPresente(){
        homeViewModel.venditoreModelPresente.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getVenditoreModelPresente()) {
                homeViewModel.trovaEImpostaAste();
            }
        });
    }
    public void osservaCategorieVuote(){
        homeViewModel.categorieVuote.observe(getViewLifecycleOwner(), (valore)->{
            if(valore){
                text_view_nessuna_asta_in_categorie.setVisibility(View.GONE);
                text_view_aste_consigliate_home.setVisibility(View.GONE);
                recyclerViewAsteConsigliate.setVisibility(View.GONE);
            }else{
                text_view_nessuna_asta_in_categorie.setVisibility(View.VISIBLE);
            }
        });
    }

    public void osservaNumeroNotifiche(){
        homeViewModel.numeroNotifiche.observe(getViewLifecycleOwner(), (numero)->{
            if(numero > 0){
                if(numero > 9){
                    iconaNotifiche.setVisibility(View.VISIBLE);
                    iconaNotifiche.setText("9+");
                } else {
                    iconaNotifiche.setVisibility(View.VISIBLE);
                    iconaNotifiche.setText(String.valueOf(numero));
                }
            } else {
                iconaNotifiche.setVisibility(View.GONE);
            }
        });
    }
    public void checkRecylerVuoti(){
        if(astaAdapterConsigliate.getItemCount()==0){
            homeViewModel.checkCategorie();
        }else{
            text_view_nessuna_asta_in_categorie.setVisibility(View.GONE);
        }
        if(astaAdapterInScadenza.getItemCount()==0){
            text_view_nessuna_asta_in_scadenza.setVisibility(View.VISIBLE);
        }else{
            text_view_nessuna_asta_in_scadenza.setVisibility(View.GONE);
        }
        if(astaAdapterNuove.getItemCount()==0){
            text_view_nessuna_asta_nuova.setVisibility(View.VISIBLE);
        }else{
            text_view_nessuna_asta_nuova.setVisibility(View.GONE);
        }
    }

}