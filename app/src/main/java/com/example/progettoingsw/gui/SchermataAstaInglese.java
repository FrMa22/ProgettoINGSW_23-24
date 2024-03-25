package com.example.progettoingsw.gui;

import android.content.Intent;
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

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.google.android.material.button.MaterialButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SchermataAstaInglese extends GestoreComuniImplementazioni {
    private ProgressBar progress_bar_schermata_asta_inglese;
    private RelativeLayout relativeLayoutSchermataAstaInglese;
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
    private AstaIngleseDAO astaIngleseDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inglese);
        astaIngleseDAO = new AstaIngleseDAO(this);
        relativeLayoutSchermataAstaInglese = findViewById(R.id.relativeLayoutSchermataAstaInglese);
        progress_bar_schermata_asta_inglese = findViewById(R.id.progress_bar_schermata_asta_inglese);
        progress_bar_schermata_asta_inglese.setVisibility(View.VISIBLE);
        setAllClickable(relativeLayoutSchermataAstaInglese,false);

        // Inizializzazione dei TextView, ImageView e altri elementi
        textViewNomeProdotto = findViewById(R.id.textViewNomeProdottoSchermataAstaInglese);
        imageViewProdotto = findViewById(R.id.ImageViewSchermataAstaInglese);
        textViewDescrizione = findViewById(R.id.textViewDescrizioneSchermataAstaInglese);
        textViewPrezzo = findViewById(R.id.textViewPrezzoAttualeSchermataAstaInglese);
        textViewOffertaAttuale = findViewById(R.id.textViewOffertaAttualeSchermataAstaInglese);
        textViewIntervalloOfferte = findViewById(R.id.textViewIntervalloOfferte);
        textViewVenditore = findViewById(R.id.textViewVenditoreSchermataAstaInglese);

        id = getIntent().getIntExtra("id",0);
        email = getIntent().getStringExtra("email");
        tipoUtente = getIntent().getStringExtra("tipoUtente");
        Toast.makeText(this, "l'id è " + id + ", la mail è " + email + ", il tipoutente è " + tipoUtente, Toast.LENGTH_SHORT).show();

        // Inizializza il timer con una durata di 10 secondi
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Il timer sta andando avanti, non è necessario fare nulla qui
            }
            public void onFinish() {
                Log.d("Timer inglese", "Timer scaduto");
                astaIngleseDAO.getPrezzoECondizioneAstaByID(id);
                start();
            }
        };
        // Avvia il timer
        countDownTimer.start();

        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaInglese);
        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SchermataAstaInglese.this, AcquirenteMainActivity.class);//test del login
                intent.putExtra("email", email);
                intent.putExtra("tipoUtente", tipoUtente);
                startActivity(intent);
            }
        });



        bottoneNuovaOfferta =  findViewById(R.id.bottoneOffertaSchermataAstaInglese);
        bottoneNuovaOfferta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                astaIngleseDAO.getAstaIngleseByID(id);
                PopUpNuovaOfferta popUpNuovaOfferta = new PopUpNuovaOfferta(SchermataAstaInglese.this,email,id, textViewPrezzo.getText().toString(), SchermataAstaInglese.this);
                popUpNuovaOfferta.show();
            }
        });
        String valoreDaModificare = getIntent().getStringExtra("editTextPrezzo");
        if (valoreDaModificare != null) {
            textViewPrezzo.setText(valoreDaModificare);
        }

        astaIngleseDAO.openConnection();
        astaIngleseDAO.getAstaIngleseByID(id);
//        astaIngleseDAO.closeConnection();



    textViewVenditore.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailVenditore = textViewVenditore.getText().toString();
            Intent intent=new Intent(SchermataAstaInglese.this, ProfiloVenditore.class);
            intent.putExtra("email",emailVenditore);
            startActivity(intent);
        }
    });


    }


    @Override //questo metodo serve per fare un refresh dei valori dei campi dopo una possibile modifica fatta da PopUp
    public void onResume() {
        super.onResume();
        astaIngleseDAO.openConnection();
        astaIngleseDAO.getAstaIngleseByID(id);
        astaIngleseDAO.closeConnection();
    }

    public void setAstaData(AstaIngleseItem astaIngleseItem) {
        if (astaIngleseItem != null) {
            // Imposta i dati recuperati sui TextView e ImageView della tua activity
            textViewNomeProdotto.setText(astaIngleseItem.getNome());
            textViewDescrizione.setText(astaIngleseItem.getDescrizione());
            textViewPrezzo.setText(astaIngleseItem.getPrezzoAttuale());
            textViewVenditore.setText(astaIngleseItem.getEmailVenditore());


            String intervalloOfferte = astaIngleseItem.getIntervalloTempoOfferte();
            // Ottieni la data e l'ora attuali con il giorno incluso
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // Analizza l'intervallo in ore e minuti
            String[] parts = intervalloOfferte.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);

            // Calcola l'intervallo totale in minuti
            int intervalloMinuti = hours * 60 + minutes;

            // Aggiungi l'intervallo alla data e all'ora attuali
            LocalDateTime scadenza = now.plusMinutes(intervalloMinuti);

            // Formatta il risultato nel formato desiderato
            String scadenzaFormattata = scadenza.format(formatter);
            textViewIntervalloOfferte.setText(scadenzaFormattata);

            // Imposta l'immagine solo se non è nulla
            if (astaIngleseItem.getImmagine() != null) {
                imageViewProdotto.setImageBitmap(astaIngleseItem.getImmagine());
            }

        } else {
            // Gestisci il caso in cui non ci siano dati recuperati
            Log.d("Errore", "Impossibile recuperare i dati dell'asta");
        }

        progress_bar_schermata_asta_inglese.setVisibility(View.GONE);
        setAllClickable(relativeLayoutSchermataAstaInglese, true);
    }
    private long convertiStringaInSecondi(String intervalloOfferteString) {
        // Assumendo che l'intervallo sia nel formato "HH:mm:ss"
        String[] parts = intervalloOfferteString.split(":");
        long ore = Long.parseLong(parts[0]);
        long minuti = Long.parseLong(parts[1]);
        long secondi = Long.parseLong(parts[2]);
        return ore * 3600 + minuti * 60 + secondi;
    }


    private String calcolaTempoRimanente(long secondiRimanenti) {
        long ore = secondiRimanenti / 3600;
        long minuti = (secondiRimanenti % 3600) / 60;
        long secondi = secondiRimanenti % 60;

        return String.format("%02d:%02d:%02d", ore, minuti, secondi);
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
    public void handlePopUp(){
        progress_bar_schermata_asta_inglese.setVisibility(View.VISIBLE);
        setAllClickable(relativeLayoutSchermataAstaInglese,false);
        astaIngleseDAO.openConnection();
        astaIngleseDAO.getAstaIngleseByID(id);
        astaIngleseDAO.closeConnection();

    }

}
