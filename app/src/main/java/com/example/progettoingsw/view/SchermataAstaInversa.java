package com.example.progettoingsw.view;

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

import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.DAO.AstaPreferitaInversaDAO;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.item.AstaInversaItem;
import com.google.android.material.button.MaterialButton;

public class SchermataAstaInversa extends GestoreComuniImplementazioni {
    Controller controller;
    ImageButton bottoneBack;
    private TextView text_view_tua_offerta_attuale_asta_inversa;
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
    ImageButton imageButtonPreferiti;
    Drawable drawablePreferiti ;
    Drawable drawableCuoreVuoto;
    Drawable drawableCuorePieno;
    private AstaPreferitaInversaDAO astaPreferitaInversaDAO;

    private AstaInversaDAO astaInversaDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schermata_asta_inversa);
        astaInversaDAO = new AstaInversaDAO(this);
        astaPreferitaInversaDAO = new AstaPreferitaInversaDAO(this);

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
        imageButtonPreferiti= findViewById(R.id.aggiuntiPreferitiButtonAstaInversa);
        drawablePreferiti = imageButtonPreferiti.getDrawable();
        drawableCuoreVuoto = ContextCompat.getDrawable(this, R.drawable.ic_cuore_vuoto);
        drawableCuorePieno = ContextCompat.getDrawable(this, R.drawable.ic_cuore_pieno);
        text_view_tua_offerta_attuale_asta_inversa = findViewById(R.id.text_view_tua_offerta_attuale_asta_inversa);




        id = getIntent().getIntExtra("id",0);
        email = getIntent().getStringExtra("email");
        tipoUtente = getIntent().getStringExtra("tipoUtente");

        if(tipoUtente.equals("acquirente")){
            bottoneOffertaSchermataAstaInversa.setVisibility(View.INVISIBLE);
        }

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

        bottoneBack =  findViewById(R.id.bottoneBackSchermataAstaInversa);

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent intent = new Intent(SchermataAstaInversa.this, AcquirenteMainActivity.class);//test del login
//                intent.putExtra("email", email);
//                intent.putExtra("tipoUtente", tipoUtente);
//                startActivity(intent);
                onBackPressed();
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
        //controllo per far si che un acquirente non possa inserire un asta inversa nei preferiti
        if(tipoUtente.equals("acquirente")){
            imageButtonPreferiti.setVisibility(View.INVISIBLE);
        }else{
            astaPreferitaInversaDAO.openConnection();
            astaPreferitaInversaDAO.verificaByID(id,email);
            imageButtonPreferiti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Preferiti asta inversa", "drawable preferiti");
                    if (imageButtonPreferiti.getDrawable().getConstantState().equals(drawableCuoreVuoto.getConstantState())){
                        Log.d("Preferiti asta inversa", "non è in preferito");
                        setAllClickable(relativeLayoutSchermataAstaInversa,false);
                        astaPreferitaInversaDAO.inserisciByID(id,email);

                    } else if (imageButtonPreferiti.getDrawable().getConstantState().equals(drawableCuorePieno.getConstantState())){
                        Log.d("Preferiti asta inversa", "è in preferito");
                        setAllClickable(relativeLayoutSchermataAstaInversa,false);
                        astaPreferitaInversaDAO.eliminaByID(id,email);
                    }
                }
            });
        }

        astaInversaDAO.openConnection();
        astaInversaDAO.getAstaInversaByID(id);
        astaInversaDAO.verificaAttualeVincitore(email,id);
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
            if (astaInversaItem.getCondizione().equals("chiusa")) {
                bottoneOffertaSchermataAstaInversa.setVisibility(View.INVISIBLE);
                imageButtonPreferiti.setVisibility(View.INVISIBLE);
                if(countDownTimer != null){
                    countDownTimer.cancel();
                }
            }else{
                if(countDownTimer!=null) {
                    countDownTimer.cancel();
                    countDownTimer.start();
                }
            }

            progress_bar_schermata_asta_inversa.setVisibility(View.GONE);
            setAllClickable(relativeLayoutSchermataAstaInversa, true);
        } else {
            // Gestisci il caso in cui non ci siano dati recuperati
            Log.d("Errore", "Impossibile recuperare i dati dell'asta");
        }
    }

    public void setPrezzo(Integer prezzoNuovo){
        textViewPrezzoAttualeSchermataAstaInversa.setText(Integer.toString(prezzoNuovo));
        astaInversaDAO.verificaAttualeVincitore(email,id);
    }
    public void getPrezzoeCondizione(String prezzo_aggiornato, String condizione_aggiornata){
        if(condizione_aggiornata.equals("aperta")){
            textViewPrezzoAttualeSchermataAstaInversa.setText(prezzo_aggiornato);
            // Resetta il timer per farlo ripartire da 10 secondi
            Log.d("timer ", "cancello e faccio iniziare");
            if(countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer.start();
            }
        }else{
            Toast.makeText(this, "Accidenti! L'asta si è conclusa", Toast.LENGTH_SHORT).show();
            bottoneOffertaSchermataAstaInversa.setVisibility(View.INVISIBLE);
            if(countDownTimer != null){
                countDownTimer.cancel();
            }
        }

    }

    public  void verificaPreferiti(boolean check){
        if (check){
            imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
        }else {
            imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);

        }
    }
    public void handleOffertaAttualeTua(Boolean result){
        if(result){
            text_view_tua_offerta_attuale_asta_inversa.setVisibility(View.VISIBLE);
        }else{
            text_view_tua_offerta_attuale_asta_inversa.setVisibility(View.INVISIBLE);
        }
    }
    public void handleInserimento(Boolean result){
        if(result){
            imageButtonPreferiti.setImageDrawable(drawableCuorePieno);
        }else{
            Toast.makeText(this, "Errore nell'inserimento.", Toast.LENGTH_SHORT).show();
        }
        setAllClickable(relativeLayoutSchermataAstaInversa,true);
    }
    public void handleEliminazione(Boolean result){
        if(result){
            imageButtonPreferiti.setImageDrawable(drawableCuoreVuoto);
        }else{
            Toast.makeText(this, "Errore nella rimozione.", Toast.LENGTH_SHORT).show();
        }
        setAllClickable(relativeLayoutSchermataAstaInversa,true);
    }

}


