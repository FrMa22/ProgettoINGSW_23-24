package com.example.progettoingsw.view;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.DialogPersonalizzato;
import com.example.progettoingsw.view.acquirente.FragmentCreaAstaInversa;
import com.example.progettoingsw.view.venditore.VenditoreAstaInglese;
import com.example.progettoingsw.view.venditore.VenditoreAstaRibasso;
import com.example.progettoingsw.viewmodel.CreaAstaIngleseViewModel;
import com.example.progettoingsw.viewmodel.CreaAstaInversaViewModel;
import com.example.progettoingsw.viewmodel.CreaAstaRibassoViewModel;

import java.util.ArrayList;

public class PopUpAggiungiCategorieAsta extends DialogPersonalizzato implements View.OnClickListener {
    private LinearLayout linear_layout_categorie_crea_asta;
    private Context mContext;
    private Resources resources;
    private ArrayList<String> categorieScelte;
    private ArrayList<String> switchTexts;
    private FragmentCreaAstaInversa fragmentCreaAstaInversa;
    private VenditoreAstaInglese venditoreAstaInglese;
    private VenditoreAstaRibasso venditoreAstaRibasso;
    private Button buttonSalvaCategorieCreaAsta;
    private Button buttonAnnullaCategorieCreaAsta;
    private CreaAstaInversaViewModel creaAstaInversaViewModel;
    private CreaAstaIngleseViewModel creaAstaIngleseViewModel;
    private CreaAstaRibassoViewModel creaAstaRibassoViewModel;

    public PopUpAggiungiCategorieAsta(Context context, FragmentCreaAstaInversa fragmentCreaAstaInversa, CreaAstaInversaViewModel creaAstaInversaViewModel){
        super(context);
        mContext = context;
        this.fragmentCreaAstaInversa = fragmentCreaAstaInversa;
        this.creaAstaInversaViewModel = creaAstaInversaViewModel;
        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Esegui le azioni desiderate quando il popup viene chiuso
                // Ad esempio, esegui un'azione o mostra un messaggio
                Log.d("PopUp", "Popup chiuso premendo all'esterno");
                // Aggiungi qui le azioni desiderate
            }
        });
    }
    public PopUpAggiungiCategorieAsta(Context context, VenditoreAstaInglese venditoreAstaInglese, CreaAstaIngleseViewModel creaAstaIngleseViewModel){
        super(context);
        mContext = context;
        this.venditoreAstaInglese = venditoreAstaInglese;
        this.creaAstaIngleseViewModel = creaAstaIngleseViewModel;
    }
    public PopUpAggiungiCategorieAsta(Context context, VenditoreAstaRibasso venditoreAstaRibasso, CreaAstaRibassoViewModel creaAstaRibassoViewModel){
        super(context);
        mContext = context;
        this.venditoreAstaRibasso = venditoreAstaRibasso;
        this.creaAstaRibassoViewModel = creaAstaRibassoViewModel;
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

//        switchTexts = new ArrayList<>();
//        if(!categorieScelte.isEmpty()){
//            switchTexts = categorieScelte;
//            for (String categoria : switchTexts) {
//                Log.d("PopUp switch texts", "Categoria: " + categoria);
//            }
//        }

        populateLinearLayout();
        osservaCategorieInserite();
        Log.d("popup", "prima del check");
        if(creaAstaInversaViewModel != null) {
            creaAstaInversaViewModel.checkCategorieInserite();
        }else if(creaAstaIngleseViewModel != null){
            creaAstaIngleseViewModel.checkCategorieInserite();
        }else if(creaAstaRibassoViewModel != null){
            creaAstaRibassoViewModel.checkCategorieInserite();
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAnnullaCategorieCreaAsta) {
            Log.d("Bottoni in popup" , "annulla");
            rimuoviOsservatori();
            dismiss();
        } else if (view.getId() == R.id.buttonSalvaCategorieCreaAsta) {
            if (creaAstaIngleseViewModel != null) {
                creaAstaIngleseViewModel.saveCategorieScelte();
                //fragmentCreaAstaInversa.handlePopUp(switchTexts);
            } else if (creaAstaRibassoViewModel != null) {
                creaAstaRibassoViewModel.saveCategorieScelte();
                //venditoreAstaInglese.handlePopUp(switchTexts);
            }
            else if (creaAstaInversaViewModel != null) {
                creaAstaInversaViewModel.saveCategorieScelte();
//                venditoreAstaRibasso.handlePopUp(switchTexts);
            }
            rimuoviOsservatori();
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
            switchButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            switchButton.setTextColor(resources.getColor(R.color.colore_hint));
            // Controllo se la categoria corrente è già stata selezionata
//            if (categorieScelte.contains(categorieArray[i])) {
//                switchButton.setChecked(true);
//            }

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
                        switchButton.setTextColor(resources.getColor(R.color.colore_secondario));

                        //switchTexts.add(switchButton.getText().toString());
                    } else { // Se lo switch è stato deselezionato, rimuovi il testo dall'array
                        switchButton.setTextColor(resources.getColor(R.color.colore_hint));

                        //switchTexts.remove(switchButton.getText().toString());
                    }
                }
            });
            switchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (switchButton.isChecked()) {
                        if (creaAstaInversaViewModel != null) {
                            creaAstaInversaViewModel.addCategoriaProvvisoria(switchButton.getText().toString());
                        } else if (creaAstaIngleseViewModel != null) {
                            creaAstaIngleseViewModel.addCategoriaProvvisoria(switchButton.getText().toString());
                        } else if (creaAstaRibassoViewModel != null) {
                            creaAstaRibassoViewModel.addCategoriaProvvisoria(switchButton.getText().toString());
                        }
                    } else {
                        if (creaAstaInversaViewModel != null) {
                            creaAstaInversaViewModel.removeCategoriaProvvisoria(switchButton.getText().toString());
                        } else if (creaAstaIngleseViewModel != null) {
                            creaAstaIngleseViewModel.removeCategoriaProvvisoria(switchButton.getText().toString());
                        } else if (creaAstaRibassoViewModel != null) {
                            creaAstaRibassoViewModel.removeCategoriaProvvisoria(switchButton.getText().toString());
                        }
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
    private void selectCategories(ArrayList<String> categorieInserite) {
        for (int i = 0; i < linear_layout_categorie_crea_asta.getChildCount(); i++) {
            View view = linear_layout_categorie_crea_asta.getChildAt(i);
            if (view instanceof Switch) {
                Switch switchButton = (Switch) view;
                String categoria = switchButton.getText().toString();
                if (categorieInserite.contains(categoria)) {
                    switchButton.setChecked(true);
                }
            }
        }
    }
    private void rimuoviOsservatori() {
        if(creaAstaInversaViewModel!=null){
            creaAstaInversaViewModel.categorieInserite.removeObservers(fragmentCreaAstaInversa);
        }else if(creaAstaIngleseViewModel!=null){
            creaAstaIngleseViewModel.categorieInserite.removeObservers(venditoreAstaInglese);
        }else if(creaAstaRibassoViewModel!=null){
            creaAstaRibassoViewModel.categorieInserite.removeObservers(venditoreAstaRibasso);
        }
    }
    public void osservaCategorieInserite() {
        Log.d("osservaCategorieInserite","entrato");
        if (creaAstaInversaViewModel != null) {
            creaAstaInversaViewModel.categorieInserite.observe(fragmentCreaAstaInversa, (listaCategorie) -> {
                if (creaAstaInversaViewModel.listaCategorieNotEmpty()) {
                    Log.d("popupaggiungicategoriaAsta", "categorie " + listaCategorie);
                    selectCategories(listaCategorie);
                }else{
                    populateLinearLayout();
                }
            });
        }else if(creaAstaIngleseViewModel!=null){
            creaAstaIngleseViewModel.categorieInserite.observe(venditoreAstaInglese, (listaCategorie) -> {
                if (creaAstaIngleseViewModel.listaCategorieNotEmpty()) {
                    selectCategories(listaCategorie);
                }else{
                    populateLinearLayout();
            }
            });
        }else if(creaAstaRibassoViewModel!=null){
            creaAstaRibassoViewModel.categorieInserite.observe(venditoreAstaRibasso, (listaCategorie) -> {
                if (creaAstaRibassoViewModel.listaCategorieNotEmpty()) {
                    selectCategories(listaCategorie);
                }else{
                    populateLinearLayout();
                }
            });
        }
    }
}