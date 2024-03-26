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

import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.AstaAdapter;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;
import com.example.progettoingsw.model.AstaInversaItem;
import com.example.progettoingsw.model.AstaRibassoItem;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaInversa extends GestoreComuniImplementazioni {
    Controller controller;
    ImageButton bottoneBack;
    private CountDownTimer countDownTimer;
    private ProgressBar progress_bar_schermata_asta_inversa;
    private RelativeLayout relativeLayoutSchermataAstaInversa;
    private int id;
    private String email;
    private String tipoUtente;
    private TextView textViewNomeProdottoSchermataAstaInversa;
    private ImageView ImageViewSchermataAstaInversa;
    private TextView textViewDescrizioneSchermataAstaInversa;
    private TextView textViewPrezzoAttualeSchermataAstaInversa;
    private TextView textViewDataScadenzaSchermataAstaInversa;
    private MaterialButton bottoneOffertaSchermataAstaInversa;
    private TextView textViewAcquirenteSchermataAstaInversa;
    ImageButton bottonePreferito;
    private AstaInversaDAO astaInversaDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inversa);
        astaInversaDAO = new AstaInversaDAO(this);

        progress_bar_schermata_asta_inversa = findViewById(R.id.progress_bar_schermata_asta_inversa);
        relativeLayoutSchermataAstaInversa = findViewById(R.id.relativeLayoutSchermataAstaInversa);
        progress_bar_schermata_asta_inversa.setVisibility(View.VISIBLE);
        setAllClickable(relativeLayoutSchermataAstaInversa,false);

        textViewNomeProdottoSchermataAstaInversa = findViewById(R.id.textViewNomeProdottoSchermataAstaInversa);
        ImageViewSchermataAstaInversa = findViewById(R.id.ImageViewSchermataAstaInversa);
        textViewDescrizioneSchermataAstaInversa = findViewById(R.id.textViewDescrizioneSchermataAstaInversa);
        textViewPrezzoAttualeSchermataAstaInversa = findViewById(R.id.textViewPrezzoAttualeSchermataAstaInversa);
        textViewDataScadenzaSchermataAstaInversa = findViewById(R.id.textViewDataScadenzaSchermataAstaInversa);
        bottoneOffertaSchermataAstaInversa = findViewById(R.id.bottoneOffertaSchermataAstaInversa);
        textViewAcquirenteSchermataAstaInversa = findViewById(R.id.textViewAcquirenteSchermataAstaInversa);


        id = getIntent().getIntExtra("id",0);
        email = getIntent().getStringExtra("email");
        tipoUtente = getIntent().getStringExtra("tipoUtente");

        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Il timer sta andando avanti, non è necessario fare nulla qui
                Log.d("Timer", "Tempo rimanente: " + millisUntilFinished / 1000 + " secondi");
            }
            public void onFinish() {
                Log.d("Timer", "Timer scaduto");
                astaInversaDAO.getPrezzoECondizioneAstaByID(id);
            }
        };
        Log.d("timer ", "inizia");
        countDownTimer.start();

        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaInversa);

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SchermataAstaInversa.this, AcquirenteMainActivity.class);//test del login
                intent.putExtra("email", email);
                intent.putExtra("tipoUtente", tipoUtente);
                startActivity(intent);
            }
        });



        textViewAcquirenteSchermataAstaInversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAcquirente = textViewAcquirenteSchermataAstaInversa.getText().toString();
                Intent intent=new Intent(SchermataAstaInversa.this, ProfiloAcquirente.class);
                intent.putExtra("email",emailAcquirente);
                startActivity(intent);
            }
        });


        bottoneOffertaSchermataAstaInversa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                astaInversaDAO.getAstaInversaByID(id);
                PopUpNuovaOfferta popUpNuovaOfferta = new PopUpNuovaOfferta(SchermataAstaInversa.this,email,id, textViewPrezzoAttualeSchermataAstaInversa.getText().toString(), SchermataAstaInversa.this);
                popUpNuovaOfferta.show();
            }
        });
        astaInversaDAO.openConnection();
        astaInversaDAO.getAstaInversaByID(id);
    }
    // questi metodi onPause, onStop, onDestroy e onResume servono a stoppare il timer quando non si è piu su questa schermata e a farlo ricominciare quando si torna
    @Override
    protected void onPause() {
        super.onPause();
        // Ferma il countDownTimer se è attivo
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Ferma il countDownTimer se è attivo
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ferma il countDownTimer se è attivo
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // Avvia nuovamente il countDownTimer
        if (countDownTimer != null ) {
            Log.d("timer ", "inizia on Resume");
            countDownTimer.cancel();
            countDownTimer.start();
        }
    }


    public void setAstaData(AstaInversaItem astaInversaItem) {
        if (astaInversaItem != null) {
            // Imposta i dati recuperati sui TextView e ImageView della tua activity
            textViewNomeProdottoSchermataAstaInversa.setText(astaInversaItem.getNome());
            textViewDescrizioneSchermataAstaInversa.setText(astaInversaItem.getDescrizione());
            textViewPrezzoAttualeSchermataAstaInversa.setText(String.valueOf(astaInversaItem.getPrezzoAttuale()));
            textViewDataScadenzaSchermataAstaInversa.setText(String.valueOf(astaInversaItem.getDataDiScadenza()));
            textViewAcquirenteSchermataAstaInversa.setText(astaInversaItem.getEmailAcquirente());
            // Imposta l'immagine solo se non è nulla
            if (astaInversaItem.getImmagine() != null) {
                ImageViewSchermataAstaInversa.setImageBitmap(astaInversaItem.getImmagine());
            }
            // Altri dati da impostare...
        } else {
            // Gestisci il caso in cui non ci siano dati recuperati
            Log.d("Errore", "Impossibile recuperare i dati dell'asta");
        }
        progress_bar_schermata_asta_inversa.setVisibility(View.GONE);
        setAllClickable(relativeLayoutSchermataAstaInversa,true);

    }
    public void setPrezzo(Integer prezzoNuovo){
        textViewPrezzoAttualeSchermataAstaInversa.setText(Integer.toString(prezzoNuovo));
    }
    public void getPrezzoeCondizione(String prezzo_aggiornato, String condizione_aggiornata){
        if(condizione_aggiornata.equals("aperta")){
            textViewPrezzoAttualeSchermataAstaInversa.setText(prezzo_aggiornato);
            // Resetta il timer per farlo ripartire da 10 secondi
            Log.d("timer ", "cancello e faccio iniziare");
            countDownTimer.cancel();
            countDownTimer.start();
        }else{
            Toast.makeText(this, "Accidenti! L'asta si è conclusa", Toast.LENGTH_SHORT).show();
            bottoneBack.callOnClick();
        }

    }
}


