package com.example.progettoingsw.view;

import android.graphics.Bitmap;
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
import com.example.progettoingsw.viewmodel.SchermataAstaIngleseViewModel;
import com.google.android.material.button.MaterialButton;

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
    private Bitmap immagineConvertita;
    private Boolean isAstaChiusa;
    private String intervalloConvertito;
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
        osservaAstaRecuperata();
        osservaErroreRecuperoAsta();
        osservaTipoUtenteChecked();
        osservaIsPartecipazioneAvvenuta();
        osservaIsAstaInPreferiti();
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
            Log.d("setAstaData", "Entrato");
            // Imposta i dati recuperati sui TextView e ImageView della tua activity
            textViewNomeProdotto.setText(astaIngleseRecuperata.getNome());
            textViewDescrizione.setText(astaIngleseRecuperata.getDescrizione());
            textViewPrezzo.setText(String.valueOf(astaIngleseRecuperata.getPrezzoAttuale()));
            textViewVenditore.setText(astaIngleseRecuperata.getId_venditore());
            textViewSogliaRialzoSchermataAstaInglese.setText(String.valueOf(astaIngleseRecuperata.getRialzoMin()));


            //errore modello MVVM
//            if(immagineConvertita != null){
//                imageViewProdotto.setImageBitmap(immagineConvertita);
//            }else{
//                //immagine di default
//            }


//            if(isAstaChiusa){
//                bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
//                imageButtonPreferiti.setVisibility(View.INVISIBLE);
//                textViewIntervalloOfferte.setText("Asta chiusa.");
//            }else{
//                textViewIntervalloOfferte.setText(intervalloConvertito);
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

    public void osservaAstaRecuperata(){
        schermataAstaIngleseViewModel.astaRecuperata.observe(this, (asta) -> {
            if (asta != null) {
                Log.d("asta recuperata" , "qui");
                osservaImmagineAstaConvertita(asta);
                schermataAstaIngleseViewModel.convertiImmagine(asta.getImmagine());
            }
        });
    }
    public void osservaImmagineAstaConvertita(Asta_allingleseModel asta){
        schermataAstaIngleseViewModel.immagineAstaConvertita.observe(this, (immagine) -> {
            if (immagine != null) {
                imageViewProdotto.setImageBitmap(immagine);
            }else{
                imageViewProdotto.setImageResource(R.drawable.no_image_available);
            }
            osservaIsAstaChiusa(asta);
            schermataAstaIngleseViewModel.isAstaChiusa();
        });
    }
    public void osservaIsAstaChiusa(Asta_allingleseModel asta){
        schermataAstaIngleseViewModel.isAstaChiusa.observe(this, (valore) -> {
            if(valore){
                Log.d("asta chiusa" , "si");
                bottoneNuovaOfferta.setVisibility(View.INVISIBLE);
                imageButtonPreferiti.setVisibility(View.INVISIBLE);
                textViewIntervalloOfferte.setText("Asta chiusa.");
            }else {
                Log.d("asta chiusa" , "no");
                osservaConvertiIntervalloOfferte(asta);
                schermataAstaIngleseViewModel.convertiIntervalloOfferte(asta);
            }
        });
    }
    public void osservaConvertiIntervalloOfferte(Asta_allingleseModel asta){
        schermataAstaIngleseViewModel.intervalloOfferteConvertito.observe(this, (intervallo) ->{
            textViewIntervalloOfferte.setText(intervallo);
            Log.d("converti intervallo" , "qui");
            setAstaData(asta);
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
            Log.d("osservaIsAstaRecuperata", "sto recuper");
            if (messaggio) {
                Toast.makeText(this, "Offerta accettata", Toast.LENGTH_SHORT).show();
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
            Log.d("osservaIsAstaRecuperata", "sto recuper");
            if (messaggio) {
                imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
                imageButtonPreferiti.setOnClickListener(v -> eliminazioneInPreferiti());
            }else{
                imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);
                imageButtonPreferiti.setOnClickListener(v -> inserimentoInPreferiti());
            }
        });
    }



}
