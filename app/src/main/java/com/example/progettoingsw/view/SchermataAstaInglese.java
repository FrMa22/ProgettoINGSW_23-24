package com.example.progettoingsw.view;

import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.DAO.AstaPreferitaIngleseDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.viewmodel.PopUpNuovaOffertaViewModel;
import com.example.progettoingsw.viewmodel.SchermataAstaIngleseViewModel;
import com.google.android.material.button.MaterialButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SchermataAstaInglese extends GestoreComuniImplementazioni implements PopUpNuovaOfferta.PopupDismissListener {
    private SchermataAstaIngleseViewModel schermataAstaIngleseViewModel;
    private ProgressBar progress_bar_schermata_asta_inglese;
    private RelativeLayout relativeLayoutSchermataAstaInglese;
    private TextView text_view_tua_offerta_attuale;
    Controller controller;
    ImageButton bottoneBack;
    MaterialButton bottoneNuovaOfferta;
    ImageButton bottonePreferito;
    private int id;
    private String email;
    private String tipoUtente;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimerTempoRimanente;
    TextView textViewNomeProdotto;
    ImageView imageViewProdotto;
    TextView textViewDescrizione;
    TextView textViewScadenza;
    TextView textViewPrezzo;
    TextView textViewOffertaAttuale;
    TextView textViewIntervalloOfferte;
    TextView textViewVenditore;
    ImageButton imageButtonPreferiti;
    Drawable drawablePreferiti ;
    Drawable drawableCuoreVuoto;
    Drawable drawableCuorePieno;
    private AstaIngleseDAO astaIngleseDAO;
    private AstaPreferitaIngleseDAO astaPreferitaIngleseDAO;
    boolean isPreferito;
    private TextView textViewSogliaRialzoSchermataAstaInglese;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inglese);

        relativeLayoutSchermataAstaInglese = findViewById(R.id.relativeLayoutSchermataAstaInglese);
        progress_bar_schermata_asta_inglese = findViewById(R.id.progress_bar_schermata_asta_inglese);
        //progress_bar_schermata_asta_inglese.setVisibility(View.VISIBLE);
        //setAllClickable(relativeLayoutSchermataAstaInglese,false);

        // Inizializzazione dei TextView, ImageView e altri elementi
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

        schermataAstaIngleseViewModel = new ViewModelProvider(this).get(SchermataAstaIngleseViewModel.class);
        osservaIsAstaRecuperata();
        osservaErroreRecuperoAsta();
        osservaTipoUtenteChecked();
        osservaIsPartecipazioneAvvenuta();
        osservaVerificaAstaInPreferitiChecked();
        schermataAstaIngleseViewModel.verificaAstaInPreferiti();
        schermataAstaIngleseViewModel.checkTipoUtente();
        schermataAstaIngleseViewModel.getAstaData();

//        id = getIntent().getIntExtra("id",0);
//        email = getIntent().getStringExtra("email");
//        tipoUtente = getIntent().getStringExtra("tipoUtente");
//        Toast.makeText(this, "l'id è " + id + ", la mail è " + email + ", il tipoutente è " + tipoUtente, Toast.LENGTH_SHORT).show();



        // Inizializza il timer con una durata di 10 secondi
//        countDownTimer = new CountDownTimer(10000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                // Stampa il numero di secondi rimanenti
//                Log.d("Timer inglese", "Secondi mancanti: " + millisUntilFinished / 1000);
//            }
//            public void onFinish() {
//                Log.d("Timer inglese", "Timer scaduto");
//                //test: se getAstaIngleseByID è troppo pesante meglio l'altro ma va aggiunto il recupero e aggiornamento della scadenza
//                astaIngleseDAO.getAstaIngleseByID(id);
//                //astaIngleseDAO.getPrezzoECondizioneAstaByID(id);
//                start();
//            }
//        };
//        // Avvia il timer
//        countDownTimer.start();

        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaInglese);
        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent intent = new Intent(SchermataAstaInglese.this, AcquirenteMainActivity.class);//test del login
//                intent.putExtra("email", email);
//                intent.putExtra("tipoUtente", tipoUtente);
//                startActivity(intent);
                Log.d("bottone back schermata asta inglese", "premuto");
                onBackPressed();
            }
        });



        bottoneNuovaOfferta =  findViewById(R.id.bottoneOffertaSchermataAstaInglese);
//        if(tipoUtente.equals("venditore")){
//            bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
//        }
        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopUpNuovaOfferta popUpNuovaOfferta = new PopUpNuovaOfferta(SchermataAstaInglese.this, SchermataAstaInglese.this);
                popUpNuovaOfferta.show();
            }
        });
//        String valoreDaModificare = getIntent().getStringExtra("editTextPrezzo");
//        if (valoreDaModificare != null) {
//            textViewPrezzo.setText(valoreDaModificare);
//        }
//
//        astaIngleseDAO.openConnection();
//        astaIngleseDAO.getAstaIngleseByID(id);
//        astaIngleseDAO.verificaAttualeVincitore(email,id);
//        astaIngleseDAO.closeConnection();



//    textViewVenditore.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String emailVenditore = textViewVenditore.getText().toString();
//            Intent intent=new Intent(SchermataAstaInglese.this, ProfiloVenditore.class);
//            intent.putExtra("email",emailVenditore);
//            startActivity(intent);
//        }
//    });
//        if(tipoUtente.equals("venditore")){
//            imageButtonPreferiti.setVisibility(View.INVISIBLE);
//        }else{
//            astaPreferitaIngleseDAO.openConnection();
//            astaPreferitaIngleseDAO.verificaByID(id,email);
//            imageButtonPreferiti.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("Preferiti asta inglese", "drawable preferiti");
//                    if (imageButtonPreferiti.getDrawable().getConstantState().equals(drawableCuoreVuoto.getConstantState())){
//                        Log.d("Preferiti asta inglese", "non è in preferito");
//                        setAllClickable(relativeLayoutSchermataAstaInglese,false);
//                        astaPreferitaIngleseDAO.inserisciByID(id,email);
//
//                    } else if (imageButtonPreferiti.getDrawable().getConstantState().equals(drawableCuorePieno.getConstantState())){
//                        Log.d("Preferiti asta inglese", "è in preferito");
//                        setAllClickable(relativeLayoutSchermataAstaInglese,false);
//                        astaPreferitaIngleseDAO.eliminaByID(id,email);
//                    }
//                }
//            });
//        }





    }
    @Override
    public void onPopupDismissed() {
        // Metodo chiamato quando il pop-up viene chiuso
        Log.d("OnPopUpDismissed", "entrato");
        schermataAstaIngleseViewModel.getAstaData();
    }
    // questi metodi onPause, onStop, onDestroy e onResume servono a stoppare il timer quando non si è piu su questa schermata e a farlo ricominciare quando si torna
    @Override
    protected void onPause() {
        super.onPause();
        // Ferma il countDownTimer se è attivo
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Ferma il countDownTimer se è attivo
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ferma il countDownTimer se è attivo
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
    }
    @Override //questo metodo serve per fare un refresh dei valori dei campi dopo una possibile modifica fatta da PopUp
    public void onResume() {
        super.onResume();
//        astaIngleseDAO.openConnection();
//        astaIngleseDAO.getAstaIngleseByID(id);
//        astaIngleseDAO.closeConnection();
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//            countDownTimer.start();
//        }
    }

    public void setAstaData(Asta_allingleseModel astaIngleseRecuperata) {
        if (astaIngleseRecuperata != null) {
            Log.d("setAstaData", "Entrato");
            // Imposta i dati recuperati sui TextView e ImageView della tua activity
            textViewNomeProdotto.setText(astaIngleseRecuperata.getNome());
            textViewDescrizione.setText(astaIngleseRecuperata.getDescrizione());
            textViewPrezzo.setText(String.valueOf(astaIngleseRecuperata.getPrezzoAttuale()));
            textViewVenditore.setText(astaIngleseRecuperata.getId_venditore());
            textViewSogliaRialzoSchermataAstaInglese.setText(String.valueOf(astaIngleseRecuperata.getRialzoMin()));



            imageViewProdotto.setImageBitmap(schermataAstaIngleseViewModel.convertiImmagine(astaIngleseRecuperata.getImmagine()));

            if(schermataAstaIngleseViewModel.isAstaChiusa()){
                bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
                imageButtonPreferiti.setVisibility(View.INVISIBLE);
                textViewIntervalloOfferte.setText("Asta chiusa.");
            }else{
                String intervalloOfferte = astaIngleseRecuperata.getIntervalloTempoOfferte();
                String scadenzaFormattata = schermataAstaIngleseViewModel.convertiIntervalloOfferte(intervalloOfferte);
                textViewIntervalloOfferte.setText(scadenzaFormattata);
            }
//            if (astaIngleseRecuperata.getCondizione().equals("chiusa")) {
//                bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
//                countDownTimer.cancel();
//                textViewIntervalloOfferte.setText("Asta chiusa.");
//
//            }

        } else {
            // Gestisci il caso in cui non ci siano dati recuperati
            Log.d("Errore", "Impossibile recuperare i dati dell'asta");
        }

//        progress_bar_schermata_asta_inglese.setVisibility(View.GONE);
//        setAllClickable(relativeLayoutSchermataAstaInglese, true);
    }

    public  void verificaPreferiti(boolean check){
        if (check){
            imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
            isPreferito=true;
        }else {
            imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);
            isPreferito = false;

        }
    }
    public void setPrezzo(Integer prezzoNuovo){
        textViewPrezzo.setText(Integer.toString(prezzoNuovo));
    }
    public void getPrezzoeCondizione(String prezzo_aggiornato, String condizione_aggiornata){
        if(condizione_aggiornata.equals("aperta")){
            textViewPrezzo.setText(prezzo_aggiornato);
            // Resetta il timer per farlo ripartire da 10 secondi
            countDownTimer.start();
        }else{
            Toast.makeText(this, "Accidenti! L'asta si è conclusa", Toast.LENGTH_SHORT).show();
            bottoneBack.callOnClick();
        }
    }
    public void handleOffertaAttualeTua(Boolean result){
        Log.d("handleOffertaAttuale", "result : " + result);
        if(result){
            text_view_tua_offerta_attuale.setVisibility(View.VISIBLE);
        }else{
            text_view_tua_offerta_attuale.setVisibility(View.INVISIBLE);
        }
    }

    public void handleInserimento(Boolean result){
        if(result){
            imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
        }else{
            Toast.makeText(this, "Errore nell'inserimento.", Toast.LENGTH_SHORT).show();
        }
        setAllClickable(relativeLayoutSchermataAstaInglese,true);
    }
    public void handleEliminazione(Boolean result){
        if(result){
            imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);
        }else{
            Toast.makeText(this, "Errore nella rimozione.", Toast.LENGTH_SHORT).show();
        }
        setAllClickable(relativeLayoutSchermataAstaInglese,true);
    }

    public void osservaIsAstaRecuperata(){
        schermataAstaIngleseViewModel.isAstaRecuperata.observe(this, (messaggio) -> {
            if (schermataAstaIngleseViewModel.getIsAstaRecuperata()) {
                Log.d("osservaIsAstaRecuperata", "sto recuperando l'asta");
                Asta_allingleseModel astaRecuperata = schermataAstaIngleseViewModel.getAstaRecuperata();
                setAstaData(astaRecuperata);
            }
        });
    }
    public void osservaErroreRecuperoAsta(){
        schermataAstaIngleseViewModel.erroreRecuperoAsta.observe(this, (messaggio) -> {
            if (schermataAstaIngleseViewModel.isErroreRecuperoAsta()) {
                Toast.makeText(this, schermataAstaIngleseViewModel.getErroreRecuperoAsta(), Toast.LENGTH_SHORT).show();
                Log.d("osservaIsAstaRecuperata", "sto recuperando l'asta");
                Asta_allingleseModel astaRecuperata = schermataAstaIngleseViewModel.getAstaRecuperata();
                setAstaData(astaRecuperata);
            }
        });
    }
    public void setImpostazioniAstaAcquirente(){

    }
    public void setImpostazioniAstaVenditore(){
        imageButtonPreferiti.setVisibility(View.INVISIBLE);
        bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
    }

    public void osservaTipoUtenteChecked(){
        schermataAstaIngleseViewModel.tipoUtenteChecked.observe(this, (messaggio) -> {
            if (schermataAstaIngleseViewModel.isTipoUtenteChecked()) {
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
            Log.d("osservaIsAstaRecuperata", "sto recuper");
            if (schermataAstaIngleseViewModel.getIsPartecipazioneAvvenuta()) {
                Toast.makeText(this, schermataAstaIngleseViewModel.getMessaggioPartecipazioneAstaInglese(), Toast.LENGTH_SHORT).show();
                Log.d("osservaIsAstaRecuperata", "sto recuperando l'asta");
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
    public void osservaVerificaAstaInPreferitiChecked(){
        schermataAstaIngleseViewModel.verificaAstaInPreferitiChecked.observe(this, (messaggio) -> {
            Log.d("osservaIsAstaRecuperata", "sto recuper");
            if (schermataAstaIngleseViewModel.getVerificaAstaInPreferitiChecked()) {
                if(schermataAstaIngleseViewModel.getAstaInPreferiti()){
                    //asta in preferiti
                    imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
                    imageButtonPreferiti.setOnClickListener(v -> eliminazioneInPreferiti());
                }else{
                    //asta non in preferiti
                    imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);
                    imageButtonPreferiti.setOnClickListener(v -> inserimentoInPreferiti());
                }
            }else{
                Toast.makeText(this, "errore in verifica asta in preferiti", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
