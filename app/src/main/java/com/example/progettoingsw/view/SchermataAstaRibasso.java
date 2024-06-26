package com.example.progettoingsw.view;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.viewmodel.SchermataAstaRibassoViewModel;
import com.google.android.material.button.MaterialButton;


public class SchermataAstaRibasso extends GestoreComuniImplementazioni {
    private SchermataAstaRibassoViewModel schermataAstaRibassoViewModel;
    ImageButton bottoneBack;
    MaterialButton bottoneNuovaOfferta;
    private ProgressBar progress_bar_schermata_asta_ribasso;
    TextView textViewNomeProdotto;
    ImageView imageViewProdotto;
    TextView textViewDescrizione;
    TextView textViewProssimoDecremento;
    TextView textViewOffertaAttuale;
    TextView textViewVenditore;
    ImageButton imageButtonPreferiti;
    Drawable drawablePreferiti ;
    Drawable drawableCuoreVuoto;
    Drawable drawableCuorePieno;
    private RelativeLayout relativeLayoutSchermataAstaRibasso;
    private Dialog popUpConfermaOffertaDialog;
    private ImageButton bottone_info_schermata_asta_ribasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_ribasso);

        textViewNomeProdotto = findViewById(R.id.textViewNomeProdottoSchermataAstaRibasso);
        imageViewProdotto = findViewById(R.id.ImageViewSchermataAstaRibasso);
        textViewDescrizione = findViewById(R.id.textViewDescrizioneSchermataAstaRibasso);
        textViewProssimoDecremento = findViewById(R.id.textViewProssimoDecrementoAstaRibasso);
        textViewOffertaAttuale = findViewById(R.id.textViewOffertaAttualeSchermataAstaRibasso);
        textViewVenditore = findViewById(R.id.textViewVenditoreSchermataAstaRibasso);
        imageButtonPreferiti= findViewById(R.id.aggiuntiPreferitiButtonAstaRibasso);
        drawablePreferiti = imageButtonPreferiti.getDrawable();
        drawableCuoreVuoto = ContextCompat.getDrawable(this, R.drawable.ic_cuore_vuoto);
        drawableCuorePieno = ContextCompat.getDrawable(this, R.drawable.ic_cuore_pieno);
        bottone_info_schermata_asta_ribasso = findViewById(R.id.bottone_info_schermata_asta_ribasso);
        schermataAstaRibassoViewModel =new ViewModelProvider(this).get(SchermataAstaRibassoViewModel.class);
        osservaAstaRecuperata();
        osservaErroreRecuperoAsta();
        osservaTipoUtenteChecked();
        osservaIsAstaInPreferiti();
        osservaVaiInVenditore();
        osservaAstaDaMostrare();
        osservaApriPopUpInfo();

        osservaConvertiIntervalloOfferte();
        osservaIsAstaChiusa();
        osservaImmagineAstaConvertita();

        schermataAstaRibassoViewModel.checkTipoUtente();
        schermataAstaRibassoViewModel.getAstaData();

        relativeLayoutSchermataAstaRibasso = findViewById(R.id.relativeLayoutSchermataAstaRibasso);
        progress_bar_schermata_asta_ribasso = findViewById(R.id.progress_bar_schermata_asta_ribasso);


        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaRibasso);
        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });


          bottoneNuovaOfferta =  findViewById(R.id.bottoneOffertaSchermataAstaRibasso);


        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popUpConfermaOffertaDialog = new Dialog(SchermataAstaRibasso.this);
                popUpConfermaOffertaDialog.setContentView(R.layout.pop_up_conferma_offerta);
                popUpConfermaOffertaDialog.show();
                MaterialButton bottoneAnnullaPopuP=(MaterialButton) popUpConfermaOffertaDialog.findViewById(R.id.bottoneAnnullaPopUpAsta);;
                MaterialButton bottoneConfermaPopUP=(MaterialButton) popUpConfermaOffertaDialog.findViewById(R.id.bottoneConfermaPopUpAsta);;
                TextView offertaAttuale= (TextView) popUpConfermaOffertaDialog.findViewById(R.id.TextViewOffertaAsta);
                offertaAttuale.setText("€ " + textViewOffertaAttuale.getText().toString());
                bottoneAnnullaPopuP.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popUpConfermaOffertaDialog.dismiss();
                    }
                });
                bottoneConfermaPopUP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        osservaIsAcquistoAvvenuto();
                        schermataAstaRibassoViewModel.eseguiAcquistoAsta();
                    }
                });

            }
        });
        textViewVenditore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVenditore = textViewVenditore.getText().toString();
                schermataAstaRibassoViewModel.vaiInVenditore(emailVenditore);
            }
        });

        bottone_info_schermata_asta_ribasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schermataAstaRibassoViewModel.creaPopUpInformazioni(SchermataAstaRibasso.this);
            }
        });

    }

    public void setAstaData(Asta_alribassoModel astaRibassoRecuperata) {
        if (astaRibassoRecuperata != null) {
            textViewNomeProdotto.setText(astaRibassoRecuperata.getNome());
            textViewDescrizione.setText(astaRibassoRecuperata.getDescrizione());
            textViewOffertaAttuale.setText(String.valueOf(astaRibassoRecuperata.getPrezzoAttuale()));

            textViewVenditore.setText(astaRibassoRecuperata.getId_venditore());


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
    }


    public void osservaAstaRecuperata(){
        schermataAstaRibassoViewModel.astaRecuperata.observe(this, (asta) -> {
            if (asta != null) {
                schermataAstaRibassoViewModel.convertiImmagine(asta.getPath_immagine());
            }
        });
    }
    public void osservaImmagineAstaConvertita(){
        schermataAstaRibassoViewModel.immagineAstaConvertita.observe(this, (immagine) -> {
            if (immagine != null) {
                imageViewProdotto.setImageBitmap(immagine);
            }else{
                imageViewProdotto.setImageResource(R.drawable.no_image_available);
            }
            schermataAstaRibassoViewModel.isAstaChiusa();
        });
    }
    public void osservaIsAstaChiusa(){
        schermataAstaRibassoViewModel.isAstaChiusa.observe(this, (valore) -> {
            if(valore){
                bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
                imageButtonPreferiti.setVisibility(View.INVISIBLE);
                textViewProssimoDecremento.setText("Asta chiusa.");
            }else {
                schermataAstaRibassoViewModel.convertiIntervalloOfferte();
            }
        });
    }
    public void osservaConvertiIntervalloOfferte(){
        schermataAstaRibassoViewModel.intervalloOfferteConvertito.observe(this, (intervallo) ->{
            if(!intervallo.equals("")) {
                textViewProssimoDecremento.setText(intervallo);
                schermataAstaRibassoViewModel.recuperaAstaDaMostrare();
            }
        });
    }
    public void osservaAstaDaMostrare(){
        schermataAstaRibassoViewModel.astadaMostrare.observe(this, (asta) ->{
            if(schermataAstaRibassoViewModel.isAstaDaMostrare()){
                setAstaData(asta);
            }
        });
    }
    public void osservaErroreRecuperoAsta(){
        schermataAstaRibassoViewModel.erroreRecuperoAsta.observe(this, (messaggio) -> {
            if (schermataAstaRibassoViewModel.isErroreRecuperoAsta()) {
                Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setImpostazioniAstaAcquirente(){
        schermataAstaRibassoViewModel.verificaAstaInPreferiti();
    }
    public void setImpostazioniAstaVenditore(){
        imageButtonPreferiti.setVisibility(View.INVISIBLE);
        bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
    }
    public void osservaIsAcquistoAvvenuto(){
        schermataAstaRibassoViewModel.messaggioAcquistaAstaRibasso.observe(this, (messaggio) -> {
            if(schermataAstaRibassoViewModel.isMessaggioAcquistaAstaRibasso()) {
                if (schermataAstaRibassoViewModel.getIsAcquistoAvvenuto()) {
                    Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
                    schermataAstaRibassoViewModel.getAstaData();
                    popUpConfermaOffertaDialog.dismiss();
                }
            }
        });
    }
    public void osservaTipoUtenteChecked(){
        schermataAstaRibassoViewModel.tipoUtenteChecked.observe(this, (messaggio) -> {
            if (schermataAstaRibassoViewModel.isTipoUtenteChecked()) {
                if(schermataAstaRibassoViewModel.isAcquirente()){
                    //mi è permesso acquistare l'asta o metterla nei preferiti
                    setImpostazioniAstaAcquirente();
                }else{
                    //non mi è permesso acquistare l'asta o metterla nei preferiti
                    setImpostazioniAstaVenditore();
                }

            }
        });
    }
    public void inserimentoInPreferiti(){
        schermataAstaRibassoViewModel.inserimentoAstaInPreferiti();
    }
    public void eliminazioneInPreferiti(){
        schermataAstaRibassoViewModel.eliminazioneAstaInPreferiti();
    }
    public void osservaIsAstaInPreferiti(){
        schermataAstaRibassoViewModel.isAstaInPreferiti.observe(this, (messaggio) -> {
            if (messaggio) {
                imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
                imageButtonPreferiti.setOnClickListener(v -> eliminazioneInPreferiti());
            }else{
                imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);
                imageButtonPreferiti.setOnClickListener(v -> inserimentoInPreferiti());
            }
        });
    }
    public void osservaVaiInVenditore(){
        schermataAstaRibassoViewModel.vaiInVenditore.observe(this, (valore) ->{
            if(valore){
                Intent intent=new Intent(SchermataAstaRibasso.this, ProfiloVenditore.class);
                startActivity(intent);
            }
        });
    }
    public void osservaApriPopUpInfo(){
        schermataAstaRibassoViewModel.popUpInformazioni.observe(this, (valore) ->{
            if(schermataAstaRibassoViewModel.isPopUpInformazioni()){
                valore.show();
            }
        });
    }
}

