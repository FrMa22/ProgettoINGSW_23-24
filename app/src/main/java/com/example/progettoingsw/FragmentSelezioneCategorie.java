package com.example.progettoingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CheckboxGridAdapter;
import com.example.progettoingsw.gestori_gui.GridItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentSelezioneCategorie extends Fragment {
    private Controller controller;
    private NavigationView navigationView;
    private List<String> selectedRadioButtonItems = new ArrayList<>();
    private RecyclerView recyclerViewCategoria;
    private CheckboxGridAdapter checkboxGridAdapter;

    public FragmentSelezioneCategorie() {
        // Costruttore vuoto richiesto dal framework
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selezione_categorie, container, false);
        recyclerViewCategoria = view.findViewById(R.id.recyclerViewCategoria);
        return view;
    }

    // Nella tua classe FragmentSelezioneCategorie
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new Controller();

        // Carica l'array di nomi e immagini alternati
        List<String> itemNamesAndImages = Arrays.asList(getResources().getStringArray(R.array.item_names_and_images));

        // Crea e imposta l'adapter per la RecyclerView
        checkboxGridAdapter = new CheckboxGridAdapter(itemNamesAndImages, requireContext());
        recyclerViewCategoria.setAdapter(checkboxGridAdapter);
        recyclerViewCategoria.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }


    private List<GridItem> createGridItemListFromResources() {
        List<GridItem> gridItemList = new ArrayList<>();

        String[] itemNamesAndImages = getResources().getStringArray(R.array.item_names_and_images);

        for (int i = 0; i < itemNamesAndImages.length; i += 2) {
            String name = itemNamesAndImages[i];
            int imageResId = getResources().getIdentifier(itemNamesAndImages[i + 1], "drawable", requireContext().getPackageName());
            gridItemList.add(new GridItem(name, imageResId));
        }

        return gridItemList;
    }
}
