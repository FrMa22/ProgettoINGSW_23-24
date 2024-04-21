package com.example.progettoingsw.view;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettoingsw.DAO.AstaPreferitaRibassoDAO;
import com.example.progettoingsw.DAO.AstaRibassoDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.viewmodel.SchermataAstaRibassoViewModel;
import com.google.android.material.button.MaterialButton;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SchermataAstaRibasso extends GestoreComuniImplementazioni {
    private SchermataAstaRibassoViewModel schermataAstaRibassoViewModel;
    Controller controller;
    ImageButton bottoneBack;
    MaterialButton bottoneNuovaOfferta;
    ImageButton bottonePreferito;
    private ProgressBar progress_bar_schermata_asta_ribasso;
    private int id;
    private String email;
    private String tipoUtente;
    TextView textViewNomeProdotto;
    ImageView imageViewProdotto;
    TextView textViewDescrizione;
    TextView textViewProssimoDecremento;
    TextView textViewOffertaAttuale;
    TextView textViewVenditore;
    private AstaRibassoDAO astaRibassoDAO;
    private CountDownTimer countDownTimerControlloOgni10sec;
    ImageButton imageButtonPreferiti;
    Drawable drawablePreferiti ;
    Drawable drawableCuoreVuoto;
    Drawable drawableCuorePieno;
    private AstaPreferitaRibassoDAO astaPreferitaRibassoDAO;
    private RelativeLayout relativeLayoutSchermataAstaRibasso;
    private Dialog popUpConfermaOffertaDialog;
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

        schermataAstaRibassoViewModel =new ViewModelProvider(this).get(SchermataAstaRibassoViewModel.class);
        osservaAstaRecuperata();
        osservaErroreRecuperoAsta();
        osservaTipoUtenteChecked();
        osservaIsAstaInPreferiti();
        osservaVaiInVenditore();


        schermataAstaRibassoViewModel.checkTipoUtente();
        schermataAstaRibassoViewModel.getAstaData();

        relativeLayoutSchermataAstaRibasso = findViewById(R.id.relativeLayoutSchermataAstaRibasso);
//        astaRibassoDAO = new AstaRibassoDAO(this);
//        astaPreferitaRibassoDAO = new AstaPreferitaRibassoDAO(this);
        progress_bar_schermata_asta_ribasso = findViewById(R.id.progress_bar_schermata_asta_ribasso);

//        progress_bar_schermata_asta_ribasso.setVisibility(View.VISIBLE);
//        setAllClickable(relativeLayoutSchermataAstaRibasso, false);

//        id = getIntent().getIntExtra("id",0);
//        email = getIntent().getStringExtra("email");
//        tipoUtente = getIntent().getStringExtra("tipoUtente");
//        Toast.makeText(this, "l'id è " + id + ", la mail è " + email + ", il tipoutente è " + tipoUtente, Toast.LENGTH_SHORT).show();





//        countDownTimerControlloOgni10sec = new CountDownTimer(10000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                // Il timer sta andando avanti, non è necessario fare nulla qui
//                Log.d("Timer ribasso ogni 10sec", "Tempo rimanente: " + millisUntilFinished / 1000 + " secondi");
//            }
//            public void onFinish() {
//                Log.d("Timer ribasso ogni 10sec", "Timer scaduto");
//                astaRibassoDAO.recuperaInfoAsta(id);
//                start();
//            }
//        };

        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaRibasso);
        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent intent = new Intent(SchermataAstaRibasso.this, AcquirenteMainActivity.class);//test del login
//                intent.putExtra("email", email);
//                intent.putExtra("tipoUtente", tipoUtente);
//                startActivity(intent);
                onBackPressed();
            }
        });


          bottoneNuovaOfferta =  findViewById(R.id.bottoneOffertaSchermataAstaRibasso);

//        if(tipoUtente.equals("venditore")){
//            bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
//        }

        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popUpConfermaOffertaDialog = new Dialog(SchermataAstaRibasso.this);
                popUpConfermaOffertaDialog.setContentView(R.layout.pop_up_conferma_offerta);
                popUpConfermaOffertaDialog.show();
                MaterialButton bottoneAnnullaPopuP=(MaterialButton) popUpConfermaOffertaDialog.findViewById(R.id.bottoneAnnullaPopUpAsta);;
                MaterialButton bottoneConfermaPopUP=(MaterialButton) popUpConfermaOffertaDialog.findViewById(R.id.bottoneConfermaPopUpAsta);;
                TextView offertaAttuale= (TextView) popUpConfermaOffertaDialog.findViewById(R.id.TextViewOffertaAsta);
                offertaAttuale.setText(textViewOffertaAttuale.getText().toString());
                bottoneAnnullaPopuP.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popUpConfermaOffertaDialog.dismiss();
//                        Intent intent = new Intent(SchermataAstaRibasso.this, AcquirenteMainActivity.class);//test del login
//                        intent.putExtra("email", email);
//                        intent.putExtra("tipoUtente", tipoUtente);
//                        startActivity(intent);
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
//        if(tipoUtente.equals("venditore")){
//            imageButtonPreferiti.setVisibility(View.INVISIBLE);
//        }else{
//            astaPreferitaRibassoDAO.openConnection();
//            astaPreferitaRibassoDAO.verificaByID(id,email);
//            imageButtonPreferiti.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("Preferiti asta ribasso", "drawable preferiti");
//                    if (imageButtonPreferiti.getDrawable().getConstantState().equals(drawableCuoreVuoto.getConstantState())){
//                        Log.d("Preferiti asta ribasso", "non è in preferito");
//                        setAllClickable(relativeLayoutSchermataAstaRibasso,false);
//                        astaPreferitaRibassoDAO.inserisciByID(id,email);
//
//                    } else if (imageButtonPreferiti.getDrawable().getConstantState().equals(drawableCuorePieno.getConstantState())){
//                        Log.d("Preferiti asta ribasso", "è in preferito");
//                        setAllClickable(relativeLayoutSchermataAstaRibasso,false);
//                        astaPreferitaRibassoDAO.eliminaByID(id,email);
//                    }
//                }
//            });
//        }
//
//        astaRibassoDAO.openConnection();
//        astaRibassoDAO.getAstaRibassoByID(id);
//        astaRibassoDAO.closeConnection();
//
//
        textViewVenditore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVenditore = textViewVenditore.getText().toString();
                schermataAstaRibassoViewModel.vaiInVenditore(emailVenditore);
            }
        });
        //astaRibassoDAO.recuperaInfoAsta(id);



    }

    public void setAstaData(Asta_alribassoModel astaRibassoRecuperata) {
        if (astaRibassoRecuperata != null) {
            // Imposta i dati recuperati sui TextView e ImageView della tua activity
            textViewNomeProdotto.setText(astaRibassoRecuperata.getNome());
            textViewDescrizione.setText(astaRibassoRecuperata.getDescrizione());
            textViewOffertaAttuale.setText(String.valueOf(astaRibassoRecuperata.getPrezzoAttuale()));

            textViewVenditore.setText(astaRibassoRecuperata.getId_venditore());
            // Imposta l'immagine solo se non è nulla
            //imageViewProdotto.setImageBitmap(schermataAstaRibassoViewModel.convertiImmagine(astaRibassoRecuperata.getImmagine()));
//            if(schermataAstaRibassoViewModel.isAstaChiusa()){
//                bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
//                textViewProssimoDecremento.setText("Asta chiusa.");
//                imageButtonPreferiti.setVisibility(View.INVISIBLE);
//            }else{
//                textViewProssimoDecremento.setText(schermataAstaRibassoViewModel.convertiIntervalloOfferte(astaRibassoRecuperata.getIntervalloDecrementale()));
//            }


        } else {
            // Gestisci il caso in cui non ci siano dati recuperati
            Log.d("Errore", "Impossibile recuperare i dati dell'asta");
        }
//        progress_bar_schermata_asta_ribasso.setVisibility(View.GONE);
//        setAllClickable(relativeLayoutSchermataAstaRibasso, true);
    }
    public void getPrezzoCondizioneIntervallo(String prezzoAttuale, String condizione, String intervallo) {
        if (condizione.equals("aperta")) {
            textViewOffertaAttuale.setText(prezzoAttuale);
            long orarioAttuale = System.currentTimeMillis();
            long intervalloMillisecondi = convertiIntervallo(intervallo);
            long prossimoDecremento = orarioAttuale + intervalloMillisecondi;
            String orarioProssimoDecremento = convertiOrario(prossimoDecremento);
            // Imposta l'orario del prossimo decremento nel TextView
            textViewProssimoDecremento.setText(orarioProssimoDecremento);
        } else {
            textViewProssimoDecremento.setText("Asta chiusa.");
            if(countDownTimerControlloOgni10sec != null){
                countDownTimerControlloOgni10sec.cancel();
            }
            bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Accidenti! L'asta si è conclusa", Toast.LENGTH_SHORT).show();
            //bottoneBack.callOnClick();
        }
    }
    private long convertiIntervallo(String intervallo) {
        // Dividi l'intervallo in ore, minuti e secondi
        String[] partiIntervallo = intervallo.split(":");
        long ore = Long.parseLong(partiIntervallo[0]);
        long minuti = Long.parseLong(partiIntervallo[1]);
        long secondi = Long.parseLong(partiIntervallo[2]);
        // Calcola il tempo totale in millisecondi
        long tempoTotaleMillisecondi = (ore * 3600 + minuti * 60 + secondi) * 1000;
        return tempoTotaleMillisecondi;
    }
    // Metodo per convertire l'orario millisecondi in un formato leggibile (hh:mm)
    private String convertiOrario(long millisecondi) {
        // Converti i millisecondi in un oggetto LocalDateTime utilizzando il fuso orario locale
        LocalDateTime oraLocale = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(millisecondi),
                ZoneId.systemDefault()
        );
        // Formatta l'orario locale in una stringa nel formato hh:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return oraLocale.format(formatter);
    }
    // questi metodi onPause, onStop, onDestroy e onResume servono a stoppare il timer quando non si è piu su questa schermata e a farlo ricominciare quando si torna
    @Override
    protected void onPause() {
        super.onPause();
        // Ferma il countDownTimerControlloOgni10sec se è attivo
        if (countDownTimerControlloOgni10sec != null) {
            countDownTimerControlloOgni10sec.cancel();
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        // Ferma il countDownTimerControlloOgni10sec se è attivo
        if (countDownTimerControlloOgni10sec != null) {
            countDownTimerControlloOgni10sec.cancel();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ferma il countDownTimerControlloOgni10sec se è attivo
        if (countDownTimerControlloOgni10sec != null) {
            countDownTimerControlloOgni10sec.cancel();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        // Avvia nuovamente il countDownTimer
        if (countDownTimerControlloOgni10sec != null) {
            Log.d("onResume ribasso" ,"timer ");
            countDownTimerControlloOgni10sec.cancel();
            countDownTimerControlloOgni10sec.start();
        }

    }

    public  void verificaPreferiti(boolean check){
        if (check){
            imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
        }else {
            imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);

        }
    }
    public void handleInserimento(Boolean result){
        if(result){
            imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
        }else{
            Toast.makeText(this, "Errore nell'inserimento.", Toast.LENGTH_SHORT).show();
        }
        setAllClickable(relativeLayoutSchermataAstaRibasso,true);
    }
    public void handleEliminazione(Boolean result){
        if(result){
            imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);
        }else{
            Toast.makeText(this, "Errore nella rimozione.", Toast.LENGTH_SHORT).show();
        }
        setAllClickable(relativeLayoutSchermataAstaRibasso,true);
    }



    public void osservaAstaRecuperata(){
        schermataAstaRibassoViewModel.astaRecuperata.observe(this, (asta) -> {
            if (asta != null) {
                osservaImmagineAstaConvertita(asta);
                schermataAstaRibassoViewModel.convertiImmagine(asta.getPath_immagine());
            }
        });
    }
    public void osservaImmagineAstaConvertita(Asta_alribassoModel asta){
        schermataAstaRibassoViewModel.immagineAstaConvertita.observe(this, (immagine) -> {
            if (immagine != null) {
                imageViewProdotto.setImageBitmap(immagine);
            }else{
                imageViewProdotto.setImageResource(R.drawable.no_image_available);
            }
            osservaIsAstaChiusa(asta);
            schermataAstaRibassoViewModel.isAstaChiusa();
        });
    }
    public void osservaIsAstaChiusa(Asta_alribassoModel asta){
        schermataAstaRibassoViewModel.isAstaChiusa.observe(this, (valore) -> {
            if(valore){
                bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
                imageButtonPreferiti.setVisibility(View.INVISIBLE);
                textViewProssimoDecremento.setText("Asta chiusa.");
            }else {
                osservaConvertiIntervalloOfferte(asta);
                schermataAstaRibassoViewModel.convertiIntervalloOfferte(asta);
            }
        });
    }
    public void osservaConvertiIntervalloOfferte(Asta_alribassoModel asta){
        schermataAstaRibassoViewModel.intervalloOfferteConvertito.observe(this, (intervallo) ->{
            Log.d("osservaConvertiIntervalloOfferte", intervallo);
            textViewProssimoDecremento.setText(intervallo);
            setAstaData(asta);
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
                Log.d("osservaIsAcquistoAvvenuto", "qui");
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
            Log.d("osservaIsAstaRecuperata", "sto recuper");
            if (messaggio) {
                imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
                imageButtonPreferiti.setOnClickListener(v -> eliminazioneInPreferiti());
            }else{
                imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);
                imageButtonPreferiti.setOnClickListener(v -> inserimentoInPreferiti());
            }
//                osservaIsAstaInPreferiti();
//                schermataAstaIngleseViewModel.getIsAstaInPreferiti();

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
}

