package com.example.progettoingsw;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.progettoingsw.gui.acquirente.AcquirenteAstaInversa;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class PopUpAggiungiCategorieAsta extends Dialog implements View.OnClickListener {
    private LinearLayout linear_layout_categorie_crea_asta;
    private Context mContext;
    private Resources resources;
    private ArrayList<String> categorieScelte;
    private ArrayList<String> switchTexts;
    private AcquirenteAstaInversa acquirenteAstaInversa;
    private Button buttonSalvaCategorieCreaAsta;
    private Button buttonAnnullaCategorieCreaAsta;

    public PopUpAggiungiCategorieAsta(Context context, AcquirenteAstaInversa acquirenteAstaInversa, ArrayList<String> categorieScelte){
        super(context);
        mContext = context;
        this.acquirenteAstaInversa = acquirenteAstaInversa;
        this.categorieScelte = categorieScelte;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_aggiungi_categorie_asta);

        buttonAnnullaCategorieCreaAsta = findViewById(R.id.buttonAnnullaCategorieCreaAsta);
        buttonAnnullaCategorieCreaAsta.setOnClickListener(this);
        buttonSalvaCategorieCreaAsta = findViewById(R.id.buttonSalvaCategorieCreaAsta);
        buttonSalvaCategorieCreaAsta.setOnClickListener(this);
        linear_layout_categorie_crea_asta = findViewById(R.id.linear_layout_categorie_crea_asta);
        resources = mContext.getResources();
        switchTexts = new ArrayList<>();
        if(!categorieScelte.isEmpty()){
            switchTexts = categorieScelte;
            for (String categoria : switchTexts) {
                Log.d("PopUp switch texts", "Categoria: " + categoria);
            }
        }

        populateLinearLayout();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAnnullaCategorieCreaAsta) {
            Log.d("Bottoni in popup" , "annulla");
            dismiss();
        } else if (view.getId() == R.id.buttonSalvaCategorieCreaAsta) {
            Log.d("Bottoni in popup" , "salva");
            acquirenteAstaInversa.handlePopUp(switchTexts);
            dismiss();
        }
    }
    private void populateLinearLayout() {
        String[] categorieArray = resources.getStringArray(R.array.categories_array);
        TypedArray immaginiArray = resources.obtainTypedArray(R.array.immagini_categorie);

        int thickness = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2, // 2dp
                resources.getDisplayMetrics()
        );
        int marginStartEndInPixel = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                20, // 20dp
                resources.getDisplayMetrics()
        );
        int marginTopInPixel = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10, // 10dp
                resources.getDisplayMetrics()
        );

        for (int i = 0; i < categorieArray.length; i++) {
            Switch switchButton = new Switch(getContext());
            switchButton.setText(categorieArray[i]);
            switchButton.setTextSize(15); // Aumentato il testo per renderlo più alto
            switchButton.setTextColor(resources.getColor(R.color.colore_secondario));
            // Controllo se la categoria corrente è già stata selezionata
            if (categorieScelte.contains(categorieArray[i])) {
                switchButton.setChecked(true);
            }

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
            switchLayoutParams.setMargins(marginStartEndInPixel, marginTopInPixel, marginStartEndInPixel, 0); // Margine superiore di 10dp
            switchButton.setLayoutParams(switchLayoutParams);

            // Imposta un ascoltatore di clic per gestire l'evento di clic su ogni switch
            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Se lo switch è stato selezionato, aggiungi il testo all'array
                    if (isChecked) {
                        switchTexts.add(switchButton.getText().toString());
                    } else { // Se lo switch è stato deselezionato, rimuovi il testo dall'array
                        switchTexts.remove(switchButton.getText().toString());
                    }
                }
            });

            linear_layout_categorie_crea_asta.addView(switchButton);

            // Aggiungi la vista con lo spessore nero tra gli switch
            if (i < categorieArray.length - 1) { // Aggiungi la vista solo se non è l'ultimo elemento
                View separatorView = new View(getContext());
                separatorView.setBackgroundColor(resources.getColor(R.color.colore_trattino_separatore));
                LinearLayout.LayoutParams separatorLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        thickness
                );
                separatorLayoutParams.setMargins(marginStartEndInPixel, marginStartEndInPixel, marginStartEndInPixel, 0); // Margini Start e End di 20dp
                separatorView.setLayoutParams(separatorLayoutParams);
                linear_layout_categorie_crea_asta.addView(separatorView);
            }
        }

        immaginiArray.recycle();
    }
}