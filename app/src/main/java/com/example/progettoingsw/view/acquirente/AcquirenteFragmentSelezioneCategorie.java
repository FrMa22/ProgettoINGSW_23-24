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

import com.example.progettoingsw.view.SchermataAstePerCategoria;

import java.util.ArrayList;
import java.util.List;

public class AcquirenteFragmentSelezioneCategorie extends Fragment {
    private List<String> selectedRadioButtonItems = new ArrayList<>();
    private LinearLayout linearLayoutCategorie;
    private ArrayAdapter<String> categorieAdapter;
    String email ;
    String tipoUtente;
    public AcquirenteFragmentSelezioneCategorie(String email, String tipoUtente) {
        this.email=email;
        this.tipoUtente=tipoUtente;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acquirente_fragment_selezione_categorie, container, false);
        linearLayoutCategorie = view.findViewById(R.id.linear_layout_categorie);
        populateLinearLayout();
        return view;
    }


    // Nella tua classe AcquirenteFragmentSelezioneCategorie
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

            // Imposta il testo al centro
            button.setGravity(Gravity.CENTER);

            // Imposta il colore di sfondo del bottone a trasparente
            button.setBackgroundColor(Color.TRANSPARENT);

            // Imposta il layout personalizzato come sfondo del bottone
            button.setBackgroundResource(R.drawable.bordo_sotto_grigio);

            // Imposta un ascoltatore di clic per gestire l'evento di clic su ogni bottone
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String categoriaSelezionata = ((Button) v).getText().toString();

                    Intent intent = new Intent(requireContext(), SchermataAstePerCategoria.class);
                    intent.putExtra("categoria_selezionata", categoriaSelezionata);
                    intent.putExtra("email",email);
                    intent.putExtra("tipoUtente",tipoUtente);
                    startActivity(intent);
                }
            });

            linearLayoutCategorie.addView(button);
        }

        immaginiArray.recycle();
    }






}
