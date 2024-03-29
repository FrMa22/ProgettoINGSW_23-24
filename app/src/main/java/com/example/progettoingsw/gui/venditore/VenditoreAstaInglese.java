package com.example.progettoingsw.gui.venditore;
import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.progettoingsw.DAO.AstaIngleseDAO;
import com.example.progettoingsw.DAO.AstaInversaDAO;
import com.example.progettoingsw.DAO.LoginDAO;
import com.example.progettoingsw.PopUpAggiungiCategorieAsta;
import com.example.progettoingsw.R;
import com.example.progettoingsw.classe_da_estendere.GestoreComuniImplementazioni;
import com.example.progettoingsw.controllers_package.Controller;
import com.example.progettoingsw.controllers_package.InsertAsta;
import com.example.progettoingsw.gui.acquirente.AcquirenteAstaInversa;
import com.example.progettoingsw.gui.acquirente.AcquirenteFragmentHome;
import com.example.progettoingsw.gui.acquirente.AcquirenteMainActivity;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class VenditoreAstaInglese extends GestoreComuniImplementazioni {
    AppCompatButton bottoneConferma;
    ImageButton bottoneBack;
    ImageButton bottone_info;
    EditText nome;
    private int idAsta;
    EditText descrizione;
    EditText baseAsta;
    EditText intervalloAsta;
    EditText rialzoAsta;
    Uri uriImmagine;

    Controller controller;
    private byte [] img;
    ImageView immagineProdotto;
    ImageButton bottoneInserisciImmagine;
    private ArrayList<String> listaCategorieScelte;
    ActivityResultLauncher<Intent> resultLauncher;
    private String selectedDateString;
    private String selectedHourString;
    private MaterialButton bottoneCategorieAstaInglese;
    private ProgressBar progressBarVenditoreAstaInglese;
    private RelativeLayout relativeLayoutAstaInglese;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venditore_asta_inglese);
         controller = new Controller();
        progressBarVenditoreAstaInglese = findViewById(R.id.progressBarVenditoreAstaInglese);
        relativeLayoutAstaInglese = findViewById(R.id.relativeLayoutAstaInglese);
        AstaIngleseDAO astaIngleseDao = new AstaIngleseDAO(VenditoreAstaInglese.this);
        listaCategorieScelte = new ArrayList<>();
        baseAsta=findViewById(R.id.editTextBaseAstaAstaInglese);
        intervalloAsta=findViewById(R.id.editTextTimerDecrementoAstaInglese);
        rialzoAsta=findViewById(R.id.editTextSogliaRialzoAstaInglese);

        nome = findViewById(R.id.editTextNomeBeneCreaAstaInglese);
        descrizione=findViewById(R.id.editTextDescrizioneCreaAstaInglese);
        email = getIntent().getStringExtra("email");
        img=null;

        bottoneConferma =  findViewById(R.id.bottoneConfermaAstaInglese);
        bottoneBack =  findViewById(R.id.bottoneBackAstaInglese);
        bottone_info = findViewById(R.id.button_info_asta_inglese_venditore);

        immagineProdotto= findViewById(R.id.imageViewCreaAstaInglese);
        bottoneInserisciImmagine = findViewById(R.id.imageButtonInserisciImmagineCreaAstaInglese);
        bottoneInserisciImmagine.setOnClickListener(view ->prelevaImmagine());//significa che chiama il metodo prelevaImmagine

        bottoneCategorieAstaInglese = findViewById(R.id.bottoneCategorieAstaInglese);
        bottoneCategorieAstaInglese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpAggiungiCategorieAsta popUpAggiungiCategorieAsta = new PopUpAggiungiCategorieAsta(VenditoreAstaInglese.this, VenditoreAstaInglese.this,listaCategorieScelte);
                popUpAggiungiCategorieAsta.show();
            }
        });

        bottone_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showPopup();
            }
        });

        registraRisultati();

        bottoneBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(VenditoreAstaInglese.this, AcquirenteMainActivity.class);//test del login
                intent.putExtra("email", email);
                intent.putExtra("tipoUtente", "venditore");
                startActivity(intent);
//        AppCompatActivity activity = (AppCompatActivity) VenditoreAstaInglese.this;
//        Fragment fragment = new AcquirenteFragmentHome(email, "venditore");
//        ((AcquirenteMainActivity) activity).navigateToFragmentAndSelectIcon(fragment);
            }
        });

        bottoneConferma.setOnClickListener(v -> {
            //
            //Toast.makeText(AstaInglese.this,"Connessione esistente",Toast.LENGTH_SHORT).show();
            String nomeProdotto = nome.getText().toString().trim();
            String descrizioneProdotto = descrizione.getText().toString().trim();
            String base = baseAsta.getText().toString().trim();
            String intervallo = intervalloAsta.getText().toString().trim();
            String rialzo=rialzoAsta.getText().toString().trim();

            if(!nomeProdotto.isEmpty() && !base.isEmpty() && !intervallo.isEmpty() && !rialzo.isEmpty()) {
                // Chiamata al metodo per creare l'asta nel database
                progressBarVenditoreAstaInglese.setVisibility(View.VISIBLE);
                setAllClickable(relativeLayoutAstaInglese,false);
                astaIngleseDao.openConnection();
                astaIngleseDao.creaAstaInglese(base, intervallo, rialzo, nomeProdotto, descrizioneProdotto, email, img);
            }
            else{
                if(nomeProdotto.isEmpty()){
                    Toast.makeText(this, "Si prega di inserire un nome.", Toast.LENGTH_SHORT).show();
                }else if(base.isEmpty()){
                    Toast.makeText(this, "Si prega di inserire un prezzo base.", Toast.LENGTH_SHORT).show();
                }else if(intervallo.isEmpty()){
                    Toast.makeText(this, "Si prega di inserire un intervallo per le offerte.", Toast.LENGTH_SHORT).show();
                }else if(rialzo.isEmpty()) {
                    Toast.makeText(this, "Si prega di inserire un valore minimo di rialzo.", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }



    private void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cos'è un asta all'inglese?"); // Puoi impostare un titolo per il popup
        builder.setMessage(" Un’asta all’inglese è caratterizzata da una base d’asta iniziale pubblica, specificata dal venditore al momento della " +
                "creazione dell’asta, da un intervallo di tempo fisso per presentare nuove offerte (di default 1 " +
                "ora), e da una soglia di rialzo (di default 10€).\n Gli acquirenti possono presentare un’offerta per " +
                "il prezzo corrente.\n Quando viene presentata un’offerta, il timer per la presentazione di nuove " +
                "offerte viene resettato. Quando il timer raggiunge lo zero senza che siano presentate nuove " +
                "offerte, l’ultima offerta si aggiudica il bene/servizio in vendita, e il venditore e gli acquirenti che " +
                "hanno partecipato all’asta visualizzano una notifica. "); // Il testo da mostrare nel popup

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Azione da eseguire quando si preme il pulsante OK
                dialog.dismiss(); // Chiudi il popup
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void prelevaImmagine(){
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            resultLauncher.launch(intent);
        } else {
            Toast.makeText(this, "Nessuna app disponibile per gestire la selezione delle immagini", Toast.LENGTH_SHORT).show();
        }
    }
    private void registraRisultati() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            uriImmagine = result.getData().getData();
                            displayImage(uriImmagine);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Nessuna Immagine selezionata", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }


    // Metodo per comprimere un'immagine Bitmap e convertirla in un array di byte
    private byte[] compressAndConvertToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream); // Compressione JPEG con qualità del 50%
        return outputStream.toByteArray();
    }

    private void displayImage(Uri uri) {
        try {
            InputStream inputStream = this.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Ridimensiona l'immagine per adattarla alla dimensione desiderata
            int targetWidth = 500; // Imposta la larghezza desiderata
            int targetHeight = (int) (bitmap.getHeight() * (targetWidth / (double) bitmap.getWidth())); // Calcola l'altezza in base al rapporto
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);

            // Comprimi l'immagine
            byte[] compressedImageBytes = compressAndConvertToByteArray(resizedBitmap);

            // Imposta l'immagine ridimensionata nella ImageView
            immagineProdotto.setImageBitmap(resizedBitmap);

            // Salva i byte compressi per l'invio
            img = compressedImageBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handlePopUp(ArrayList<String> switchTexts){
        this.listaCategorieScelte = switchTexts;
        // Iterare attraverso gli elementi di switchTexts e stamparli nel log
        for (String categoria : switchTexts) {
            Log.d("PopUpHandler", "Categoria: " + categoria);
        }
    }
    public void handleID(int id){
        this.idAsta = id;
        AstaIngleseDAO astaIngleseDAO = new AstaIngleseDAO(VenditoreAstaInglese.this);
        if(!listaCategorieScelte.isEmpty()){
            astaIngleseDAO.openConnection();
            Log.d("id recuperato è Venditore inglese : " , " id: " + idAsta);
            InsertAsta asta = new InsertAsta(idAsta,listaCategorieScelte);
            astaIngleseDAO.inserisciCategorieAstaInglese(asta);
            astaIngleseDAO.closeConnection();

        }else{
            astaIngleseDAO.closeConnection();
        }
        Intent intent = new Intent(VenditoreAstaInglese.this, AcquirenteMainActivity.class);//test del login
        intent.putExtra("email", email);
        intent.putExtra("tipoUtente", "venditore");
        startActivity(intent);
        progressBarVenditoreAstaInglese.setVisibility(View.INVISIBLE);
        setAllClickable(relativeLayoutAstaInglese,true);
        Toast.makeText(this, "Asta creata con successo!", Toast.LENGTH_SHORT).show();
//        AppCompatActivity activity = (AppCompatActivity) VenditoreAstaInglese.this;
//        Fragment fragment = new AcquirenteFragmentHome(email, "venditore");
//        ((AcquirenteMainActivity) activity).navigateToFragmentAndSelectIcon(fragment);
    }


}
