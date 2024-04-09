package com.example.progettoingsw.view.acquirente;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.DAO.AstaDAOAcquirente;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.view.SchermataAstaInglese;
import com.example.progettoingsw.view.SchermataAstaInversa;
import com.example.progettoingsw.view.SchermataAstaRibasso;
import com.example.progettoingsw.item.AstaIngleseItem;
import com.example.progettoingsw.item.AstaInversaItem;
import com.example.progettoingsw.item.AstaRibassoItem;
import com.example.progettoingsw.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    private CountDownTimer countDownTimerNumeroNotifiche;
    private RelativeLayout relative_layout_home_acquirente;
    private AstaDAOAcquirente astaDAOAcquirente;
    private AstaAdapter astaAdapterConsigliate;
    private AstaAdapter astaAdapterInScadenza;
    private AstaAdapter astaAdapterNuove;
    private TextView iconaNotifiche;
    private ProgressBar progressBarAcquirenteFragmentHome;
    ImageButton button_notifiche;
    private String email;
    private String tipoUtente;
    private ArrayList<String> categorie;
    private TextView text_view_nessuna_asta_in_scadenza;
    private TextView text_view_aste_consigliate_home;
    private TextView text_view_nessuna_asta_in_categorie;
    private HomeViewModel homeViewModel;
    private List<Asta_allingleseModel> listaAsta_allingleseScadenzaRecente;
    public FragmentHome() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Conserva il fragment durante i cambiamenti di configurazione
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

        osservaAcquirenteModelPresente();
        osservaVenditoreModelPresente();
        homeViewModel.checkTipoUtente();

        relative_layout_home_acquirente = view.findViewById(R.id.relative_layout_home_acquirente);
        progressBarAcquirenteFragmentHome = view.findViewById(R.id.progressBarAcquirenteFragmentHome);
        text_view_nessuna_asta_in_scadenza = view.findViewById(R.id.text_view_nessuna_asta_in_scadenza);
        text_view_aste_consigliate_home = view.findViewById(R.id.text_view_aste_consigliate_home);
        text_view_nessuna_asta_in_categorie = view.findViewById(R.id.text_view_nessuna_asta_in_categorie);

//        //rendo l'icona di caricamento visibile ad inizio recuperi dal db e il bottom menu non clickabile
//        progressBarAcquirenteFragmentHome.setVisibility(View.VISIBLE);
//        setNavigationView(false);
//        setAllClickable(relative_layout_home_acquirente,false);
//
//        // Inizializza il DAO e l'adapter
//        astaDAOAcquirente = new AstaDAOAcquirente(this, email,tipoUtente);
//
          astaAdapterConsigliate = new AstaAdapter(getContext(), null);

          // Inizializza il RecyclerView e imposta l'adapter
          RecyclerView recyclerViewAsteConsigliate = view.findViewById(R.id.recycler_view_aste_consigliate);
//        // Utilizza LinearLayoutManager con orientamento orizzontale per far si che il recycler sia orizzontale, di default è verticale
          LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
          recyclerViewAsteConsigliate.setLayoutManager(linearLayoutManager);
//
//
//        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
          DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
          recyclerViewAsteConsigliate.addItemDecoration(dividerItemDecoration);

          astaAdapterConsigliate.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                int position = recyclerViewAsteConsigliate.getChildAdapterPosition(v);
                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapterConsigliate.getItem(position);
                homeViewModel.gestisciClickRecyclerView(asta);

            }
        });
        recyclerViewAsteConsigliate.setAdapter(astaAdapterConsigliate);

        // Inizializza il RecyclerView e imposta l'adapter
        astaAdapterInScadenza = new AstaAdapter(getContext(), null);
        RecyclerView recyclerViewAsteInScadenza = view.findViewById(R.id.recycler_view_aste_in_scadenza);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAsteInScadenza.setLayoutManager(linearLayoutManager2);


        recyclerViewAsteInScadenza.addItemDecoration(dividerItemDecoration);
        recyclerViewAsteInScadenza.setAdapter(astaAdapterInScadenza);
        astaAdapterInScadenza.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                // Ottieni la posizione dell'elemento cliccato
                int position = recyclerViewAsteInScadenza.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapterInScadenza.getItem(position);
                homeViewModel.gestisciClickRecyclerView(asta);

            }
        });

        astaAdapterNuove = new AstaAdapter(getContext(), null);
        RecyclerView recyclerViewAsteNuove = view.findViewById(R.id.recycler_view_aste_nuove);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAsteNuove.setLayoutManager(linearLayoutManager3);


        recyclerViewAsteNuove.addItemDecoration(dividerItemDecoration);
        recyclerViewAsteNuove.setAdapter(astaAdapterNuove);
        astaAdapterNuove.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ottieni la posizione dell'elemento cliccato
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                int position = recyclerViewAsteNuove.getChildAdapterPosition(v);

                // Ottieni l'oggetto Asta corrispondente alla posizione cliccata
                Object asta = astaAdapterNuove.getItem(position);
                homeViewModel.gestisciClickRecyclerView(asta);

            }
        });
//        button_notifiche= view.findViewById(R.id.openNotifiche);
//        button_notifiche.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), SchermataNotifiche.class);
//                intent.putExtra("email", email);
//                intent.putExtra("tipoUtente",tipoUtente);
//                startActivity(intent);
//
//            }
//        });
//        iconaNotifiche = view.findViewById(R.id.iconaNotifiche);
//        NotificheDAO notificheDAO = new NotificheDAO(FragmentHome.this, email,tipoUtente);
//        notificheDAO.openConnection();
//        notificheDAO.checkNotifiche();
//
////        countDownTimerNumeroNotifiche = new CountDownTimer(5000, 1000) {
////            public void onTick(long millisUntilFinished) {
////                // Stampa il numero di secondi rimanenti
////                Log.d("Timer home fragment", "Secondi mancanti: " + millisUntilFinished / 1000);
////            }
////            public void onFinish() {
////                Log.d("Timer home fragment", "Timer scaduto");
////                notificheDAO.checkNotifiche();
////                start();
////            }
////        };
////        // Avvia il timer
////        countDownTimerNumeroNotifiche.start();

//        ImageButton preferitiButton = view.findViewById(R.id.openPreferiti);
//        preferitiButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), PreferitiActivity.class);
//                intent.putExtra("email", email);
//                intent.putExtra("tipoUtente",tipoUtente);
//                startActivity(intent);
//            }
//        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Chiudi la connessione al database quando il fragment viene distrutto
//        astaInversaItemDAO.closeConnection();
    }

    //questo metodo lo deve chiamare la classe DAO quando termina di recuperare le aste dal DB e gli passa tutto ciò che ha trovato, in questo caso un arraylist di object
    //l'arraylist deve essere di object per passare oggetti di diverse classi (in questo caso AstaInversa, AstaRibasso e AstaInglese
    //se si recupera dal DB solo un tipo di asta basta usare l'arraylist del tipo corrispondente ma questo con object funziona sia con 1 solo tipo che con più
    public void handleAsteConsigliateResult(ArrayList<Object> prodotti) {
        Log.d("FragmentHome" , "handleAsteConsigliateResult");
        boolean prodottiVuoto = prodotti == null || prodotti.isEmpty();
        if(categorie.size()==0){
            text_view_nessuna_asta_in_categorie.setVisibility(View.GONE);
            astaAdapterConsigliate.setAste(null);
            text_view_aste_consigliate_home.setVisibility(View.GONE);
        }else {

            if (!prodottiVuoto) {
                astaAdapterConsigliate.setAste(prodotti);
            } else {
                astaAdapterConsigliate.setAste(null);
            }
            if (prodottiVuoto) {
                text_view_nessuna_asta_in_categorie.setVisibility(View.VISIBLE);
            } else {
                text_view_nessuna_asta_in_categorie.setVisibility(View.GONE);
            }
        }
    }
    public void handleAsteInScadenzaResult(ArrayList<Object> prodotti) {
        boolean prodottiVuoto = prodotti == null || prodotti.isEmpty();
        Log.d("FragmentHome" , "handleAsteInScadenzaResult");
        if (!prodottiVuoto) {
            astaAdapterInScadenza.setAste(prodotti);
        }else{
            astaAdapterInScadenza.setAste(null);
        }
        if(prodottiVuoto){
            text_view_nessuna_asta_in_scadenza.setVisibility(View.VISIBLE);
        }else {
            text_view_nessuna_asta_in_scadenza.setVisibility(View.GONE);
        }

    }
    public void handleAsteNuoveResult(ArrayList<Object> prodotti) {
        // Aggiorna l'adapter con i nuovi prodotti
        Log.d("FragmentHome" , "handleAsteNuoveResult");
        if(prodotti != null){
            astaAdapterNuove.setAste(prodotti);
        }else{
            Log.d("handleConsigliateResult", "null");
        }
        //        //rendo l'icona di caricamento non piu visibile e il menu clickabile
        progressBarAcquirenteFragmentHome.setVisibility(View.INVISIBLE);
        setNavigationView(true);
        setAllClickable(relative_layout_home_acquirente, true);
    }


    public void setCategorie(ArrayList<String> categorieRecuperate){
        if(categorieRecuperate.size() == 0){
            text_view_aste_consigliate_home.setVisibility(View.GONE);
            text_view_nessuna_asta_in_categorie.setVisibility(View.GONE);
        }else{
            text_view_aste_consigliate_home.setVisibility(View.VISIBLE);
            text_view_nessuna_asta_in_categorie.setVisibility(View.VISIBLE);
        }
        this.categorie = categorieRecuperate;
        Log.d("setCategorie", "numero di categorie: " + categorieRecuperate.size() );
    }

    private void setNavigationView(Boolean valore) {
        AcquirenteMainActivity activity = (AcquirenteMainActivity) getActivity();
        if (activity != null) {
            // Abilita la BottomNavigationView
            Log.d("setNavigationView" , "preso comando : " + valore);
            activity.enableBottomNavigationView(valore);
        }
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
    public void handleGetNumeroNotifiche(int numero){
        Log.d("handleGetNumeroNotifiche" , "il numero di notifiche è :" + numero);
        if(numero>0){
            iconaNotifiche.setVisibility(View.VISIBLE);
            iconaNotifiche.setText(String.valueOf(numero));
        }else{
            iconaNotifiche.setVisibility(View.GONE);
        }
    }
    // questi metodi onPause, onStop, onDestroy e onResume servono a stoppare il timer quando non si è piu su questa schermata e a farlo ricominciare quando si torna
    @Override
    public void onPause() {
        super.onPause();
        astaAdapterInScadenza.stopAllTimers();
        astaAdapterConsigliate.stopAllTimers();
        astaAdapterNuove.stopAllTimers();
        // Ferma il countDownTimer se è attivo
//        if (countDownTimerNumeroNotifiche != null) {
//            countDownTimerNumeroNotifiche.cancel();
//        }
    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        // Ferma il countDownTimer se è attivo
//        if (countDownTimerNumeroNotifiche != null) {
//            countDownTimerNumeroNotifiche.cancel();
//        }
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // Ferma il countDownTimer se è attivo
//        if (countDownTimerNumeroNotifiche != null) {
//            countDownTimerNumeroNotifiche.cancel();
//        }
//    }
//    @Override
//    public void onResume() {
//        Log.d("Home" , "entrato in onResume");
//        super.onResume();
////        astaDAOAcquirente = new AstaDAOAcquirente(this, email,tipoUtente);
////        // Apri la connessione al database e ottieni i prodotti
////        astaDAOAcquirente.openConnection();
////        astaDAOAcquirente.getCategorie();
////        astaDAOAcquirente.getAsteCategorieAcquirente();
////        astaDAOAcquirente.getAsteScadenzaRecente();
////        astaDAOAcquirente.getAsteNuove();
////        astaDAOAcquirente.closeConnection();
////        NotificheDAO notificheDAO = new NotificheDAO(FragmentHome.this, email,tipoUtente);
////        notificheDAO.openConnection();
////        notificheDAO.checkNotifiche();
////        notificheDAO.closeConnection();
////        // Ferma il countDownTimer se è attivo
////        if (countDownTimerNumeroNotifiche != null) {
////            countDownTimerNumeroNotifiche.cancel();
////            countDownTimerNumeroNotifiche.start();
////        }
//    }

    public void osservaaste_allingleseCategoriaNomePresenti() {
        homeViewModel.aste_allingleseCategoriaNomePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getAste_allingleseCategoriaNomePresenti()) {
                ArrayList<Object> listaOggetti = new ArrayList<>(homeViewModel.getListaAsta_allingleseCategoriaNome());
                Log.d("FragmentHome" , "osserva asta all inglese categoria, prima,count: " + listaOggetti.size());
                astaAdapterConsigliate.setAste(listaOggetti);
                Log.d("FragmentHome" , "osserva asta all inglese categoria, dopo");
            }
        });
    }
    public void osservaaste_allingleseInScadenzaPresenti() {
        homeViewModel.aste_allingleseInScadenzaPresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getAste_allingleseInScadenzaPresenti()) {
                ArrayList<Object> listaOggetti = new ArrayList<>(homeViewModel.getListaAsta_allingleseScadenzaRecente());
                Log.d("FragmentHome" , "osserva asta all inglese in scadenza, prima,count: " + listaOggetti.size());
                astaAdapterInScadenza.setAste(listaOggetti);
                Log.d("FragmentHome" , "osserva asta all inglese in scadenza, dopo");
            }
        });
    }
    public void osservaaste_allingleseNuovePresenti() {
        homeViewModel.aste_allingleseNuovePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getAste_allingleseNuovePresenti()) {
                ArrayList<Object> listaOggetti = new ArrayList<>(homeViewModel.getListaAsta_allingleseNuove());
                Log.d("FragmentHome" , "osserva asta all inglese nuove, prima,count: " + listaOggetti.size());
                astaAdapterNuove.setAste(listaOggetti);
                Log.d("FragmentHome" , "osserva asta all inglese nuove, dopo");
            }
        });
    }
    public void osservaaste_alribassoCategoriaNomePresenti() {
        homeViewModel.aste_alribassoCategoriaNomePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getAste_alribassoCategoriaNomePresenti()) {
                ArrayList<Object> listaOggetti = new ArrayList<>(homeViewModel.getListaAsta_alribassoCategoriaNome());
                Log.d("FragmentHome" , "osserva asta all inglese categoria, prima,count: " + listaOggetti.size());
                astaAdapterConsigliate.setAste(listaOggetti);
                Log.d("FragmentHome" , "osserva asta all inglese categoria, dopo");
            }
        });
    }
    public void osservaAste_alribassoNuovePresenti() {
        homeViewModel.aste_alribassoNuovePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getAste_alribassoNuovePresenti()) {
                ArrayList<Object> listaOggetti = new ArrayList<>(homeViewModel.getListaAsta_alribassoNuove());
                Log.d("FragmentHome" , "osserva asta al ribasso nuove, prima,count: " + listaOggetti.size());
                astaAdapterNuove.setAste(listaOggetti);
                Log.d("FragmentHome" , "osserva asta al ribasso nuove, dopo");
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
                Toast.makeText(getContext(), "Entrato come acquirente in home.", Toast.LENGTH_SHORT).show();
                osservaaste_allingleseInScadenzaPresenti();
                osservaaste_allingleseNuovePresenti();
                osservaAste_alribassoNuovePresenti();
                osservaaste_allingleseCategoriaNomePresenti();
                osservaaste_alribassoCategoriaNomePresenti();
                osservaEntraInSchermataAstaInglese();
                osservaEntraInSchermataAstaRibasso();
                homeViewModel.trovaEImpostaAste();
            }
        });
    }
    public void osservaaste_inversaInScadenzaPresenti() {
        homeViewModel.aste_inversaInScadenzaPresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getAste_inversaInScadenzaPresenti()) {
                ArrayList<Object> listaOggetti = new ArrayList<>(homeViewModel.getListaAsta_inversaScadenzaRecente());
                Log.d("FragmentHome" , "osserva asta inverse, prima,count: " + listaOggetti.size());
                astaAdapterInScadenza.setAste(listaOggetti);
                Log.d("FragmentHome" , "osserva asta inverse, dopo");
            }
        });
    }
    public void osservaaste_inversaNuovePresenti() {
        homeViewModel.aste_inversaNuovePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getAste_inversaNuovePresenti()) {
                ArrayList<Object> listaOggetti = new ArrayList<>(homeViewModel.getListaAsta_inversaNuove());
                Log.d("FragmentHome" , "osserva asta al ribasso, prima,count: " + listaOggetti.size());
                astaAdapterNuove.setAste(listaOggetti);
                Log.d("FragmentHome" , "osserva asta al ribasso, dopo");
            }
        });
    }
    public void osservaaste_inversaCategoriaNomePresenti() {
        homeViewModel.aste_inversaCategoriaNomePresenti.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeViewModel.getAste_inversaCategoriaNomePresenti()) {
                ArrayList<Object> listaOggetti = new ArrayList<>(homeViewModel.getListaAsta_inversaCategoriaNome());
                Log.d("FragmentHome" , "osserva asta all inglese, prima,count: " + listaOggetti.size());
                astaAdapterConsigliate.setAste(listaOggetti);
                Log.d("FragmentHome" , "osserva asta all inglese, dopo");
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
                Toast.makeText(getContext(), "Entrato come acquirente in home.", Toast.LENGTH_SHORT).show();
                osservaaste_inversaInScadenzaPresenti();
                osservaaste_inversaNuovePresenti();
                osservaaste_inversaCategoriaNomePresenti();
                osservaEntraInSchermataAstaInversa();
                homeViewModel.trovaEImpostaAste();
            }
        });
    }


}