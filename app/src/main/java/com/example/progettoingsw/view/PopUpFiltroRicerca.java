package com.example.progettoingsw.view;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.view.acquirente.FragmentRicercaAsta;
import com.example.progettoingsw.viewmodel.RicercaAstaViewModel;

import java.util.ArrayList;

public class PopUpFiltroRicerca extends DialogPersonalizzato implements View.OnClickListener {
    private Button buttonSalva;
    private Button buttonAnnullaFiltro;
    private String ordinamentoPrezzo;
    private Context mContext;
    private ArrayList<String> switchTexts;
    private SwitchCompat switch_ordinamento_popup_ricerca;
    private LinearLayout linear_layout_categorie_popup_ricerca;
    private Resources resources;
    private FragmentRicercaAsta fragmentRicercaAsta;
    private ArrayList<String> categorieScelte;
    private String ordineScelto;
    private RicercaAstaViewModel ricercaAstaViewModel;
    public PopUpFiltroRicerca(Context context, FragmentRicercaAsta fragmentRicercaAsta, RicercaAstaViewModel ricercaAstaViewModel) {
        super(context);
        mContext = context;
        this.fragmentRicercaAsta = fragmentRicercaAsta;
        this.ricercaAstaViewModel  = ricercaAstaViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_filtro_ricerca);
        resources = mContext.getResources();




        linear_layout_categorie_popup_ricerca = findViewById(R.id.linear_layout_categorie_popup_ricerca);

//        for (String categoria : categorieScelte) {
//            Log.d("PopUp categorie scelte", "Categoria: " + categoria);
//        }
//        switchTexts = new ArrayList<>();
//        if(!categorieScelte.isEmpty()){
//            switchTexts = categorieScelte;
//            for (String categoria : switchTexts) {
//                Log.d("PopUp switch texts", "Categoria: " + categoria);
//            }
//        }
        switch_ordinamento_popup_ricerca = findViewById(R.id.switch_ordinamento_popup_ricerca);
        //ordinamentoPrezzo = ordineScelto;
//        if (ordinamentoPrezzo.equals("ASC")) {
//            // Se l'ordinamento è ascendente, impostiamo lo switch come non cliccato
//            switch_ordinamento_popup_ricerca.setChecked(false);
//            switch_ordinamento_popup_ricerca.setText("Crescente");
//        } else if (ordinamentoPrezzo.equals("DESC")) {
//            // Se l'ordinamento è discendente, impostiamo lo switch come cliccato
//            switch_ordinamento_popup_ricerca.setChecked(true);
//            switch_ordinamento_popup_ricerca.setText("Decrescente");
//        }
        switch_ordinamento_popup_ricerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ricercaAstaViewModel.gestisciSwitchOrdinamento();
            }
        });
        buttonSalva = findViewById(R.id.buttonSalvaFiltro);
        buttonAnnullaFiltro = findViewById(R.id.buttonAnnullaFiltro);

        //populateLinearLayout();
        buttonAnnullaFiltro.setOnClickListener(this);
        buttonSalva.setOnClickListener(this);

        Log.d("main popup", "prima degli osserva");
        osservaCategorie();
        osservaNoCategorie();
        osservaOrdinamento();
        osservaChiudiPopUp();
        ricercaAstaViewModel.setCategoriePopUp();
        ricercaAstaViewModel.setOrdinamentoPopUp();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAnnullaFiltro) {
            Log.d("Bottoni in popup" , "annulla");
            ricercaAstaViewModel.chiudi();
        } else if (view.getId() == R.id.buttonSalvaFiltro) {
            Log.d("Bottoni in popup" , "salva");
            ricercaAstaViewModel.salva();
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
            switchButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20); // Aumentato il testo per renderlo più alto
            switchButton.setTextColor(resources.getColor(R.color.colore_hint));
            // Controllo se la categoria corrente è già stata selezionata
            if(categorieScelte!=null) {
                Log.d("populate", "categorieScelte non null" + categorieScelte);
                if (categorieScelte.contains(categorieArray[i])) {
                    switchButton.setChecked(true);
                }
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
            switchLayoutParams.setMargins(marginTopInPixel, marginTopInPixel, marginTopInPixel, 0); // Margine superiore di 10dp
            switchButton.setLayoutParams(switchLayoutParams);

            // Imposta un ascoltatore di clic per gestire l'evento di clic su ogni switch
            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        switchButton.setTextColor(resources.getColor(R.color.colore_secondario));
                        //switchTexts.add(switchButton.getText().toString());
                    } else { // Se lo switch è stato deselezionato, rimuovi il testo dall'array
                        switchButton.setTextColor(resources.getColor(R.color.colore_hint));
                        //switchTexts.remove(switchButton.getText().toString());
                    }
                    ricercaAstaViewModel.gestisciSwitchCategorie(switchButton);
                }
            });

            linear_layout_categorie_popup_ricerca.addView(switchButton);

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
                linear_layout_categorie_popup_ricerca.addView(separatorView);
            }
        }

        immaginiArray.recycle();
    }
    private void rimuoviOsservatori() {
        ricercaAstaViewModel.noCategorie.removeObservers(fragmentRicercaAsta);
        ricercaAstaViewModel.categorie.removeObservers(fragmentRicercaAsta);
        ricercaAstaViewModel.ordinamento.removeObservers(fragmentRicercaAsta);
        ricercaAstaViewModel.chiudiPopUp.removeObservers(fragmentRicercaAsta);
    }

    public void osservaNoCategorie(){
        ricercaAstaViewModel.noCategorie.observe(fragmentRicercaAsta, (valore)->{
            if(valore){
                Log.d("osservaNoCategorie","entrato");
                populateLinearLayout();
            }
        });
    }
    public void osservaCategorie(){
        ricercaAstaViewModel.categorie.observe(fragmentRicercaAsta, (listaCategorie)->{
            Log.d("osservaCategorie", "entrato");
            if(ricercaAstaViewModel.isCategorie()){
                Log.d("osservaCategorie", "entrato nell if");
                categorieScelte = listaCategorie;
                Log.d("osservaCategorie", "chiamo populate");
                populateLinearLayout();
        }
        });
    }
    public void osservaOrdinamento(){
        ricercaAstaViewModel.ordinamento.observe(fragmentRicercaAsta, (ordine)->{
            if(ricercaAstaViewModel.isOrdinamento()){
                if (ricercaAstaViewModel.isOrdinamentoAsc()) {
                    switch_ordinamento_popup_ricerca.setChecked(false);
                    switch_ordinamento_popup_ricerca.setText("Crescente");
                } else {
                    // Se l'ordinamento è discendente, impostiamo lo switch come cliccato
                    switch_ordinamento_popup_ricerca.setChecked(true);
                    switch_ordinamento_popup_ricerca.setText("Decrescente");
                }
            }
        });
    }
    public void osservaChiudiPopUp(){
        ricercaAstaViewModel.chiudiPopUp.observe(fragmentRicercaAsta, (valore) -> {
            if(valore){
                Log.d("osservaChiudiPopup","entrato");
                rimuoviOsservatori();
                ricercaAstaViewModel.resetPerPopUp();
                dismiss();
            }
        });
    }
}
