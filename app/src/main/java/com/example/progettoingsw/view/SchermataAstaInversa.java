package com.example.progettoingsw.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.viewmodel.SchermataAstaInversaViewModel;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaInversa extends GestoreComuniImplementazioni implements PopUpNuovaOfferta.PopupDismissListener{
    private SchermataAstaInversaViewModel schermataAstaInversaViewModel;
    ImageButton bottoneBack;
    private TextView text_view_tua_offerta_attuale_asta_inversa;
    private TextView textViewNomeProdottoSchermataAstaInversa;
    private ImageView imageViewSchermataAstaInversa;
    private TextView textViewDescrizioneSchermataAstaInversa;
    private TextView textViewPrezzoAttualeSchermataAstaInversa;
    private TextView textViewDataScadenzaSchermataAstaInversa;
    private MaterialButton bottoneOffertaSchermataAstaInversa;
    private TextView textViewAcquirenteSchermataAstaInversa;
    ImageButton imageButtonPreferiti;
    Drawable drawablePreferiti ;
    Drawable drawableCuoreVuoto;
    Drawable drawableCuorePieno;
    private ImageButton bottone_info_schermata_asta_inversa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inversa);
        textViewNomeProdottoSchermataAstaInversa = findViewById(R.id.textViewNomeProdottoSchermataAstaInversa);
        imageViewSchermataAstaInversa = findViewById(R.id.ImageViewSchermataAstaInversa);
        textViewDescrizioneSchermataAstaInversa = findViewById(R.id.textViewDescrizioneSchermataAstaInversa);
        textViewPrezzoAttualeSchermataAstaInversa = findViewById(R.id.textViewPrezzoAttualeSchermataAstaInversa);
        textViewDataScadenzaSchermataAstaInversa = findViewById(R.id.textViewDataScadenzaSchermataAstaInversa);
        bottoneOffertaSchermataAstaInversa = findViewById(R.id.bottoneOffertaSchermataAstaInversa);
        textViewAcquirenteSchermataAstaInversa = findViewById(R.id.textViewAcquirenteSchermataAstaInversa);
        imageButtonPreferiti= findViewById(R.id.aggiuntiPreferitiButtonAstaInversa);
        drawablePreferiti = imageButtonPreferiti.getDrawable();
        drawableCuoreVuoto = ContextCompat.getDrawable(this, R.drawable.ic_cuore_vuoto);
        drawableCuorePieno = ContextCompat.getDrawable(this, R.drawable.ic_cuore_pieno);
        text_view_tua_offerta_attuale_asta_inversa = findViewById(R.id.text_view_tua_offerta_attuale_asta_inversa);
        bottone_info_schermata_asta_inversa = findViewById(R.id.bottone_info_schermata_asta_inversa);

        schermataAstaInversaViewModel = new ViewModelProvider(this).get(SchermataAstaInversaViewModel.class);

        osservaAstaRecuperata();
        osservaErroreRecuperoAsta();
        osservaTipoUtenteAcquirente();
        osservaIsAstaInPreferiti();
        osservaIsPartecipazioneAvvenuta();
        osservaIsUltimaOffertaTua();
        osservaVaiInAcquirente();
        osservaApriPopUpInfo();

        osservaIsAstaChiusa();
        osservaImmagineAstaConvertita();
        osservaAstaDaMostrare();

        schermataAstaInversaViewModel.checkTipoUtente();
        schermataAstaInversaViewModel.getAstaData();


        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaInversa);

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });



        textViewAcquirenteSchermataAstaInversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAcquirente = textViewAcquirenteSchermataAstaInversa.getText().toString();
                schermataAstaInversaViewModel.vaiInAcquirente(emailAcquirente);
            }
        });


        bottoneOffertaSchermataAstaInversa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopUpNuovaOfferta popUpNuovaOfferta = new PopUpNuovaOfferta(SchermataAstaInversa.this, SchermataAstaInversa.this);
                popUpNuovaOfferta.show();
            }
        });
        bottone_info_schermata_asta_inversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schermataAstaInversaViewModel.creaPopUpInformazioni(SchermataAstaInversa.this);
            }
        });
    }
    @Override
    public void onPopupDismissed() {
        schermataAstaInversaViewModel.checkUltimaOfferta();
        schermataAstaInversaViewModel.getAstaData();
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


    public void setAstaData(Asta_inversaModel astaInversaRecuperata) {
        if (astaInversaRecuperata != null) {
            // Imposta i dati recuperati sui TextView e ImageView della tua activity
            textViewNomeProdottoSchermataAstaInversa.setText(astaInversaRecuperata.getNome());
            textViewDescrizioneSchermataAstaInversa.setText(astaInversaRecuperata.getDescrizione());
            textViewPrezzoAttualeSchermataAstaInversa.setText(String.valueOf(astaInversaRecuperata.getPrezzoAttuale()));

            textViewAcquirenteSchermataAstaInversa.setText(astaInversaRecuperata.getId_acquirente());

        }
    }

    public void setPrezzo(Integer prezzoNuovo){
        textViewPrezzoAttualeSchermataAstaInversa.setText(Integer.toString(prezzoNuovo));

    }


    public void osservaAstaRecuperata(){
        schermataAstaInversaViewModel.astaRecuperata.observe(this, (asta) -> {
            if (asta != null) {
                schermataAstaInversaViewModel.convertiImmagine(asta.getPath_immagine());
            }
        });
    }
    public void osservaImmagineAstaConvertita(){
        schermataAstaInversaViewModel.immagineAstaConvertita.observe(this, (immagine) -> {
            if (immagine != null) {
                imageViewSchermataAstaInversa.setImageBitmap(immagine);
            }else{
                imageViewSchermataAstaInversa.setImageResource(R.drawable.no_image_available);
            }
            schermataAstaInversaViewModel.isAstaChiusa();
        });
    }
    public void osservaIsAstaChiusa(){
        schermataAstaInversaViewModel.isAstaChiusa.observe(this, (valore) -> {
            if(valore){
                bottoneOffertaSchermataAstaInversa.setVisibility(View.INVISIBLE);
                imageButtonPreferiti.setVisibility(View.INVISIBLE);
                textViewDataScadenzaSchermataAstaInversa.setText("Asta chiusa");
            }else {
                schermataAstaInversaViewModel.recuperaAstaDaMostrare();
            }
        });
    }
    public void osservaAstaDaMostrare(){
        schermataAstaInversaViewModel.astadaMostrare.observe(this, (asta) ->{
            if(schermataAstaInversaViewModel.isAstaDaMostrare()){
                textViewDataScadenzaSchermataAstaInversa.setText(String.valueOf(asta.getDataDiScadenza()));
                setAstaData(asta);
            }
        });
    }
    public void osservaErroreRecuperoAsta(){
        schermataAstaInversaViewModel.erroreRecuperoAsta.observe(this, (messaggio) -> {
            if (schermataAstaInversaViewModel.isErroreRecuperoAsta()) {
                Toast.makeText(this, schermataAstaInversaViewModel.getErroreRecuperoAsta(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setImpostazioniAstaAcquirente(){
        imageButtonPreferiti.setVisibility(View.INVISIBLE);
        bottoneOffertaSchermataAstaInversa.setVisibility(View.INVISIBLE);
    }
    public void setImpostazioniAstaVenditore(){
        schermataAstaInversaViewModel.checkUltimaOfferta();
        schermataAstaInversaViewModel.verificaAstaInPreferiti();
    }
    public void osservaTipoUtenteAcquirente(){
        schermataAstaInversaViewModel.tipoUtenteAcquirente.observe(this, (isAcquirente) -> {
            if (isAcquirente) {
                    //mi è permesso acquistare l'asta o metterla nei preferiti
                    setImpostazioniAstaAcquirente();
                }else{
                    //non mi è permesso acquistare l'asta o metterla nei preferiti
                    setImpostazioniAstaVenditore();
                }
        });
    }
    public void osservaIsPartecipazioneAvvenuta(){
        schermataAstaInversaViewModel.isPartecipazioneAvvenuta.observe(this, (messaggio) -> {
            if (messaggio) {
                schermataAstaInversaViewModel.getAstaData();
            }
        });
    }
    public void inserimentoInPreferiti(){
        schermataAstaInversaViewModel.inserimentoAstaInPreferiti();
    }
    public void eliminazioneInPreferiti(){
        schermataAstaInversaViewModel.eliminazioneAstaInPreferiti();
    }
    public void osservaIsAstaInPreferiti(){
        schermataAstaInversaViewModel.isAstaInPreferiti.observe(this, (messaggio) -> {
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
        schermataAstaInversaViewModel.isUltimaOffertaTua.observe(this, (valore) ->{
            if(valore){
                text_view_tua_offerta_attuale_asta_inversa.setVisibility(View.VISIBLE);
            }
        });
    }
    public void osservaVaiInAcquirente(){
        schermataAstaInversaViewModel.vaiInAcquirente.observe(this, (valore) ->{
            if(valore){
                Intent intent=new Intent(SchermataAstaInversa.this, ProfiloAcquirente.class);
                startActivity(intent);
            }
        });
    }
    public void osservaApriPopUpInfo(){
        schermataAstaInversaViewModel.popUpInformazioni.observe(this, (valore) ->{
            if(schermataAstaInversaViewModel.isPopUpInformazioni()){
                valore.show();
            }
        });
    }
}


