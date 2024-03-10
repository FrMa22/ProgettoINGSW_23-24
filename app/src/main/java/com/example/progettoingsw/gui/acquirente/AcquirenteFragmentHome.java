package com.example.progettoingsw.gui.acquirente;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.DAO.AstaDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.AstaAdapter;

import java.util.ArrayList;

public class AcquirenteFragmentHome extends Fragment {

    private AstaDAO astaDAO;
    private AstaAdapter astaAdapter;
    TextView textView_condizione_prova;
    ImageView image_view_prova;
    TextView textView_nome_prova;
    TextView textView_descrizione_prova;
    TextView textView_prezzo_prova;
    TextView textView_data_scadenza_prova;
    private String email;





    public AcquirenteFragmentHome(String email) {
        this.email = email;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acquirente_fragment_home, container, false);
        // Inizializza il DAO e l'adapter
        astaDAO = new AstaDAO(this, email);
        astaAdapter = new AstaAdapter(getContext(), null);

        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_prodotti);
        // Utilizza LinearLayoutManager con orientamento orizzontale per far si che il recycler sia orizzontale, di default è verticale
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Aggiungi un decorator predefinito per ridurre lo spazio tra le aste, superfluo
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(astaAdapter);

        // Apri la connessione al database e ottieni i prodotti
        astaDAO.openConnection();
        astaDAO.getAste();
        astaDAO.closeConnection();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //codice per l'asta di prova, commentato
//        textView_condizione_prova = view.findViewById(R.id.textView_condizione_prova);
        image_view_prova = view.findViewById(R.id.image_view_item);
        textView_nome_prova = view.findViewById(R.id.textView_nome_item);
        textView_descrizione_prova = view.findViewById(R.id.textView_descrizione_item);
        textView_prezzo_prova = view.findViewById(R.id.textView_prezzo_item);
        textView_data_scadenza_prova = view.findViewById(R.id.textView_data_scadenza_item);

//        AcquirenteHomeDAO acquirenteHomeDAO = new AcquirenteHomeDAO(this, email);
//        acquirenteHomeDAO.openConnection();
//        acquirenteHomeDAO.findAstaInversaProva();
//        acquirenteHomeDAO.closeConnection();




        ImageButton preferitiButton = view.findViewById(R.id.openPreferiti);
        preferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Apri l'activity dei preferiti
                // Controller.redirectActivity(getActivity(), PreferitiActivity.class);
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
    public void handleProdottiResult(ArrayList<Object> prodotti) {
        // Aggiorna l'adapter con i nuovi prodotti
        astaAdapter.setAste(prodotti);
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
}
