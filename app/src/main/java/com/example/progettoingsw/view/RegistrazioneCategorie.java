package com.example.progettoingsw.view;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.viewmodel.RegistrazioneViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class RegistrazioneCategorie extends GestoreComuniImplementazioni {

    private MaterialButton bottoneProseguiInteressiRegistrazione;
    private MaterialButton bottoneSaltaRegistrazioneCategorie;
    private LinearLayout linear_layout_interno_registrazione_social;
    private ArrayList<String> switchTexts;
    private ProgressBar progress_bar_registrazione_categorie;
    private RegistrazioneViewModel registrazioneViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione_categorie);

        Toast.makeText(this, "Registrazione completata! Aiutaci a capire cosa ti piace selezionando le categorie di tuo interesse.", Toast.LENGTH_LONG).show();

        progress_bar_registrazione_categorie = findViewById(R.id.progress_bar_registrazione_categorie);
        switchTexts = new ArrayList<>();
        bottoneProseguiInteressiRegistrazione = findViewById(R.id.bottoneProseguiInteressiRegistrazione);
        bottoneSaltaRegistrazioneCategorie = findViewById(R.id.bottoneSaltaRegistrazioneCategorie);
        linear_layout_interno_registrazione_social = findViewById(R.id.linear_layout_interno_registrazione_social);
        registrazioneViewModel = new ViewModelProvider(this).get(RegistrazioneViewModel.class);

        osservaVaiInHome();

        bottoneProseguiInteressiRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrazioneViewModel.registraCategorie();
            }
        });

        bottoneSaltaRegistrazioneCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrazioneViewModel.premutoSalta();
            }
        });

// Aggiungi un'azione al dispatcher del tasto indietro
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                bottoneSaltaRegistrazioneCategorie.performClick();
            }
        });
        populateLinearLayout();
    }



    private void populateLinearLayout() {
        String[] categorieArray = getResources().getStringArray(R.array.categories_array);
        TypedArray immaginiArray = getResources().obtainTypedArray(R.array.immagini_categorie);
        int thickness = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2, // 2dp
                getResources().getDisplayMetrics()
        );
        int marginStartEndInPixel = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                20, // 20dp
                getResources().getDisplayMetrics()
        );
        int marginTopInPixel = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10, // 10dp
                getResources().getDisplayMetrics()
        );

        for (int i = 0; i < categorieArray.length; i++) {
            Switch switchButton = new Switch(this);
            switchButton.setText(categorieArray[i]);
            switchButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36); // Aumentato il testo per renderlo più alto
            switchButton.setTextColor(getResources().getColor(R.color.colore_hint));

            try {
                // Imposta l'immagine a sinistra del testo
                Drawable immagine = immaginiArray.getDrawable(i);
                immagine.setBounds(0, 0, immagine.getIntrinsicWidth(), immagine.getIntrinsicHeight());
                switchButton.setCompoundDrawablesWithIntrinsicBounds(immagine, null, null, null); // Qui ho invertito le icone
            } catch (Resources.NotFoundException e) {
                Log.e("Errore", "Impossibile trovare l'immagine per la categoria " + categorieArray[i]);
            }

            // Imposta il testo al centro e l'allineamento del testo a destra
            switchButton.setGravity(Gravity.CENTER); // Centro verticalmente e allinea a destra

            // Imposta il colore di sfondo dello switch a trasparente
            switchButton.setBackgroundColor(Color.TRANSPARENT);

            // Imposta i margini per lo switch
            LinearLayout.LayoutParams switchLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            switchLayoutParams.setMargins(marginTopInPixel, marginTopInPixel, marginTopInPixel, 0); // Margine superiore di 10dp
            switchButton.setLayoutParams(switchLayoutParams);

            // Imposta un ascoltatore di clic per gestire l'evento di clic su ogni switch
            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Se lo switch è stato selezionato, aggiungi il testo all'array
                    if (isChecked) {
                        switchButton.setTextColor(getResources().getColor(R.color.colore_secondario));
                        registrazioneViewModel.aggiungiCategoria(switchButton.getText().toString());
                    } else { // Se lo switch è stato deselezionato, rimuovi il testo dall'array
                        switchButton.setTextColor(getResources().getColor(R.color.colore_hint));
                        registrazioneViewModel.rimuoviCategoria(switchButton.getText().toString());
                    }
                }
            });

            linear_layout_interno_registrazione_social.addView(switchButton);

            // Aggiungi la vista con lo spessore nero tra gli switch
            if (i < categorieArray.length - 1) { // Aggiungi la vista solo se non è l'ultimo elemento
                View separatorView = new View(this);
                separatorView.setBackgroundColor(getResources().getColor(R.color.colore_trattino_separatore));
                LinearLayout.LayoutParams separatorLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        thickness
                );
                separatorLayoutParams.setMargins(marginStartEndInPixel, marginStartEndInPixel, marginStartEndInPixel, 0); // Margini Start e End di 20dp
                separatorView.setLayoutParams(separatorLayoutParams);
                linear_layout_interno_registrazione_social.addView(separatorView);
            }
        }

        immaginiArray.recycle();
    }




    public void osservaVaiInHome(){
        registrazioneViewModel.vaiInHome.observe(this, (valore) ->{
            if(valore){
                Intent intent = new Intent(RegistrazioneCategorie.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

