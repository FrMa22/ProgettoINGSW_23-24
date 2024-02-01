package com.example.progettoingsw;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.progettoingsw.controllers_package.Controller;

public class FragmentRicercaAsta extends Fragment {
    private EditText edittext_ricerca;
    private ImageButton openDrawerButton;
    private ImageButton button_filtro;

    public FragmentRicercaAsta() {
        // Costruttore vuoto richiesto dal framework
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ricerca_asta, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edittext_ricerca = view.findViewById(R.id.edittext_ricerca);

        Intent intent = requireActivity().getIntent();
        String valoreRicercato = intent.getStringExtra("valoreRicercato");
        edittext_ricerca.setText(valoreRicercato);

        button_filtro = view.findViewById(R.id.button_filtro);
        button_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpFiltroRicerca popUpFiltroRicerca = new PopUpFiltroRicerca(getContext());
                popUpFiltroRicerca.show();
            }
        });




    }
}