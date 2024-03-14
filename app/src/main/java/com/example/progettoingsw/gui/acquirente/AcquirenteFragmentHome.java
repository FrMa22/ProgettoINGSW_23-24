package com.example.progettoingsw.gui.acquirente;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.DAO.AcquirenteHomeDAO;
import com.example.progettoingsw.DAO.AstaDAOAcquirente;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.LeMieAste;
import com.example.progettoingsw.gui.PreferitiActivity;
import com.example.progettoingsw.gui.venditore.VenditoreMainActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class AcquirenteFragmentHome extends Fragment {
    private RelativeLayout relative_layout_home_acquirente;
    private AstaDAOAcquirente astaDAOAcquirente;
    private AstaAdapter astaAdapterConsigliate;
    private AstaAdapter astaAdapterInScadenza;
    private AstaAdapter astaAdapterNuove;

    private ProgressBar progressBarAcquirenteFragmentHome;
    TextView textView_condizione_prova;
    ImageView image_view_prova;
    TextView textView_nome_prova;
    TextView textView_descrizione_prova;
    TextView textView_prezzo_prova;
    TextView textView_data_scadenza_prova;
    MaterialButton button_le_mie_aste;
    private String email;
    private ArrayList<String> categorie;
    private AcquirenteHomeDAO acquirenteHomeDAO;
    public AcquirenteFragmentHome(String email) {
        this.email = email;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acquirente_fragment_home, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        relative_layout_home_acquirente = view.findViewById(R.id.relative_layout_home_acquirente);
        progressBarAcquirenteFragmentHome = view.findViewById(R.id.progressBarAcquirenteFragmentHome);

        // Inizializza il DAO e l'adapter
        astaDAOAcquirente = new AstaDAOAcquirente(this, email);

        astaAdapterConsigliate = new AstaAdapter(getContext(), null);
        categorie = new ArrayList<>();

        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerViewAsteConsigliate = view.findViewById(R.id.recycler_view_aste_consigliate);
        // Utilizza LinearLayoutManager con orientamento orizzontale per far si che il recycler sia orizzontale, di default è verticale
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAsteConsigliate.setLayoutManager(linearLayoutManager);

        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        recyclerViewAsteConsigliate.addItemDecoration(dividerItemDecoration);
        recyclerViewAsteConsigliate.setAdapter(astaAdapterConsigliate);

        //rendo l'icona di caricamento visibile ad inizio recuperi dal db e il bottom menu non clickabile
        progressBarAcquirenteFragmentHome.setVisibility(View.VISIBLE);
        setNavigationView(false);
        relative_layout_home_acquirente.setClickable(false);

        // Apri la connessione al database e ottieni i prodotti
        astaDAOAcquirente.openConnection();
        Log.d("categorie in home prima" , "numero di categorie: " + categorie.isEmpty());
        astaDAOAcquirente.getCategorie();
        Log.d("categorie in home" , "numero di categorie: " + categorie.size());
        astaDAOAcquirente.getAsteCategorieAcquirente();
        astaDAOAcquirente.closeConnection();


        // Inizializza il RecyclerView e imposta l'adapter
        astaAdapterInScadenza = new AstaAdapter(getContext(), null);
        RecyclerView recyclerViewAsteInScadenza = view.findViewById(R.id.recycler_view_aste_in_scadenza);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAsteInScadenza.setLayoutManager(linearLayoutManager2);


        recyclerViewAsteInScadenza.addItemDecoration(dividerItemDecoration);
        recyclerViewAsteInScadenza.setAdapter(astaAdapterInScadenza);

        astaDAOAcquirente.openConnection();
        astaDAOAcquirente.getAsteScadenzaRecente();
        astaDAOAcquirente.closeConnection();

        astaAdapterNuove = new AstaAdapter(getContext(), null);
        RecyclerView recyclerViewAsteNuove = view.findViewById(R.id.recycler_view_aste_nuove);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAsteNuove.setLayoutManager(linearLayoutManager3);


        recyclerViewAsteNuove.addItemDecoration(dividerItemDecoration);
        recyclerViewAsteNuove.setAdapter(astaAdapterNuove);

        astaDAOAcquirente.openConnection();
        astaDAOAcquirente.getAsteNuove();
        astaDAOAcquirente.closeConnection();
        //codice per l'asta di prova, commentato
//        textView_condizione_prova = view.findViewById(R.id.textView_condizione_prova);
        image_view_prova = view.findViewById(R.id.image_view_item);
        textView_nome_prova = view.findViewById(R.id.textView_nome_item);
        textView_descrizione_prova = view.findViewById(R.id.textView_descrizione_item);
        textView_prezzo_prova = view.findViewById(R.id.textView_prezzo_item);
        textView_data_scadenza_prova = view.findViewById(R.id.textView_data_scadenza_item);

        AcquirenteHomeDAO acquirenteHomeDAO = new AcquirenteHomeDAO(this, email);
        acquirenteHomeDAO.openConnection();
        acquirenteHomeDAO.findAstaInversaProva();
        acquirenteHomeDAO.closeConnection();

        //rendo l'icona di caricamento non piu visibile e il menu clickabile
        progressBarAcquirenteFragmentHome.setVisibility(View.INVISIBLE);
        setNavigationView(true);
        relative_layout_home_acquirente.setClickable(true);

        button_le_mie_aste = view.findViewById(R.id.button_le_mie_aste);
        button_le_mie_aste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("le aste oooh");
                Intent intent = new Intent(getContext(), LeMieAste.class);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });



        ImageButton preferitiButton = view.findViewById(R.id.openPreferiti);
        preferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Controller.redirectActivityEmailTipoUtente(getActivity(), PreferitiActivity.class,email,"acquirente");
                Toast.makeText(getContext(), "Apri l'activity dei preferiti", Toast.LENGTH_SHORT).show();
            }
        });
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
        // Aggiorna l'adapter con i nuovi prodotti
        if(prodotti != null){
            astaAdapterConsigliate.setAste(prodotti);
        }else{
            Log.d("handleConsigliateResult", "null");
        }

    }
    public void handleAsteInScadenzaResult(ArrayList<Object> prodotti) {
        // Aggiorna l'adapter con i nuovi prodotti
        if(prodotti != null){
            Log.d("handleScadenzaResult", "ok");
            astaAdapterInScadenza.setAste(prodotti);
        }else{
            Log.d("handleScadenzaResult ", "null");
        }
    }
    public void handleAsteNuoveResult(ArrayList<Object> prodotti) {
        // Aggiorna l'adapter con i nuovi prodotti
        if(prodotti != null){
        astaAdapterNuove.setAste(prodotti);
        }else{
            Log.d("handleConsigliateResult", "null");
        }
    }



    //metodo per l'asta di prova, commentabile
    public void handleProva( String nome, String descrizione, String prezzo, String data, String condizione, Bitmap foto){
        Log.d("home", "nome , descrizione, prezzo, data, condizione: " + nome + descrizione + prezzo + data + condizione);
        textView_nome_prova.setText(nome);
        textView_descrizione_prova.setText(descrizione);
        textView_prezzo_prova.setText(prezzo);
        textView_data_scadenza_prova.setText(data);
//        textView_condizione_prova.setText(condizione);
        image_view_prova.setImageBitmap(foto);
    }
    public void setCategorie(ArrayList<String> categorie){
        this.categorie = categorie;
    }

    private void setNavigationView(Boolean valore) {
        AcquirenteMainActivity activity = (AcquirenteMainActivity) getActivity();
        if (activity != null) {
            // Abilita la BottomNavigationView
            Log.d("setNavigationView" , "preso comando : " + valore);
            activity.enableBottomNavigationView(valore);
        }
    }

}
