package com.example.progettoingsw.gui.acquirente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.DAO.ProdottoDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.controllers_package.ProdottoAdapter;
import com.example.progettoingsw.model.Prodotto;

import java.util.List;

public class AcquirenteFragmentHome extends Fragment {

    private ProdottoDAO prodottoDAO;
    private ProdottoAdapter prodottoAdapter;

    public AcquirenteFragmentHome() {
        // Costruttore vuoto richiesto dal framework
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acquirente_fragment_home, container, false);
        // Inizializza il DAO e l'adapter
        prodottoDAO = new ProdottoDAO(this);
        prodottoAdapter = new ProdottoAdapter(getContext(), null);

        // Inizializza il RecyclerView e imposta l'adapter
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_prodotti);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(prodottoAdapter);

        // Apri la connessione al database e ottieni i prodotti
        prodottoDAO.openConnection();
        prodottoDAO.getProdotti();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        prodottoDAO.closeConnection();
    }

    public void handleProdottiResult(List<Prodotto> prodotti) {
        // Aggiorna l'adapter con i nuovi prodotti
        prodottoAdapter.setProdotti(prodotti);
    }
}
