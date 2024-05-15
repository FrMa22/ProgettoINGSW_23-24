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


        populateLinearLayout();
        osservaCategorieInserite();
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
            rimuoviOsservatori();
            dismiss();
        } else if (view.getId() == R.id.buttonSalvaCategorieCreaAsta) {
            if (creaAstaIngleseViewModel != null) {
                creaAstaIngleseViewModel.saveCategorieScelte();
            } else if (creaAstaRibassoViewModel != null) {
                creaAstaRibassoViewModel.saveCategorieScelte();
            }
            else if (creaAstaInversaViewModel != null) {
                creaAstaInversaViewModel.saveCategorieScelte();
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


            try {
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
                    if (isChecked) {
                        switchButton.setTextColor(resources.getColor(R.color.colore_secondario));

                    } else {
                        switchButton.setTextColor(resources.getColor(R.color.colore_hint));

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

            if (i < categorieArray.length - 1) {
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
        if (creaAstaInversaViewModel != null) {
            creaAstaInversaViewModel.categorieInserite.observe(fragmentCreaAstaInversa, (listaCategorie) -> {
                if (creaAstaInversaViewModel.listaCategorieNotEmpty()) {
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