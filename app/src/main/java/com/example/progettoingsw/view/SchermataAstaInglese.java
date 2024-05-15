package com.example.progettoingsw.view;

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
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.viewmodel.SchermataAstaIngleseViewModel;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaInglese extends GestoreComuniImplementazioni implements PopUpNuovaOfferta.PopupDismissListener {
    private SchermataAstaIngleseViewModel schermataAstaIngleseViewModel;
    private ProgressBar progress_bar_schermata_asta_inglese;
    private RelativeLayout relativeLayoutSchermataAstaInglese;
    private TextView text_view_tua_offerta_attuale;
    ImageButton bottoneBack;
    MaterialButton bottoneNuovaOfferta;
    TextView textViewNomeProdotto;
    ImageView imageViewProdotto;
    TextView textViewDescrizione;
    TextView textViewPrezzo;
    TextView textViewOffertaAttuale;
    TextView textViewIntervalloOfferte;
    TextView textViewVenditore;
    ImageButton imageButtonPreferiti;
    Drawable drawablePreferiti ;
    Drawable drawableCuoreVuoto;
    Drawable drawableCuorePieno;
    boolean isPreferito;
    private TextView textViewSogliaRialzoSchermataAstaInglese;
    private ImageButton bottone_info_schermata_asta_inglese;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inglese);

        relativeLayoutSchermataAstaInglese = findViewById(R.id.relativeLayoutSchermataAstaInglese);
        progress_bar_schermata_asta_inglese = findViewById(R.id.progress_bar_schermata_asta_inglese);

        textViewSogliaRialzoSchermataAstaInglese = findViewById(R.id.textViewSogliaRialzoSchermataAstaInglese);
        textViewNomeProdotto = findViewById(R.id.textViewNomeProdottoSchermataAstaInglese);
        imageViewProdotto = findViewById(R.id.ImageViewSchermataAstaInglese);
        textViewDescrizione = findViewById(R.id.textViewDescrizioneSchermataAstaInglese);
        textViewPrezzo = findViewById(R.id.textViewPrezzoAttualeSchermataAstaInglese);
        textViewOffertaAttuale = findViewById(R.id.textViewOffertaAttualeSchermataAstaInglese);
        textViewIntervalloOfferte = findViewById(R.id.textViewIntervalloOfferte);
        textViewVenditore = findViewById(R.id.textViewVenditoreSchermataAstaInglese);
        imageButtonPreferiti= findViewById(R.id.aggiuntiPreferitiButtonAstaInglese);
        drawablePreferiti = imageButtonPreferiti.getDrawable();
        drawableCuoreVuoto = ContextCompat.getDrawable(this, R.drawable.ic_cuore_vuoto);
        drawableCuorePieno = ContextCompat.getDrawable(this, R.drawable.ic_cuore_pieno);
        text_view_tua_offerta_attuale = findViewById(R.id.text_view_tua_offerta_attuale);
        bottone_info_schermata_asta_inglese = findViewById(R.id.bottone_info_schermata_asta_inglese);

        schermataAstaIngleseViewModel = new ViewModelProvider(this).get(SchermataAstaIngleseViewModel.class);
        osservaAstaRecuperata();
        osservaErroreRecuperoAsta();
        osservaTipoUtenteChecked();
        osservaIsPartecipazioneAvvenuta();
        osservaIsAstaInPreferiti();
        osservaIsUltimaOffertaTua();
        osservaVaiInVenditore();

        osservaConvertiIntervalloOfferte();
        osservaIsAstaChiusa();
        osservaImmagineAstaConvertita();
        osservaAstaDaMostrare();
        osservaApriPopUpInfo();

        schermataAstaIngleseViewModel.checkTipoUtente();
        schermataAstaIngleseViewModel.getAstaData();


        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaInglese);
        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });



        bottoneNuovaOfferta =  findViewById(R.id.bottoneOffertaSchermataAstaInglese);
        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopUpNuovaOfferta popUpNuovaOfferta = new PopUpNuovaOfferta(SchermataAstaInglese.this, SchermataAstaInglese.this);
                popUpNuovaOfferta.show();
            }
        });



    textViewVenditore.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailVenditore = textViewVenditore.getText().toString();
            schermataAstaIngleseViewModel.vaiInVenditore(emailVenditore);

        }
    });
    bottone_info_schermata_asta_inglese.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            schermataAstaIngleseViewModel.creaPopUpInformazioni(SchermataAstaInglese.this);
        }
    });





    }
    @Override
    public void onPopupDismissed() {
        schermataAstaIngleseViewModel.checkUltimaOfferta();
        schermataAstaIngleseViewModel.getAstaData();
    }
    // questi metodi onPause, onStop, onDestroy e onResume servono a stoppare il timer quando non si è piu su questa schermata e a farlo ricominciare quando si torna
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

    public void setAstaData(Asta_allingleseModel astaIngleseRecuperata) {
        if (astaIngleseRecuperata != null) {
            textViewNomeProdotto.setText(astaIngleseRecuperata.getNome());
            textViewDescrizione.setText(astaIngleseRecuperata.getDescrizione());
            textViewPrezzo.setText(String.valueOf(astaIngleseRecuperata.getPrezzoAttuale()));
            textViewVenditore.setText(astaIngleseRecuperata.getId_venditore());
            textViewSogliaRialzoSchermataAstaInglese.setText(String.valueOf(astaIngleseRecuperata.getRialzoMin()));
        }
    }

    public void setPrezzo(Integer prezzoNuovo){
        textViewPrezzo.setText(Integer.toString(prezzoNuovo));
    }


    public void osservaAstaRecuperata(){
        schermataAstaIngleseViewModel.astaRecuperata.observe(this, (asta) -> {
            if (asta != null) {
                schermataAstaIngleseViewModel.convertiImmagine(asta.getPath_immagine());
            }
        });
    }
    public void osservaImmagineAstaConvertita(){
        schermataAstaIngleseViewModel.immagineAstaConvertita.observe(this, (immagine) -> {
            if (immagine != null) {
                imageViewProdotto.setImageBitmap(immagine);
            }else{
                imageViewProdotto.setImageResource(R.drawable.no_image_available);
            }
            schermataAstaIngleseViewModel.isAstaChiusa();
        });
    }
    public void osservaIsAstaChiusa(){
        schermataAstaIngleseViewModel.isAstaChiusa.observe(this, (valore) -> {
            if(valore){
                bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
                imageButtonPreferiti.setVisibility(View.INVISIBLE);
                textViewIntervalloOfferte.setText("Asta chiusa.");
            }else {
                schermataAstaIngleseViewModel.convertiIntervalloOfferte();
            }
        });
    }
    public void osservaConvertiIntervalloOfferte(){
        schermataAstaIngleseViewModel.intervalloOfferteConvertito.observe(this, (intervallo) ->{
            if(!intervallo.equals("")){
                textViewIntervalloOfferte.setText(intervallo);
                schermataAstaIngleseViewModel.recuperaAstaDaMostrare();
            }

        });
    }
    public void osservaAstaDaMostrare(){
        schermataAstaIngleseViewModel.astadaMostrare.observe(this, (asta) ->{
            if(schermataAstaIngleseViewModel.isAstaDaMostrare()){
                setAstaData(asta);
            }
        });
    }

    public void osservaErroreRecuperoAsta(){
        schermataAstaIngleseViewModel.erroreRecuperoAsta.observe(this, (messaggio) -> {
            if (schermataAstaIngleseViewModel.isErroreRecuperoAsta()) {
                Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setImpostazioniAstaAcquirente(){
        schermataAstaIngleseViewModel.checkUltimaOfferta();
        schermataAstaIngleseViewModel.verificaAstaInPreferiti();
    }
    public void setImpostazioniAstaVenditore(){
        imageButtonPreferiti.setVisibility(View.INVISIBLE);
        bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
    }

    public void osservaTipoUtenteChecked(){
        schermataAstaIngleseViewModel.tipoUtenteChecked.observe(this, (checked) -> {
            if (checked) {
                if(schermataAstaIngleseViewModel.isAcquirente()){
                    //mi è permesso acquistare l'asta o metterla nei preferiti
                    setImpostazioniAstaAcquirente();
                }else{
                    //non mi è permesso acquistare l'asta o metterla nei preferiti
                    setImpostazioniAstaVenditore();
                }

            }
        });
    }
    public void osservaIsPartecipazioneAvvenuta(){
        schermataAstaIngleseViewModel.isPartecipazioneAvvenuta.observe(this, (messaggio) -> {
            if (messaggio) {
                schermataAstaIngleseViewModel.getAstaData();
            }
        });
    }
    public void inserimentoInPreferiti(){
        schermataAstaIngleseViewModel.inserimentoAstaInPreferiti();
    }
    public void eliminazioneInPreferiti(){
        schermataAstaIngleseViewModel.eliminazioneAstaInPreferiti();
    }
    public void osservaIsAstaInPreferiti(){
        schermataAstaIngleseViewModel.isAstaInPreferiti.observe(this, (messaggio) -> {
            if (messaggio) {
                imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
                imageButtonPreferiti.setOnClickListener(v -> eliminazioneInPreferiti());
            }else{
                imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);
                imageButtonPreferiti.setOnClickListener(v -> inserimentoInPreferiti());
            }
        });
    }
    public void osservaIsUltimaOffertaTua(){
        schermataAstaIngleseViewModel.isUltimaOffertaTua.observe(this, (valore) ->{
            if(valore){
                text_view_tua_offerta_attuale.setVisibility(View.VISIBLE);
            }
        });
    }
    public void osservaVaiInVenditore(){
        schermataAstaIngleseViewModel.vaiInVenditore.observe(this, (valore) ->{
            if(valore){
                Intent intent=new Intent(SchermataAstaInglese.this, ProfiloVenditore.class);
                startActivity(intent);
            }
        });
    }
    public void osservaApriPopUpInfo(){
        schermataAstaIngleseViewModel.popUpInformazioni.observe(this, (valore) ->{
            if(schermataAstaIngleseViewModel.isPopUpInformazioni()){
                valore.show();
            }
        });
    }

}
