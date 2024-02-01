package com.example.progettoingsw;

import com.example.progettoingsw.gestori_gui.ElementoListaCategorie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gestori_gui.CheckboxGridAdapter;
import com.example.progettoingsw.gestori_gui.ElementoListaCategorie;
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
        LinearLayout linearLayout = view.findViewById(R.id.linear_layout_checkbox); // Riferimento al tuo LinearLayout
        populateLinearLayout(linearLayout); // Popola il LinearLayout con ImageView e CheckBox
        return view;
    }

    // Nella tua classe FragmentSelezioneCategorie
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new Controller();

    }

    private void populateLinearLayout(LinearLayout linearLayout) {
        List<ElementoListaCategorie> elementiLista = createElementiListaFromResources();

        for (ElementoListaCategorie elemento : elementiLista) {
            FrameLayout frameLayout = new FrameLayout(requireContext());

            // ImageView con dimensioni fisse (100dp x 100dp)
            ImageView imageView = new ImageView(requireContext());
            imageView.setImageResource(elemento.getImmagine());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(100, 100);
            imageView.setLayoutParams(imageParams);

            // CheckBox con altezza fissa (100dp) e larghezza che occupa lo spazio rimanente
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setText(elemento.getTesto());
            FrameLayout.LayoutParams checkBoxParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 100);
            checkBoxParams.leftMargin = 100; // Imposta il margine sinistro per evitare sovrapposizioni
            checkBox.setLayoutParams(checkBoxParams);

            frameLayout.addView(imageView);
            frameLayout.addView(checkBox);

            linearLayout.addView(frameLayout);
        }
    }




    private List<ElementoListaCategorie> createElementiListaFromResources() {
        // Recupera gli array di percorsi per immagini e testi
        String[] arrayPercorsiImmagini = getResources().getStringArray(R.array.array_immagini_categorie);
        String[] arrayTesti = getResources().getStringArray(R.array.array_testi_categorie);

        // Crea l'ArrayList finale contenente oggetti ElementoListaCategorie
        List<ElementoListaCategorie> elementiLista = new ArrayList<>();

        // Verifica che gli array abbiano la stessa lunghezza
        if (arrayPercorsiImmagini.length == arrayTesti.length) {
            // Itera attraverso gli array e crea gli oggetti ElementoListaCategorie
            for (int i = 0; i < arrayPercorsiImmagini.length; i++) {
                // Ottieni l'identificatore della risorsa per l'immagine dal percorso
                int immagineResId = getResources().getIdentifier(arrayPercorsiImmagini[i], "drawable", requireContext().getPackageName());

                // Aggiungi l'elemento alla lista
                elementiLista.add(new ElementoListaCategorie(immagineResId, arrayTesti[i]));
            }
        } else {
            // Se gli array hanno lunghezze diverse, gestisci l'errore o la situazione indesiderata
            // Puoi loggare un messaggio di errore o eseguire altre azioni a seconda delle tue esigenze
        }

        return elementiLista;
    }



}
