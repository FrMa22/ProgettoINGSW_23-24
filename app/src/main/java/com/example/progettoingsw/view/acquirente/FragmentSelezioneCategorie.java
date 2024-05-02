package com.example.progettoingsw.view.acquirente;

import com.example.progettoingsw.R;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.view.SchermataAstaInglese;
import com.example.progettoingsw.view.SchermataAstaInversa;
import com.example.progettoingsw.view.SchermataAstaRibasso;
import com.example.progettoingsw.view.SchermataAstePerCategoria;
import com.example.progettoingsw.viewmodel.SelezioneCategorieViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class FragmentSelezioneCategorie extends Fragment {
    private List<String> selectedRadioButtonItems = new ArrayList<>();
    private LinearLayout linearLayoutCategorie;
    private ArrayAdapter<String> categorieAdapter;
    private SelezioneCategorieViewModel selezioneCategorieViewModel;
    public FragmentSelezioneCategorie() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selezione_categorie, container, false);
        linearLayoutCategorie = view.findViewById(R.id.linear_layout_categorie);
        populateLinearLayout();
        return view;
    }


    // Nella tua classe FragmentSelezioneCategorie
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selezioneCategorieViewModel = new ViewModelProvider(this).get(SelezioneCategorieViewModel.class);
        osservaCategoriaSelezionata();


    }
    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Fragment Selezione Categorie");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "FragmentSelezioneCategorie");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }
    private void populateLinearLayout() {
        String[] categorieArray = getResources().getStringArray(R.array.categories_array);
        TypedArray immaginiArray = getResources().obtainTypedArray(R.array.immagini_categorie);

        for (int i = 0; i < categorieArray.length; i++) {
            Button button = new Button(requireContext());
            button.setText(categorieArray[i]);
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
            button.setTextColor(getResources().getColor(R.color.colore_secondario));

            try {
                // Imposta l'immagine a sinistra del testo
                Drawable immagine = immaginiArray.getDrawable(i);
                immagine.setBounds(0, 0, immagine.getIntrinsicWidth(), immagine.getIntrinsicHeight());
                button.setCompoundDrawablesWithIntrinsicBounds(immagine, null, getResources().getDrawable(R.drawable.ic_freccia_piccola_dx), null);
            } catch (Resources.NotFoundException e) {
                Log.e("Errore", "Impossibile trovare l'immagine per la categoria " + categorieArray[i]);
            }
            button.setGravity(Gravity.CENTER);
            button.setBackgroundColor(Color.TRANSPARENT);
            //button.setBackgroundResource(R.drawable.bordo_sotto_grigio);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String categoriaSelezionata = ((Button) v).getText().toString();
                    selezioneCategorieViewModel.categoriaCliccata(categoriaSelezionata);
                }
            });

            linearLayoutCategorie.addView(button);
        }

        immaginiArray.recycle();
    }

    public void osservaCategoriaSelezionata(){
        selezioneCategorieViewModel.categoriaSelezionata.observe(getViewLifecycleOwner(), (valore) ->{
            if(valore){
                Intent intent = new Intent(requireContext(), SchermataAstePerCategoria.class);
                startActivity(intent);
            }
        });
    }


}
