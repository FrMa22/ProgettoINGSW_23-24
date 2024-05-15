package com.example.progettoingsw.view.venditore;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.view.PopUpAggiungiCategorieAsta;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.view.MainActivity;
import com.example.progettoingsw.viewmodel.CreaAstaRibassoViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class VenditoreAstaRibasso extends GestoreComuniImplementazioni {
    private CreaAstaRibassoViewModel creaAstaRibassoViewModel;
    private Bitmap bitmap;
    AppCompatButton bottoneConferma;
    ImageButton bottoneBack;
    ImageButton button_info_asta_Ribasso;
    EditText nome;
    EditText descrizione;
    private ImageButton imageButtonRimuoviImmagineCreaAstaRibasso;
    EditText baseAsta;
    EditText intervalloDecremento;
    EditText sogliaDecremento;
    EditText prezzominimoAsta;
    ActivityResultLauncher<Intent> resultLauncher;
    private ArrayList<String> listaCategorieScelte;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    private MaterialButton bottoneCategorieAstaRibasso;
    private ProgressBar progressBarVenditoreAstaRibasso;
    private RelativeLayout relativeLayoutAstaRibasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venditore_asta_ribasso);



        progressBarVenditoreAstaRibasso = findViewById(R.id.progressBarVenditoreAstaRibasso);
        relativeLayoutAstaRibasso = findViewById(R.id.relativeLayoutAstaRibasso);
        listaCategorieScelte = new ArrayList<>();
        button_info_asta_Ribasso = findViewById(R.id.button_info_asta_Ribasso);
        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaRibasso);
        bottoneBack =  findViewById(R.id.bottoneBackAstaRibasso);
        imageButtonRimuoviImmagineCreaAstaRibasso = findViewById(R.id.imageButtonRimuoviImmagineCreaAstaRibasso);

        baseAsta=findViewById(R.id.editTextBaseAstaAstaRibasso);
        intervalloDecremento=findViewById(R.id.editTextTimerDecrementoAstaRibasso);
        sogliaDecremento=findViewById(R.id.editTextSogliaDecrementoAstaRibasso);
        prezzominimoAsta=findViewById(R.id.editTextPrezzoSegretoAstaRibasso);

        nome = findViewById(R.id.editTextNomeBeneCreaAstaRibasso);
        descrizione=findViewById(R.id.editTextDescrizioneCreaAstaRibasso);

        immagineProdotto= findViewById(R.id.imageViewCreaAstaRibasso);
        bottoneInserisciImmagine = findViewById(R.id.imageButtonInserisciImmagineCreaAstaRibasso);
        bottoneInserisciImmagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLauncher.launch(new Intent(Intent.ACTION_PICK).setType("image/*"));
            }
        });
        registraRisultati();

        bottoneCategorieAstaRibasso = findViewById(R.id.bottoneCategorieAstaRibasso);
        bottoneCategorieAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaAstaRibassoViewModel.apriPopUp();
            }
        });

        imageButtonRimuoviImmagineCreaAstaRibasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                immagineProdotto.setImageResource(android.R.color.transparent); // Rimuove l'immagine
                bitmap = null;
            }
        });




        bottoneConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nomeProdotto = nome.getText().toString().trim();
                String descrizioneProdotto = descrizione.getText().toString().trim();
                String base = baseAsta.getText().toString().trim();
                String intervallo = intervalloDecremento.getText().toString().trim();
                String soglia = sogliaDecremento.getText().toString().trim();
                String min = prezzominimoAsta.getText().toString().trim();
                creaAstaRibassoViewModel.creaAsta(nomeProdotto,descrizioneProdotto,base,intervallo,soglia,min,bitmap);
            }
        });



        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                creaAstaRibassoViewModel.premutoBack();

            }
        });
        button_info_asta_Ribasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creaAstaRibassoViewModel.apriPopUpInformazioni();
            }
        });

        creaAstaRibassoViewModel = new ViewModelProvider(this).get(CreaAstaRibassoViewModel.class);
        osservaErroreNome();
        osservaErroreDescrizione();
        osservaErroreBaseAsta();
        osservaErroreIntervalloDecrementale();
        osservaErrorePrezzoSegreto();
        osservaErroreSogliaDiDecremento();
        osservaErroreGenerico();
        osservaApriGalleria();
        osservaCreaPopUpInformazioni();
        osservaApriPopUpInformazioni();
        osservaApriPopUpCategorie();
        osservaAstaCreata();
        osservaImmagineConvertita();
        osservaTornaIndietro();
        osservaAstaCreataPopUp();

    }
    public void osservaAstaCreataPopUp(){
        creaAstaRibassoViewModel.astaCreataPopUp.observe(VenditoreAstaRibasso.this, (testo) -> {
            if(creaAstaRibassoViewModel.checkAstaCreataPopUp()){
                Toast.makeText(this, testo, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void osservaTornaIndietro(){
        creaAstaRibassoViewModel.tornaIndietro.observe(this , (valore)->{
            if(valore){
                Intent intent = new Intent(VenditoreAstaRibasso.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void osservaApriPopUpInformazioni(){
        creaAstaRibassoViewModel.apriPopUpInformazioni.observe(this, (valore) ->{
            if(valore){
                creaAstaRibassoViewModel.creaPopUpInformazioni(this);
            }
        });
    }
    public void osservaCreaPopUpInformazioni(){
        creaAstaRibassoViewModel.popUpInformazioni.observe(this, (valore) ->{
            if(creaAstaRibassoViewModel.isPopUpInformazioni()){
                valore.show();
            }
        });
    }
    public void osservaApriPopUpCategorie(){
        creaAstaRibassoViewModel.apriPopUpCategoria.observe(this, (valore) ->{
            if(valore){
                PopUpAggiungiCategorieAsta popUpAggiungiCategorieAsta = new PopUpAggiungiCategorieAsta(this, VenditoreAstaRibasso.this,creaAstaRibassoViewModel);
                popUpAggiungiCategorieAsta.show();
            }
        });
    }
    public void osservaAstaCreata(){
        creaAstaRibassoViewModel.astaCreata.observe(this, (result) -> {
            if(result){
                bottoneBack.performClick();
            }
        });
    }
    public void osservaErroreNome(){
        creaAstaRibassoViewModel.erroreNome.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreNome()){
                nome.setError(errore);
            }
        });
    }
    public void osservaErroreDescrizione(){
        creaAstaRibassoViewModel.erroreDescrizione.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreDescrizione()){
                descrizione.setError(errore);
            }
        });
    }
    public void osservaErroreBaseAsta(){
        creaAstaRibassoViewModel.erroreBaseAsta.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreBaseAsta()){
                baseAsta.setError(errore);
            }
        });
    }
    public void osservaErroreIntervalloDecrementale(){
        creaAstaRibassoViewModel.erroreIntervalloDecrementale.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreIntervalloDecrementale()){
                intervalloDecremento.setError(errore);
            }
        });
    }
    public void osservaErroreSogliaDiDecremento(){
        creaAstaRibassoViewModel.erroreSogliaDiDecremento.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreSogliaDiDecremento()){
                sogliaDecremento.setError(errore);
            }
        });
    }
    public void osservaErrorePrezzoSegreto(){
        creaAstaRibassoViewModel.errorePrezzoSegreto.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErrorePrezzoSegreto()){
                prezzominimoAsta.setError(errore);
            }
        });
    }
    public void osservaErroreGenerico(){
        creaAstaRibassoViewModel.erroreGenerico.observe(this, (errore) ->{
            if(creaAstaRibassoViewModel.isErroreGenerico()){
                Toast.makeText(this, errore, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void osservaApriGalleria(){
        creaAstaRibassoViewModel.apriGalleria.observe(this, (intent) -> {
            if(creaAstaRibassoViewModel.isApriGalleria()){
                resultLauncher.launch(intent);
                registraRisultati();
            }
        });
    }
    public void osservaImmagineConvertita(){
        creaAstaRibassoViewModel.immagineConvertita.observe(this, (immagine) -> {
            if(creaAstaRibassoViewModel.isImmagineConverita()){
                bitmap = immagine;
                immagineProdotto.setImageBitmap(immagine);
            }
        });
    }

    private void registraRisultati() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            creaAstaRibassoViewModel.setImmagine(result, VenditoreAstaRibasso.this);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }




}

